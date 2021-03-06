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

package me.abje.lingua.interpreter.obj;

import me.abje.lingua.interpreter.Interpreter;
import me.abje.lingua.interpreter.InterpreterException;

/**
 * The Lingua "null" singleton. Has no fields, cannot be invoked, and is generally quite useless.
 */
public class NullObj extends Obj {
    public static final ClassObj SYNTHETIC = bridgeClass(NullObj.class);

    /**
     * The singleton instance.
     */
    public static final NullObj NULL = new NullObj();

    /**
     * Constructs a new Null. This constructor is for private use.
     */
    private NullObj() {
        super(SYNTHETIC);
    }

    @Override
    public Obj getMember(Interpreter interpreter, String name) {
        throw new InterpreterException("NullReferenceException", "attempt to dereference null");
    }

    @Override
    public void setMember(Interpreter interpreter, String name, Obj value) {
        throw new InterpreterException("NullReferenceException", "attempt to dereference null");
    }

    @Override
    public String toString() {
        return "null";
    }

    /**
     * Null is not truthy.
     *
     * @return False.
     */
    @Override
    public boolean isTruthy() {
        return false;
    }
}
