package structures.lists;

public class CircularLinkedList implements List{
    private Node first; //Apuntador al inicio de la lista
    private Node last; //Apuntador al final de la lista

    public CircularLinkedList() {
        this.first = this.last = null; // La lista no existe.
    } // End of constructor for class [SinglyLinkedList].

    @Override
    public int size() throws ListException {
        // Método que retorna el tamaño (cantidad de elementos) de la lista.
        if (isEmpty()) throw new ListException("Circular Linked list is empty"); // Se evalúa primero si el tamaño de la lista es nulo, se retorna un cero porque la lista está vacía

        int size = 1; // Se declara la variable en uno porque si se sabe que el primer nodo no es nuo, ya se tiene un nodo
        Node aux = first; // Se declara un nodo para la verificación de que el primer nodo siempre va a ser el first.

        while (aux.next != last){ // Se le dice que mientras que el próximo nodo sea diferente de null, va a entrar en el ciclo
            size++; //Si ese nodo es diferente de null, va a ir incrementando el contador de size para contar los espacios.
            aux = aux.next; //Luego el nodo va a pasar al siguiente nodo para seguir con el ciclo
        }// End of 'while'.

        return size + 1; //+1 para que cuente el ultimo nodo
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
    @Override
    public boolean contains(Object element) throws ListException {
        // Método que valora si la lista contiene a un cliente en específico.
        if (isEmpty()) throw new ListException("Circulas linked list is empty");

        Node aux = first;
        while (aux != last) {
            if (aux.data.equals(element)) return true;
            aux = aux.next;
        }// End of 'while'.

        //Se sale del while cuando aux == last, por lo tanto, solo queda verificar si el elemento a buscar esta en el ultimo nodo
        return util.Utility.compare(aux.data, element) == 0;

    }// Contains que recibe elemento

    @Override
    public void add(Object element) {
        Node newNode = new Node(element);
        if(isEmpty()) first = last = newNode;
        else {
            last.next = newNode;
            //SE coloca a last a apuntar al ultimo nodo
            last = newNode;

        }
        //Se realiza el enlace circular
        last.next = first;

    }// End of method [add].

    @Override
    public void addFirst(Object element) {

        Node newNode = new Node(element);

        if(isEmpty()) first = last = newNode;
        else{
            newNode.next = first;
            first = newNode;
        }// End of 'else'.

        //Se realiza el enlace circular
        last.next = first;

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
            sortbyId();
        }catch (ListException LE){
            throw new RuntimeException(LE);
        }// End of 'catch'.
    }// End of method [addInSortedList].

    @Override
    public void remove(Object element) throws ListException {
        if (isEmpty()) {
            throw new ListException("Circular Linked List is Empty");
        }
        //CASO 1 el elemento a suprimir está al inicio:
        if (util.Utility.compare(first.data, element) == 0) {
            first = first.next; //saltamos al primer nodo
        } else {  //caso 2 suprimir el ultimo
            Node prev = first; //dejo un apuntador al nodo anterior
            Node aux = first.next;
            while (aux != last && !(util.Utility.compare(aux.data, element) == 0)) {
                prev = aux;
                aux = aux.next;
            }
            //se sale cuando encuentra el elemento
            if (util.Utility.compare(aux.data, element) == 0) {
                //ya lo encontró procede a desenlazar el nodo
                prev.next = aux.next;
            }

            //Que pasas si el elemento a suprimir esta en el ultimo nodo
            if (aux == last && util.Utility.compare(aux.data, element) == 0) {
                last = prev; //desenlaza el ultimo nodo
            }  }

        //Mantengo el enlace circular
        last.next = first;

        //Otro caso:
        //Si solo queda un nodo y es el que quiero eliminar
        if (first == last && util.Utility.compare(first.data, element) == 0) {
            clear();//anulo la lista
        }
    }

    @Override
    public Object removeFirst() throws ListException {

        if (isEmpty())
            throw new ListException("Circular Linked List is Empty");

        Node aux = first.next;
        first = aux;

        return aux.data;

    }// End of method [removeFirst].

    @Override
    public Object removeLast() throws ListException {
        if (isEmpty()) throw new ListException("Circularly Linked List is Empty");

        // Si solo hay un elemento en la lista
        if (first == last) {
            Object data = first.data;
            first = last = null; // La lista queda vacía
            return data;
        }

        Node prev = null;
        Node aux = first;

        // Recorrer hasta llegar al penúltimo nodo (justo antes de last)
        while (aux.next != last) {
            aux = aux.next;
        }

        // 'aux' ahora está en el penúltimo nodo, 'prev' es innecesario
        prev = aux;
        Object data = last.data; // Guardar los datos a devolver

        // Remover el último nodo
        last = prev;
        last.next = first; // Mantener el carácter circular de la lista

        return data;

    }// End of method [removeLast].

    @Override
    public void sort(){}


    @Override
    public void sortbyId() throws ListException {
        if (isEmpty()) throw new ListException("SinglyLinkedList is empty");

        if(isEmpty()){
            throw new ListException("Circular Linked List is Empty");
        }
        for (int i = 1; i <= size() ; i++) {
            for (int j = i+1; j <= size() ; j++) {
                if(util.Utility.compare(getNode(j).data, getNode(i).data)<0){
                    Object aux = getNode(i).data;
                    getNode(i).data = getNode(j).data;
                    getNode(j).data = aux;
                }
            }
        }

    }


    @Override
    public void sortbyName() throws ListException {

        // Método que ordena la lista por nombre de los clientes, alfabéticamente.
        if (isEmpty()) throw new ListException("SinglyLinkedList is empty");

        for (int i = 1; i <= size(); i++)
            for (int j = i + 1; j <= size(); j++) {
                String idI = (String) getNode(i).data;
                String idJ = (String) getNode(j).data;
                if (idJ.compareTo(idI) > 0) {
                    Object aux = getNode(i).data;
                    getNode(i).data = getNode(j).data;
                    getNode(j).data = aux;
                }// End of 'if'.
            }// End of 'for' loop.

    }

    @Override
    public int indexOf(Object element) throws ListException {
        // Método que retorna el índice de un elemento de la lista.
        if(isEmpty()) throw new ListException("Singly Linked List is Empty");

        Node aux = first;
        int index=1; //la lista inicia en 1
        while(aux!=last){
            if(util.Utility.compare(aux.data, element)==0) return index;

            index++; // Incrementa el indice.
            aux=aux.next; // Muevo aux al siguiente nodo.
        }// End of 'while'.

        //Se sale cuando alcanza last
        if (util.Utility.compare(aux.data, element) == 0)
            return index;
        return -1; // Indica que el elemento no existe.
    }// End of method [indexOf].

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
       return null;
    }

    @Override
    public Object getNext(Object element) throws ListException {
       return null;
    }


    @Override
    public Node getNode(int index) throws ListException {
        if (isEmpty()) throw new ListException("Singly Linked List is empty.");

        Node aux = first;
        int i = 1; // Posicion del primer nodo.

        while (aux != last) {
            if (util.Utility.compare(i, index) == 0) return aux;
            i++; // Incremento la variable local.
            aux = aux.next; // Muevo a 'aux' al siguiente nodo.
        }// End of 'while'.

        //Se sae cuando aux == last
        if (util.Utility.compare(i, index) == 0) return aux;

        return null; // Si llega aqui, es porque no encontro el index.
    }// End of method [getNode].

    @Override
    public String toString() {
        String result = "Circularly Linked List Content\n\n";
        Node aux = first;
        while(aux!=last){
            result += aux.data + " ";
            aux = aux.next;
        }
        return result + "\n" + aux.data;
    }

}// End of class [SinglyLinkedList].
