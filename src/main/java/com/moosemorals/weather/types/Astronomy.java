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
 * Sunrise, Sunset, Moonrise, Moonset for a location/date pair.
 *
 * @author osric
 */
public class Astronomy {

    private final DateTime sunrise;
    private final DateTime sunset;
    private final DateTime moonrise;
    private final DateTime moonset;

    /**
     * Time of sunrise (with offset time zone).
     *
     * @return DateTime time of sunrise
     */
    public DateTime getSunrise() {
        return sunrise;
    }

    /**
     * Time of sunset (with offset time zone).
     *
     * @return DateTime time of sunset
     */
    public DateTime getSunset() {
        return sunset;
    }

    /**
     * Time of moonrise (with offset time zone). Will be null when the moon
     * doesn't rise on the given day.
     *
     * @return DateTime Time of moonrise, or null if there is no moonrise
     */
    public DateTime getMoonrise() {
        return moonrise;
    }

    /**
     * Time of moonset (with offset time zone). Will be null when the moon
     * doesn't set on the given day.
     *
     * @return DateTime Time of moonrise, or null if there is no moonset
     */
    public DateTime getMoonset() {
        return moonset;
    }

    private Astronomy(DateTime sunrise, DateTime sunset, DateTime moonrise, DateTime moonset) {
        this.sunrise = sunrise;
        this.sunset = sunset;
        this.moonrise = moonrise;
        this.moonset = moonset;
    }

    /**
     * Build an Astronomy data class. Not really useful to end users.
     */
    public static class Builder {

        private DateTime sunrise;
        private DateTime sunset;
        private DateTime moonrise;
        private DateTime moonset;

        public Builder() {
            super();
        }

        public Builder setSunrise(DateTime sunrise) {
            this.sunrise = sunrise;
            return this;
        }

        public Builder setSunset(DateTime sunset) {
            this.sunset = sunset;
            return this;
        }

        public Builder setMoonrise(DateTime moonrise) {
            this.moonrise = moonrise;
            return this;
        }

        public Builder setMoonset(DateTime moonset) {
            this.moonset = moonset;
            return this;
        }

        public Astronomy build() {
            return new Astronomy(sunrise, sunset, moonrise, moonset);
        }
    }

}
