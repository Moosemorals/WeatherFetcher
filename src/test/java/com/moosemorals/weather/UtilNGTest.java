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
package com.moosemorals.weather;

import static org.testng.Assert.assertEquals;
import org.testng.annotations.Test;

/**
 *
 * @author Osric Wilkinson <osric@fluffypeople.com>
 */
public class UtilNGTest {

    public UtilNGTest() {
    }

    @Test
    public void assembleURL_noParam() throws Exception {
        String result = Util.assembleURL("a");

        assertEquals(result, "a");
    }

    @Test
    public void assembleURL_oneParam() throws Exception {
        String result = Util.assembleURL("a", "b", "c");

        assertEquals(result, "a?b=c");
    }

    @Test
    public void assembleURL_twoParam() throws Exception {
        String result = Util.assembleURL("a", "b", "c", "d", "e");

        assertEquals(result, "a?b=c&d=e");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void assembleURL_oddParam() throws Exception {
        String result = Util.assembleURL("a", "b");
    }

}
