package structures.lists;

import java.io.Serializable;

public class Node implements Serializable {
    public Object data;
    public Node prev; //apuntador al nodo anterior
    public Node next; //apuntador al nodo siguiente

    public Node(Object data) {
        this.data = data;
        this.prev = this.next = null;
    }
}
