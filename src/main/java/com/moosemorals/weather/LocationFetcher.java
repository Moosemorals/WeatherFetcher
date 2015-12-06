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

import com.moosemorals.weather.reports.ErrorReport;
import com.moosemorals.weather.reports.FetchResult;
import com.moosemorals.weather.reports.LocationReport;
import com.moosemorals.weather.reports.Report;
import com.moosemorals.weather.xml.ErrorParser;
import com.moosemorals.weather.xml.LocationParser;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import javax.xml.stream.XMLStreamException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Osric Wilkinson <osric@fluffypeople.com>
 */
public class LocationFetcher {

    private static final String ENDPOINT = "https://api.worldweatheronline.com/free/v2/search.ashx";

    private final static Logger log = LoggerFactory.getLogger(LocationFetcher.class);
    private final String apiKey;
    private final String query;

    private final int numResults;

    public LocationFetcher(String apiKey, String query, int numResults) {
        this.apiKey = apiKey;
        this.query = query;
        this.numResults = numResults;
    }

    public FetchResult fetch() throws IOException {
        // Check for required parameters
        if (apiKey == null) {
            throw new NullPointerException("API key not set");
        }

        if (query == null) {
            throw new NullPointerException("Query not set");
        }

        Map<String, String> param = new HashMap<>();

        param.put("q", query);
        param.put("timezone", "yes");
        param.put("format", "xml");
        param.put("num_of_results", Integer.toString(numResults));

        // For logging, build the request with a hidden api key
        param.put("key", "HIDDEN");
        String loggableTarget = Util.assembleURL(ENDPOINT, Util.flattenMap(param));

        // For live use, build the request with the real api.
        param.put("key", apiKey);
        URL target = new URL(Util.assembleURL(ENDPOINT, Util.flattenMap(param)));

        FetchResult.Builder resultBuilder = new FetchResult.Builder();

        log.debug("Fetching URL {}", loggableTarget);

        HttpURLConnection conn = (HttpURLConnection) target.openConnection();

        try {
            conn.connect();
            int status = conn.getResponseCode();
            log.debug("Response {}", status);

            resultBuilder.setRequestsPerSecond(Util.getIntFromHeader(conn, "x-apiaxleproxy-qps-left"));
            resultBuilder.setRequestsPerDay(Util.getIntFromHeader(conn, "x-apiaxleproxy-qpd-left"));

            if (status == 200) {

                Report report = new LocationParser().parse(conn.getInputStream());
                if (report instanceof LocationReport) {
                    resultBuilder.setLocation((LocationReport) report);
                } else {
                    resultBuilder.setError((ErrorReport) report);
                }

            } else {
                ErrorReport error = new ErrorParser().parse(conn.getErrorStream());
                resultBuilder.setError(error);
            }

        } catch (XMLStreamException ex) {
            resultBuilder.setError(new ErrorReport(ex));
        }

        return resultBuilder.build();
    }

    public static class Builder {

        private String apiKey;
        private String query;
        private int numResults;

        public Builder() {
            super();
        }

        public Builder setApiKey(String apiKey) {
            this.apiKey = apiKey;
            return this;
        }

        public Builder setQuery(String query) {
            this.query = query;
            return this;
        }

        public Builder setNumResults(int numResults) {
            this.numResults = numResults;
            return this;
        }

        public LocationFetcher build() {
            return new LocationFetcher(apiKey, query, numResults);
        }
    }

}
