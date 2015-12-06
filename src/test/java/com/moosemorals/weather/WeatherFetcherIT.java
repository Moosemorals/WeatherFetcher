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
import com.moosemorals.weather.reports.WeatherReport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
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
public class WeatherFetcherIT {

    private final Logger log = LoggerFactory.getLogger(WeatherFetcherIT.class);

    private String apiKey;
    private int requestsPerSecond;
    private int requestsPerDay;

    @BeforeClass
    public void setUpClass() throws Exception {
        apiKey = TestUtils.loadAPIKey(getClass().getResourceAsStream("/apiKey"));
    }

    @AfterMethod
    public void afterMethod() throws Exception {
        log.info("Currently have {} requests left this second, {} requests left this day", requestsPerSecond, requestsPerDay);
        if (requestsPerSecond <= 1) {
            log.info("Run out of requests, sleeping for two seconds");
            Thread.sleep(2000);
        }
        if (requestsPerDay <= 1) {
            log.error("Run out of requests for today, giving up");
            assertTrue(false, "No requests availible");
        }
    }

    @AfterClass
    public void tearDownClass() throws Exception {
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void fetchNoParams() throws Exception {
        FetchResult result = new WeatherFetcher.Builder().build().fetch();
    }

    @Test
    public void basicFetch() throws Exception {
        // Fetching for Newcastle Upon Tyne, UK
        FetchResult result = new WeatherFetcher.Builder()
                .setApiKey(apiKey)
                .setLocation("NE6")
                .build()
                .fetch();

        requestsPerDay = result.getRequestsPerDay();
        requestsPerSecond = result.getRequestsPerSecond();

        assertTrue(result.isSuccess());
        assertEquals(result.getError(), null);

        WeatherReport report = result.getWeather();
        assertNotNull(report, "Report should not be null");
        assertNotNull(report.getCurrent(), "Current conditions should not be null");
        assertEquals(report.getLanguage(), "en", "Language should be en (English)");
        assertNotNull(report.getLocation(), "Location should not be null");
        assertNotNull(report.getQuery(), "Query should not be null");
        assertNotNull(report.getDate(), "Date should not be null");
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
        FetchResult result = new WeatherFetcher.Builder()
                .setApiKey(apiKey)
                .setForecast(false)
                .setCurrent(false)
                .setLocation("NE6")
                .build()
                .fetch();

        requestsPerDay = result.getRequestsPerDay();
        requestsPerSecond = result.getRequestsPerSecond();

        assertTrue(result.isSuccess());
        assertEquals(result.getError(), null);

        WeatherReport report = result.getWeather();
        assertNotNull(report, "Report should not be null");
        assertEquals(report.getCurrent(), null, "Current should be null");
        assertEquals(report.getLanguage(), "en", "Language should be en (English)");
        assertNotNull(report.getLocation(), "Location should not be null");
        assertNotNull(report.getQuery(), "Query should not be null");
        assertNotNull(report.getDate(), "Date should not be null");

        assertTrue(report.getDailyForecasts().isEmpty(), "Should have no daily forecast");
        assertTrue(report.getHourlyForecasts().isEmpty(), "Should have no hourly forecast");
    }

    @Test
    public void fetchOneDay() throws Exception {
        // Fetching for Newcastle Upon Tyne, UK
        FetchResult result = new WeatherFetcher.Builder()
                .setApiKey(apiKey)
                .setNumOfDays(1)
                .setLocation("NE6")
                .build()
                .fetch();

        requestsPerDay = result.getRequestsPerDay();
        requestsPerSecond = result.getRequestsPerSecond();

        assertTrue(result.isSuccess());
        assertEquals(result.getError(), null);

        WeatherReport report = result.getWeather();
        assertNotNull(report, "Report should not be null");
        assertEquals(report.getLanguage(), "en", "Language should be en (English)");
        assertNotNull(report.getCurrent(), "Current conditions should not be null");
        assertNotNull(report.getLocation(), "Location should not be null");
        assertNotNull(report.getQuery(), "Query should not be null");
        assertNotNull(report.getDate(), "Date should not be null");

        assertEquals(report.getDailyForecasts().size(), 1, "Should have 1 daily forecasts");
        assertEquals(report.getHourlyForecasts().size(), 8 * 1, "Should have 8 reports/day for 1 day");

    }

    @Test
    public void fetch6Hours() throws Exception {
        // Fetching for Newcastle Upon Tyne, UK
        FetchResult result = new WeatherFetcher.Builder()
                .setApiKey(apiKey)
                .setFrequency(6)
                .setLocation("NE6")
                .build()
                .fetch();

        requestsPerDay = result.getRequestsPerDay();
        requestsPerSecond = result.getRequestsPerSecond();

        assertTrue(result.isSuccess());
        assertEquals(result.getError(), null);

        WeatherReport report = result.getWeather();
        assertNotNull(report, "Report should not be null");
        assertEquals(report.getLanguage(), "en", "Language should be en (English)");
        assertNotNull(report.getCurrent(), "Current conditions should not be null");
        assertNotNull(report.getLocation(), "Location should not be null");
        assertNotNull(report.getQuery(), "Query should not be null");
        assertNotNull(report.getDate(), "Date should not be null");

        assertEquals(report.getDailyForecasts().size(), 3, "Should have 3 daily forecasts");
        assertEquals(report.getHourlyForecasts().size(), 4 * 3, "Should have 4 reports/day for 3 days");
    }

    @Test
    public void fetchLanguage() throws Exception {
        // Fetching for Newcastle Upon Tyne, UK
        FetchResult result = new WeatherFetcher.Builder()
                .setApiKey(apiKey)
                .setForecast(false)
                .setCurrent(true)
                .setLocation("NE6")
                .setLanguage("uk")
                .build()
                .fetch();

        requestsPerDay = result.getRequestsPerDay();
        requestsPerSecond = result.getRequestsPerSecond();

        assertTrue(result.isSuccess());
        assertEquals(result.getError(), null);

        WeatherReport report = result.getWeather();
        assertNotNull(report, "Report should not be null");
        assertNotNull(report.getCurrent(), "Current should not be null");
        assertNotNull(report.getLocation(), "Location should not be null");
        assertNotNull(report.getQuery(), "Query should not be null");
        assertNotNull(report.getDate(), "Date should not be null");

        assertEquals(report.getLanguage(), "uk", "Language should be uk (Ukranian)");
        assertTrue(report.getDailyForecasts().isEmpty(), "Should have no daily forecast");
        assertTrue(report.getHourlyForecasts().isEmpty(), "Should have no hourly forecast");
    }

    @Test
    public void fetchError() throws Exception {
        String bogusKey = "XXXX-testing-XXXX";

        FetchResult result = new WeatherFetcher.Builder()
                .setApiKey(bogusKey)
                .setLocation("NE6")
                .build()
                .fetch();

        assertFalse(result.isSuccess());

        ErrorReport report = result.getError();

        assertNotNull(report);
        assertNotNull(report.getType());
        assertEquals(report.getType(), "KeyError");

        assertNotNull(report.getMessage());
        assertEquals(report.getMessage(), "'" + bogusKey + "' is not a valid key.");
    }
}
