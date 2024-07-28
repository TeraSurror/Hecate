import java.util.List;
import java.util.Map;

public class HecateClass implements HecateCallable {
    final String name;
    final HecateClass superclass;
    private final Map<String, HecateFunction> methods;

    HecateClass(String name, HecateClass superclass, Map<String, HecateFunction> methods) {
        this.name = name;
        this.methods = methods;
        this.superclass = superclass;
    }

    HecateFunction findMethod(String name) {
        if (methods.containsKey(name)) {
            return methods.get(name);
        }

        if (superclass != null) {
            return superclass.findMethod(name);
        }

        return null;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int arity() {
        HecateFunction initilizer = findMethod("init");
        if (initilizer == null) return 0;
        return initilizer.arity();
    }

    @Override
    public Object call(Interpreter interpreter, List<Object> arguments) {
        HecateInstance instance = new HecateInstance(this);
        HecateFunction initializer = findMethod("init");
        if (initializer != null) {
            initializer.bind(instance).call(interpreter, arguments);
        }
        return instance;
    }
}
