package structures.trees;

import domain.Course;

import java.util.ArrayList;
import java.util.List;

public class AVL implements Tree {
    private BTreeNode root; // única entrada al árbol

    public AVL() {
        this.root = null;
    }

    @Override
    public int size() throws TreeException {
        if (isEmpty()) {
            throw new TreeException("AVL Binary Search Tree is empty.");
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
            throw new TreeException("AVL AVL Binary Search Tree is empty.");
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

    private BTreeNode add(BTreeNode node, Object element, String sequence) {
        if (node == null) node = new BTreeNode(element, "Added as " + sequence + ".");

        else {

            if (util.Utility.compare(element, node.data) < 0)
                node.left = add(node.left, element, sequence + "/left");

            else if (util.Utility.compare(element, node.data) > 0)
                node.right = add(node.right, element, sequence + "/right");

        }
        node = reBalance(node, element); // Se determina si se necesita rebalanceo.
        return node;
    }

    private BTreeNode reBalance(BTreeNode node, Object element) {
        int balance = getBalanceFactor(node);

        // Left-Left Case
        if (balance > 1 && util.Utility.compare(element, node.data) < 0) {
            node.path += ". Simple Right Rotation";
            return rightRotate(node);
        }

        // Right-Right Case
        if (balance < -1 && util.Utility.compare(element, node.data) > 0) {
            node.path += ". Simple Left Rotation";
            return leftRotate(node);
        }

        // Left-Right case
        if (balance > 1 && util.Utility.compare(element, node.data) > 0) {
            node.path += ". Double Left/Right Rotation";
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // Right-Left case
        if (balance < -1 && util.Utility.compare(element, node.data) < 0) {
            node.path += ". Double Right/Left Rotation";
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }
        return node;
    }

    private BTreeNode leftRotate(BTreeNode node) {
        BTreeNode node1 = node.right;
        BTreeNode node2 = node1.left;
        //se realiza la rotacion (perform rotation)
        node1.left = node;
        node.right = node2;
        return node1;
    }

    private BTreeNode rightRotate(BTreeNode node) {
        BTreeNode node1 = node.left;
        BTreeNode node2 = node1.right;
        //se realiza la rotacion (perform rotation)
        node1.right = node;
        node.left = node2;
        return node1;
    }

    private int getBalanceFactor(BTreeNode node) {
        if (node == null) return 0;
        else return height(node.left) - height(node.right);
    }

    @Override
    public void remove(Object element) throws TreeException {
        if (isEmpty())
            throw new TreeException("AVL Binary Search Tree is empty.");
        root = remove(root, element);
    }

    private BTreeNode remove(BTreeNode node, Object element) {
        if (node != null) {
            if (util.Utility.compare(element, node.data) < 0)
                node.left = remove(node.left, element);
            else if (util.Utility.compare(element, node.data) > 0)
                node.right = remove(node.right, element);
            else if (util.Utility.compare(node.data, element) == 0)
                // Caso 1. Si es una hoja (nodo sin hijos)
                if (node.left == null && node.right == null)
                    return null;
                    // Caso 2. El nodo solo tiene un hijo
                else if (node.left == null && node.right != null)
                    return node.right;
                else if (node.left != null && node.right == null)
                    return node.left;
                    // Caso 3. El nodo tiene dos hijos
                else if (node.left != null && node.right != null) {
                    Object value = min(node.right);
                    node.data = value;
                    node.right = remove(node.right, value);
                }
            // Luego de suprimir se determina si requiere o no un rebalanceo.
            node = reBalance(node, element);
        }
        return node;
    }

    @Override
    public int height(Object element) throws TreeException {
        if (isEmpty()) throw new TreeException("AVL Binary Search Tree is empty.");
        return height(root, element, 0);
    }

    private int height(BTreeNode node, Object element, int counter) {
        if (node == null) return 0; // el elemento no existe
        else if (util.Utility.compare(node.data, element) == 0) return counter;
        else if (util.Utility.compare(element, node.data) < 0)
            return height(node.left, element, ++counter);
        else return height(node.right, element, ++counter);
        //else return Math.max(height(node.left, element, ++counter), height(node.right, element, counter));
    }

    @Override
    public int height() throws TreeException {
        if (isEmpty()) throw new TreeException("AVL Binary Search Tree is empty.");
        return height(root) - 1;
    }

    private int height(BTreeNode node) {
        if (node == null) return 0;
        else return Math.max(height(node.left), height(node.right)) + 1;
    }

    @Override
    public Object min() throws TreeException {
        if (isEmpty()) throw new TreeException("AVL Binary Search Tree is empty.");
        return min(root);
    }

    private Object min(BTreeNode node) {
        // Método interno.
        if (node.left != null) return min(node.left);
        return node.data;

    }

    @Override
    public Object max() throws TreeException {
        if (isEmpty()) throw new TreeException("AVL Binary Search Tree is empty.");
        return max(root);
    }

    private Object max(BTreeNode node) {
        if (node.right != null) return max(node.right);
        return node.data;
    }

    @Override
    public String preOrder() throws TreeException {
        if (isEmpty()) throw new TreeException("AVL Binary Search Tree is empty.");
        return preOrder(root) + "\n";
    }

    // nodo-izq-der
    private String preOrder(BTreeNode node) {
        String result = "";
        if (node != null) {
            result = node.data + " ";
            result += preOrder(node.left);
            result += preOrder(node.right);
        }
        return result;
    }

    @Override
    public String inOrder() throws TreeException {
        if (isEmpty()) throw new TreeException("AVL Binary Search Tree is empty.");
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

    public List<Course> inOrderUsage() throws TreeException {
        if (isEmpty()) throw new TreeException("AVL Binary Search Tree is empty.");
        List<Course> courses = new ArrayList<>();
        inOrder(root, courses);
        return courses;
    }

    private void inOrder(BTreeNode node, List<Course> courses) {
        if (node != null) {
            inOrder(node.left, courses);
            courses.add((Course) node.data);
            inOrder(node.right, courses);
        }
    }

    @Override
    public String postOrder() throws TreeException {
        if (isEmpty()) throw new TreeException("AVL Binary Search Tree is empty.");
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
            return "AVL Binary Search Tree is empty.";
        String result = "AVL BINARY SEARCH TREE TOUR...\n";
        result += "PreOrder: " + preOrder(root) + "\n";
        result += "InOrder: " + inOrder(root) + "\n";
        result += "PostOrder: " + postOrder(root) + "\n";
        return result;
    }

    public boolean isBalanced() throws TreeException {
        if (isEmpty())
            throw new TreeException("AVL Binary Search Tree is empty.");
        return isBalanced(root);
    }

    private boolean isBalanced(BTreeNode node) {
        if (node == null) return true;

        int leftHeight = height(node.left);
        int rightHeight = height(node.right);

        return Math.abs(leftHeight - rightHeight) <= 1
                && isBalanced(node.left)
                && isBalanced(node.right);
    }

    public BTreeNode getRoot() {
        return root;
    }
}
