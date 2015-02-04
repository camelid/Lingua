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

package me.abje.lingua.parser.parselet;

import me.abje.lingua.lexer.Token;
import me.abje.lingua.parser.ParseException;
import me.abje.lingua.parser.Parser;
import me.abje.lingua.parser.Precedence;
import me.abje.lingua.parser.expr.*;

/**
 * Parses a binary mutator expression, in the form of <code>a += b</code>.
 */
public class BinaryMutatorParselet implements InfixParselet {
    /**
     * The token type of the operation.
     */
    private Token.Type type;

    /**
     * Creates a new binary mutator parselet.
     * @param type The token type of the operation.
     */
    public BinaryMutatorParselet(Token.Type type) {
        this.type = type;
    }

    @Override
    public Expr parse(Parser parser, Expr left, Token token) {
        if (left instanceof IndexExpr) {
            IndexExpr index = (IndexExpr) left;
            Expr value = parser.next(Precedence.ASSIGNMENT - 1);
            return new IndexSetExpr(index.getTarget(), index.getIndex(), new OperatorExpr(left, type, value));
        } else if (left instanceof MemberAccessExpr) {
            MemberAccessExpr member = (MemberAccessExpr) left;
            Expr value = parser.next(Precedence.ASSIGNMENT - 1);
            return new MemberSetExpr(member.getLeft(), member.getName(), new OperatorExpr(left, type, value));
        } else if (left instanceof NameExpr) {
            Expr value = parser.next(Precedence.ASSIGNMENT);
            return new AssignmentExpr(((NameExpr) left).getValue(), new OperatorExpr(left, type, value));
        } else {
            throw new ParseException("assignments must have a function call, index expression, or variable as a target");
        }
    }

    @Override
    public int getPrecedence() {
        return Precedence.ASSIGNMENT;
    }
}