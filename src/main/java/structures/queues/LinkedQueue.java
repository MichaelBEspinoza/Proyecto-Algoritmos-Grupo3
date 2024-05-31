/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package structures.queues;

/**
 *
 * @author Profesor Lic. Gilberth Chaves A.
 * Cola enlazada
 */
public class LinkedQueue implements Queue {
    private Node front; //anterior
    private Node rear; //posterior
    private int count; //control de elementos encolados
    
    //Constructor
    public LinkedQueue(){
        front=rear=null;
        count=0;
    }
    
    public Node getFront(){
        return this.front;
    }

    @Override
    public int size() {
        return count;
    }

    @Override
    public void clear() {
        front=rear=null;
        count=0;
    }

    @Override
    public boolean isEmpty() {
        return front==null;
    }

    @Override
    public int indexOf(Object element) throws QueueException {
        if(isEmpty())
            throw new QueueException("Linked Queue is Empty");
        LinkedQueue aux = new LinkedQueue();
        int pos1=1;
        int pos2=-1; //si es -1 no existe
        while(!isEmpty()){
            if(util.Utility.compare(front(), element)==0){
                pos2 = pos1;
            }
            aux.enQueue(deQueue());
            pos1++;
        }//while
       //al final dejamos la cola en su estado original
        while(!aux.isEmpty()){
            enQueue(aux.deQueue());
        }
        return pos2;
    }

    @Override
    public void enQueue(Object element) throws QueueException {
        Node newNode = new Node(element);
        if(isEmpty()){ //la cola no existe
            rear = newNode;
            //garantizo q anterior quede apuntando al primer nodo
            front=rear; //anterior=posterior 
        }else{ //significa q al menos hay un elemento en la cola
            rear.next = newNode; //posterior.sgte = nuevoNodo
            rear = newNode; //posterior = nuevoNodo
        }
        //al final actualizo el contador
        count++;
    }
    
    @Override
    public void enQueue(Object element, Integer priority) throws QueueException {
        Node newNode = new Node(element, priority);
        if(isEmpty()){ //la cola no existe
            rear = newNode;
            //garantizo que anterior quede apuntando al primer nodo
            front = rear;
        }else{ //que pasa si ya hay elementos encolados
            Node aux = front;
            Node prev = front;
            while(aux!=null&&aux.priority>=priority){
                prev = aux; //dejo un rastro
                aux = aux.next;
            }
            //se sale cuando alcanza nulo o la prioridad del nuevo elemento
            //es mayor
            if(aux==front){
                newNode.next = front;
                front = newNode;
            }else
                if(aux==null){
                    prev.next = newNode;
                    rear = newNode;
                }else{ //en cualquier otro caso
                    prev.next = newNode;
                    newNode.next = aux;
                }
        }
    }

    @Override
    public Object deQueue() throws QueueException {
        if(isEmpty())
            throw new QueueException("Linked Queue is Empty");
        Object element = front.data;
        //caso 1. cuando solo hay un elemento
        //cuando estan apuntando al mismo nodo
        if(front==rear){
            clear(); //elimino la cola
        }else{ //caso 2. caso contrario
            front = front.next; //anterior=anterior.sgte
        }
        //actualizo el contador de elementos encolados
        count--;
        return element;
    }

    @Override
    public boolean contains(Object element) throws QueueException {
        if(isEmpty())
            throw new QueueException("Linked Queue is Empty");
        LinkedQueue aux = new LinkedQueue();
        boolean finded = false;
        while(!isEmpty()){
            if(util.Utility.compare(front(), element)==0){
                finded = true;
            }
            aux.enQueue(deQueue());
        }//while
        //al final dejamos la cola en su estado original
        while(!aux.isEmpty()){
            enQueue(aux.deQueue());
        }
        return finded;
    }

    @Override
    public Object peek() throws QueueException {
        if(isEmpty())
            throw new QueueException("Linked Queue is Empty");
        return front.data;
    }

    @Override
    public Object front() throws QueueException {
        if(isEmpty())
            throw new QueueException("Linked Queue is Empty");
        return front.data;
    }
    
    public Integer frontPriority() throws QueueException {
        if(isEmpty())
            throw new QueueException("Linked Queue is Empty");
        return front.priority;
    }
    
    @Override
    public String toString(){
        if(isEmpty()) return "Linked Queue is Empty";
        String result = "\nLinked Queue Content:\n";
        try {
            LinkedQueue aux = new LinkedQueue();
            while(!isEmpty()){
                result+=front()+"\n";
                aux.enQueue(deQueue());
            }
            //al final dejamos la cola en su estado original
            while(!aux.isEmpty()){
                enQueue(aux.deQueue());
            }
        }catch(QueueException ex){
            System.out.println(ex.getMessage());
        }
        return result;
    }
}
