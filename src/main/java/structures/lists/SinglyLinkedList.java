package structures.lists;

public class SinglyLinkedList implements List {
    private Node first; // Apuntador al inicio de la lista

    public SinglyLinkedList() {
        this.first = null; // La lista no existe.
    } // End of constructor for class [SinglyLinkedList].

    @Override
    public int size() throws ListException {
        if (isEmpty()) return 0;
        int size = 1;
        Node aux = first;
        while (aux.next != null) {
            size++;
            aux = aux.next;
        }
        return size;
    }

    @Override
    public void clear() {
        this.first = null;
    }

    @Override
    public boolean isEmpty() {
        return this.first == null;
    }

    @Override
    public boolean contains(Object element) throws ListException {
        if (isEmpty()) throw new ListException("Singly linked list is empty");

        Node aux = first;
        while (aux != null) {
            if (aux.data.equals(element)) return true;
            aux = aux.next;
        }
        return false;
    }

    @Override
    public void add(Object element) {
        Node newNode = new Node(element);
        if (isEmpty()) first = newNode;
        else {
            Node aux = first;
            while (aux.next != null) aux = aux.next;
            aux.next = newNode;
        }
    }

    @Override
    public void addFirst(Object element) {
        Node newNode = new Node(element);
        if (isEmpty()) first = newNode;
        else {
            newNode.next = first;
            first = newNode;
        }
    }

    @Override
    public void addLast(Object element) {
        add(element);
    }

    @Override
    public void addInSortedList(Object element) {
        try {
            add(element);
            sort();
        } catch (ListException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void remove(Object element) throws ListException {
        if (isEmpty()) throw new ListException("Singly Linked List is empty.");

        if (first.data.equals(element)) {
            first = first.next;
            return;
        }

        Node prev = first;
        Node aux = first.next;

        while (aux != null && !aux.data.equals(element)) {
            prev = aux;
            aux = aux.next;
        }

        if (aux != null && aux.data.equals(element)) {
            prev.next = aux.next;
        }
    }

    @Override
    public Object removeFirst() throws ListException {
        if (isEmpty()) throw new ListException("Singly Linked List is empty.");
        Object data = first.data;
        first = first.next;
        return data;
    }

    @Override
    public Object removeLast() throws ListException {
        if (isEmpty()) throw new ListException("Singly Linked List is empty.");
        if (first.next == null) {
            Object data = first.data;
            first = null;
            return data;
        }

        Node prev = first;
        Node aux = first.next;

        while (aux.next != null) {
            prev = aux;
            aux = aux.next;
        }

        prev.next = null;
        return aux.data;
    }

    @Override
    public void sort() throws ListException {
        if (isEmpty()) throw new ListException("Singly Linked List is empty.");

        boolean swapped;
        do {
            swapped = false;
            Node current = first;
            Node previous = null;
            Node next = first.next;

            while (next != null) {
                if (((Comparable) current.data).compareTo(next.data) > 0) {
                    swapped = true;
                    if (previous != null) {
                        Node temp = next.next;
                        previous.next = next;
                        next.next = current;
                        current.next = temp;
                    } else {
                        Node temp = next.next;
                        first = next;
                        next.next = current;
                        current.next = temp;
                    }
                    previous = next;
                    next = current.next;
                } else {
                    previous = current;
                    current = next;
                    next = next.next;
                }
            }
        } while (swapped);
    }

    @Override
    public void sortbyId() {
    }

    @Override
    public void sortbyName() {
    }

    @Override
    public int indexOf(Object element) throws ListException {
        if (isEmpty()) throw new ListException("Singly Linked List is empty");

        Node aux = first;
        int index = 1;
        while (aux != null) {
            if (aux.data.equals(element)) return index;
            index++;
            aux = aux.next;
        }
        return -1;
    }

    @Override
    public Object getFirst() throws ListException {
        if (isEmpty()) throw new ListException("Singly Linked List is empty");
        return first.data;
    }

    @Override
    public Object getLast() throws ListException {
        if (isEmpty()) throw new ListException("Singly Linked List is empty");
        Node aux = first;
        while (aux.next != null) aux = aux.next;
        return aux.data;
    }

    @Override
    public Object getPrev(Object element) throws ListException {
        if (isEmpty()) throw new ListException("Singly Linked List is empty");
        if (first.data.equals(element)) return "It's the first, it has no previous";

        Node aux = first;
        while (aux.next != null) {
            if (aux.next.data.equals(element)) return aux.data;
            aux = aux.next;
        }
        return "Does not exist in Single Linked List";
    }

    @Override
    public Object getNext(Object element) throws ListException {
        if (isEmpty()) throw new ListException("Singly Linked List is empty");
        Node aux = first;
        while (aux != null && !aux.data.equals(element)) {
            aux = aux.next;
        }
        if (aux != null && aux.next != null) return aux.next.data;
        return null;
    }

    @Override
    public Node getNode(int index) throws ListException {
        if (isEmpty()) throw new ListException("Singly Linked List is empty");

        Node aux = first;
        int i = 1;

        while (aux != null) {
            if (i == index) return aux;
            i++;
            aux = aux.next;
        }

        return null;
    }
    public Node getNode(Object element) throws ListException {
        if(isEmpty()){
            throw new ListException("Singly Linked List is Empty");
        }
        Node aux = first;
        while(aux!=null){
            if(util.Utility.compare(aux.data, element)==0) {  //ya encontro el elemento
                return aux;
            }
            aux = aux.next; //muevo aux al sgte nodo
        }
        return null; //si llega aqui es xq no encontro el index
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("Singly Linked List Content\n\n");
        Node aux = first;
        while (aux != null) {
            result.append(aux.data).append(" ");
            aux = aux.next;
        }
        return result.toString();
    }
}
