/*
 * The MIT License
 *
 * Copyright 2015 Osric Wilkinson <osric@fluffypeople.com>.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.moosemorals.weather;

import com.moosemorals.weather.types.ErrorReport;
import com.moosemorals.weather.types.FetchResult;
import com.moosemorals.weather.xml.ErrorParser;
import com.moosemorals.weather.xml.WeatherParser;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import javax.xml.stream.XMLStreamException;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Fetch weather data from the backend, using a provided HttpClient or building
 * one if needed. </p>
 *
 * Build and set options on a Fetcher using {@link Fetcher.Builder}, and then
 * call @{#fetch(String) fetch} to get the result.
 *
 * @author Osric Wilkinson <osric@fluffypeople.com>
 */
public class Fetcher {

    private static final Logger log = LoggerFactory.getLogger(Fetcher.class);
    private static final String ENDPOINT = "https://api.worldweatheronline.com/free/v2/weather.ashx";

    /**
     * Required link back to the API. Code that uses the api must display a link
     * to the provider. This is how they recommend you format it.
     */
    public static final String BOILERPLATE = "Powered by <a href=\"http://www.worldweatheronline.com/\" title=\"Free Weather API\" target=\"_blank\">World Weather Online</a>";

    private final String apiKey;
    private final String location;
    private final String language;
    private final CloseableHttpClient httpClient;
    private final int num_of_days;
    private final DateTime date;
    private final boolean forecast;
    private final boolean current;
    private final int timePeriod;

    private Fetcher(String apiKey, String location, String language, CloseableHttpClient httpClient, int num_of_days, DateTime date, boolean forecast, boolean current, int timePeriod) {
        this.apiKey = apiKey;
        this.location = location;
        this.language = language;
        this.httpClient = httpClient;
        this.num_of_days = num_of_days;
        this.date = date;
        this.forecast = forecast;
        this.current = current;
        this.timePeriod = timePeriod;
    }

    /**
     * Fetch weather report using parameters set in the builder. </p>
     *
     * Throws IOException for problems. Potential problems include network
     * issues (can't connect to the API) or API issues (Missing/invalid API key)
     *
     * @return WeatherReport containing current data
     * @throws IOException if there are network problems, or the api request is
     */
    public FetchResult fetch() throws IOException {
        // Check for required parameters
        if (apiKey == null) {
            throw new NullPointerException("API key not set");
        }

        if (location == null) {
            throw new NullPointerException("Location not set");
        }

        Map<String, String> param = new HashMap<>();

        param.put("q", location);
        param.put("extra", "utcDateTime");
        param.put("num_of_days", Integer.toString(num_of_days));
        param.put("tp", Integer.toString(timePeriod));
        param.put("format", "xml");
        param.put("showlocaltime", "yes");
        if (date != null) {
            param.put("date", DateTimeFormat.forPattern("yyyy-MM-dd").print(date));
        }
        if (language != null) {
            param.put("lang", language);
        }
        if (!forecast) {
            param.put("fx", "no");
        }
        if (!current) {
            param.put("cc", "no");
        }

        // For logging, build the request with a hidden api key
        param.put("key", "HIDDEN");
        String loggableTarget = Util.assembleURL(Fetcher.ENDPOINT, Util.flattenMap(param));

        // For live use, build the request with the real api.
        param.put("key", apiKey);
        String target = Util.assembleURL(Fetcher.ENDPOINT, Util.flattenMap(param));

        HttpGet request = new HttpGet(target);

        log.debug("Fetching URL {}", loggableTarget);
        try (CloseableHttpResponse response = httpClient.execute(request)) {
            StatusLine status = response.getStatusLine();
            log.debug("Response {}", status);
            HttpEntity body = response.getEntity();

            int requestsPerSecond = getIntFromHeader(response, "x-apiaxleproxy-qps-left");
            int requestsPerDay = getIntFromHeader(response, "x-apiaxleproxy-qpd-left");

            try {
                if (status.getStatusCode() == 200) {
                    return new FetchResult(new WeatherParser().parse(dumpInputStream(body.getContent())), requestsPerSecond, requestsPerDay);
                } else {
                    ErrorReport error = new ErrorParser().parse(body.getContent());
                    throw new IOException("Could not fetch result from [" + loggableTarget + "]: " + error.getType() + ": " + error.getMessage());
                }
            } finally {
                EntityUtils.consume(body);
            }

        } catch (XMLStreamException ex) {
            throw new IOException("Could not parse result from [" + loggableTarget + "]: " + ex.getMessage(), ex);
        }
    }

    private static int getIntFromHeader(CloseableHttpResponse response, String headerName) {
        Header firstHeader = response.getFirstHeader(headerName);
        if (firstHeader != null) {
            return Integer.parseInt(firstHeader.getValue(), 10);
        } else {
            return -1;
        }
    }

    private static InputStream dumpInputStream(InputStream in) throws IOException {
        if (!log.isDebugEnabled()) {
            return in;
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));

        StringBuilder data = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            data.append(line).append(System.lineSeparator());
        }

        log.debug("Recieved File\n------\n{}\n------", data.toString());

        return new ByteArrayInputStream(data.toString().getBytes("UTF-8"));
    }

    /**
     * Build a Fetcher and set its options.
     */
    public static class Builder {

        private String apiKey;
        private String location;
        private String language;
        private CloseableHttpClient httpClient = HttpClients.createDefault();
        private int num_of_days = 3;
        private DateTime date = null;
        private boolean forecast = true;
        private boolean current = true;
        private int timePeriod = 3;

        public Builder() {
            super();
        }

        /**
         * Api key from worldweatheronline.com for their V2 API. Required, no
         * default. </p>
         *
         * You can register for a key at
         * <a href="https://developer.worldweatheronline.com/auth/register">https://developer.worldweatheronline.com/auth/register</a>
         *
         * @param apiKey String api key to use
         * @return this Builder for chaining
         * @throws IllegalArgumentException for a null apiKey
         */
        public Builder setApiKey(String apiKey) {
            if (apiKey == null) {
                throw new IllegalArgumentException("API key must not be null");
            }
            this.apiKey = apiKey;
            return this;
        }

        /**
         * Location to fetch for. Required, no default.</p>
         *
         * May be a UK or Canadian postcode, US zip code, name of a city, IPv4
         * address (in dotted quad notation) or Latitude, Longitude pair
         * (decimal degrees, comma separated)
         *
         * @param location String name of the location. The API will work out
         * the type from context.
         * @return this Builder for chaining
         */
        public Builder setLocation(String location) {
            this.location = location;
            return this;
        }

        /**
         * Language for human readable text. Optional, default en. </p>
         *
         * <a href="http://www.worldweatheronline.com/api/docs/multilingual.aspx">List
         * of availible languages</a>.
         *
         * @param language String ISO language code
         * @return this Builder for chaining.
         */
        public Builder setLanguage(String language) {
            this.language = language;
            return this;
        }

        /**
         * HttpClient to use to fetch. Optional, default
         * HttpClients.getDefault(). </p>
         *
         * Provide your own client to manage e.g. cookies, certificates or
         * caching.
         *
         * @param httpClient CloseableHttpClient to use to connect to the API
         * @return this Builder for chaining.
         */
        public Builder setHttpClient(CloseableHttpClient httpClient) {
            this.httpClient = httpClient;
            return this;
        }

        /**
         * Number of days to fetch. Optional, default 3. </p>
         *
         * Apparently can be as high as 15, but I don't think the free api
         * supports that many.
         *
         * @param num_of_days int number of days to fetch
         * @return this Builder for chaining
         */
        public Builder setNumOfDays(int num_of_days) {
            if (num_of_days < 0) {
                throw new IllegalArgumentException("Number of days must be positive");
            }
            this.num_of_days = num_of_days;
            return this;
        }

        /**
         * Date to fetch weather for. Optional, default today. </p>
         *
         * @param date DateTime date to fetch weather for.
         * @return this Builder for chaining
         */
        public Builder setDate(DateTime date) {
            this.date = date;
            return this;
        }

        /**
         * Fetch weather forecast. Optional, default true. </p>
         *
         * False means don't fetch any forecast information.
         *
         * @param forecast boolean true to fetch forecast, false otherwise.
         * @return this Builder for chaining
         */
        public Builder setForecast(boolean forecast) {
            this.forecast = forecast;
            return this;
        }

        /**
         * Fetch current conditions. Optional, default true. </p>
         *
         * False means don't fetch current weather conditions for your location.
         *
         * @param current boolean true to fetch current conditions, false
         * otherwise.
         * @return this Builder for chaining
         */
        public Builder setCurrent(boolean current) {
            this.current = current;
            return this;
        }

        /**
         * Frequency of forecasts, in hours. Optional, default 3. </p>
         *
         * Can be 3, 6, 12 or 24. Other values are ignored by the API (and
         * revert to 3)
         *
         * @param timePeriod int hours between forecasts
         * @return this Builder for chaining
         */
        public Builder setFrequency(int timePeriod) {
            this.timePeriod = timePeriod;
            return this;
        }

        public Fetcher build() {
            return new Fetcher(apiKey, location, language, httpClient, num_of_days, date, forecast, current, timePeriod);
        }
    }

}
