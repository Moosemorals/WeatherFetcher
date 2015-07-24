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
 *
 * @author osric
 */
public class Current {

    private final LocalTime observationTime;
    private final int tempC;
    private final int tempF;
    private final int windspeedMPH;
    private final int windspeedKPH;
    private final int winddirDegree;
    private final String winddirName;
    private final int weatherCode;
    private final String weatherIconUrl;
    private final String weatherDesc;
    private final float precipMM;
    private final int humidity;
    private final int visibilityKm;
    private final int visibilityMiles;
    private final int pressureMb;
    private final int pressureInches;
    private final int cloudcover;

    private Current(LocalTime observationTime, int tempC, int tempF, int windspeedMPH, int windspeedKPH, int winddirDegree, String winddirName, int weatherCode, String weatherIconUrl, String weatherDesc, float precipMM, int humidity, int visibilityKm, int visibilityMiles, int pressureMb, int pressureInches, int cloudcover) {
        this.observationTime = observationTime;
        this.tempC = tempC;
        this.tempF = tempF;
        this.windspeedMPH = windspeedMPH;
        this.windspeedKPH = windspeedKPH;
        this.winddirDegree = winddirDegree;
        this.winddirName = winddirName;
        this.weatherCode = weatherCode;
        this.weatherIconUrl = weatherIconUrl;
        this.weatherDesc = weatherDesc;
        this.precipMM = precipMM;
        this.humidity = humidity;
        this.visibilityKm = visibilityKm;
        this.visibilityMiles = visibilityMiles;
        this.pressureMb = pressureMb;
        this.pressureInches = pressureInches;
        this.cloudcover = cloudcover;
    }

    public LocalTime getObservationTime() {
        return observationTime;
    }

    public int getTempC() {
        return tempC;
    }

    public int getTempF() {
        return tempF;
    }

    public int getWindspeedMPH() {
        return windspeedMPH;
    }

    public int getWindspeedKPH() {
        return windspeedKPH;
    }

    public int getWinddirDegree() {
        return winddirDegree;
    }

    public String getWinddirName() {
        return winddirName;
    }

    public int getWeatherCode() {
        return weatherCode;
    }

    public String getWeatherIconUrl() {
        return weatherIconUrl;
    }

    public String getWeatherDesc() {
        return weatherDesc;
    }

    public float getPrecipMM() {
        return precipMM;
    }

    public int getHumidity() {
        return humidity;
    }

    public int getVisibilityKm() {
        return visibilityKm;
    }

    public int getVisibilityMiles() {
        return visibilityMiles;
    }

    public int getPressureMb() {
        return pressureMb;
    }

    public int getPressureInches() {
        return pressureInches;
    }

    public int getCloudcover() {
        return cloudcover;
    }

    public static class Builder {

        private LocalTime observationTime;
        private int tempC;
        private int tempF;
        private int windspeedMPH;
        private int windspeedKPH;
        private int winddirDegree;
        private String winddirName;
        private int weatherCode;
        private String weatherIconUrl;
        private String weatherDesc;
        private float precipMM;
        private int humidity;
        private int visibilityKm;
        private int visibilityMiles;
        private int pressureMb;
        private int pressureInches;
        private int cloudcover;

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

        public Builder setWindspeedMPH(int windspeedMPH) {
            this.windspeedMPH = windspeedMPH;
            return this;
        }

        public Builder setWindspeedKPH(int windspeedKPH) {
            this.windspeedKPH = windspeedKPH;
            return this;
        }

        public Builder setWinddirDegree(int winddirDegree) {
            this.winddirDegree = winddirDegree;
            return this;
        }

        public Builder setWinddirName(String winddirName) {
            this.winddirName = winddirName;
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

        public Builder setPrecipMM(float precipMM) {
            this.precipMM = precipMM;
            return this;
        }

        public Builder setHumidity(int humidity) {
            this.humidity = humidity;
            return this;
        }

        public Builder setVisibilityKm(int visibilityKm) {
            this.visibilityKm = visibilityKm;
            return this;
        }

        public Builder setVisibilityMiles(int visibilityMiles) {
            this.visibilityMiles = visibilityMiles;
            return this;
        }

        public Builder setPressureMb(int pressureMb) {
            this.pressureMb = pressureMb;
            return this;
        }

        public Builder setPressureInches(int pressureInches) {
            this.pressureInches = pressureInches;
            return this;
        }

        public Builder setCloudcover(int cloudcover) {
            this.cloudcover = cloudcover;
            return this;
        }

        public Current build() {
            return new Current(observationTime, tempC, tempF, windspeedMPH, windspeedKPH, winddirDegree, winddirName, weatherCode, weatherIconUrl, weatherDesc, precipMM, humidity, visibilityKm, visibilityMiles, pressureMb, pressureInches, cloudcover);
        }
    }

}
