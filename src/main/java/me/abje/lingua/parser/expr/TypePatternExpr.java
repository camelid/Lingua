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

package me.abje.lingua.parser.expr;

import me.abje.lingua.interpreter.Environment;
import me.abje.lingua.interpreter.Interpreter;
import me.abje.lingua.interpreter.InterpreterException;
import me.abje.lingua.interpreter.obj.Obj;
import me.abje.lingua.lexer.Token;
import me.abje.lingua.util.DefinitionType;

public class TypePatternExpr extends Expr {
    private final Expr left;
    private final String type;

    public TypePatternExpr(Token token, Expr left, String type) {
        super(token);
        this.left = left;
        this.type = type;
    }

    @Override
    public Obj evaluate(Interpreter interpreter) {
        throw new InterpreterException("InvalidOperationException", "cannot evaluate type pattern");
    }

    @Override
    public Obj match(Interpreter interpreter, Environment.Frame frame, Obj obj, DefinitionType type) {
        if (left.match(interpreter, frame, obj, type).getType().isSubclassOf(interpreter.getEnv().get(this.type))) {
            return obj;
        } else {
            return null;
        }
    }

    @Override
    public String toString() {
        return left + ": " + type;
    }
}
