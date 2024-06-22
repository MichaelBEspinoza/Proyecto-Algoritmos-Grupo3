package structures.graph;

import structures.lists.ListException;
import structures.lists.SinglyLinkedList;
import structures.queues.LinkedQueue;
import structures.queues.QueueException;
import structures.stack.LinkedStack;
import structures.stack.StackException;

public class SinglyLinkedListGraph implements Graph {
    private SinglyLinkedList vertexList;
    private LinkedStack stack;
    private LinkedQueue queue;

    public SinglyLinkedListGraph() {
        initObjects();
    }

    private void initObjects() {
        this.vertexList = new SinglyLinkedList();
        this.stack = new LinkedStack();
        this.queue = new LinkedQueue();
    }

    @Override
    public int size() throws ListException {
        return this.vertexList.size();
    }

    @Override
    public void clear() {
        initObjects();
    }

    @Override
    public boolean isEmpty() {
        return this.vertexList.isEmpty();
    }

    @Override
    public boolean containsVertex(Object element) throws GraphException, ListException {
        if (isEmpty()) {
            throw new GraphException("Singly Linked List Graph is empty");
        }
        int n = vertexList.size();
        for (int i = 1; i <= n; i++) {
            Vertex vertex = (Vertex) vertexList.getNode(i).data;
            if (util.Utility.compare(vertex.data, element) == 0)
                return true;
        }
        return false;
    }

    @Override
    public boolean containsEdge(Object a, Object b) throws GraphException, ListException {
        if (isEmpty()) {
            throw new GraphException("Singly Linked List Graph is empty");
        }
        if (!containsVertex(a) || !containsVertex(b)) return false;

        int n = vertexList.size();
        for (int i = 1; i <= n; i++) {
            Vertex vertex = (Vertex) vertexList.getNode(i).data;
            if ((util.Utility.compare(vertex.data, a) == 0)
                    && !vertex.edgesList.isEmpty()
                    && vertex.edgesList.contains(new EdgeWeight(b, null))
            )
                return true;
        }
        return false; // no existe la arista
    }

    @Override
    public void addVertex(Object element) throws GraphException, ListException {
        if (vertexList.isEmpty())
            vertexList.add(new Vertex(element));
        else if (!vertexList.contains(new Vertex(element)))
            vertexList.add(new Vertex(element));
    }

    @Override
    public void addEdge(Object a, Object b) throws GraphException, ListException {
        if (!containsVertex(a) || !containsVertex(b)) {
            throw new GraphException("Cannot add edge between vertexes ["
                    + a + "] and [" + b + "]");
        }
        addVertexEdgeWeight(a, b, null, "addEdge");
        addVertexEdgeWeight(b, a, null, "addEdge");
    }

    private void addVertexEdgeWeight(Object a, Object b, Object weight, String action) throws ListException {
        for (int i = 1; i <= vertexList.size(); i++) {
            Vertex vertex = (Vertex) vertexList.getNode(i).data;
            if ((util.Utility.compare(vertex.data, a) == 0)) {
                switch (action) {
                    case "addEdge":
                        vertex.edgesList.add(new EdgeWeight(b, weight));
                        break;
                    case "addWeight":
                        vertex.edgesList.getNode(new EdgeWeight(b, null))
                                .setData(new EdgeWeight(b, weight));
                        break;
                    case "remove":
                        if (vertex.edgesList != null && !vertex.edgesList.isEmpty())
                            vertex.edgesList.remove(new EdgeWeight(b, weight));
                }
            }
        }
    }

    @Override
    public void addWeight(Object a, Object b, Object weight) throws GraphException, ListException {
        if (!containsEdge(a, b)) {
            throw new GraphException("Cannot add weight between vertexes ["
                    + a + "] and [" + b + "]");
        }
        addVertexEdgeWeight(a, b, weight, "addWeight");
        addVertexEdgeWeight(b, a, weight, "addWeight");
    }

    @Override
    public void addEdgeWeight(Object a, Object b, Object weight) throws GraphException, ListException {
        if (!containsVertex(a) || !containsVertex(b)) {
            throw new GraphException("Cannot add edge between vertexes ["
                    + a + "] and [" + b + "]");
        }
        if (!containsEdge(a, b)) {
            addVertexEdgeWeight(a, b, weight, "addEdge");
            addVertexEdgeWeight(b, a, weight, "addEdge");
        }
    }

    @Override
    public void removeVertex(Object element) throws GraphException, ListException {
        if (isEmpty()) {
            throw new GraphException("Singly Linked List Graph is empty");
        }
        if (containsVertex(element)) {
            boolean removed = false;
            for (int i = 1; !removed && i <= vertexList.size(); i++) {
                Vertex vertex = (Vertex) vertexList.getNode(i).data;
                if (util.Utility.compare(vertex.data, element) == 0) {
                    vertexList.remove(vertex);
                    removed = true;
                    for (int j = 1; vertexList != null && !vertexList.isEmpty()
                            && j <= vertexList.size(); j++) {
                        vertex = (Vertex) vertexList.getNode(j).data;
                        if (!vertex.edgesList.isEmpty())
                            addVertexEdgeWeight(vertex.data, element, null, "remove");
                    }
                }
            }
        }
    }

    @Override
    public void removeEdge(Object a, Object b) throws GraphException, ListException {
        if (!containsVertex(a) || !containsVertex(b)) {
            throw new GraphException("There's no some of the vertexes");
        }
        addVertexEdgeWeight(a, b, null, "remove");
        addVertexEdgeWeight(b, a, null, "remove");
    }

    @Override
    public String dfs() throws GraphException, StackException, ListException {
        setVisited(false);
        Vertex vertex = (Vertex) vertexList.getNode(1).data;
        String info = vertex + ", ";
        vertex.setVisited(true);
        stack.clear();
        stack.push(1);
        while (!stack.isEmpty()) {
            int index = adjacentVertexNotVisited((int) stack.top());
            if (index == -1)
                stack.pop();
            else {
                vertex = (Vertex) vertexList.getNode(index).data;
                vertex.setVisited(true);
                info += vertex + ", ";
                stack.push(index);
            }
        }
        return info;
    }

    @Override
    public String bfs() throws GraphException, QueueException, ListException {
        setVisited(false);
        Vertex vertex = (Vertex) vertexList.getNode(1).data;
        String info = vertex + ", ";
        vertex.setVisited(true);
        queue.clear();
        queue.enQueue(1);
        int index2;
        while (!queue.isEmpty()) {
            int index1 = (int) queue.deQueue();
            while ((index2 = adjacentVertexNotVisited(index1)) != -1) {
                vertex = (Vertex) vertexList.getNode(index2).data;
                vertex.setVisited(true);
                info += vertex + ", ";
                queue.enQueue(index2);
            }
        }
        return info;
    }

    private void setVisited(boolean value) throws ListException {
        for (int i = 1; i <= vertexList.size(); i++) {
            Vertex vertex = (Vertex) vertexList.getNode(i).data;
            vertex.setVisited(value);
        }
    }

    private int adjacentVertexNotVisited(int index) throws ListException {
        Vertex vertex1 = (Vertex) vertexList.getNode(index).data;
        for (int i = 1; i <= vertexList.size(); i++) {
            Vertex vertex2 = (Vertex) vertexList.getNode(i).data;
            if (!vertex2.edgesList.isEmpty() && vertex2.edgesList
                    .contains(new EdgeWeight(vertex1.data, null))
                    && !vertex2.isVisited())
                return i;
        }
        return -1;
    }

    @Override
    public String toString() {
        String result = "SINGLY LINKED LIST GRAPH CONTENT...\n";
        try {
            for (int i = 1; i <= vertexList.size(); i++) {
                Vertex vertex = (Vertex) vertexList.getNode(i).data;
                result += "\nThe vertex in the position " + i + " is: " + vertex + "\n";
                if (!vertex.edgesList.isEmpty()) {
                    result += "........EDGES AND WEIGHTS: " + vertex.edgesList + "\n";
                }
            }
        } catch (ListException ex) {
            System.out.println(ex.getMessage());
        }
        return result;
    }

    @Override
    public Object getWeight(Object a, Object b) throws GraphException, ListException {
        if (!containsEdge(a, b)) {
            throw new GraphException("Edge does not exist.");
        }
        for (int i = 1; i <= vertexList.size(); i++) {
            Vertex vertex = (Vertex) vertexList.getNode(i).data;
            if (util.Utility.compare(vertex.data, a) == 0) {
                EdgeWeight edgeWeight = (EdgeWeight) vertex.edgesList.getNode(new EdgeWeight(b, null)).data;
                return edgeWeight.getWeight();
            }
        }
        return null;
    }

    public Vertex[] getVerticesArray() throws ListException {
        Vertex[] array = new Vertex[vertexList.size()];
        for (int i = 1; i <= vertexList.size(); i++) {
            array[i - 1] = (Vertex) vertexList.getNode(i).data;
        }
        return array;
    }
}
