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
 * Predicted weather for an hour.
 *
 * @author osric
 */
public class HourlyForecast {

    private final DateTime time;
    private final int tempC;
    private final int tempF;
    private final int windspeedMiles;
    private final int windspeedKPH;
    private final int winddirDegree;
    private final String winddir16Point;
    private final int weatherCode;
    private final String weatherDesc;
    private final String weatherIconUrl;
    private final float precipMM;
    private final int humidity;
    private final int visibility;
    private final int pressureMb;
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

    /**
     * Hour that the forecast is for, UTC.
     *
     * @return DateTime target hour
     */
    public DateTime getTime() {
        return time;
    }

    /**
     * Predicted air temperature, &deg;C.
     *
     * @return int Air temperature, &deg;C
     */
    public int getTempC() {
        return tempC;
    }

    /**
     * Predicted air temperature, &deg;F.
     *
     * @return int Air temperature, &deg;F
     */
    public int getTempF() {
        return tempF;
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
        return windspeedKPH;
    }

    /**
     * Predicted wind direction in degrees from North.
     *
     * @return int wind direction in degrees
     */
    public int getWinddirDegree() {
        return winddirDegree;
    }

    /**
     * Predicted wind direction as compass point.
     *
     * @return String wind direction as compass point
     */
    public String getWinddir16Point() {
        return winddir16Point;
    }

    /**
     * (Arbitrary) code to describe the predicted weather. A list of code <->
     * human readable strings is available from
     * <a href="http://www.worldweatheronline.com/feed/wwoConditionCodes.xml">http://www.worldweatheronline.com/feed/wwoConditionCodes.xml</a>
     *
     * @return int weather code
     */
    public int getWeatherCode() {
        return weatherCode;
    }

    /**
     * Human readable string to describe the predicted leather.
     *
     * @return String current weather
     */
    public String getWeatherDesc() {
        return weatherDesc;
    }

    /**
     * URL to an icon for the predicted weather.
     *
     * @return String icon URL
     */
    public String getWeatherIconUrl() {
        return weatherIconUrl;
    }

    /**
     * Predicted precipitation in mm. </p>
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
     * Predicted relative humidity as an integer percent (between 0 and 100).
     *
     * @return int relative humidity %
     */
    public int getHumidity() {
        return humidity;
    }

    /**
     * Predicted visibility in kilometres (between 0 and 10). </p>
     *
     * Visibility of 10km should be taken as unlimited visibility.
     *
     * @return int visibility in kilometres
     */
    public int getVisibility() {
        return visibility;
    }

    /**
     * Predicted atmospheric pressure in millibar.
     *
     * @return int pressure in millibar
     */
    public int getPressure() {
        return pressureMb;
    }

    /**
     * Predicted cloud cover as an integer percent (between 0 and 100)
     *
     * @return int cloud cover %
     */
    public int getCloudcover() {
        return cloudcover;
    }

    /**
     * Predicted heat index, &deg;C. </p>
     *
     * Combines temperature and humidity to suggest how hot it would feel in
     * dryer air. </p>
     *
     * For more on heat index see
     * <a href="https://en.wikipedia.org/wiki/Heat_index">Wikipeida article</a>.
     *
     * @return int heat index, &deg;C
     */
    public int getHeatIndexC() {
        return heatIndexC;
    }

    /**
     * Predicted heat index, &deg;F. </p>
     *
     * Combines temperature and humidity to suggest how hot it would feel in
     * dryer air. </p>
     *
     * For more on heat index see
     * <a href="https://en.wikipedia.org/wiki/Heat_index">Wikipeida article</a>.
     *
     * @return int heat index, &deg;F
     */
    public int getHeatIndexF() {
        return heatIndexF;
    }

    /**
     * Predicted dew point, &deg;C. </p>
     *
     * What temperature do you need to cool the air too, to reach 100% relative
     * humidity?
     *
     * @return int dew point, &deg;C
     */
    public int getDewPointC() {
        return dewPointC;
    }

    /**
     * Predicted dew point, &deg;F. </p>
     *
     * What temperature do you need to cool the air too, to reach 100% relative
     * humidity?
     *
     * @return int dew point, &deg;F
     */
    public int getDewPointF() {
        return dewPointF;
    }

    /**
     * Predicted wind chill, &deg;C. </p>
     *
     * Wind chill is the "perceived decrease in air temperature felt by the body
     * on exposed skin due to the flow of air"
     * (<a href="https://en.wikipedia.org/wiki/Wind_chill">Wikipedia
     * article</a>)
     *
     * @return int wind chill, &deg;C
     */
    public int getWindChillC() {
        return windChillC;
    }

    /**
     * Predicted wind chill, &deg;C. </p>
     *
     * Wind chill is the "perceived decrease in air temperature felt by the body
     * on exposed skin due to the flow of air"
     * (<a href="https://en.wikipedia.org/wiki/Wind_chill">Wikipedia
     * article</a>)
     *
     * @return int wind chill, &deg;C
     */
    public int getWindChillF() {
        return windChillF;
    }

    /**
     * Predicted wind gust speeds, miles per hour.
     *
     * @return int wind gust speed, miles per hour
     */
    public int getWindGustMiles() {
        return windGustMiles;
    }

    /**
     * Predicted wind gust speeds, kilometres per hour.
     *
     * @return int wind gust speed, kilometres per hour
     */
    public int getWindGustKmph() {
        return windGustKmph;
    }

    /**
     * Predicted "Feels Like" or apparent temperature &deg;C. Calculated(using
     * an unknown algorithm that is apparently based on actual temperature,
     * relative humidity, and wind speed.
     *
     * @return int "Feels Like" temperature, &deg;C
     */
    public int getFeelsLikeC() {
        return feelsLikeC;
    }

    /**
     * Predicted "Feels Like" or apparent temperature &deg;F. Calculated(using
     * an unknown algorithm that is apparently based on actual temperature,
     * relative humidity, and wind speed.
     *
     * @return int "Feels Like" temperature, &deg;F
     */
    public int getFeelsLikeF() {
        return feelsLikeF;
    }

    /**
     * Predicted chance of rain as an integer percent (between 0 and 100).
     *
     * @return int chance of rain %
     */
    public int getChanceOfRain() {
        return chanceOfRain;
    }

    /**
     * Predicted chance of wind as an integer percent (between 0 and 100).
     *
     * @return int chance of wind %
     */
    public int getChanceOfWindy() {
        return chanceOfWindy;
    }

    /**
     * Predicted chance of overcast as an integer percent (between 0 and 100).
     *
     * @return int chance of overcast %
     */
    public int getChanceOfOvercast() {
        return chanceOfOvercast;
    }

    /**
     * Predicted chance of sunny as an integer percent (between 0 and 100).
     *
     * @return int chance of sunny %
     */
    public int getChanceOfSunny() {
        return chanceOfSunny;
    }

    /**
     * Predicted chance of frost as an integer percent (between 0 and 100).
     *
     * @return int chance of frost %
     */
    public int getChanceOfFrost() {
        return chanceOfFrost;
    }

    /**
     * Predicted chance of fog as an integer percent (between 0 and 100).
     *
     * @return int chance of fog %
     */
    public int getChanceOfFog() {
        return chanceOfFog;
    }

    /**
     * Predicted chance of snow as an integer percent (between 0 and 100).
     *
     * @return int chance of snow %
     */
    public int getChanceofSnow() {
        return chanceofSnow;
    }

    /**
     * Predicted chance of thunder as an integer percent (between 0 and 100).
     *
     * @return int chance of thunder %
     */
    public int getChanceOfThunder() {
        return chanceOfThunder;
    }

    /**
     * Predicted chance of remaining dry as an integer percent (between 0 and
     * 100). </p>
     *
     * I'm not sure what this one is. Its not (1 - chance of rain).
     *
     * @return int chance of remaining dry %
     */
    public int getChanceOfRemdry() {
        return chanceOfRemdry;
    }

    /**
     * Predicted chance of high temperatures as an integer percent (between 0
     * and 100).
     *
     * @return int chance of high temperature %
     */
    public int getChanceOfHightemp() {
        return chanceOfHightemp;
    }

    public HourlyForecast(DateTime time, int tempC, int tempF, int windspeedMiles, int windspeedKPH, int winddirDegree, String winddir16Point, int weatherCode, String weatherDesc, String weatherIconUrl, float precipMM, int humidity, int visibility, int pressureMb, int cloudcover, int heatIndexC, int heatIndexF, int dewPointC, int dewPointF, int windChillC, int windChillF, int windGustMiles, int windGustKmph, int feelsLikeC, int feelsLikeF, int chanceOfRain, int chanceOfWindy, int chanceOfOvercast, int chanceOfSunny, int chanceOfFrost, int chanceOfFog, int chanceofSnow, int chanceOfThunder, int chanceOfRemdry, int chanceOfHightemp) {
        this.time = time;
        this.tempC = tempC;
        this.tempF = tempF;
        this.windspeedMiles = windspeedMiles;
        this.windspeedKPH = windspeedKPH;
        this.winddirDegree = winddirDegree;
        this.winddir16Point = winddir16Point;
        this.weatherCode = weatherCode;
        this.weatherDesc = weatherDesc;
        this.weatherIconUrl = weatherIconUrl;
        this.precipMM = precipMM;
        this.humidity = humidity;
        this.visibility = visibility;
        this.pressureMb = pressureMb;
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

    /**
     * Build an hourly forecast. Not much use to end users.
     */
    public static class Builder {

        private DateTime time;
        private int tempC;
        private int tempF;
        private int windspeedMiles;
        private int windspeedKPH;
        private int winddirDegree;
        private String winddir16Point;
        private int weatherCode;
        private String weatherDesc;
        private String weatherIconUrl;
        private float precipMM;
        private int humidity;
        private int visibility;
        private int pressureMb;
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

        public Builder setTime(DateTime time) {
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

        public Builder setHumidity(int humidity) {
            this.humidity = humidity;
            return this;
        }

        public Builder setVisibility(int visibility) {
            this.visibility = visibility;
            return this;
        }

        public Builder setPressureMb(int pressureMb) {
            this.pressureMb = pressureMb;
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

        public HourlyForecast build() {
            return new HourlyForecast(time, tempC, tempF, windspeedMiles, windspeedKPH, winddirDegree, winddir16Point, weatherCode, weatherDesc, weatherIconUrl, precipMM, humidity, visibility, pressureMb, cloudcover, heatIndexC, heatIndexF, dewPointC, dewPointF, windChillC, windChillF, windGustMiles, windGustKmph, feelsLikeC, feelsLikeF, chanceOfRain, chanceOfWindy, chanceOfOvercast, chanceOfSunny, chanceOfFrost, chanceOfFog, chanceofSnow, chanceOfThunder, chanceOfRemdry, chanceOfHightemp);
        }
    }

}
