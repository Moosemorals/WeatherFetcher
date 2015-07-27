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

import org.joda.time.LocalTime;

/**
 * Current weather at the requested location. </p>
 *
 * While the class claims current, observations may be several hours old.
 * Empirically, {@code observationTime} seems to be more-or-less current time,
 * rather than the time the observation was made.
 *
 * @author osric
 */
public class Current {

    private final LocalTime observationTime;
    private final int tempC;
    private final int tempF;
    private final int weatherCode;
    private final String weatherIconUrl;
    private final String weatherDesc;
    private final int windspeedMiles;
    private final int windspeedKmph;
    private final int winddirDegree;
    private final String winddir16Point;
    private final float precipMM;
    private final int humidity;
    private final int visibility;
    private final int pressure;
    private final int cloudcover;
    private final int feelsLikeC;
    private final int feelsLikeF;

    private Current(LocalTime observationTime, int tempC, int tempF, int weatherCode, String weatherIconUrl, String weatherDesc, int windspeedMiles, int windspeedKmph, int winddirDegree, String winddir16Point, float precipMM, int humidity, int visibility, int pressure, int cloudcover, int feelsLikeC, int feelsLikeF) {
        this.observationTime = observationTime;
        this.tempC = tempC;
        this.tempF = tempF;
        this.weatherCode = weatherCode;
        this.weatherIconUrl = weatherIconUrl;
        this.weatherDesc = weatherDesc;
        this.windspeedMiles = windspeedMiles;
        this.windspeedKmph = windspeedKmph;
        this.winddirDegree = winddirDegree;
        this.winddir16Point = winddir16Point;
        this.precipMM = precipMM;
        this.humidity = humidity;
        this.visibility = visibility;
        this.pressure = pressure;
        this.cloudcover = cloudcover;
        this.feelsLikeC = feelsLikeC;
        this.feelsLikeF = feelsLikeF;
    }

    /**
     * Time of the observation, UTC.
     *
     * @return int Time of the observation, UTC.
     */
    public LocalTime getObservationTime() {
        return observationTime;
    }

    /**
     * Current air temperature, &deg;C.
     *
     * @return int air temperature, &deg;C
     */
    public int getTempC() {
        return tempC;
    }

    /**
     * Current air temperature, &deg;F.
     *
     * @return int air temperature, &deg;F
     */
    public int getTempF() {
        return tempF;
    }

    /**
     * (Arbitrary) code to describe the current weather. A list of code <->
     * human readable strings is available from
     * <a href="http://www.worldweatheronline.com/feed/wwoConditionCodes.xml">http://www.worldweatheronline.com/feed/wwoConditionCodes.xml</a>
     *
     * @return int weather code
     */
    public int getWeatherCode() {
        return weatherCode;
    }

    /**
     * URL to an icon for the current weather.
     *
     * @return String icon URL
     */
    public String getWeatherIconUrl() {
        return weatherIconUrl;
    }

    /**
     * Human readable string to describe the current leather.
     *
     * @return String current weather
     */
    public String getWeatherDesc() {
        return weatherDesc;
    }

    /**
     * Current wind speed in miles per hour.
     *
     * @return int wind speed in miles per hour
     */
    public int getWindspeedMiles() {
        return windspeedMiles;
    }

    /**
     * Current wind speed in kilometres per hour.
     *
     * @return int wind speed in kilometres per hour
     */
    public int getWindspeedKmph() {
        return windspeedKmph;
    }

    /**
     * Current wind direction in degrees from North.
     *
     * @return int wind direction in degrees
     */
    public int getWinddirDegree() {
        return winddirDegree;
    }

    /**
     * Current wind direction as compass point.
     *
     * @return String wind direction as compass point
     */
    public String getWinddir16Point() {
        return winddir16Point;
    }

    /**
     * Current precipitation in mm. </p>
     *
     * I suspect that actually, this is how much precipitation since the last
     * observation.
     *
     * @return int precipitation in mm
     */
    public float getPrecipMM() {
        return precipMM;
    }

    /**
     * Current relative humidity as an integer percent (between 0 and 100).
     *
     * @return int relative humidity %
     */
    public int getHumidity() {
        return humidity;
    }

    /**
     * Current visibility in kilometres (between 0 and 10). </p>
     *
     * Visibility of 10km should be taken as unlimited visibility.
     *
     * @return int visibility in kilometres
     */
    public int getVisibility() {
        return visibility;
    }

    /**
     * Current atmospheric pressure in millibar.
     *
     * @return int pressure in millibar
     */
    public int getPressure() {
        return pressure;
    }

    /**
     * Current cloud cover as an integer percent (between 0 and 100)
     *
     * @return int cloud cover %
     */
    public int getCloudcover() {
        return cloudcover;
    }

    /**
     * Current "Feels Like" or apparent temperature &deg;C. Calculated(using an
     * unknown algorithm that is apparently based on actual temperature,
     * relative humidity, and wind speed.
     *
     * @return int "Feels Like" temperature, &deg;C
     */
    public int getFeelsLikeC() {
        return feelsLikeC;
    }

    /**
     * Current "Feels Like" or apparent temperature &deg;F. Calculated(using an
     * unknown algorithm that is apparently based on actual temperature,
     * relative humidity, and wind speed.
     *
     * @return int "Feels Like" temperature, &deg;F
     */
    public int getFeelsLikeF() {
        return feelsLikeF;
    }

    /**
     * Build a new Weather Report. Not really useful to end users.
     */
    public static class Builder {

        private LocalTime observationTime;
        private int tempC;
        private int tempF;
        private int weatherCode;
        private String weatherIconUrl;
        private String weatherDesc;
        private int windspeedMiles;
        private int windspeedKmph;
        private int winddirDegree;
        private String winddir16Point;
        private float precipMM;
        private int humidity;
        private int visibility;
        private int pressure;
        private int cloudcover;
        private int feelsLikeC;
        private int feelsLikeF;

        public Builder() {
            super();
        }

        public Builder setObservationTime(LocalTime observationTime) {
            this.observationTime = observationTime;
            return this;
        }

        public Builder setTempC(int tempC) {
            this.tempC = tempC;
            return this;
        }

        public Builder setTempF(int tempF) {
            this.tempF = tempF;
            return this;
        }

        public Builder setWeatherCode(int weatherCode) {
            this.weatherCode = weatherCode;
            return this;
        }

        public Builder setWeatherIconUrl(String weatherIconUrl) {
            this.weatherIconUrl = weatherIconUrl;
            return this;
        }

        public Builder setWeatherDesc(String weatherDesc) {
            this.weatherDesc = weatherDesc;
            return this;
        }

        public Builder setWindspeedMiles(int windspeedMiles) {
            this.windspeedMiles = windspeedMiles;
            return this;
        }

        public Builder setWindspeedKmph(int windspeedKmph) {
            this.windspeedKmph = windspeedKmph;
            return this;
        }

        public Builder setWinddirDegree(int winddirDegree) {
            this.winddirDegree = winddirDegree;
            return this;
        }

        public Builder setWinddir16Point(String winddir16Point) {
            this.winddir16Point = winddir16Point;
            return this;
        }

        public Builder setPrecipMM(float precipMM) {
            this.precipMM = precipMM;
            return this;
        }

        public Builder setHumidity(int humidity) {
            this.humidity = humidity;
            return this;
        }

        public Builder setVisibility(int visibility) {
            this.visibility = visibility;
            return this;
        }

        public Builder setPressure(int pressure) {
            this.pressure = pressure;
            return this;
        }

        public Builder setCloudcover(int cloudcover) {
            this.cloudcover = cloudcover;
            return this;
        }

        public Builder setFeelsLikeC(int feelsLikeC) {
            this.feelsLikeC = feelsLikeC;
            return this;
        }

        public Builder setFeelsLikeF(int feelsLikeF) {
            this.feelsLikeF = feelsLikeF;
            return this;
        }

        public Current build() {
            return new Current(observationTime, tempC, tempF, weatherCode, weatherIconUrl, weatherDesc, windspeedMiles, windspeedKmph, winddirDegree, winddir16Point, precipMM, humidity, visibility, pressure, cloudcover, feelsLikeC, feelsLikeF);
        }
    }

}
