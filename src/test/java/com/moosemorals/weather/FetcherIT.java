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

import com.moosemorals.weather.types.FetchResult;
import com.moosemorals.weather.types.WeatherReport;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 *
 * @author Osric Wilkinson <osric@fluffypeople.com>
 */
public class FetcherIT {

    private final Logger log = LoggerFactory.getLogger(FetcherIT.class);

    public FetcherIT() {
    }

    private CloseableHttpClient httpClient;
    private String apiKey;
    private int requestsPerSecond;
    private int requestsPerDay;

    @BeforeClass
    public void setUpClass() throws Exception {
        httpClient = HttpClients.createDefault();
        apiKey = loadAPIKey(getClass().getResourceAsStream("/apiKey"));
    }

    @AfterMethod
    public void afterMethod() throws Exception {
        log.info("Currently have {} requests left this second, {} requests left this day", requestsPerSecond, requestsPerDay);
        if (requestsPerSecond <= 1) {
            log.info("Run out of requests, sleeping for a second");
            Thread.sleep(1000);
        }
        if (requestsPerDay <= 1) {
            log.error("Run out of requests for today, giving up");
            assertTrue(false, "No requests availible");
        }
    }

    @AfterClass
    public void tearDownClass() throws Exception {
        httpClient.close();
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void fetchNoParams() throws Exception {
        FetchResult result = new Fetcher.Builder().build().fetch();
    }

    @Test
    public void basicFetch() throws Exception {
        // Fetching for Newcastle Upon Tyne, UK
        FetchResult result = new Fetcher.Builder()
                .setApiKey(apiKey)
                .setLocation("NE6")
                .build()
                .fetch();

        requestsPerDay = result.getRequestsPerDay();
        requestsPerSecond = result.getRequestsPerSecond();

        WeatherReport report = result.getReport();
        assertNotNull(report, "Report should not be null");
        assertNotNull(report.getCurrent(), "Current conditions should not be null");
        assertEquals(report.getDailyForecasts().size(), 3, "Should have 3 daily forecasts");
        assertEquals(report.getHourlyForecasts().size(), 8 * 3, "Should have 8 reports/day for 3 days");

        // Newcastle is in the Europe/London timezone.
        // Turns out the server clock isn't good enough to test this.
        // Ah, well.
        // assertEquals(report.getLocalTime(), new DateTime(DateTimeZone.forID("Europe/London")).withMillisOfSecond(0).withSecondOfMinute(0));
    }

    @Test
    public void fetchNothing() throws Exception {
        // Fetching for Newcastle Upon Tyne, UK
        FetchResult result = new Fetcher.Builder()
                .setApiKey(apiKey)
                .setForecast(false)
                .setCurrent(false)
                .setLocation("NE6")
                .build()
                .fetch();

        requestsPerDay = result.getRequestsPerDay();
        requestsPerSecond = result.getRequestsPerSecond();

        WeatherReport report = result.getReport();
        assertNotNull(report, "Report should not be null");
        assertEquals(report.getCurrent(), null, "Current should be null");
        assertTrue(report.getDailyForecasts().isEmpty(), "Should have no daily forecast");
        assertTrue(report.getHourlyForecasts().isEmpty(), "Should have no hourly forecast");
    }

    @Test
    public void fetchOneDay() throws Exception {
        // Fetching for Newcastle Upon Tyne, UK
        FetchResult result = new Fetcher.Builder()
                .setApiKey(apiKey)
                .setNumOfDays(1)
                .setLocation("NE6")
                .build()
                .fetch();

        requestsPerDay = result.getRequestsPerDay();
        requestsPerSecond = result.getRequestsPerSecond();

        WeatherReport report = result.getReport();
        assertNotNull(report, "Report should not be null");
        assertNotNull(report.getCurrent(), "Current conditions should not be null");
        assertEquals(report.getDailyForecasts().size(), 1, "Should have 1 daily forecasts");
        assertEquals(report.getHourlyForecasts().size(), 8 * 1, "Should have 8 reports/day for 1 day");

    }

    @Test
    public void fetch6Hours() throws Exception {
        // Fetching for Newcastle Upon Tyne, UK
        FetchResult result = new Fetcher.Builder()
                .setApiKey(apiKey)
                .setFrequency(6)
                .setLocation("NE6")
                .build()
                .fetch();

        requestsPerDay = result.getRequestsPerDay();
        requestsPerSecond = result.getRequestsPerSecond();

        WeatherReport report = result.getReport();
        assertNotNull(report, "Report should not be null");
        assertNotNull(report.getCurrent(), "Current conditions should not be null");
        assertEquals(report.getDailyForecasts().size(), 3, "Should have 3 daily forecasts");
        assertEquals(report.getHourlyForecasts().size(), 4 * 3, "Should have 4 reports/day for 3 days");

    }

    private static String loadAPIKey(InputStream in) throws Exception {
        if (in == null) {
            throw new Exception("API key not found. Copy it to /src/test/resource/apiKey and try again.");
        }
        try (BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"))) {
            return br.readLine();
        }
    }
}
