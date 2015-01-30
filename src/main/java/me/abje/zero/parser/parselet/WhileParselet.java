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

package me.abje.zero.parser.parselet;

import me.abje.zero.lexer.Token;
import me.abje.zero.parser.Parser;
import me.abje.zero.parser.expr.BlockExpr;
import me.abje.zero.parser.expr.Expr;
import me.abje.zero.parser.expr.WhileExpr;

import java.util.ArrayList;
import java.util.List;

public class WhileParselet implements PrefixParselet {
    private boolean doWhile;

    public WhileParselet(boolean doWhile) {
        this.doWhile = doWhile;
    }

    @Override
    public Expr parse(Parser parser, Token token) {
        Expr condition;
        Expr body;
        if (doWhile) {
            List<Expr> exprs = new ArrayList<>();
            while (parser.peek() != null && !parser.peek().is(Token.Type.WHILE)) {
                exprs.add(parser.next());
            }
            parser.expect(Token.Type.WHILE);
            condition = parser.next();
            body = new BlockExpr(exprs);
        } else {
            condition = parser.next();
            body = parser.next();
        }
        return new WhileExpr(condition, body);
    }
}
