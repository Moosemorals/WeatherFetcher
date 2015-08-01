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
import java.util.List;
import org.joda.time.DateTimeZone;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;
import org.testng.annotations.Test;

/**
 *
 * @author Osric Wilkinson <osric@fluffypeople.com>
 */
public class LocationParserNGTest {

    public LocationParserNGTest() {
    }

    @Test
    public void testParse() throws Exception {
        Report raw = new LocationParser().parse(getClass().getResourceAsStream("/search-result.xml"));

        assertNotNull(raw);
        assertTrue(raw instanceof LocationReport, "Should be LocationReport but got " + raw.getClass().getName());
        LocationReport report = (LocationReport) raw;

        List<Location> locations = report.getLocations();

        assertEquals(locations.size(), 10);

        Location l = locations.get(0);

        assertEquals(l.getName(), "Byker");
        assertEquals(l.getRegion(), "Tyne and Wear");
        assertEquals(l.getCountry(), "United Kingdom");
        assertEquals(l.getPopulation(), 0);
        assertEquals(l.getLatitude(), 54.974, 0.00001);
        assertEquals(l.getLongitude(), -1.572, 0.00001);
        assertEquals(l.getTimezone(), DateTimeZone.forOffsetHours(1));
    }

    @Test
    public void testError() throws Exception {
        Report raw = new LocationParser().parse(getClass().getResourceAsStream("/search-error.xml"));

        assertNotNull(raw);
        assertTrue(raw instanceof ErrorReport, "Should be ErrorRepor but got " + raw.getClass().getName());
        ErrorReport report = (ErrorReport) raw;

        assertEquals(report.getMessage(), "Unable to find any matching weather location to the query submitted!");
        assertEquals(report.getType(), "APIError");
    }

    @Test
    public void checkMyJodaTimeUnderstanding() throws Exception {
        assertEquals(DateTimeZone.forOffsetHours(1), DateTimeZone.forOffsetMillis(60 * 60 * 1000));
    }

}
