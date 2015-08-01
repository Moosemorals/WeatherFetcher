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

import com.moosemorals.weather.reports.ErrorReport;
import com.moosemorals.weather.reports.Report;
import com.moosemorals.weather.reports.WeatherReport;
import com.moosemorals.weather.types.Astronomy;
import com.moosemorals.weather.types.Current;
import com.moosemorals.weather.types.DailyForecast;
import com.moosemorals.weather.types.HourlyForecast;
import com.moosemorals.weather.types.Query;
import java.io.IOException;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Parse XML weather data from the World Weather Online v2 API.
 *
 * @author Osric Wilkinson osric@fluffypeople.com
 */
public class WeatherParser extends BaseParser<Report> {

    private final Logger log = LoggerFactory.getLogger(WeatherParser.class);

    private static final String LANG_TAG = "lang_";

    @Override
    public Report parse(XMLStreamReader parser) throws XMLStreamException, IOException {
        parser.require(XMLStreamReader.START_ELEMENT, NAMESPACE, "data");

        WeatherReport.Builder builder = new WeatherReport.Builder();
        DateTime when = null;

        while (parser.next() != XMLStreamReader.END_ELEMENT) {
            if (parser.getEventType() != XMLStreamReader.START_ELEMENT) {
                continue;
            }

            switch (parser.getLocalName()) {
                case "error":
                    return readError(parser);
                case "request":
                    builder.setQuery(readQuery(parser));
                    break;
                case "nearest_area":
                    builder.setLocation(new LocationParser().readLocation(parser, "nearest_area"));
                    break;
                case "time_zone":
                    when = readTimeZone(parser);
                    builder.setWhen(when);
                    break;
                case "current_condition":
                    builder.setCurrent(readCurrent(parser, builder));
                    break;
                case "weather":
                    builder.addDailyForecast(readForecast(parser, builder, when));
                    break;
                default:
                    log.warn("Top: Skiping unexpected tag {}", parser.getLocalName());
                    skipTag(parser);
                    break;
            }
        }
        return builder.build();
    }

    private ErrorReport readError(XMLStreamReader parser) throws XMLStreamException, IOException {
        parser.require(XMLStreamReader.START_ELEMENT, NAMESPACE, "error");

        while (parser.next() != XMLStreamReader.START_ELEMENT) {
            // skip noise
        }
        return new ErrorReport("APIError", readTag(parser, "msg"));
    }

    private Current readCurrent(XMLStreamReader parser, WeatherReport.Builder reportBuilder) throws XMLStreamException, IOException {

        parser.require(XMLStreamReader.START_ELEMENT, NAMESPACE, "current_condition");

        DateTimeFormatter fmt = DateTimeFormat.forPattern("hh:mm aa").withZoneUTC();
        Current.Builder builder = new Current.Builder();

        while (parser.next() != XMLStreamReader.END_ELEMENT) {
            if (parser.getEventType() != XMLStreamReader.START_ELEMENT) {
                continue;
            }

            switch (parser.getLocalName()) {
                case "observation_time":
                    builder.setObservationTime(fmt.parseLocalTime(readTag(parser, "observation_time")));
                    break;
                case "temp_C":
                    builder.setTempC(readIntTag(parser, "temp_C"));
                    break;
                case "temp_F":
                    builder.setTempF(readIntTag(parser, "temp_F"));
                    break;
                case "weatherCode":
                    builder.setWeatherCode(readIntTag(parser, "weatherCode"));
                    break;
                case "weatherIconUrl":
                    builder.setWeatherIconUrl(readTag(parser, "weatherIconUrl").trim());
                    break;
                case "weatherDesc":
                    builder.setWeatherDesc(readTag(parser, "weatherDesc").trim());
                    break;
                case "windspeedMiles":
                    builder.setWindspeedMiles(readIntTag(parser, "windspeedMiles"));
                    break;
                case "windspeedKmph":
                    builder.setWindspeedKmph(readIntTag(parser, "windspeedKmph"));
                    break;
                case "winddirDegree":
                    builder.setWinddirDegree(readIntTag(parser, "winddirDegree"));
                    break;
                case "winddir16Point":
                    builder.setWinddir16Point(readTag(parser, "winddir16Point"));
                    break;
                case "precipMM":
                    builder.setPrecipMM(readFloatTag(parser, "precipMM"));
                    break;
                case "humidity":
                    builder.setHumidity(readIntTag(parser, "humidity"));
                    break;
                case "visibility":
                    builder.setVisibility(readIntTag(parser, "visibility"));
                    break;
                case "pressure":
                    builder.setPressure(readIntTag(parser, "pressure"));
                    break;
                case "cloudcover":
                    builder.setCloudcover(readIntTag(parser, "cloudcover"));
                    break;
                case "FeelsLikeC":
                    builder.setFeelsLikeC(readIntTag(parser, "FeelsLikeC"));
                    break;
                case "FeelsLikeF":
                    builder.setFeelsLikeF(readIntTag(parser, "FeelsLikeF"));
                    break;
                default:
                    // Cope with the crazy way that languages are handled.
                    if (parser.getLocalName().startsWith(LANG_TAG)) {
                        builder.setWeatherDesc(readTag(parser, parser.getLocalName()).trim());

                        reportBuilder.setLanguage(parser.getLocalName().substring(LANG_TAG.length()));

                        break;
                    }
                    log.warn("Current: Skiping unexpected tag {}", parser.getLocalName());
                    skipTag(parser);
                    break;
            }
        }
        return builder.build();

    }

    private DailyForecast readForecast(XMLStreamReader parser, WeatherReport.Builder reportBuilder, DateTime when) throws XMLStreamException, IOException {
        parser.require(XMLStreamReader.START_ELEMENT, NAMESPACE, "weather");

        DailyForecast.Builder builder = new DailyForecast.Builder();

        DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd").withZone(when.getZone());

        while (parser.next() != XMLStreamReader.END_ELEMENT) {
            if (parser.getEventType() != XMLStreamReader.START_ELEMENT) {
                continue;
            }

            switch (parser.getLocalName()) {
                case "date":
                    builder.setDate(fmt.parseDateTime(readTag(parser, "date")));
                    break;
                case "astronomy":
                    builder.setAstronomy(readAstronmy(parser, when));
                    break;
                case "maxtempC":
                    builder.setMaxTempC(readIntTag(parser, "maxtempC"));
                    break;
                case "maxtempF":
                    builder.setMaxTempF(readIntTag(parser, "maxtempF"));
                    break;
                case "mintempC":
                    builder.setMinTempC(readIntTag(parser, "mintempC"));
                    break;
                case "mintempF":
                    builder.setMinTempF(readIntTag(parser, "mintempF"));
                    break;
                case "uvIndex":
                    builder.setUvIndex(readIntTag(parser, "uvIndex"));
                    break;
                case "hourly":
                    reportBuilder.addHourlyForecast(readHour(parser));
                    break;
                default:
                    log.warn("Forecast: Skiping unexpected tag {}", parser.getLocalName());
                    skipTag(parser);
                    break;
            }
        }

        return builder.build();
    }

    private Astronomy readAstronmy(XMLStreamReader parser, DateTime when) throws XMLStreamException, IOException {
        parser.require(XMLStreamReader.START_ELEMENT, NAMESPACE, "astronomy");

        Astronomy.Builder builder = new Astronomy.Builder();

        DateTimeFormatter fmt = DateTimeFormat.forPattern("hh:mm aa");

        while (parser.next() != XMLStreamReader.END_ELEMENT) {
            if (parser.getEventType() != XMLStreamReader.START_ELEMENT) {
                continue;
            }

            String raw;
            LocalTime time;
            switch (parser.getLocalName()) {
                case "sunrise":
                    raw = readTag(parser, "sunrise");
                    if (!raw.equals("No sunrise")) {
                        time = fmt.parseLocalTime(raw);
                        builder.setSunrise(when.withHourOfDay(time.getHourOfDay()).withMinuteOfHour(time.getMinuteOfHour()));
                    } else {
                        builder.setSunrise(null);
                    }
                    break;
                case "sunset":
                    raw = readTag(parser, "sunset");
                    if (!raw.equals("No sunset")) {
                        time = fmt.parseLocalTime(raw);
                        builder.setSunset(when.withHourOfDay(time.getHourOfDay()).withMinuteOfHour(time.getMinuteOfHour()));
                    } else {
                        builder.setSunset(null);
                    }
                    break;
                case "moonrise":
                    raw = readTag(parser, "moonrise");
                    if (!raw.equals("No moonrise")) {
                        time = fmt.parseLocalTime(raw);
                        builder.setMoonrise(when.withHourOfDay(time.getHourOfDay()).withMinuteOfHour(time.getMinuteOfHour()));
                    } else {
                        builder.setMoonrise(null);
                    }
                    break;
                case "moonset":
                    raw = readTag(parser, "moonset");
                    if (!raw.equals("No moonset")) {
                        time = fmt.parseLocalTime(raw);
                        builder.setMoonset(when.withHourOfDay(time.getHourOfDay()).withMinuteOfHour(time.getMinuteOfHour()));
                    } else {
                        builder.setMoonset(null);
                    }
                    break;
                default:
                    log.warn("Astro: Skiping unexpected tag {}", parser.getLocalName());
                    skipTag(parser);
                    break;
            }
        }

        return builder.build();
    }

    private HourlyForecast readHour(XMLStreamReader parser) throws XMLStreamException, IOException {
        parser.require(XMLStreamReader.START_ELEMENT, NAMESPACE, "hourly");

        HourlyForecast.Builder builder = new HourlyForecast.Builder();

        LocalTime utcTime = null;
        DateTime utcDate = null;

        while (parser.next() != XMLStreamReader.END_ELEMENT) {
            if (parser.getEventType() != XMLStreamReader.START_ELEMENT) {
                continue;
            }

            switch (parser.getLocalName()) {
                case "time":
                    skipTag(parser);
                    break;
                case "UTCdate":
                    utcDate = DateTimeFormat.forPattern("yyyy-MM-dd").withZoneUTC().parseDateTime(readTag(parser, "UTCdate"));
                    break;
                case "UTCtime":
                    utcTime = readTime(readTag(parser, "UTCtime"));
                    break;
                case "tempC":
                    builder.setTempC(readIntTag(parser, "tempC"));
                    break;
                case "tempF":
                    builder.setTempF(readIntTag(parser, "tempF"));
                    break;
                case "windspeedMiles":
                    builder.setWindspeedMiles(readIntTag(parser, "windspeedMiles"));
                    break;
                case "windspeedKmph":
                    builder.setWindspeedKPH(readIntTag(parser, "windspeedKmph"));
                    break;
                case "winddirDegree":
                    builder.setWinddirDegree(readIntTag(parser, "winddirDegree"));
                    break;
                case "winddir16Point":
                    builder.setWinddir16Point(readTag(parser, "winddir16Point"));
                    break;
                case "weatherCode":
                    builder.setWeatherCode(readIntTag(parser, "weatherCode"));
                    break;
                case "weatherIconUrl":
                    builder.setWeatherIconUrl(readTag(parser, "weatherIconUrl").trim());
                    break;
                case "weatherDesc":
                    builder.setWeatherDesc(readTag(parser, "weatherDesc").trim());
                    break;
                case "precipMM":
                    builder.setPrecipMM(readFloatTag(parser, "precipMM"));
                    break;
                case "humidity":
                    builder.setHumidity(readIntTag(parser, "humidity"));
                    break;
                case "visibility":
                    builder.setVisibility(readIntTag(parser, "visibility"));
                    break;
                case "pressure":
                    builder.setPressureMb(readIntTag(parser, "pressure"));
                    break;
                case "cloudcover":
                    builder.setCloudcover(readIntTag(parser, "cloudcover"));
                    break;
                case "HeatIndexC":
                    builder.setHeatIndexC(readIntTag(parser, "HeatIndexC"));
                    break;
                case "HeatIndexF":
                    builder.setHeatIndexF(readIntTag(parser, "HeatIndexF"));
                    break;
                case "DewPointC":
                    builder.setDewPointC(readIntTag(parser, "DewPointC"));
                    break;
                case "DewPointF":
                    builder.setDewPointF(readIntTag(parser, "DewPointF"));
                    break;
                case "WindChillC":
                    builder.setWindChillC(readIntTag(parser, "WindChillC"));
                    break;
                case "WindChillF":
                    builder.setWindChillF(readIntTag(parser, "WindChillF"));
                    break;
                case "WindGustMiles":
                    builder.setWindGustMiles(readIntTag(parser, "WindGustMiles"));
                    break;
                case "WindGustKmph":
                    builder.setWindGustKmph(readIntTag(parser, "WindGustKmph"));
                    break;
                case "FeelsLikeC":
                    builder.setFeelsLikeC(readIntTag(parser, "FeelsLikeC"));
                    break;
                case "FeelsLikeF":
                    builder.setFeelsLikeF(readIntTag(parser, "FeelsLikeF"));
                    break;
                case "chanceofrain":
                    builder.setChanceOfRain(readIntTag(parser, "chanceofrain"));
                    break;
                case "chanceofwindy":
                    builder.setChanceOfWindy(readIntTag(parser, "chanceofwindy"));
                    break;
                case "chanceofovercast":
                    builder.setChanceOfOvercast(readIntTag(parser, "chanceofovercast"));
                    break;
                case "chanceofremdry":
                    builder.setChanceOfRemdry(readIntTag(parser, "chanceofremdry"));
                    break;
                case "chanceofhightemp":
                    builder.setChanceOfHightemp(readIntTag(parser, "chanceofhightemp"));
                    break;
                case "chanceofsnow":
                    builder.setChanceofSnow(readIntTag(parser, "chanceofsnow"));
                    break;
                case "chanceofsunshine":
                    builder.setChanceOfSunny(readIntTag(parser, "chanceofsunshine"));
                    break;
                case "chanceoffog":
                    builder.setChanceOfFog(readIntTag(parser, "chanceoffog"));
                    break;
                case "chanceoffrost":
                    builder.setChanceOfFrost(readIntTag(parser, "chanceoffrost"));
                    break;
                case "chanceofthunder":
                    builder.setChanceOfThunder(readIntTag(parser, "chanceofthunder"));
                    break;
                default:
                    // Cope with the crazy way that languages are handled.
                    if (parser.getLocalName().startsWith(LANG_TAG)) {
                        builder.setWeatherDesc(readTag(parser, parser.getLocalName()).trim());
                        break;
                    }
                    log.warn("Hour: Skiping unexpected tag {}", parser.getLocalName());
                    skipTag(parser);
                    break;
            }
        }

        if (utcDate != null && utcTime != null) {
            DateTime time = utcDate.withHourOfDay(utcTime.getHourOfDay()).withMinuteOfHour(utcTime.getMinuteOfHour());
            builder.setTime(time);
        } else {
            builder.setTime(null);
        }

        return builder.build();
    }

    protected DateTime readTimeZone(XMLStreamReader parser) throws XMLStreamException, IOException {
        parser.require(XMLStreamReader.START_ELEMENT, NAMESPACE, "time_zone");

        String rawDate = null;
        float utcOffset = 0.0f;

        while (parser.next() != XMLStreamReader.END_ELEMENT) {
            if (parser.getEventType() != XMLStreamReader.START_ELEMENT) {
                continue;
            }

            String raw;
            switch (parser.getLocalName()) {
                case "localtime":
                    rawDate = readTag(parser, "localtime");
                    break;
                case "utcOffset":
                    utcOffset = readFloatTag(parser, "utcOffset");
                    break;
            }
        }

        int hoursOffset = (int) utcOffset;
        int minutesOffset = (int) ((utcOffset - hoursOffset) * 60.0);

        DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm").withZone(DateTimeZone.forOffsetHoursMinutes(hoursOffset, minutesOffset));

        return fmt.parseDateTime(rawDate);
    }

    private Query readQuery(XMLStreamReader parser) throws XMLStreamException, IOException {
        parser.require(XMLStreamReader.START_ELEMENT, NAMESPACE, "request");

        Query.Builder builder = new Query.Builder();

        while (parser.next() != XMLStreamReader.END_ELEMENT) {
            if (parser.getEventType() != XMLStreamReader.START_ELEMENT) {
                continue;
            }

            switch (parser.getLocalName()) {
                case "type":
                    builder.setType(readTag(parser, "type"));
                    break;
                case "query":
                    builder.setName(readTag(parser, "query"));
                    break;
            }
        }

        return builder.build();
    }

    private static LocalTime readTime(String raw) throws XMLStreamException {
        if (raw.equals("0")) {
            return new LocalTime(0, 0);
        } else {
            String hour = raw.substring(0, raw.length() - 2);
            String minute = raw.substring(raw.length() - 2);
            try {
                return new LocalTime(Integer.parseInt(hour, 10), Integer.parseInt(minute, 10));
            } catch (NumberFormatException ex) {
                throw new XMLStreamException("Can't parse time: " + ex.getMessage(), ex);
            }
        }
    }

}
