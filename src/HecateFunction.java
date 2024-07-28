import java.util.List;

public class HecateFunction implements HecateCallable{
    private final Stmt.Function declaration;
    private final Environment closure;
    private final boolean isInitializer;

    HecateFunction (Stmt.Function declaration, Environment closure, boolean isInitializer) {
        this.declaration = declaration;
        this.isInitializer = isInitializer;
        this.closure = closure;
    }

    HecateFunction bind(HecateInstance instance) {
        Environment enviroment = new Environment(closure);
        enviroment.define("this", instance);
        return new HecateFunction(declaration, enviroment, isInitializer);
    }

    @Override
    public int arity() {
        return declaration.params.size();
    }

    @Override
    public Object call(Interpreter interpreter, List<Object> arguments) {
        Environment environment = new Environment(closure);
        for (int i = 0; i < declaration.params.size(); i++) {
            environment.define(declaration.params.get(i).lexeme, arguments.get(i));
        }

        try {
            interpreter.excecuteBlock(declaration.body, environment);
        } catch (Return returnValue) {
            if (isInitializer) return closure.getAt(0, "this");
            return returnValue.value;
        }

        if (isInitializer) return closure.getAt(0, "this");

        return null;
    }

    @Override
    public String toString() {
        return "<fn " + declaration.name.lexeme + ">";
    }
}
