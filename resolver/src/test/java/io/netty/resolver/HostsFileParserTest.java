/*
 * Copyright 2015 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package io.netty.resolver;

import io.netty.util.CharsetUtil;
import io.netty.util.internal.ResourcesUtil;
import org.junit.Assume;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.Map;

import static org.junit.Assert.*;

public class HostsFileParserTest {

    @Test
    public void testParse() throws IOException {
        String hostsString = "127.0.0.1 host1" + "\n" + // single hostname, separated with blanks
                "::1 host1" + "\n" + // same as above, but IPv6
                "\n" + // empty line
                "192.168.0.1\thost2" + "\n" + // single hostname, separated with tabs
                "#comment" + "\n" + // comment at the beginning of the line
                " #comment  " + "\n" + // comment in the middle of the line
                "192.168.0.2  host3  #comment" + "\n" + // comment after hostname
                "192.168.0.3  host4  host5 host6" + "\n" + // multiple aliases
                "192.168.0.4  host4" + "\n" + // host mapped to a second address, must be ignored
                "192.168.0.5  HOST7" + "\n" + // uppercase host, should match lowercase host
                "192.168.0.6  host7" + "\n" // should be ignored since we have the uppercase host already
                ;

        HostsFileEntries entries = HostsFileParser.parse(new BufferedReader(new StringReader(hostsString)));
        Map<String, Inet4Address> inet4Entries = entries.inet4Entries();
        Map<String, Inet6Address> inet6Entries = entries.inet6Entries();

        assertEquals("Expected 7 IPv4 entries", 7, inet4Entries.size());
        assertEquals("Expected 1 IPv6 entries", 1, inet6Entries.size());
        assertEquals("127.0.0.1", inet4Entries.get("host1").getHostAddress());
        assertEquals("192.168.0.1", inet4Entries.get("host2").getHostAddress());
        assertEquals("192.168.0.2", inet4Entries.get("host3").getHostAddress());
        assertEquals("192.168.0.3", inet4Entries.get("host4").getHostAddress());
        assertEquals("192.168.0.3", inet4Entries.get("host5").getHostAddress());
        assertEquals("192.168.0.3", inet4Entries.get("host6").getHostAddress());
        assertNotNull("uppercase host doesn't resolve", inet4Entries.get("host7"));
        assertEquals("192.168.0.5", inet4Entries.get("host7").getHostAddress());
        assertEquals("0:0:0:0:0:0:0:1", inet6Entries.get("host1").getHostAddress());
    }

    @Test
    public void testParseUnicode() throws IOException {
        final Charset unicodeCharset;
        try {
            unicodeCharset = Charset.forName("unicode");
        } catch (UnsupportedCharsetException e) {
            Assume.assumeNoException(e);
            return;
        }
        testParseFile(HostsFileParser.parse(
                ResourcesUtil.getFile(getClass(),  "hosts-unicode"), unicodeCharset));
    }

    @Test
    public void testParseMultipleCharsets() throws IOException {
        final Charset unicodeCharset;
        try {
            unicodeCharset = Charset.forName("unicode");
        } catch (UnsupportedCharsetException e) {
            Assume.assumeNoException(e);
            return;
        }
        testParseFile(HostsFileParser.parse(ResourcesUtil.getFile(getClass(),  "hosts-unicode"),
                                            CharsetUtil.UTF_8, CharsetUtil.ISO_8859_1, unicodeCharset));
    }

    private static void testParseFile(HostsFileEntries entries) throws IOException {
        Map<String, Inet4Address> inet4Entries = entries.inet4Entries();
        Map<String, Inet6Address> inet6Entries = entries.inet6Entries();

        assertEquals("Expected 2 IPv4 entries", 2, inet4Entries.size());
        assertEquals("Expected 1 IPv6 entries", 1, inet6Entries.size());
        assertEquals("127.0.0.1", inet4Entries.get("localhost").getHostAddress());
        assertEquals("255.255.255.255", inet4Entries.get("broadcasthost").getHostAddress());
        assertEquals("0:0:0:0:0:0:0:1", inet6Entries.get("localhost").getHostAddress());
    }
}
