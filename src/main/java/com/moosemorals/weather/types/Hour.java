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

/**
 *
 * @author osric
 */
public class Hour {

    private final long time;
    private final int tempC;
    private final int tempF;
    private final int windspeedMiles;
    private final int windspeedKPH;
    private final int windspeedKnots;
    private final int windspeedMPS;
    private final int winddirDegree;
    private final String winddir16Point;
    private final int weatherCode;
    private final String weatherDesc;
    private final String weatherIconUrl;
    private final float precipMM;
    private final float precipInches;
    private final int humidity;
    private final int visibility;
    private final int VisiblityMiles;
    private final int pressureMb;
    private final int pressureInches;
    private final int cloudcover;
    private final int heatIndexC;
    private final int heatIndexF;
    private final int dewPointC;
    private final int dewPointF;
    private final int windChillC;
    private final int windChillF;
    private final int windGustMiles;
    private final int windGustKmph;
    private final int feelsLikeC;
    private final int feelsLikeF;
    private final int chanceOfRain;
    private final int chanceOfWindy;
    private final int chanceOfOvercast;
    private final int chanceOfSunny;
    private final int chanceOfFrost;
    private final int chanceOfFog;
    private final int chanceofSnow;
    private final int chanceOfThunder;
    private final int chanceOfRemdry;
    private final int chanceOfHightemp;

    public long getTime() {
        return time;
    }

    public int getTempC() {
        return tempC;
    }

    public int getTempF() {
        return tempF;
    }

    public int getWindspeedMiles() {
        return windspeedMiles;
    }

    public int getWindspeedKPH() {
        return windspeedKPH;
    }

    public int getWindspeedKnots() {
        return windspeedKnots;
    }

    public int getWindspeedMPS() {
        return windspeedMPS;
    }

    public int getWinddirDegree() {
        return winddirDegree;
    }

    public String getWinddir16Point() {
        return winddir16Point;
    }

    public int getWeatherCode() {
        return weatherCode;
    }

    public String getWeatherDesc() {
        return weatherDesc;
    }

    public String getWeatherIconUrl() {
        return weatherIconUrl;
    }

    public float getPrecipMM() {
        return precipMM;
    }

    public float getPrecipInches() {
        return precipInches;
    }

    public int getHumidity() {
        return humidity;
    }

    public int getVisibility() {
        return visibility;
    }

    public int getVisiblityMiles() {
        return VisiblityMiles;
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

    public int getHeatIndexC() {
        return heatIndexC;
    }

    public int getHeatIndexF() {
        return heatIndexF;
    }

    public int getDewPointC() {
        return dewPointC;
    }

    public int getDewPointF() {
        return dewPointF;
    }

    public int getWindChillC() {
        return windChillC;
    }

    public int getWindChillF() {
        return windChillF;
    }

    public int getWindGustMiles() {
        return windGustMiles;
    }

    public int getWindGustKmph() {
        return windGustKmph;
    }

    public int getFeelsLikeC() {
        return feelsLikeC;
    }

    public int getFeelsLikeF() {
        return feelsLikeF;
    }

    public int getChanceOfRain() {
        return chanceOfRain;
    }

    public int getChanceOfWindy() {
        return chanceOfWindy;
    }

    public int getChanceOfOvercast() {
        return chanceOfOvercast;
    }

    public int getChanceOfSunny() {
        return chanceOfSunny;
    }

    public int getChanceOfFrost() {
        return chanceOfFrost;
    }

    public int getChanceOfFog() {
        return chanceOfFog;
    }

    public int getChanceofSnow() {
        return chanceofSnow;
    }

    public int getChanceOfThunder() {
        return chanceOfThunder;
    }

    public int getChanceOfRemdry() {
        return chanceOfRemdry;
    }

    public int getChanceOfHightemp() {
        return chanceOfHightemp;
    }

    protected Hour(long time, int tempC, int tempF, int windspeedMiles, int windspeedKPH, int windspeedKnots, int windspeedMPS, int winddirDegree, String winddir16Point, int weatherCode, String weatherDesc, String weatherIconUrl, float precipMM, float precipInches, int humidity, int visibility, int VisiblityMiles, int pressureMb, int pressureInches, int cloudcover, int heatIndexC, int heatIndexF, int dewPointC, int dewPointF, int windChillC, int windChillF, int windGustMiles, int windGustKmph, int feelsLikeC, int feelsLikeF, int chanceOfRain, int chanceOfWindy, int chanceOfOvercast, int chanceOfSunny, int chanceOfFrost, int chanceOfFog, int chanceofSnow, int chanceOfThunder, int chanceOfRemdry, int chanceOfHightemp) {
        this.time = time;
        this.tempC = tempC;
        this.tempF = tempF;
        this.windspeedMiles = windspeedMiles;
        this.windspeedKPH = windspeedKPH;
        this.windspeedKnots = windspeedKnots;
        this.windspeedMPS = windspeedMPS;
        this.winddirDegree = winddirDegree;
        this.winddir16Point = winddir16Point;
        this.weatherCode = weatherCode;
        this.weatherDesc = weatherDesc;
        this.weatherIconUrl = weatherIconUrl;
        this.precipMM = precipMM;
        this.precipInches = precipInches;
        this.humidity = humidity;
        this.visibility = visibility;
        this.VisiblityMiles = VisiblityMiles;
        this.pressureMb = pressureMb;
        this.pressureInches = pressureInches;
        this.cloudcover = cloudcover;
        this.heatIndexC = heatIndexC;
        this.heatIndexF = heatIndexF;
        this.dewPointC = dewPointC;
        this.dewPointF = dewPointF;
        this.windChillC = windChillC;
        this.windChillF = windChillF;
        this.windGustMiles = windGustMiles;
        this.windGustKmph = windGustKmph;
        this.feelsLikeC = feelsLikeC;
        this.feelsLikeF = feelsLikeF;
        this.chanceOfRain = chanceOfRain;
        this.chanceOfWindy = chanceOfWindy;
        this.chanceOfOvercast = chanceOfOvercast;
        this.chanceOfSunny = chanceOfSunny;
        this.chanceOfFrost = chanceOfFrost;
        this.chanceOfFog = chanceOfFog;
        this.chanceofSnow = chanceofSnow;
        this.chanceOfThunder = chanceOfThunder;
        this.chanceOfRemdry = chanceOfRemdry;
        this.chanceOfHightemp = chanceOfHightemp;
    }

    public static class Builder {

        private long time;
        private int tempC;
        private int tempF;
        private int windspeedMiles;
        private int windspeedKPH;
        private int windspeedKnots;
        private int windspeedMPS;
        private int winddirDegree;
        private String winddir16Point;
        private int weatherCode;
        private String weatherDesc;
        private String weatherIconUrl;
        private float precipMM;
        private float precipInches;
        private int humidity;
        private int visibility;
        private int VisiblityMiles;
        private int pressureMb;
        private int pressureInches;
        private int cloudcover;
        private int heatIndexC;
        private int heatIndexF;
        private int dewPointC;
        private int dewPointF;
        private int windChillC;
        private int windChillF;
        private int windGustMiles;
        private int windGustKmph;
        private int feelsLikeC;
        private int feelsLikeF;
        private int chanceOfRain;
        private int chanceOfWindy;
        private int chanceOfOvercast;
        private int chanceOfSunny;
        private int chanceOfFrost;
        private int chanceOfFog;
        private int chanceofSnow;
        private int chanceOfThunder;
        private int chanceOfRemdry;
        private int chanceOfHightemp;

        public Builder() {
            super();
        }

        public Builder setTime(long time) {
            this.time = time;
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

        public Builder setWindspeedMiles(int windspeedMiles) {
            this.windspeedMiles = windspeedMiles;
            return this;
        }

        public Builder setWindspeedKPH(int windspeedKPH) {
            this.windspeedKPH = windspeedKPH;
            return this;
        }

        public Builder setWindspeedKnots(int windspeedKnots) {
            this.windspeedKnots = windspeedKnots;
            return this;
        }

        public Builder setWindspeedMPS(int windspeedMPS) {
            this.windspeedMPS = windspeedMPS;
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

        public Builder setWeatherCode(int weatherCode) {
            this.weatherCode = weatherCode;
            return this;
        }

        public Builder setWeatherDesc(String weatherDesc) {
            this.weatherDesc = weatherDesc;
            return this;
        }

        public Builder setWeatherIconUrl(String weatherIconUrl) {
            this.weatherIconUrl = weatherIconUrl;
            return this;
        }

        public Builder setPrecipMM(float precipMM) {
            this.precipMM = precipMM;
            return this;
        }

        public Builder setPrecipInches(float precipInches) {
            this.precipInches = precipInches;
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

        public Builder setVisiblityMiles(int VisiblityMiles) {
            this.VisiblityMiles = VisiblityMiles;
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

        public Builder setHeatIndexC(int heatIndexC) {
            this.heatIndexC = heatIndexC;
            return this;
        }

        public Builder setHeatIndexF(int heatIndexF) {
            this.heatIndexF = heatIndexF;
            return this;
        }

        public Builder setDewPointC(int dewPointC) {
            this.dewPointC = dewPointC;
            return this;
        }

        public Builder setDewPointF(int dewPointF) {
            this.dewPointF = dewPointF;
            return this;
        }

        public Builder setWindChillC(int windChillC) {
            this.windChillC = windChillC;
            return this;
        }

        public Builder setWindChillF(int windChillF) {
            this.windChillF = windChillF;
            return this;
        }

        public Builder setWindGustMiles(int windGustMiles) {
            this.windGustMiles = windGustMiles;
            return this;
        }

        public Builder setWindGustKmph(int windGustKmph) {
            this.windGustKmph = windGustKmph;
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

        public Builder setChanceOfRain(int chanceOfRain) {
            this.chanceOfRain = chanceOfRain;
            return this;
        }

        public Builder setChanceOfWindy(int chanceOfWindy) {
            this.chanceOfWindy = chanceOfWindy;
            return this;
        }

        public Builder setChanceOfOvercast(int chanceOfOvercast) {
            this.chanceOfOvercast = chanceOfOvercast;
            return this;
        }

        public Builder setChanceOfSunny(int chanceOfSunny) {
            this.chanceOfSunny = chanceOfSunny;
            return this;
        }

        public Builder setChanceOfFrost(int chanceOfFrost) {
            this.chanceOfFrost = chanceOfFrost;
            return this;
        }

        public Builder setChanceOfFog(int chanceOfFog) {
            this.chanceOfFog = chanceOfFog;
            return this;
        }

        public Builder setChanceofSnow(int chanceofSnow) {
            this.chanceofSnow = chanceofSnow;
            return this;
        }

        public Builder setChanceOfThunder(int chanceOfThunder) {
            this.chanceOfThunder = chanceOfThunder;
            return this;
        }

        public Builder setChanceOfRemdry(int chanceOfRemdry) {
            this.chanceOfRemdry = chanceOfRemdry;
            return this;
        }

        public Builder setChanceOfHightemp(int chanceOfHightemp) {
            this.chanceOfHightemp = chanceOfHightemp;
            return this;
        }

        public Hour build() {
            return new Hour(time, tempC, tempF, windspeedMiles, windspeedKPH, windspeedKnots, windspeedMPS, winddirDegree, winddir16Point, weatherCode, weatherDesc, weatherIconUrl, precipMM, precipInches, humidity, visibility, VisiblityMiles, pressureMb, pressureInches, cloudcover, heatIndexC, heatIndexF, dewPointC, dewPointF, windChillC, windChillF, windGustMiles, windGustKmph, feelsLikeC, feelsLikeF, chanceOfRain, chanceOfWindy, chanceOfOvercast, chanceOfSunny, chanceOfFrost, chanceOfFog, chanceofSnow, chanceOfThunder, chanceOfRemdry, chanceOfHightemp);
        }
    }

}
