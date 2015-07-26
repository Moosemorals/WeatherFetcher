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

    private Current current;
    private DateTime when;
    private final List<Forecast> forecast;
    private ErrorReport error;

    public WeatherReport() {
        forecast = new ArrayList<>();
    }

    public ErrorReport getError() {
        return error;
    }

    public void setError(ErrorReport error) {
        this.error = error;
    }

    public boolean isSuccess() {
        return error == null;
    }

    public Current getCurrent() {
        return current;
    }

    public void setCurrent(Current c) {
        this.current = c;
    }

    public void addForecast(Forecast f) {
        forecast.add(f);
    }

    public List<Forecast> getForecasts() {
        return forecast;
    }

    public DateTime getLocalTime() {
        return when;
    }

    public void setLocalTime(DateTime when) {
        this.when = when;
    }

}
