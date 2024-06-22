package structures.graph;

import structures.lists.ListException;
import structures.queues.QueueException;
import structures.stack.StackException;

import java.util.EmptyStackException;

public interface Graph {
    int size() throws ListException;
    void clear();
    boolean isEmpty();
    boolean containsVertex(Object element) throws GraphException, ListException;
    boolean containsEdge(Object a, Object b) throws GraphException, ListException;
    void addVertex(Object element) throws GraphException, ListException;
    void addEdge(Object a, Object b) throws GraphException, ListException;
    void addWeight(Object a, Object b, Object weight) throws GraphException, ListException;
    void addEdgeWeight(Object a, Object b, Object weight) throws GraphException, ListException;
    void removeVertex(Object element) throws GraphException, ListException;
    void removeEdge(Object a, Object b) throws GraphException, ListException;
    String dfs() throws GraphException, EmptyStackException, ListException, StackException;
    String bfs() throws GraphException, QueueException, ListException;
    Object getWeight(Object a, Object b) throws GraphException, ListException; // Nueva declaración

}
