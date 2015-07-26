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
package com.moosemorals.weather.types;

/**
 * Confirmation from the API of the location that was requested.
 *
 * @author Osric Wilkinson <osric@fluffypeople.com>
 */
public class Location {

    private final String name;
    private final String type;

    private Location(String name, String type) {
        this.name = name;
        this.type = type;
    }

    /**
     * Name of the location.
     *
     * @return String name of the location
     */
    public String getName() {
        return name;
    }

    /**
     * Type of the location. Something like "UK Postcode" or "City".
     *
     * @return String type of location
     */
    public String getType() {
        return type;
    }

    /**
     * Build a new Location. Not really useful to end users.
     */
    public static class Builder {

        private String name;
        private String type;

        public Builder() {
            super();
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setType(String type) {
            this.type = type;
            return this;
        }

        public Location build() {
            return new Location(name, type);
        }
    }

}
