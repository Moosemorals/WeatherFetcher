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
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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
 *
 * @author Osric Wilkinson <osric@fluffypeople.com>
 */
public class Fetcher {

    private static final Logger log = LoggerFactory.getLogger(Fetcher.class);
    private static final String ENDPOINT = "https://api.worldweatheronline.com/free/v2/weather.ashx";

    /**
     * Required link back to the API
     */
    public static final String BOILERPLATE = "Powered by <a href=\"http://www.worldweatheronline.com/\" title=\"Free Weather API\" target=\"_blank\">World Weather Online</a>";

    private final String apiKey;
    private final int num_of_days;
    private final DateTime date;
    private final boolean forecast;
    private final boolean current;
    private final int timePeriod;

    private Fetcher(String apiKey, int num_of_days, DateTime date, boolean forecast, boolean current, int timePeriod) {
        this.apiKey = apiKey;
        this.num_of_days = num_of_days;
        this.date = date;
        this.forecast = forecast;
        this.current = current;
        this.timePeriod = timePeriod;
    }

    /**
     * Fetch weather report using parameters set in the builder. </p>
     *
     * Location parameter here can be the name of a city, a UK or Canadian post
     * code, a US zip code, an ipv4 address (in dotted quad notation) or
     * Latitude,Longitude (decimal degrees, separated by comma).</p>
     *
     * Throws IOException for problems. Potential problems include network
     * issues (can't connect to the API) or API issues (Missing/invalid API key)
     *
     * @param location String location of weather report
     * @return WeatherReport containg current data
     * @throws IOException if there are network problems, or the api request is
     */
    public FetchResult fetch(String location) throws IOException {
        return fetch(location, HttpClients.createDefault());
    }

    /**
     * Fetch weather report using parameters set in the builder. </p>
     *
     * Location parameter here can be the name of a city, a UK or Canadian post
     * code, a US zip code, an ipv4 address (in dotted quad notation) or
     * Latitude,Longitude (decimal degrees, separated by comma).</p>
     *
     * HttpClient is a httpClient that has all ready been configured and is
     * ready to use.</p>
     *
     * Throws IOException for problems. Potential problems include network
     * issues (can't connect to the API) or API issues (Missing/invalid API key)
     *
     * @param location String location of weather report
     * @param httpClient HttpClient to use to fetch report
     * @return WeatherReport containg current data
     * @throws IOException if there are network problems, or the api request is
     */
    public FetchResult fetch(String location, CloseableHttpClient httpClient) throws IOException {

        Map<String, String> param = new HashMap<>();

        param.put("q", location);
        param.put("key", "[HIDDEN]");
        param.put("extra", "utcDateTime");
        param.put("num_of_days", Integer.toString(num_of_days));
        param.put("tp", Integer.toString(timePeriod));
        param.put("format", "xml");
        param.put("showlocaltime", "yes");
        if (date != null) {
            param.put("date", DateTimeFormat.forPattern("yyyy-MM-dd").print(date));
        }
        if (!forecast) {
            param.put("fx", "no");
        }
        if (!current) {
            param.put("cc", "no");
        }

        String loggableTarget = assembleURL(ENDPOINT, flattenMap(param));

        param.put("key", apiKey);
        String target = assembleURL(ENDPOINT, flattenMap(param));

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
     * Turn a Map&lt;String, String&gt; to an array of Strings.
     *
     * @param map Map&lt;String, String&gt; to convert
     * @return String[] containg ordered key/value pairs
     */
    static String[] flattenMap(Map<String, String> map) {
        String[] result = new String[map.size() * 2];

        int i = 0;
        for (String key : map.keySet()) {
            result[i] = key;
            result[i + 1] = map.get(key);
            i += 2;
        }
        return result;
    }

    /**
     * Add a query string to a URL.
     *
     * @param base String Base url. Should be well formed, but that isn't
     * checked.
     * @param parameters String... list of key/value pairs.
     * @return String URL ready to pass to httpClient to fetch
     * @throws IllegalArgumentException if the parameters don't come in pairs
     * @throws UnsupportedEncodingException if Java doesn't know UTF-8
     */
    static String assembleURL(String base, String... parameters) throws UnsupportedEncodingException {
        if (parameters == null || parameters.length == 0) {
            return base;
        } else if (parameters.length % 2 != 0) {
            throw new IllegalArgumentException("Parameters must come in (name, value) pairs");
        }

        StringBuilder result = new StringBuilder();
        result.append(base).append("?");
        for (int i = 0; i < parameters.length; i += 2) {
            if (i >= 2) {
                result.append("&");
            }
            result.append(URLEncoder.encode(parameters[i], "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(parameters[i + 1], "UTF-8"));
        }
        return result.toString();
    }

    /**
     * Build a Fetcher
     */
    public static class Builder {

        private String apiKey;
        private int num_of_days = 3;
        private DateTime date = null;
        private boolean forecast = true;
        private boolean current = true;
        private int timePeriod = 3;

        public Builder() {
            super();
        }

        /**
         * Required api key from worldweatheronline.com for their V2 API.
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
         * Number of days to fetch. Optional, default 3. Apparently can be as
         * high as 15, but I don't think the free api supports that many.
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
         * Date to fetch weather for. Optional, default today.
         *
         * @param date DateTime date to fetch weather for.
         * @return this Builder for chaining
         */
        public Builder setDate(DateTime date) {
            this.date = date;
            return this;
        }

        /**
         * Fetch weather forecast. Optional, default true. False means don't
         * fetch any forecast information.
         *
         * @param forecast boolean true to fetch forecast, false otherwise.
         * @return this Builder for chaining
         */
        public Builder setForecast(boolean forecast) {
            this.forecast = forecast;
            return this;
        }

        /**
         * Fetch current conditions. Optional, default true. False means don't
         * fetch current weather conditions for your location.
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
         * Frequency of forecasts, in hours. Optional, default 3. Can be 3, 6,
         * 12 or 24. Other values are ignored by the API (and revert to 3)
         *
         * @param timePeriod int hours between forecasts
         * @return this Builder for chaining
         */
        public Builder setFrequency(int timePeriod) {
            this.timePeriod = timePeriod;
            return this;
        }

        public Fetcher build() {
            return new Fetcher(apiKey, num_of_days, date, forecast, current, timePeriod);
        }
    }

}
