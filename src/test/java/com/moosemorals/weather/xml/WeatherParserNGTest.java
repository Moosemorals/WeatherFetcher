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
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertNotNull;
import org.testng.annotations.Test;

/**
 *
 * @author osric
 */
public class WeatherParserNGTest {

    private final Logger log = LoggerFactory.getLogger(WeatherParserNGTest.class);

    public WeatherParserNGTest() {
    }

    @Test
    public void parse_utc() throws Exception {
        WeatherReport report = new WeatherParser().parse(getClass().getResourceAsStream("/sample-utc.xml"));

        assertNotNull(report);
        assertNotNull(report.getCurrent());
        assertNotEquals(0, report.getForecasts().size());

        DateTime when = new DateTime(2015, 7, 25, 11, 01, 0, DateTimeZone.forOffsetHours(1));

        assertEquals(report.getLocalTime(), when);

        Current current = report.getCurrent();

        assertEquals(current.getObservationTime(), new LocalTime(10, 01));

        assertEquals(current.getTempC(), 14);

        assertEquals(current.getWeatherCode(), 116);
        assertEquals(current.getWeatherIconUrl(), "http://cdn.worldweatheronline.net/images/wsymbols01_png_64/wsymbol_0002_sunny_intervals.png");
        assertEquals(current.getWeatherDesc(), "Partly Cloudy");

        assertEquals(current.getWindspeedMPH(), 12);
        assertEquals(current.getWinddirDegree(), 340);
        assertEquals(current.getWinddirName(), "NNW");

        assertEquals(current.getPrecipMM(), 0.0, 0.001);

        assertEquals(current.getVisibilityKm(), 10);

        assertEquals(current.getPressureMb(), 1012);

        assertEquals(current.getCloudcover(), 50);

        Forecast forecast = report.getForecasts().get(0);

        assertEquals(forecast.getDate(), new LocalDate(2015, 7, 25));

        assertEquals(forecast.getMaxTempC(), 18);
        assertEquals(forecast.getMaxTempF(), 64);
        assertEquals(forecast.getMinTempC(), 9);
        assertEquals(forecast.getMinTempF(), 48);
        assertEquals(forecast.getUvIndex(), 5);

        Astronomy astronomy = forecast.getAstronomy();
        assertNotNull(astronomy);

        assertEquals(astronomy.getSunrise(), new LocalTime(5, 2));
        assertEquals(astronomy.getSunset(), new LocalTime(21, 22));
        assertEquals(astronomy.getMoonrise(), new LocalTime(15, 26));
        assertEquals(astronomy.getMoonset(), new LocalTime(0, 22));

        assertEquals(forecast.getHourly().size(), 8);

        Hour hour = forecast.getHourly().get(0);

        assertEquals(hour.getTime(), new DateTime(2015, 7, 25, 0, 0, 0, DateTimeZone.UTC));

        assertEquals(hour.getTempC(), 11);
        assertEquals(hour.getTempF(), 52);
        assertEquals(hour.getWindspeedMiles(), 6);
        assertEquals(hour.getWinddirDegree(), 347);
        assertEquals(hour.getWeatherCode(), 113);
        assertEquals(hour.getWeatherIconUrl(), "http://cdn.worldweatheronline.net/images/wsymbols01_png_64/wsymbol_0008_clear_sky_night.png");
        assertEquals(hour.getWeatherDesc(), "Clear");
        assertEquals(hour.getPrecipMM(), 0.0, 0.001);
        assertEquals(hour.getHumidity(), 84);
        assertEquals(hour.getVisibility(), 10);
        assertEquals(hour.getPressureMb(), 1011);
        assertEquals(hour.getCloudcover(), 21);

        assertEquals(hour.getHeatIndexC(), 11);
        assertEquals(hour.getHeatIndexF(), 52);
        assertEquals(hour.getDewPointC(), 9);
        assertEquals(hour.getDewPointF(), 47);
        assertEquals(hour.getWindChillC(), 10);
        assertEquals(hour.getWindChillF(), 50);
        assertEquals(hour.getWindGustMiles(), 11);
        assertEquals(hour.getWindGustKmph(), 17);
        assertEquals(hour.getFeelsLikeC(), 10);
        assertEquals(hour.getFeelsLikeF(), 50);

    }

    @Test
    public void parse_sample_from_fetcher() throws Exception {
        WeatherReport report = new WeatherParser().parse(getClass().getResourceAsStream("/sample-from-fetcher.xml"));

        assertEquals(report.getForecasts().size(), 3);
        for (Forecast f : report.getForecasts()) {
            assertEquals(f.getHourly().size(), 8);
        }
    }

    @Test
    public void parse_error() throws Exception {
        WeatherReport report = new WeatherParser().parse(getClass().getResourceAsStream("/error-bad-location.xml"));

        assertEquals(report.isSuccess(), false, "Report is not a success");
        assertNotNull(report.getError(), "ErrorReport should not be null");
        assertEquals(report.getError().getMessage(), "Unable to find any matching weather location to the query submitted!", "Unexpected error message");

    }

    @Test
    public void readTimeZonePlus1() throws Exception {
        InputStream in = getInput("<?xml version=\"1.0\"?><time_zone>\n"
                + "        <localtime>2015-07-24 07:26</localtime>\n"
                + "        <utcOffset>1.0</utcOffset>\n"
                + "    </time_zone>");

        XMLStreamReader reader = XMLInputFactory.newFactory().createXMLStreamReader(in, "UTF-8");
        reader.nextTag();

        WeatherParser parser = new WeatherParser();

        DateTime result = parser.readTimeZone(reader);

        DateTime expected = new DateTime(2015, 7, 24, 7, 26, 0, DateTimeZone.forOffsetHours(1));

        assertEquals(expected, result);

    }

    @Test
    public void readTimeZoneMinus1() throws Exception {
        InputStream in = getInput("<?xml version=\"1.0\"?><time_zone>\n"
                + "        <localtime>2015-07-24 07:26</localtime>\n"
                + "        <utcOffset>-1.0</utcOffset>\n"
                + "    </time_zone>");

        XMLStreamReader reader = XMLInputFactory.newFactory().createXMLStreamReader(in, "UTF-8");
        reader.nextTag();

        WeatherParser parser = new WeatherParser();

        DateTime result = parser.readTimeZone(reader);
        DateTime expected = new DateTime(2015, 7, 24, 7, 26, 0, DateTimeZone.forOffsetHours(-1));

        assertEquals(expected, result);
    }

    @Test
    public void readTimeZonePlusHalf() throws Exception {
        InputStream in = getInput("<?xml version=\"1.0\"?><time_zone>\n"
                + "        <localtime>2015-07-24 07:26</localtime>\n"
                + "        <utcOffset>0.5</utcOffset>\n"
                + "    </time_zone>");

        XMLStreamReader reader = XMLInputFactory.newFactory().createXMLStreamReader(in, "UTF-8");
        reader.nextTag();

        WeatherParser parser = new WeatherParser();

        DateTime result = parser.readTimeZone(reader);
        DateTime expected = new DateTime(2015, 7, 24, 7, 26, 0, DateTimeZone.forOffsetHoursMinutes(0, 30));

        assertEquals(expected, result);
    }

    @Test
    public void readTimeZoneMinusHalf() throws Exception {
        InputStream in = getInput("<?xml version=\"1.0\"?><time_zone>\n"
                + "        <localtime>2015-07-24 07:26</localtime>\n"
                + "        <utcOffset>-0.5</utcOffset>\n"
                + "    </time_zone>");

        XMLStreamReader reader = XMLInputFactory.newFactory().createXMLStreamReader(in, "UTF-8");
        reader.nextTag();

        WeatherParser parser = new WeatherParser();

        DateTime result = parser.readTimeZone(reader);
        DateTime expected = new DateTime(2015, 7, 24, 7, 26, 0, DateTimeZone.forOffsetHoursMinutes(0, -30));

        assertEquals(expected, result);
    }

    private static InputStream getInput(String source) throws UnsupportedEncodingException {
        return new ByteArrayInputStream(source.getBytes("UTF-8"));
    }

}
