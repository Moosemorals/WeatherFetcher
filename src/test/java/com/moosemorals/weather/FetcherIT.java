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

import com.moosemorals.weather.types.WeatherReport;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 *
 * @author Osric Wilkinson <osric@fluffypeople.com>
 */
public class FetcherIT {

    public FetcherIT() {
    }

    private CloseableHttpClient httpClient;
    private String apiKey;

    @BeforeClass
    public void setUpClass() throws Exception {
        httpClient = HttpClients.createDefault();
        apiKey = loadAPIKey(getClass().getResourceAsStream("/apiKey"));
    }

    @Test
    public void apiKey() throws Exception {
        assertNotNull(apiKey);
    }

    @Test(dependsOnMethods = {"apiKey"})
    public void basicFetch() throws Exception {
        WeatherReport report = new Fetcher.Builder().setApiKey(apiKey).build().fetch("NE6");

        assertNotNull(report);
        assertNotNull(report.getCurrent());
        assertTrue(report.getForecasts().size() > 0);

        System.out.println(report.getLocalTime());
    }

    @AfterClass
    public void tearDownClass() throws Exception {
        httpClient.close();
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
