package structures.lists;

public class Node {
    public Object data;
    public Node prev; //apuntador al nodo anterior
    public Node next; //apuntador al nodo siguiente

    public Node(Object data) {
        this.data = data;
        this.prev = this.next = null;
    }
}
