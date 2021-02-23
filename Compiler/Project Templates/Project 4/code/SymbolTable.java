import java.util.Stack;

/**
 * Created by mojtab23 on 11/18/16.
 */
public class SymbolTable {
    private final static Scope currentScope = new Scope();
    private Stack<SymbolTableElement> stack;

    public SymbolTable() {
        stack = new Stack<>();
        stack.push(currentScope);
    }

    public void enterScope() {
        stack.push(currentScope);
    }

    public void exitScope() {
        boolean isVar;
        do {
            SymbolTableElement pop = stack.pop();
            isVar = pop.isVariable();
        } while (isVar);
    }

    public void addSymbol(String varType, String varName) {
        stack.push(new Variable(varType, varName));
    }

    public boolean checkSymbol(String varType, String varName) {
        int scopePosition = stack.search(currentScope);
        int i = stack.search(new Variable(varType, varName));
        return (i >= 0) && (i < scopePosition);
    }

    public Variable findSymbol(String varName) {
        int i = stack.lastIndexOf(new Variable(null, varName));
        return (Variable) stack.get(i);
    }

    interface SymbolTableElement {
        boolean isVariable();
    }

    public static class Scope implements SymbolTableElement {
        @Override
        public boolean isVariable() {
            return false;
        }

        @Override
        public String toString() {
            return "Scope{}";
        }
    }

    public class Variable implements SymbolTableElement {
        String varType;
        String varName;

        public Variable(String type, String name) {
            this.varType = type;
            this.varName = name;
        }

        @Override
        public String toString() {
            return "Variable{" +
                    "varType='" + varType + '\'' +
                    ", varName='" + varName + '\'' +
                    '}';
        }

        @Override
        public boolean isVariable() {
            return true;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if ((o == null) || (getClass() != o.getClass()))
                return false;
            Variable variable = (Variable) o;
            return ((varType == null) || varType.equals(variable.varType)) && varName.equals(variable.varName);
        }

        @Override
        public int hashCode() {
            int result = varType.hashCode();
            result = 31 * result + varName.hashCode();
            return result;
        }
    }
}