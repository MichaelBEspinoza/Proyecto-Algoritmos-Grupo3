package structures.graph;


public class EdgeWeight {
    private Object edge; //corresponde a la arista, en este el el nombre del vertice con quien tiene una arista
    private Object weight; //peso
    public EdgeWeight(Object edge, Object weight) {
        this.edge = edge;
        this.weight = weight;
    }

    public Object getEdge() {
        return edge;
    }

    public void setEdge(Object edge) {
        this.edge = edge;
    }

    public Object getWeight() {
        return weight;
    }

    public void setWeight(Object weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        if(weight==null) return "Edge: "+edge;
        else return "Edge: "+edge+". Weight: "+weight;
    }
}
