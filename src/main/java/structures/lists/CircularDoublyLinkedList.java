package structures.lists;

public class CircularDoublyLinkedList implements List{
    private Node first; //Apuntador al inicio de la lista
    private Node last; //Apuntador al final de la lista

    public CircularDoublyLinkedList() {
        this.first = this.last = null; // La lista no existe.
    } // End of constructor for class [SinglyLinkedList].

    @Override
    public int size() {
        if (isEmpty()) return 0; // Verifica si la lista está vacía

        Node aux = first;
        int count = 1; // Inicia el conteo ya contando el nodo 'first'

        // Recorre los nodos hasta llegar nuevamente al 'first'
        while (aux.next != first) {
            count++;
            aux = aux.next; // Se mueve al siguiente nodo
        }
        return count;
    }// End of method [size].

    @Override
    public void clear() {
        // Método que limpia la lista borrando el nodo siguiente del primer elemento.
        this.first = this.last = null; // Anulamos la lista
    }// End of method [clear].

    @Override
    public boolean isEmpty() {
        // Método que indica si la lista está vacía o no.
        return this.first == null; // Si es nulo, está vacía.
    }// End of method [isEmpty].

    public boolean contains(Object element) throws ListException {
        if (isEmpty()) throw new ListException("Circularly linked list is empty");

        Node aux = first;
        do {
            if (aux.data.equals(element)) return true;
            aux = aux.next;
        } while (aux != first); // Se sigue recorriendo hasta volver al nodo inicial

        return false; // Si se completa el ciclo y no se encontró el elemento
    }

    @Override
    public void add(Object element) {
        Node newNode = new Node(element);
        if(isEmpty()) first = last = newNode;
        else {
            last.next = newNode;
            //SE coloca a last a apuntar al ultimo nodo
            last = newNode;

        }
        //Se realiza el enlace Circular
        last.next = first;
        //Se realiza el enlace doble
        first.prev = last;

    }// End of method [add].

    @Override
    public void addFirst(Object element) {

        Node newNode = new Node(element);

        if(isEmpty()) first = last = newNode;
        else{
            newNode.next = first;
            //Hago el enlace doble
            first.prev = newNode;
            first = newNode;
        }// End of 'else'.

        //Se garantiza el enlace Circular y doble
        last.next = first;
        first.prev = last;

    }// End of method [addFirst].

    @Override
    public void addLast(Object element) {
        //Método que añade un elemento al final de la lista.
        //Fundamentalmente, es el mismo que 'add'.
        Node newNode = new Node(element);
        if(isEmpty()) first = newNode;
        else{
            Node aux = first;
            while(aux.next!=null) aux = aux.next;
            aux.next = newNode;
        }// End of 'else'.
    }// End of method [addLast].

    @Override
    public void addInSortedList(Object element) {
        // Añade un nuevo cliente y ordena la lista alfabéticamente según los nombres de sus clientes.
        try{
            add(element);
            sort();
        }catch (ListException LE){
            throw new RuntimeException(LE);
        }// End of 'catch'.
    }// End of method [addInSortedList].

    @Override
    public void remove(Object element) throws ListException {
        if (isEmpty()) throw new ListException("Circularly Linked List is empty.");

        // Caso #1: el elemento a suprimir esta al inicio.
        if (util.Utility.compare(first.data, element) == 0)
            first = first.next;
            // Caso #2: el elemento a suprimir puede estar al medio o al final.
        else {
            Node prev = first; // Dejo un apuntador al nodo anterior.
            Node aux = first.next;
            while (aux != last && !(util.Utility.compare(aux.data, element) == 0)) {
                prev = aux;
                aux = aux.next;
            }// End of 'while'.
            // Se sale cuando alcanza bulo o cuando encuentra el elemento.
            if (util.Utility.compare(aux.data, element) == 0)
                // Ya lo encontro, procedemos a desenlazar el nodo.
                prev.next = aux.next;

            if (aux == last && util.Utility.compare(aux.data, element) == 0)
                last = prev; // Desenlaza el último nodo.

        }//End of 'else'.

        // Mantengo el enlace circular.
        last.next = first;

        // Otro caso: ¿qué pasa si solo queda un nodo y es el que quiero eliminar?
        if (first == last && util.Utility.compare(first.data, element) == 0)
            clear(); // Anulo la lista.
    }// End of method [remove].

    @Override
    public Object removeFirst() throws ListException {
        if (isEmpty()) throw new ListException("Circularly Linked List is Empty");

        // Guardar el dato del nodo que será eliminado
        Object data = first.data;

        // Caso de un solo nodo en la lista
        if (first == last)
            first = last = null; // La lista queda completamente vacía
        else {
            // Mover el 'first' al siguiente nodo
            first = first.next;
            first.prev = last; // Actualizar el enlace 'prev' del nuevo 'first'
            last.next = first; // Mantener la estructura circular actualizando el enlace de 'last'
        }
        return data;
    }

    @Override
    public Object removeLast() throws ListException {
        // Elimina el último elemento de la lista.
        if (isEmpty()) throw new ListException("Circularly Linked List is Empty");

        // Guardar el dato del nodo que será eliminado
        Object data = last.data;

        // Si solo hay un elemento en la lista
        if (first == last) {
            first = last = null; // La lista queda vacía
            return data;
        }

        // No es necesario usar una variable 'prev' aquí, podemos ajustar directamente
        Node aux = first;

        // Recorrer hasta llegar al penúltimo nodo (justo antes de last)
        while (aux.next != last) {
            aux = aux.next;
        }

        // 'aux' ahora está en el penúltimo nodo, que se convertirá en el nuevo 'last'
        last = aux;
        last.next = first; // Mantener el carácter circular de la lista
        first.prev = last; // Asegurarse de que el enlace previo de 'first' apunte al nuevo 'last'

        return data;
    }
    @Override
    public void sort() throws ListException {}// End of method [sort].

    @Override
    public void sortbyId(){}
    @Override
    public void sortbyName(){}


    @Override
    public int indexOf(Object element) throws ListException {
        if (isEmpty()) throw new ListException("Circularly Linked List is Empty");

        Node aux = first;
        int index = 1; // Indexación comienza en 1 según tu especificación

        do {
            if (util.Utility.compare(aux.data, element) == 0) {
                return index; // Retorna el índice si encuentra el elemento
            }
            aux = aux.next; // Mueve aux al siguiente nodo
            index++; // Incrementa el índice
        } while (aux != first); // Continúa hasta que vuelva al nodo inicial

        return -1; // Si no encuentra el elemento, retorna -1
    }

    @Override
    public Object getFirst() throws ListException {
        // Obtiene el primer elemento de la lista.
        if(isEmpty()) throw new ListException("Singly Linked List is Empty");
        return first.data;
    }// End of method [getFirst].

    @Override
    public Object getLast() throws ListException {
        // Obtiene el último elemento de la lista.
        if(isEmpty()) throw new ListException("Singly Linked List is Empty");
        
        return last.data;
    }// End of method [getLast].

    @Override
    public Object getPrev(Object element) throws ListException {
        if (isEmpty()) throw new ListException("Circularly Linked List is Empty");

        Node current = first;
        do {
            if (util.Utility.compare(current.data, element) == 0) {
                // Si encontramos el elemento, retornamos el dato del nodo anterior
                // En listas circulares doblemente enlazadas, simplemente usamos el enlace 'prev'
                return current.prev.data;
            }
            current = current.next;
        } while (current != first); // Continuar hasta que se regrese al nodo inicial

        return "Element does not exist in the Circularly Linked List";
    }

    @Override
    public Object getNext(Object element) throws ListException {
        // Retorna el elemento que sigue después de un elemento especificado por parámetro.
        if (isEmpty()) throw new ListException("Circularly Linked List is empty.");

        Node current = first;
        do {
            // Utilizamos el método compare para verificar si el nodo actual contiene el elemento buscado
            if (util.Utility.compare(current.data, element) == 0)
                // Si encontramos el elemento, retornamos el dato del siguiente nodo
                return current.next.data;

            current = current.next;
        } while (current != first); // Continuar hasta que se regrese al nodo inicial

        // Si el elemento no se encuentra en la lista, se retorna null o un mensaje apropiado
        return "Element does not exist in the Circularly Linked List";
    }

    public Node getNode(int index) throws ListException, IllegalArgumentException, IndexOutOfBoundsException {
        if (isEmpty()) throw new ListException("Circularly Linked List is empty.");

        if (index < 1) throw new IllegalArgumentException("Index must be 1 or greater.");

        Node current = first;
        int count = 1;

        do {
            if (count == index) return current;

            current = current.next;
            count++;
        } while (current != first);
        throw new IndexOutOfBoundsException("Index " + index + " exceeds the size of the list.");
    }

    @Override
    public String toString() {
        if (first == null) return "Circularly Linked List is empty.";

        String result = "Circularly Linked List Content:\n";
        Node aux = first;
        do {
            result += aux.data + " ";
            aux = aux.next;
        } while (aux != first); // Continuar hasta que se vuelva a llegar al primer nodo.

        return result.trim(); // Usamos trim para eliminar el último espacio en blanco.
    }
}// End of class [CircularDoublyLinkedList].
