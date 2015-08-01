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
package com.moosemorals.weather.reports;

import com.moosemorals.weather.types.Location;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * List of locations from the API.
 *
 * @author Osric Wilkinson <osric@fluffypeople.com>
 */
public class LocationReport implements Report {

    private final List<Location> locations;

    private LocationReport(List<Location> locations) {
        this.locations = locations;
    }

    /**
     * Get list of locations, as an
     * {@link java.util.Collections#unmodifiableList(java.util.List) unmodifiableList}.
     *
     * @return List&lt;Location&gt; unmodifiable list of locations
     */
    public List<Location> getLocations() {
        return locations;
    }

    public static class Builder {

        private final List<Location> locations;

        public Builder() {
            locations = new ArrayList<>();
        }

        public Builder addLocation(Location l) {
            locations.add(l);
            return this;
        }

        public LocationReport build() {
            return new LocationReport(Collections.unmodifiableList(locations));
        }
    }
}
