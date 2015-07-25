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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import static org.testng.Assert.assertEquals;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 *
 * @author osric
 */
public class BaseParserTest {

    public BaseParserTest() {
    }

    private XMLInputFactory factory;

    @BeforeClass
    public void setUpClass() throws Exception {
        factory = XMLInputFactory.newFactory();
    }

    @Test
    public void readIntAttribute() throws Exception {

        InputStream in = getInput("<?xml version=\"1.0\"?><a a=\"7\"/>");

        XMLStreamReader reader = factory.createXMLStreamReader(in, "UTF-8");
        reader.nextTag();

        ConcreteParser parser = new ConcreteParser();

        int result = parser.readIntAttribute(reader, "a");

        assertEquals(7, result);
    }

    @Test
    public void readDoubleAttribute() throws Exception {

        InputStream in = getInput("<?xml version=\"1.0\"?><a a=\"7.2\"/>");

        XMLStreamReader reader = factory.createXMLStreamReader(in, "UTF-8");
        reader.nextTag();

        ConcreteParser parser = new ConcreteParser();

        double result = parser.readDoubleAttribute(reader, "a");

        assertEquals(7.2, result);
    }

    @Test
    public void readLongAttribute() throws Exception {

        InputStream in = getInput("<?xml version=\"1.0\"?><a a=\"7\"/>");

        XMLStreamReader reader = factory.createXMLStreamReader(in, "UTF-8");
        reader.nextTag();

        ConcreteParser parser = new ConcreteParser();

        long result = parser.readLongAttribute(reader, "a");

        assertEquals(7, result);
    }

    @Test
    public void readStringAttribute() throws Exception {

        InputStream in = getInput("<?xml version=\"1.0\"?><a a=\"Hello, world\"/>");

        XMLStreamReader reader = factory.createXMLStreamReader(in, "UTF-8");
        reader.nextTag();

        ConcreteParser parser = new ConcreteParser();

        String result = parser.readStringAttribute(reader, "a");

        assertEquals("Hello, world", result);
    }

    @Test(expectedExceptions = XMLStreamException.class)
    public void readMissingAttribute() throws Exception {

        InputStream in = getInput("<?xml version=\"1.0\"?><a b=\"Hello, world\"/>");

        XMLStreamReader reader = factory.createXMLStreamReader(in, "UTF-8");
        reader.nextTag();

        ConcreteParser parser = new ConcreteParser();

        String result = parser.readStringAttribute(reader, "a");
    }

    @Test
    public void readStringTag() throws Exception {

        InputStream in = getInput("<?xml version=\"1.0\"?><a>Hello, world</a>");

        XMLStreamReader reader = factory.createXMLStreamReader(in, "UTF-8");
        reader.nextTag();

        ConcreteParser parser = new ConcreteParser();

        String result = parser.readTag(reader, "a");
        assertEquals("Hello, world", result);
    }

    @Test
    public void readFloatTag() throws Exception {

        InputStream in = getInput("<?xml version=\"1.0\"?><a>1.23</a>");

        XMLStreamReader reader = factory.createXMLStreamReader(in, "UTF-8");
        reader.nextTag();

        ConcreteParser parser = new ConcreteParser();

        float result = parser.readFloatTag(reader, "a");
        assertEquals(1.23, result, 0.001);
    }

    @Test
    public void readComplexStringTag() throws Exception {

        InputStream in = getInput("<?xml version=\"1.0\"?><a><b>Hello, world</b><c>Table</c></a>");

        XMLStreamReader reader = factory.createXMLStreamReader(in, "UTF-8");
        reader.nextTag();
        reader.nextTag();

        ConcreteParser parser = new ConcreteParser();

        String result = parser.readTag(reader, "b");
        assertEquals("Hello, world", result);

        reader.next();
        assertEquals("c", reader.getLocalName());
    }

    @Test
    public void skipTag() throws Exception {

        InputStream in = getInput("<?xml version=\"1.0\"?><a><b><c>Hello, world</c>More text</b><d>Garden</d></a>");

        XMLStreamReader reader = factory.createXMLStreamReader(in, "UTF-8");
        reader.nextTag(); // puts us on <a>
        assertEquals("a", reader.getLocalName());

        reader.nextTag(); // puts us on <b>
        assertEquals("b", reader.getLocalName());

        ConcreteParser parser = new ConcreteParser();
        parser.skipTag(reader); // skips <b> (and therefore c)

        reader.next(); // Get to start of next tag
        assertEquals("d", reader.getLocalName());
    }

    private static InputStream getInput(String source) throws UnsupportedEncodingException {
        return new ByteArrayInputStream(source.getBytes("UTF-8"));
    }

    private static class ConcreteParser extends BaseParser<Object> {

        @Override
        public Object parse(XMLStreamReader parser) throws XMLStreamException, IOException {
            throw new UnsupportedOperationException("Not supported yet.");
        }

    }
}
