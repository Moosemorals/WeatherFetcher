/*
 * The MIT License
 *
 * Copyright 2015 osric.
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

import org.joda.time.DateTime;

/**
 * Daily weather forecast for a location.
 *
 * @author osric
 */
public class DailyForecast {

    private final DateTime date;
    private final int maxTempC;
    private final int maxTempF;
    private final int minTempC;
    private final int minTempF;
    private final int uvIndex;
    private final Astronomy astronomy;

    private DailyForecast(DateTime date, int maxTempC, int maxTempF, int minTempC, int minTempF, int uvIndex, Astronomy astronomy) {
        this.date = date;
        this.maxTempC = maxTempC;
        this.maxTempF = maxTempF;
        this.minTempC = minTempC;
        this.minTempF = minTempF;
        this.uvIndex = uvIndex;
        this.astronomy = astronomy;
    }

    /**
     * Day the forecast is for, with offset time zone.
     *
     * @return
     */
    public DateTime getDate() {
        return date;
    }

    /**
     * Predicted maximum daytime air temperature, &deg;C.
     *
     * @return int predicted max temperature, &deg;C
     */
    public int getMaxTempC() {
        return maxTempC;
    }

    /**
     * Predicted maximum daytime air temperature, &deg;F.
     *
     * @return int predicted max temperature, &deg;F
     */
    public int getMaxTempF() {
        return maxTempF;
    }

    /**
     * Predicted minimum nighttime air temperature, &deg;C.
     *
     * @return int predicted min temperature, &deg;C
     */
    public int getMinTempC() {
        return minTempC;
    }

    /**
     * Predicted minimum nighttime air temperature, &deg;C.
     *
     * @return int predicted min temperature, &deg;C
     */
    public int getMinTempF() {
        return minTempF;
    }

    /**
     * Predicted UV Index.
     *
     * @return int predicted UV index
     */
    public int getUvIndex() {
        return uvIndex;
    }

    /**
     * Sunrise/set, Moonrise/set.
     *
     * @return Astronomy data object holding sunrise/set, moonrise/set
     */
    public Astronomy getAstronomy() {
        return astronomy;
    }

    /**
     * Build a DailyForecast. Not really useful to end users.
     */
    public static class Builder {

        private DateTime date;
        private int maxTempC;
        private int maxTempF;
        private int minTempC;
        private int minTempF;
        private int uvIndex;
        private Astronomy astronomy;

        public Builder() {
        }

        public Builder setDate(DateTime date) {
            this.date = date;
            return this;
        }

        public Builder setMaxTempC(int maxTempC) {
            this.maxTempC = maxTempC;
            return this;
        }

        public Builder setMaxTempF(int maxTempF) {
            this.maxTempF = maxTempF;
            return this;
        }

        public Builder setMinTempC(int minTempC) {
            this.minTempC = minTempC;
            return this;
        }

        public Builder setMinTempF(int minTempF) {
            this.minTempF = minTempF;
            return this;
        }

        public Builder setUvIndex(int uvIndex) {
            this.uvIndex = uvIndex;
            return this;
        }

        public Builder setAstronomy(Astronomy astronomy) {
            this.astronomy = astronomy;
            return this;
        }

        public DailyForecast build() {
            return new DailyForecast(date, maxTempC, maxTempF, minTempC, minTempF, uvIndex, astronomy);
        }
    }

}
