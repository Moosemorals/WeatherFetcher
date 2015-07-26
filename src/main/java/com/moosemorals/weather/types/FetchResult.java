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
package com.moosemorals.weather.types;

/**
 * Result of fetching weather data.
 *
 * @author Osric Wilkinson <osric@fluffypeople.com>
 */
public class FetchResult {

    private final WeatherReport report;
    private final int requestsPerSecond;
    private final int requestsPerDay;

    /**
     * Construct new FetchResult. Not really useful to end users.
     *
     * @param report
     * @param requestsPerSecond
     * @param requestsPerDay
     */
    public FetchResult(WeatherReport report, int requestsPerSecond, int requestsPerDay) {
        this.report = report;
        this.requestsPerSecond = requestsPerSecond;
        this.requestsPerDay = requestsPerDay;
    }

    /**
     * Weather report.
     *
     * @return
     */
    public WeatherReport getReport() {
        return report;
    }

    /**
     * How many requests you have available this second. The API restricts users
     * to 5 requests per second, and reports the number available after each
     * request..
     *
     * @return int request left this second
     */
    public int getRequestsPerSecond() {
        return requestsPerSecond;
    }

    /**
     * How many requests you have available this day. The API restricts users to
     * 250 requests per day, and reports the number available after each
     * request..
     *
     * @return int request left this day
     */
    public int getRequestsPerDay() {
        return requestsPerDay;
    }

}
