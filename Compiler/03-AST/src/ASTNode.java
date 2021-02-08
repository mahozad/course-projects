import java.util.*;

class ASTNode {

    private List<ASTNode> children = new ArrayList<>();
    private static ASTNode rootNode;
    private Token token = null;
    private final String name;

    private ASTNode(String nodeName) {
        name = nodeName;
    }

    private ASTNode(String nodeName, Token nodeToken) {
        name = nodeName;
        token = nodeToken;
    }

    static void resetRootNode() {
        rootNode = null;
    }

    static ASTNode getRootNode() {
        if (rootNode == null) {
            rootNode = new ASTNode("");
        }
        return rootNode;
    }

    void clear() {
        getChildren().clear();
    }

    private List<ASTNode> getChildren() {
        return children;
    }

    ASTNode addChild(String name) {
        ASTNode newNode = new ASTNode(name);
        children.add(newNode);
        return newNode;
    }

    ASTNode addChild(Token token, String name) {
        ASTNode newNode = new ASTNode(name, token);
        children.add(newNode);
        return newNode;
    }

    void print(ASTFrame dialog) {
        print("", true, dialog);
    }

    private void print(String prefix, boolean isTail, ASTFrame dialog) {
        if (token != null) {
            dialog.setText(prefix + (isTail ? "└── " : "├── ") + name + "#" +
                    token.getTokenLexeme() + "\n");
        } else {
            dialog.setText(prefix + (isTail ? "└── " : "├── ") + name + "\n");
        }
        for (int i = 0; i < children.size() - 1; i++) {
            children.get(i).print(prefix + (isTail ? "    " : "│   "), false,
                    dialog);
        }
        if (children.size() > 0) {
            children.get(children.size() - 1).print(prefix + (isTail ? "    " :
                    "│" + "   "), true, dialog);
        }
    }
}
