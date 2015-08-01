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
import com.moosemorals.weather.types.Query;
import java.util.ArrayList;
import java.util.List;
import org.joda.time.DateTime;

/**
 * Weather for a time and location.
 *
 * @author Osric Wilkinson osric@fluffypeople.com
 */
public class WeatherReport implements Report {

    private final Query location;
    private final Current current;
    private final List<DailyForecast> forecastDays;
    private final List<HourlyForecast> forecastHours;
    private final DateTime when;
    private final String language;

    private WeatherReport(Query location, Current current, List<DailyForecast> forecastDays, List<HourlyForecast> forecastHours, DateTime when, String language) {
        this.location = location;
        this.current = current;
        this.forecastDays = forecastDays;
        this.forecastHours = forecastHours;
        this.when = when;
        this.language = language;
    }

    public Query getLocation() {
        return location;
    }

    public Current getCurrent() {
        return current;
    }

    public List<DailyForecast> getDailyForecasts() {
        return forecastDays;
    }

    public List<HourlyForecast> getHourlyForecasts() {
        return forecastHours;
    }

    public DateTime getDate() {
        return when;
    }

    public String getLanguage() {
        return language;
    }

    /**
     * Build a new WeatherReport. Not really useful to end users.
     */
    public static class Builder {

        private Query location;
        private Current current;
        private final List<DailyForecast> forecastDays;
        private final List<HourlyForecast> forecastHours;
        private DateTime when;
        private String language = "en";

        public Builder() {
            forecastDays = new ArrayList<>();
            forecastHours = new ArrayList<>();
        }

        public Builder setLocation(Query location) {
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

            return new WeatherReport(location, current, forecastDays, forecastHours, when, language);

        }
    }

}
