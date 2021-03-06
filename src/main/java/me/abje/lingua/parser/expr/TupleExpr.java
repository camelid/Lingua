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

import com.google.common.base.Joiner;
import me.abje.lingua.interpreter.Environment;
import me.abje.lingua.interpreter.Interpreter;
import me.abje.lingua.interpreter.InterpreterException;
import me.abje.lingua.interpreter.obj.Obj;
import me.abje.lingua.interpreter.obj.TupleObj;
import me.abje.lingua.lexer.Token;
import me.abje.lingua.util.DefinitionType;

import java.util.List;
import java.util.stream.Collectors;

public class TupleExpr extends Expr {
    private final List<Expr> items;

    public TupleExpr(Token token, List<Expr> items) {
        super(token);
        this.items = items;
    }

    @Override
    public Obj evaluate(Interpreter interpreter) {
        return new TupleObj(items.stream().map(interpreter::next).collect(Collectors.toList()));
    }

    @Override
    public Obj match(Interpreter interpreter, Environment.Frame frame, Obj obj, DefinitionType type) {
        if (obj instanceof TupleObj) {
            TupleObj tuple = (TupleObj) obj;
            for (int i = 0; i < items.size(); i++) {
                Expr item = items.get(i);
                if (item.getAnnotations().contains("rest")) {
                    if (i == items.size() - 1) {
                        if (tuple.size() < i || item.match(interpreter, frame, tuple.drop(i), type) == null) {
                            return null;
                        } else {
                            return obj;
                        }
                    } else {
                        throw new InterpreterException("CallException", "weird @rest annotation");
                    }
                }
                if (tuple.size() <= i || item.match(interpreter, frame, tuple.get(i), type) == null) {
                    return null;
                }
            }
            return obj;
        } else {
            return null;
        }
    }

    public List<Expr> getItems() {
        return items;
    }

    @Override
    public String toString() {
        return "(" + Joiner.on(", ").join(items) + ")";
    }
}
