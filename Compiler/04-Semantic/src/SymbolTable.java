import java.util.*;

class SymbolTable {

    private static final Scope CURRENT_SCOPE = new Scope();
    private Stack<SymbolTableElement> stack;

    SymbolTable() {
        stack = new Stack<>();
        stack.push(CURRENT_SCOPE);
    }

    void enterScope() {
        stack.push(CURRENT_SCOPE);
    }

    void exitScope() {
        boolean isVar;
        do {
            SymbolTableElement pop = stack.pop();
            isVar = pop.isVariable();
        } while (isVar);
    }

    void addSymbol(String varName) {
        stack.push(new Variable(varName));
    }

    boolean checkSymbol(String varName) {
        int i = stack.search(new Variable(varName));
        return (i > 0);
    }

    interface SymbolTableElement {

        boolean isVariable();
    }

    private static class Scope implements SymbolTableElement {

        @Override
        public boolean isVariable() {
            return false;
        }
    }

    private class Variable implements SymbolTableElement {

        private String varType;
        private String varName;

        Variable(String name) {
            varType = "variable";
            varName = name;
        }

        @Override
        public boolean isVariable() {
            return true;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if ((o == null) || (getClass() != o.getClass())) {
                return false;
            }
            Variable variable = (Variable) o;
            return ((varType == null) || varType.equals(variable.varType)) &&
                    varName.equals(variable.varName);
        }

        @Override
        public int hashCode() {
            int result = varType.hashCode();
            result = 31 * result + varName.hashCode();
            return result;
        }
    }
}
