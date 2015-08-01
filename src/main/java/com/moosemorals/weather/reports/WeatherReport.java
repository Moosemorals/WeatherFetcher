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
package com.moosemorals.weather.reports;

import com.moosemorals.weather.types.Current;
import com.moosemorals.weather.types.DailyForecast;
import com.moosemorals.weather.types.HourlyForecast;
import com.moosemorals.weather.types.Location;
import com.moosemorals.weather.types.Query;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.joda.time.DateTime;

/**
 * Weather for a time and location.
 *
 * @author Osric Wilkinson osric@fluffypeople.com
 */
public class WeatherReport implements Report {

    private final Query query;
    private final Location location;
    private final Current current;
    private final List<DailyForecast> forecastDays;
    private final List<HourlyForecast> forecastHours;
    private final DateTime when;
    private final String language;

    private WeatherReport(Query query, Location location, Current current, List<DailyForecast> forecastDays, List<HourlyForecast> forecastHours, DateTime when, String language) {
        this.query = query;
        this.location = location;
        this.current = current;
        this.forecastDays = forecastDays;
        this.forecastHours = forecastHours;
        this.when = when;
        this.language = language;
    }

    /**
     * The search terms used, as reported by the API.
     *
     * @return Query showing search terms used
     */
    public Query getQuery() {
        return query;
    }

    /**
     * Where the weather report is for.
     *
     * @return Location for weather report
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Current weather. Will be null if not requested on the API call.
     *
     * @return Current current weather
     */
    public Current getCurrent() {
        return current;
    }

    /**
     * Daily forecasts.
     *
     * @return unmodifiable List of DailyForecast
     */
    public List<DailyForecast> getDailyForecasts() {
        return forecastDays;
    }

    public List<HourlyForecast> getHourlyForecasts() {
        return forecastHours;
    }

    /**
     * Local time of the location, based on server time.
     *
     * @return DateTime Current server time, with time zone offset to location
     */
    public DateTime getDate() {
        return when;
    }

    /**
     * Language of report.
     *
     * @return String ISO language code
     */
    public String getLanguage() {
        return language;
    }

    /**
     * Build a new WeatherReport. Not really useful to end users.
     */
    public static class Builder {

        private Query query;
        private Current current;
        private Location location;
        private final List<DailyForecast> forecastDays;
        private final List<HourlyForecast> forecastHours;
        private DateTime when;
        private String language = "en";

        public Builder() {
            forecastDays = new ArrayList<>();
            forecastHours = new ArrayList<>();
        }

        public Builder setQuery(Query query) {
            this.query = query;
            return this;
        }

        public Builder setLocation(Location location) {
            this.location = location;
            return this;
        }

        public Builder setCurrent(Current current) {
            this.current = current;
            return this;
        }

        public Builder addDailyForecast(DailyForecast forecast) {
            this.forecastDays.add(forecast);
            return this;
        }

        public Builder addHourlyForecast(HourlyForecast forecast) {
            this.forecastHours.add(forecast);
            return this;
        }

        public Builder setWhen(DateTime when) {
            this.when = when;
            return this;
        }

        public Builder setLanguage(String language) {
            this.language = language;
            return this;
        }

        public WeatherReport build() {
            return new WeatherReport(query, location, current, Collections.unmodifiableList(forecastDays), Collections.unmodifiableList(forecastHours), when, language);
        }
    }

}
