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

import com.moosemorals.weather.reports.FetchResult;
import com.moosemorals.weather.reports.LocationReport;
import com.moosemorals.weather.types.Location;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
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
public class LocationFetcherIT {

    private final Logger log = LoggerFactory.getLogger(WeatherFetcherIT.class);

    private CloseableHttpClient httpClient;
    private String apiKey;
    private int requestsPerSecond;
    private int requestsPerDay;

    @Test
    public void basics() throws Exception {
        FetchResult result = new LocationFetcher.Builder()
                .setApiKey(apiKey)
                .setHttpClient(httpClient)
                .setQuery("NE6")
                .build()
                .fetch();

        assertNotNull(result, "Result should not be null");

        requestsPerDay = result.getRequestsPerDay();
        requestsPerSecond = result.getRequestsPerSecond();

        assertEquals(result.getError(), null, "Error should be null");
        assertEquals(result.getWeather(), null, "Weather should be null");

        LocationReport report = result.getLocation();
        assertNotNull(report, "Report should not be null");

        assertEquals(report.getLocations().size(), 10);

        Location location = report.getLocations().get(0);

        assertEquals(location.getName(), "Byker");
        assertEquals(location.getRegion(), "Tyne and Wear");
        assertEquals(location.getCountry(), "United Kingdom");
        assertEquals(location.getPopulation(), 0);
        assertEquals(location.getLatitude(), 54.974, 0.00001);
        assertEquals(location.getLongitude(), -1.572, 0.00001);

        // This one changes with local time. Poop.
        DateTimeZone London = DateTimeZone.forOffsetMillis(DateTimeZone.forID("Europe/London").getOffset(new DateTime()));

        assertEquals(location.getTimezone(), London);
    }

    @BeforeClass
    public void setUpClass() throws Exception {
        httpClient = HttpClients.createDefault();
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
        httpClient.close();
    }

}
