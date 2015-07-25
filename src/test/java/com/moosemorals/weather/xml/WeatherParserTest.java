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
package com.moosemorals.weather.xml;

import com.moosemorals.weather.types.Astronomy;
import com.moosemorals.weather.types.Current;
import com.moosemorals.weather.types.Forecast;
import com.moosemorals.weather.types.Hour;
import com.moosemorals.weather.types.WeatherReport;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertNotNull;
import org.testng.annotations.Test;

/**
 *
 * @author osric
 */
public class WeatherParserTest {

    public WeatherParserTest() {
    }

    @Test
    public void testParser() throws Exception {
        WeatherReport report = new WeatherParser().parse(getClass().getResourceAsStream("/sample.xml"));

        assertNotNull(report);
        assertNotNull(report.getCurrent());
        assertNotEquals(0, report.getForecasts().size());

        Current current = report.getCurrent();

        assertEquals(current.getObservationTime(), new LocalTime(6, 26));

        assertEquals(current.getTempC(), 8);

        assertEquals(current.getWeatherCode(), 113);
        assertEquals(current.getWeatherIconUrl(), "http://cdn.worldweatheronline.net/images/wsymbols01_png_64/wsymbol_0001_sunny.png");
        assertEquals(current.getWeatherDesc(), "Sunny");

        assertEquals(current.getWindspeedMPH(), 0);
        assertEquals(current.getWinddirDegree(), 278);
        assertEquals(current.getWinddirName(), "W");

        assertEquals(current.getPrecipMM(), 0.0, 0.001);

        assertEquals(current.getVisibilityKm(), 10);

        assertEquals(current.getPressureMb(), 1013);

        assertEquals(current.getCloudcover(), 0);

        Forecast forecast = report.getForecasts().get(0);

        assertEquals(forecast.getDate(), new LocalDate(2015, 7, 24));

        assertEquals(forecast.getMaxTempC(), 21);
        assertEquals(forecast.getMaxTempF(), 70);
        assertEquals(forecast.getMinTempC(), 10);
        assertEquals(forecast.getMinTempF(), 51);
        assertEquals(forecast.getUvIndex(), 5);

        Astronomy astronomy = forecast.getAstronomy();
        assertNotNull(astronomy);

        assertEquals(astronomy.getSunrise(), new LocalTime(5, 1));
        assertEquals(astronomy.getSunset(), new LocalTime(21, 24));
        assertEquals(astronomy.getMoonrise(), new LocalTime(14, 19));
        assertEquals(astronomy.getMoonset(), null);

        assertEquals(forecast.getHourly().size(), 8);

        Hour hour = forecast.getHourly().get(0);

        assertEquals(hour.getTempC(), 11);
        assertEquals(hour.getTempF(), 51);
        assertEquals(hour.getWindspeedMiles(), 7);
        assertEquals(hour.getWinddirDegree(), 254);
        assertEquals(hour.getWeatherCode(), 113);
        assertEquals(hour.getWeatherIconUrl(), "http://cdn.worldweatheronline.net/images/wsymbols01_png_64/wsymbol_0008_clear_sky_night.png");
        assertEquals(hour.getWeatherDesc(), "Clear");
        assertEquals(hour.getPrecipMM(), 0.0, 0.001);
        assertEquals(hour.getHumidity(), 85);
        assertEquals(hour.getVisibility(), 10);
        assertEquals(hour.getPressureMb(), 1014);
        assertEquals(hour.getCloudcover(), 17);

        assertEquals(hour.getHeatIndexC(), 11);
        assertEquals(hour.getHeatIndexF(), 51);
        assertEquals(hour.getDewPointC(), 8);
        assertEquals(hour.getDewPointF(), 46);
        assertEquals(hour.getWindChillC(), 9);
        assertEquals(hour.getWindChillF(), 48);
        assertEquals(hour.getWindGustMiles(), 13);
        assertEquals(hour.getWindGustKmph(), 21);
        assertEquals(hour.getFeelsLikeC(), 9);
        assertEquals(hour.getFeelsLikeF(), 48);

    }

}
