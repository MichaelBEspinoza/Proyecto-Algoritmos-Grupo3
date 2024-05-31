package structures.trees;

public interface Tree {
    //devuelve el número de elementos en el árbol
    public int size() throws TreeException;
    //private int size(BTreeNode nodo)

    //anula el árbol
    public void clear();

    //true si el árbol está vacío
    public boolean isEmpty();

    //true si el elemento existe en el árbol
    public boolean contains(Object element) throws TreeException;
    //private boolean binarySearch(BTreeNode node, Object element)

    //inserta un elemento en el árbol
    public void add (Object element);
    //private BtreeNode add(BtreeNode node, Object element)

    //suprime un elemento del árbol
    //Caso 1. El nodo a suprimir no tiene hijos
    //Caso 2. El nodo a suprimir solo tiene un hijo
    //Caso 3. El nodo a suprimir tiene dos hijos
    public void remove(Object element) throws TreeException;
    //private BTreeNode remove(BTreeNode node, Object element)

    //devuelve la altura de un nodo (el número de ancestros)
    public int height(Object element) throws TreeException;
    //private int height(BTreeNode node, Object element)

    //devuelve la altura del árbol (altura máxima de la raíz a
    //cualquier hoja del árbol)
    public int height() throws TreeException;
    //private int height(BTreeNode node)

    //devuelve el valor mínimo contenido en el árbol
    public Object min() throws TreeException;
    //private Object min(BTreeNode node)

    //devuelve el valor máximo contenido en el árbol
    public Object max() throws TreeException;
    //private Object max(BTreeNode node)

    //recorre el árbol de la forma: nodo-hijo izq-hijo der,
    //para mostrar todos los elementos existentes
    public String preOrder() throws TreeException;
    //private String preOrder(BTreeNode node)

    //recorre el árbol de la forma: hijo izq-nodo-hijo der,
    //para mostrar todos los elementos existentes
    public String inOrder() throws TreeException;
    //private String InOrder(BTreeNode node)

    ////recorre el árbol de la forma: hijo izq-hijo der-nodo,
    //para mostrar todos los elementos existentes
    public String postOrder() throws TreeException;
    //private String postOrder(BTreeNode node)


}
