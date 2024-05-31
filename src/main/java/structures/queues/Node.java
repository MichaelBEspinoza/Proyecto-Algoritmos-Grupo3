/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package structures.queues;

/**
 *
 * @author Profesor Lic. Gilberth Chaves A.
 */
public class Node {
    public Object data; //elemento guardado en el nodo
    public Integer priority; //1=high, 2=medium, 3=low
    public Node next; //enlace al sgte nodo
    
    //Constructor
    public Node(Object data){
        this.data = data;
        this.next = null; //apunta a nulo
    }
    
    //Constructor sobrecargado
    public Node(Object data, Integer priority){
        this.data = data;
        this.priority = priority;
        this.next = null; //apunta a nulo
    }
    
    //Constructor sobrecargado
    public Node(){
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

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }        
    
}
