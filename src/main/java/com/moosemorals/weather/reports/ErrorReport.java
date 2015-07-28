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

/**
 * Error report from the API.
 *
 * @author Osric Wilkinson <osric@fluffypeople.com>
 */
public class ErrorReport implements Report {

    private final String type;
    private final String message;
    private final Exception cause;

    public ErrorReport(String type, String message) {
        this.type = type;
        this.message = message;
        this.cause = null;
    }

    public ErrorReport(Exception ex) {
        this.type = ex.getClass().getName();
        this.message = ex.getMessage();
        this.cause = ex;
    }

    /**
     * Type of error.
     *
     * @return String type of error
     */
    public String getType() {
        return type;
    }

    /**
     * Human readable explanation of message.
     *
     * @return String error message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Cause (if from exception). May be null
     */
    public Exception getCause() {
        return cause;
    }

}
