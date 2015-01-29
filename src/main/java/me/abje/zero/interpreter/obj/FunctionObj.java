package me.abje.zero.interpreter.obj;

import me.abje.zero.interpreter.Environment;
import me.abje.zero.interpreter.Interpreter;
import me.abje.zero.interpreter.InterpreterException;
import me.abje.zero.parser.expr.Expr;

import java.util.List;

public class FunctionObj extends Obj {
    private String name;
    private List<String> argNames;
    private Expr body;

    public FunctionObj(List<String> argNames, Expr body) {
        this("<anon>", argNames, body);
    }

    public FunctionObj(String name, List<String> argNames, Expr body) {
        this.name = name;
        this.argNames = argNames;
        this.body = body;
    }

    public String getName() {
        return name;
    }

    public List<String> getArgNames() {
        return argNames;
    }

    public Expr getBody() {
        return body;
    }

    @Override
    public Obj call(Interpreter interpreter, List<Obj> args) {
        if (args.size() != argNames.size())
            throw new InterpreterException("invalid number of arguments for function " + name);

        Environment env = interpreter.getEnv();
        env.pushFrame();
        for (int i = 0; i < args.size(); i++) {
            env.define(argNames.get(i), args.get(i));
        }
        Obj obj = body.evaluate(interpreter);
        env.popFrame();
        return obj;
    }

    @Override
    public String toString() {
        return "<function " + name + ">";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FunctionObj that = (FunctionObj) o;

        return argNames.equals(that.argNames) && body.equals(that.body) && name.equals(that.name);

    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + argNames.hashCode();
        result = 31 * result + body.hashCode();
        return result;
    }
}
