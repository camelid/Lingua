/*
 * Copyright (c) 2015 Abe Jellinek
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

package me.abje.lingua.lexer;

import org.junit.Test;

import java.io.StringReader;

import static me.abje.lingua.lexer.Token.Type.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class LexerTest {
    @Test
    public void testNext() {
        Lexer lexer = new Lexer(new StringReader("abc*+ xyz/*test comment*/@annotation true false ->>"), "<test>");

        Token t = lexer.next();
        assertThat(t.getType(), is(NAME));
        assertThat(t.getValue(), is("abc"));

        t = lexer.next();
        assertThat(t.getType(), is(TIMES));
        assertThat(t.getValue(), is("*"));

        t = lexer.next();
        assertThat(t.getType(), is(PLUS));
        assertThat(t.getValue(), is("+"));

        t = lexer.next();
        assertThat(t.getType(), is(WHITESPACE));
        assertThat(t.getValue(), is(" "));

        t = lexer.next();
        assertThat(t.getType(), is(NAME));
        assertThat(t.getValue(), is("xyz"));

        t = lexer.next();
        assertThat(t.getType(), is(WHITESPACE));

        t = lexer.next();
        assertThat(t.getType(), is(ANNOTATION));
        assertThat(t.getValue(), is("annotation"));

        t = lexer.next();
        assertThat(t.getType(), is(WHITESPACE));

        t = lexer.next();
        assertThat(t.getType(), is(TRUE));
        assertThat(t.getValue(), is("true"));

        t = lexer.next();
        assertThat(t.getType(), is(WHITESPACE));

        t = lexer.next();
        assertThat(t.getType(), is(FALSE));
        assertThat(t.getValue(), is("false"));

        t = lexer.next();
        assertThat(t.getType(), is(WHITESPACE));

        t = lexer.next();
        assertThat(t.getType(), is(ARROW));
        assertThat(t.getValue(), is("->"));

        t = lexer.next();
        assertThat(t.getType(), is(GT));
        assertThat(t.getValue(), is(">"));
    }

    @Test
    public void testEndings() {
        Lexer lexer = new Lexer(new StringReader("abc\r\ndef\n\nghi\n"), "<test>");

        Token t = lexer.next();
        assertThat(t.getType(), is(NAME));
        assertThat(t.getValue(), is("abc"));
        assertThat(t.getLine(), is(1));

        t = lexer.next();
        assertThat(t.getType(), is(LINE));

        t = lexer.next();
        assertThat(t.getType(), is(NAME));
        assertThat(t.getValue(), is("def"));
        assertThat(t.getLine(), is(2));

        t = lexer.next();
        assertThat(t.getType(), is(LINE));

        t = lexer.next();
        assertThat(t.getType(), is(LINE));

        t = lexer.next();
        assertThat(t.getType(), is(NAME));
        assertThat(t.getValue(), is("ghi"));
        assertThat(t.getLine(), is(4));

        t = lexer.next();
        assertThat(t.getType(), is(LINE));
    }
}
