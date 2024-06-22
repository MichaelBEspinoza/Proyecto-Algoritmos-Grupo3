package structures.stack;

public class LinkedStack  implements Stack {
    private Node top;// es un apuntador
    private int counter; //cont elementos apilados

    public LinkedStack(){
        this.top = null;
        this.counter = 0;
    }

    @Override
    public int size() {
        return counter;
    }

    @Override
    public void clear() {
        top = null;
        this.counter = 0;
    }

    @Override
    public boolean isEmpty() {
        return top==null;
        //return counter==0;
    }

    @Override
    public Object peek() throws StackException {
        if(isEmpty())
            throw new StackException("Linked Stack is empty");
        return top.data;
    }

    @Override
    public Object top() throws StackException {
        if(isEmpty())
            throw new StackException("Linked Stack is empty");
        return top.data;
    }

    @Override
    public void push(Object element) throws StackException {
        Node newNode = new Node(element);
        if(isEmpty()){
            //creamos un nuevo nodo
            top = newNode;
        }
        else{
            newNode.next = top; //hacemos el enlace entre nodos
            top = newNode; //la decimos a tope q apunte a newNode
        }
        this.counter++; //incremento el contador
    }

    @Override
    public Object pop() throws StackException {
        if(isEmpty())
            throw new StackException("Linked Stack is empty");
        Object topElement = top.data;
        top = top.next; //movemos tope al sgte nodo
        counter--;
        return topElement;
    }

    @Override
    public String toString() {
        if(isEmpty()) return "Linked Stack is Empty";
        String result = "\nLinked Stack Content";
        try {
            LinkedStack aux = new LinkedStack();
            while(!isEmpty()){
                result+="\n"+peek();
                aux.push(pop());
            }

            //dejamos la pila como al inicio
            while(!aux.isEmpty()){
                push(aux.pop());
            }
        } catch (StackException ex) {
            System.out.println(ex.getMessage());
        }
        return result;
    }

}
