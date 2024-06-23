package structures.trees;

import domain.Enrollment;

import java.util.ArrayList;
import java.util.List;

public class BTree implements Tree {
    private BTreeNode root; // única entrada al árbol

    public BTree() {
        this.root = null;
    }

    public Object find(Object element) throws TreeException {
        if (isEmpty()) {
            throw new TreeException("Binary Tree is empty");
        }
        return find(root, element);
    }

    public List<Enrollment> inOrderUsage() throws TreeException {
        if (isEmpty()) throw new TreeException("Binary Search Tree is empty.");
        List<Enrollment> enrollments = new ArrayList<>();
        inOrder(root, enrollments);
        return enrollments;
    }

    private void inOrder(BTreeNode node, List<Enrollment> enrollments) {
        if (node != null) {
            inOrder(node.left, enrollments);
            enrollments.add((Enrollment) node.data);
            inOrder(node.right, enrollments);
        }
    }

    private Object find(BTreeNode node, Object element) {
        if (node == null) return null;
        if (util.Utility.compare(node.data, element) == 0) return node.data;
        Object leftResult = find(node.left, element);
        return leftResult != null ? leftResult : find(node.right, element);
    }

    @Override
    public int size() throws TreeException {
        if (isEmpty()) {
            throw new TreeException("Binary Tree is empty");
        }
        return size(root);
    }

    private int size(BTreeNode node) {
        if (node == null)
            return 0;
        else
            return 1 + size(node.left) + size(node.right);
    }

    @Override
    public void clear() {
        root = null;
    }

    @Override
    public boolean isEmpty() {
        return root == null;
    }

    @Override
    public boolean contains(Object element) throws TreeException {
        if (isEmpty()) {
            throw new TreeException("Binary Tree is empty");
        }
        return binarySearch(root, element);
    }

    private boolean binarySearch(BTreeNode node, Object element) {
        if (node == null)
            return false;
        else if (util.Utility.compare(node.data, element) == 0)
            return true; // ya lo encontró
        else
            return binarySearch(node.left, element) || binarySearch(node.right, element);
    }

    @Override
    public void add(Object element) {
        this.root = add(root, element, "root");
    }

    private BTreeNode add(BTreeNode node, Object element, String path) {
        if (node == null) { // si el árbol está vacío
            node = new BTreeNode(element, path);
        } else {
            // debemos establecer algún criterio de inserción
            int value = util.Utility.getRandom(10);
            if (value % 2 == 0) // si es par inserte por la izq
                node.left = add(node.left, element, path + "/left");
            else // si es impar inserte por la der
                node.right = add(node.right, element, path + "/right");
        }
        return node;
    }

    @Override
    public void remove(Object element) throws TreeException {
        if (isEmpty())
            throw new TreeException("Binary Tree is empty");
        root = remove(root, element);
    }

    private BTreeNode remove(BTreeNode node, Object element) {
        if (node != null) {
            if (util.Utility.compare(node.data, element) == 0) {
                // Caso 1. Si es una hoja (nodo sin hijos)
                if (node.left == null && node.right == null) {
                    return null;
                }
                // Caso 2. El nodo solo tiene un hijo
                else if (node.left == null && node.right != null) {
                    node.right = newPath(node.right, node.path);
                    return node.right;
                }
                else if (node.left != null && node.right == null) {
                    node.left = newPath(node.left, node.path);
                    return node.left;
                }
                // Caso 3. El nodo tiene dos hijos
                else if (node.left != null && node.right != null) {
                    Object value = getLeaf(node.right);
                    node.data = value;
                    node.right = removeLeaf(node.right, value);
                }
            }
            node.left = remove(node.left, element);
            node.right = remove(node.right, element);
        }
        return node;
    }

    private BTreeNode removeLeaf(BTreeNode node, Object value) {
        if (node == null) return null;
        else if (node.left == null && node.right == null && util.Utility.compare(node.data, value) == 0){
            return null;
        } else {
            node.left = removeLeaf(node.left, value);
            node.right = removeLeaf(node.right, value);
        }
        return node;
    }

    private Object getLeaf(BTreeNode node) {
        Object aux;
        if (node == null) return null;
        else {
            if (node.right == null && node.left == null) {
                return node.data;
            } else {
                aux = getLeaf(node.left);
                if (aux == null) {
                    aux = getLeaf(node.right);
                }
            }
        }
        return aux;
    }

    private BTreeNode newPath(BTreeNode right, String path) {
        BTreeNode node = null;
        return node;
    }

    @Override
    public int height(Object element) throws TreeException {
        if (isEmpty()) {
            throw new TreeException("Binary Tree is empty");
        }
        return height(root, element, 0);
    }

    private int height(BTreeNode node, Object element, int counter) {
        if (node == null) return 0; // el elemento no existe
        else if (util.Utility.compare(node.data, element) == 0) {
            return counter;
        } else return Math.max(height(node.left, element, ++counter), height(node.right, element, counter));
    }

    @Override
    public int height() throws TreeException {
        if (isEmpty())
            throw new TreeException("Binary Tree is empty");
        return height(root) - 1;
    }

    private int height(BTreeNode node) {
        if (node == null) return 0;
        else return Math.max(height(node.left), height(node.right)) + 1;
    }

    @Override
    public Object min() throws TreeException {
        return null;
    }

    @Override
    public Object max() throws TreeException {
        return null;
    }

    @Override
    public String preOrder() throws TreeException {
        if (isEmpty()) {
            throw new TreeException("Binary Tree is empty");
        }
        return preOrder(root) + "\n";
    }

    // nodo-izq-der
    private String preOrder(BTreeNode node) {
        String result = "";
        if (node != null) {
            result = node.data + "(" + node.path + ") ";
            result += preOrder(node.left);
            result += preOrder(node.right);
        }
        return result;
    }

    @Override
    public String inOrder() throws TreeException {
        if (isEmpty()) {
            throw new TreeException("Binary Tree is empty");
        }
        return inOrder(root) + "\n";
    }

    // izq-nodo-der
    private String inOrder(BTreeNode node) {
        String result = "";
        if (node != null) {
            result = inOrder(node.left);
            result += node.data + "(" + node.path + ") ";
            result += inOrder(node.right);
        }
        return result;
    }

    @Override
    public String postOrder() throws TreeException {
        if (isEmpty()) {
            throw new TreeException("Binary Tree is empty");
        }
        return postOrder(root) + "\n";
    }

    // izq-der-nodo
    private String postOrder(BTreeNode node) {
        String result = "";
        if (node != null) {
            result = postOrder(node.left);
            result += postOrder(node.right);
            result += node.data + "(" + node.path + ") ";
        }
        return result;
    }

    @Override
    public String toString() {
        if (isEmpty())
            return "Binary Tree is empty";
        String result = "BINARY TREE TOUR...\n";
        result += "PreOrder: " + preOrder(root) + "\n";
        result += "InOrder: " + inOrder(root) + "\n";
        result += "PostOrder: " + postOrder(root) + "\n";
        return result;
    }

    public BTreeNode getRoot() {
        return root;
    }
}
