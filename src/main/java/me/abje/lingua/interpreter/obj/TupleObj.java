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

import com.google.common.collect.ImmutableMap;
import me.abje.lingua.interpreter.Bridge;
import me.abje.lingua.interpreter.Interpreter;
import me.abje.lingua.interpreter.InterpreterException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class TupleObj extends Obj {
    public static final ClassObj SYNTHETIC = bridgeClass(TupleObj.class);
    private static final ImmutableMap<String, Integer> ACCESSORS = ImmutableMap.<String, Integer>builder().
            put("first", 0).
            put("second", 1).
            put("third", 2).
            put("fourth", 3).
            put("fifth", 4).
            put("sixth", 5).
            put("seventh", 6).
            put("eighth", 7).
            put("ninth", 8).
            put("_1", 0).
            put("_2", 1).
            put("_3", 2).
            put("_4", 3).
            put("_5", 4).
            put("_6", 5).
            put("_7", 6).
            put("_8", 7).
            put("_9", 8).
            build();

    private final List<Obj> items;

    public TupleObj(List<Obj> items) {
        super(SYNTHETIC);
        this.items = items;
    }

    public Obj get(int i) {
        return items.get(i);
    }

    public int size() {
        return items.size();
    }

    @Bridge
    public ListObj drop(int n) {
        return new ListObj(items.subList(n, items.size()));
    }

    @Override
    public String toString() {
        return "(" + items.stream().map(Obj::toString).collect(Collectors.joining(", ")) + ")";
    }

    @Override
    public Obj getMember(Interpreter interpreter, String name) {
        if (ACCESSORS.containsKey(name)) {
            int index = ACCESSORS.get(name);
            if (size() > index) {
                return get(index);
            } else {
                return super.getMember(interpreter, name);
            }
        }
        return super.getMember(interpreter, name);
    }

    @Override
    public void setMember(Interpreter interpreter, String name, Obj value) {
        if (ACCESSORS.containsKey(name)) {
            int index = ACCESSORS.get(name);
            if (size() >= index) {
                throw new InterpreterException("InvalidOperationException", "tuples are immutable");
            } else {
                super.setMember(interpreter, name, value);
            }
        }
        super.setMember(interpreter, name, value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TupleObj tupleObj = (TupleObj) o;
        return Objects.equals(items, tupleObj.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(items);
    }
}
