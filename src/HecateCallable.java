import java.util.List;

interface HecateCallable {
    int arity();
    Object call(Interpreter interpreter, List<Object> arguments);
}
