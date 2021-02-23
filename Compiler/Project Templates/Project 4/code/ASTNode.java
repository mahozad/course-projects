import java.util.ArrayList;
import java.util.List;

/**
 * Created by mojtab23 on 10/17/2016.
 */
public class ASTNode {


    private static ASTNode rootNode;
    private final String name;
    private Token token = null;
    private List<ASTNode> children = new ArrayList<>();

    public ASTNode(String name) {
        this.name = name;
    }

    public ASTNode(String name, Token token) {
        this.name = name;
        this.token = token;
    }

    public static ASTNode getRootNode() {
        if (rootNode == null) {
            rootNode = new ASTNode("");
        }
        return rootNode;
    }

    public List<ASTNode> getChildren() {
        return children;
    }

    public ASTNode addChild(String name) {
        ASTNode newNode = new ASTNode(name);
        this.children.add(newNode);
        return newNode;
    }

    public ASTNode addChild(String name, Token token) {
        ASTNode newNode = new ASTNode(name, token);
        this.children.add(newNode);
        return newNode;
    }


    public void print() {
        print("", true);
    }

    private void print(String prefix, boolean isTail) {
        if (token != null)
            System.out.println(prefix + (isTail ? "└── " : "├── ") + name + "#" + token.getData());
        else
            System.out.println(prefix + (isTail ? "└── " : "├── ") + name);
        for (int i = 0; i < children.size() - 1; i++) {
            children.get(i).print(prefix + (isTail ? "    " : "│   "), false);
        }
        if (children.size() > 0) {
            children.get(children.size() - 1)
                    .print(prefix + (isTail ? "    " : "│   "), true);
        }
    }


}
