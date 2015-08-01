/*
 * The MIT License
 *
 * Copyright 2015 Osric Wilkinson <osric@fluffypeople.com>.
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
import com.moosemorals.weather.reports.LocationReport;
import com.moosemorals.weather.reports.Report;
import com.moosemorals.weather.types.Location;
import static com.moosemorals.weather.xml.BaseParser.NAMESPACE;
import java.io.IOException;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import org.joda.time.DateTimeZone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Osric Wilkinson <osric@fluffypeople.com>
 */
public class LocationParser extends BaseParser<Report> {

    private final Logger log = LoggerFactory.getLogger(LocationParser.class);

    @Override
    public Report parse(XMLStreamReader parser) throws XMLStreamException, IOException {
        LocationReport.Builder builder = new LocationReport.Builder();

        while (parser.next() != XMLStreamReader.END_ELEMENT) {
            if (parser.getEventType() != XMLStreamReader.START_ELEMENT) {
                continue;
            }

            switch (parser.getLocalName()) {
                case "error":
                    return readError(parser);
                case "result":
                    builder.addLocation(readLocation(parser, "result"));
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

    protected Location readLocation(XMLStreamReader parser, String tagName) throws XMLStreamException, IOException {
        parser.require(XMLStreamReader.START_ELEMENT, NAMESPACE, tagName);

        Location.Builder builder = new Location.Builder();
        while (parser.next() != XMLStreamReader.END_ELEMENT) {
            if (parser.getEventType() != XMLStreamReader.START_ELEMENT) {
                continue;
            }

            switch (parser.getLocalName()) {
                case "areaName":
                    builder.setName(readTag(parser, "areaName").trim());
                    break;
                case "country":
                    builder.setCountry(readTag(parser, "country").trim());
                    break;
                case "region":
                    builder.setRegion(readTag(parser, "region").trim());
                    break;
                case "population":
                    builder.setPopulation(readLongTag(parser, "population"));
                    break;
                case "latitude":
                    builder.setLatitude(readFloatTag(parser, "latitude"));
                    break;
                case "longitude":
                    builder.setLongitude(readFloatTag(parser, "longitude"));
                    break;
                case "timezone":
                    builder.setTimezone(readTimezone(parser));
                    break;
                case "weatherUrl":
                    skipTag(parser);
                    break;
                default:
                    log.warn("{}: Skiping unexpected tag {}", tagName, parser.getLocalName());
                    skipTag(parser);
                    break;

            }
        }

        return builder.build();
    }

    private DateTimeZone readTimezone(XMLStreamReader parser) throws XMLStreamException, IOException {
        parser.require(XMLStreamReader.START_ELEMENT, NAMESPACE, "timezone");

        DateTimeZone result = null;

        while (parser.next() != XMLStreamReader.END_ELEMENT) {
            if (parser.getEventType() != XMLStreamReader.START_ELEMENT) {
                continue;
            }

            switch (parser.getLocalName()) {
                case "offset":
                    result = DateTimeZone.forOffsetMillis(Math.round(readFloatTag(parser, "offset") * 60 * 60 * 1000));
                    break;
                default:
                    log.warn("Unrecognised tag {} in <timezone>", parser.getLocalName());
                    skipTag(parser);
                    break;
            }
        }
        return result;
    }

}
