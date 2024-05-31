package structures.trees;

public class BTreeNode {
    public Object data;
    public BTreeNode left, right; //hijo izq, hijo der
    public String path; //valores: root/left/right

    //Constructor
    public BTreeNode(Object data){
        this.data = data;
        this.left=this.right=null;
    }

    //Constructor sobrecargado
    public BTreeNode(Object data, String path){
        this.data = data;
        this.path = path;
        this.left=this.right=null;
    }

}
