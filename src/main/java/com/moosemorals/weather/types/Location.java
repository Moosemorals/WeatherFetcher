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

import org.joda.time.DateTimeZone;

/**
 * Information about locations.
 *
 * @author Osric Wilkinson <osric@fluffypeople.com>
 */
public class Location {

    private final String name;
    private final String region;
    private final String country;
    private final long population;
    private final DateTimeZone timezone;
    private final float latitude;
    private final float longitude;

    private Location(String name, String region, String country, long population, DateTimeZone timezone, float latitude, float longitude) {
        this.name = name;
        this.region = region;
        this.country = country;
        this.population = population;
        this.timezone = timezone;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * Name of the place.
     *
     * @return String name of the place
     */
    public String getName() {
        return name;
    }

    /**
     * Region containing the place.
     *
     * @return String name of the region containing the place.
     */
    public String getRegion() {
        return region;
    }

    /**
     * Country containing the place.
     *
     * @return String name of the country containing the place.
     */
    public String getCountry() {
        return country;
    }

    /**
     * Population of the place. Not sure how current this info is. </p>
     *
     * Zero is used to show no data.
     *
     * @return long population of the place.
     */
    public long getPopulation() {
        return population;
    }

    /**
     * Offset from UTC of the place, <em>at the time of the query</em>. </p>
     *
     * @return DateTimeZone holding offset from UTC in hours
     */
    public DateTimeZone getTimezone() {
        return timezone;
    }

    /**
     * Latitude of the place, in decimal degrees. North is positive.
     *
     * @return float latitude of the place
     */
    public float getLatitude() {
        return latitude;
    }

    /**
     * Longitude of the place, in decimal degrees. West is positive.
     *
     * @return float longitude of the place.
     */
    public float getLongitude() {
        return longitude;
    }

    public static class Builder {

        private String name;
        private String region;
        private String country;
        private long population;
        private DateTimeZone timezone;
        private float latitude;
        private float longitude;

        public Builder() {
            super();
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setRegion(String region) {
            this.region = region;
            return this;
        }

        public Builder setCountry(String country) {
            this.country = country;
            return this;
        }

        public Builder setPopulation(long population) {
            this.population = population;
            return this;
        }

        public Builder setTimezone(DateTimeZone timezone) {
            this.timezone = timezone;
            return this;
        }

        public Builder setLatitude(float latitude) {
            this.latitude = latitude;
            return this;
        }

        public Builder setLongitude(float longitude) {
            this.longitude = longitude;
            return this;
        }

        public Location build() {
            return new Location(name, region, country, population, timezone, latitude, longitude);
        }
    }

}
