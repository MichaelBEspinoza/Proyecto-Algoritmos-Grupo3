package structures.stack;

public class Node {
    public Object data; //elemento guardado en el nodo
    public Node next; //enlace al sgte nodo

    public Node(Object data){
        this.data = data;
        this.next = null; //apunta a nulo
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }

    @Override
    public String toString() {
        return "Node{" + "data=" + data + '}';
    }



}
