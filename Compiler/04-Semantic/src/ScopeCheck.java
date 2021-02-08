import java.util.*;

class ScopeCheck {

    private ArrayList<String> errors = new ArrayList<>();
    private SymbolTable symbolTable;

    void run(MainFrame frame) {
        symbolTable = new SymbolTable();
        checkScope(ASTNode.getRootNode().getChildren().get(0).getChildren().get(5));
        if (errors.size() > 0) {
            errors.forEach(frame::showSemanticError);
        } else {
            frame.showNoError();
        }
    }

    private void checkScope(ASTNode node) {
        if (node.getToken() != null) {
            if (node.getToken().getTokenLexeme().equals("{")) {
                symbolTable.enterScope();
            } else if (node.getToken().getTokenLexeme().equals("}")) {
                symbolTable.exitScope();
            }
        }
        switch (node.getName()) {
            case "INIT":
                for (ASTNode child : node.getChildren()) {
                    if (child.getName().equals("IDENTIFIER")) {
                        if (symbolTable.checkSymbol(child.getToken()
                                .getTokenLexeme())) {
                            errors.add("Semantic error at line " + child.getToken
                                    ().getTokenLine() + ":\n" + "variable <" +
                                    child.getToken().getTokenLexeme() + "> is " +
                                    "already " + "defined");
                        } else {
                            symbolTable.addSymbol(child.getToken()
                                    .getTokenLexeme());
                        }
                    } else {
                        checkScope(child);
                    }
                }
                break;
            case "IDENTIFIER":
                if (!symbolTable.checkSymbol(node.getToken()
                        .getTokenLexeme())) {
                    errors.add("Semantic error at line " + node.getToken()
                            .getTokenLine() + ":\n" + "variable <" + node.getToken
                            ().getTokenLexeme() + "> is not defined");
                }
                break;
            default:
                node.getChildren().forEach(this::checkScope);
                break;
        }
    }
}
