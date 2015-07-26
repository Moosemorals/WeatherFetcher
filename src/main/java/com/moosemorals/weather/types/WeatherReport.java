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

import java.util.ArrayList;
import java.util.List;
import org.joda.time.DateTime;

/**
 * Weather for a time and location.
 *
 * @author Osric Wilkinson osric@fluffypeople.com
 */
public class WeatherReport {

    private final Location location;
    private final Current current;
    private final List<Forecast> forecast;
    private final DateTime when;
    private final ErrorReport error;

    public WeatherReport(Location location, Current current, List<Forecast> forecast, DateTime when, ErrorReport error) {
        this.location = location;
        this.current = current;
        this.forecast = forecast;
        this.when = when;
        this.error = error;
    }

    public boolean isSuccess() {
        return error == null;
    }

    public ErrorReport getError() {
        return error;
    }

    public Location getLocation() {
        return location;
    }

    public Current getCurrent() {
        return current;
    }

    public List<Forecast> getForecasts() {
        return forecast;
    }

    public DateTime getLocalTime() {
        return when;
    }

    public static class Builder {

        private Location location;
        private Current current;
        private final List<Forecast> forecast;
        private DateTime when;
        private ErrorReport error;

        public Builder() {
            forecast = new ArrayList<>();
        }

        public Builder setLocation(Location location) {
            this.location = location;
            return this;
        }

        public Builder setCurrent(Current current) {
            this.current = current;
            return this;
        }

        public Builder addForecast(Forecast forecast) {
            this.forecast.add(forecast);
            return this;
        }

        public Builder setWhen(DateTime when) {
            this.when = when;
            return this;
        }

        public Builder setError(ErrorReport error) {
            this.error = error;
            return this;
        }

        public WeatherReport build() {
            if (error == null) {
                return new WeatherReport(location, current, forecast, when, null);
            } else {
                return new WeatherReport(null, null, null, null, error);
            }
        }
    }

}
