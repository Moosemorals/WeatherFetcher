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

import com.fluffypeople.library.xml.BaseParser;
import com.moosemorals.weather.types.Astronomy;
import com.moosemorals.weather.types.Current;
import com.moosemorals.weather.types.Forecast;
import com.moosemorals.weather.types.Hour;
import com.moosemorals.weather.types.WeatherReport;
import java.io.IOException;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author osric
 */
public class WeatherParser extends BaseParser<WeatherReport> {

    private final Logger log = LoggerFactory.getLogger(WeatherParser.class);

    @Override
    public WeatherReport parse(XMLStreamReader parser) throws XMLStreamException, IOException {
        parser.require(XMLStreamReader.START_ELEMENT, NAMESPACE, "data");

        WeatherReport report = new WeatherReport();

        while (parser.next() != XMLStreamReader.END_ELEMENT) {
            if (parser.getEventType() != XMLStreamReader.START_ELEMENT) {
                continue;
            }

            switch (parser.getLocalName()) {
                case "current_condition":
                    report.setCurrent(readCurrent(parser));
                    break;
                case "weather":
                    report.addForecast(readForecast(parser));
                    break;
                default:
                    log.warn("Top: Skiping unexpected tag {}", parser.getLocalName());
                    skipTag(parser);
                    break;
            }
        }
        return report;
    }

    private Current readCurrent(XMLStreamReader parser) throws XMLStreamException, IOException {

        parser.require(XMLStreamReader.START_ELEMENT, NAMESPACE, "current_condition");

        DateTimeFormatter fmt = DateTimeFormat.forPattern("hh:mm aa");
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
                case "windspeadMiles":
                    builder.setWindspeedMPH(readIntTag(parser, "windspeedMiles"));
                    break;
                case "windspeedKmph":
                    builder.setWindspeedKPH(readIntTag(parser, "windspeedKmph"));
                    break;
                case "winddirDegree":
                    builder.setWinddirDegree(readIntTag(parser, "winddirDegree"));
                    break;
                case "winddir16Point":
                    builder.setWinddirName(readTag(parser, "winddir16Point"));
                    break;
                case "precipMM":
                    builder.setPrecipMM(readFloatTag(parser, "precipMM"));
                    break;
                case "humidity":
                    builder.setHumidity(readIntTag(parser, "humidity"));
                    break;
                case "visibility":
                    builder.setVisibilityKm(readIntTag(parser, "visibility"));
                    break;
                case "pressure":
                    builder.setPressureMb(readIntTag(parser, "pressure"));
                    break;
                case "cloudcover":
                    builder.setCloudcover(readIntTag(parser, "cloudcover"));
                    break;
                default:
                    log.warn("Current: Skiping unexpected tag {}", parser.getLocalName());
                    skipTag(parser);
                    break;
            }

        }

        return builder.build();

    }

    private Forecast readForecast(XMLStreamReader parser) throws XMLStreamException, IOException {
        parser.require(XMLStreamReader.START_ELEMENT, NAMESPACE, "weather");

        Forecast.Builder builder = new Forecast.Builder();

        DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd");

        while (parser.next() != XMLStreamReader.END_ELEMENT) {
            if (parser.getEventType() != XMLStreamReader.START_ELEMENT) {
                continue;
            }

            switch (parser.getLocalName()) {
                case "date":
                    builder.setDate(fmt.parseLocalDate(readTag(parser, "date")));
                    break;
                case "astronomy":
                    builder.setAstronomy(readAstronmy(parser));
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
                    builder.addHour(readHour(parser));
                    break;
                default:
                    log.warn("Forecast: Skiping unexpected tag {}", parser.getLocalName());
                    skipTag(parser);
                    break;
            }
        }

        return builder.build();
    }

    private Astronomy readAstronmy(XMLStreamReader parser) throws XMLStreamException, IOException {
        parser.require(XMLStreamReader.START_ELEMENT, NAMESPACE, "astronomy");

        Astronomy.Builder builder = new Astronomy.Builder();

        DateTimeFormatter fmt = DateTimeFormat.forPattern("hh:mm aa");

        while (parser.next() != XMLStreamReader.END_ELEMENT) {
            if (parser.getEventType() != XMLStreamReader.START_ELEMENT) {
                continue;
            }

            String raw;
            switch (parser.getLocalName()) {
                case "sunrise":
                    raw = readTag(parser, "sunrise");
                    if (!raw.equals("No sunrise")) {
                        builder.setSunrise(fmt.parseLocalTime(raw));
                    } else {
                        builder.setSunrise(null);
                    }
                    break;
                case "sunset":
                    raw = readTag(parser, "sunset");
                    if (!raw.equals("No sunset")) {
                        builder.setSunset(fmt.parseLocalTime(raw));
                    } else {
                        builder.setSunset(null);
                    }
                    break;
                case "moonrise":
                    raw = readTag(parser, "moonrise");
                    if (!raw.equals("No moonrise")) {
                        builder.setMoonrise(fmt.parseLocalTime(raw));
                    } else {
                        builder.setMoonrise(null);
                    }
                    break;
                case "moonset":
                    raw = readTag(parser, "moonset");
                    if (!raw.equals("No moonset")) {
                        builder.setMoonset(fmt.parseLocalTime(raw));
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

    private Hour readHour(XMLStreamReader parser) throws XMLStreamException, IOException {
        parser.require(XMLStreamReader.START_ELEMENT, NAMESPACE, "hourly");

        Hour.Builder builder = new Hour.Builder();

        DateTimeFormatter fmt = DateTimeFormat.forPattern("KK:mm a");

        while (parser.next() != XMLStreamReader.END_ELEMENT) {
            if (parser.getEventType() != XMLStreamReader.START_ELEMENT) {
                continue;
            }

            switch (parser.getLocalName()) {
                case "time":
                    skipTag(parser);
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
                case "chanceofrain":
                    builder.setChanceOfRain(readIntTag(parser, "chanceofrain"));
                    break;
                case "chanceofwindy":
                    builder.setChanceOfWindy(readIntTag(parser, "chanceofwindy"));
                    break;
                case "chanceofovercast":
                    builder.setChanceOfOvercast(readIntTag(parser, "chanceofovercast"));
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
                    log.warn("Hour: Skiping unexpected tag {}", parser.getLocalName());
                    skipTag(parser);
                    break;
            }
        }

        return builder.build();
    }
}
