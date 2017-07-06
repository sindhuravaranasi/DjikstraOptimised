
/*************************************************************************
 * Unit: Weighted Directed Edge
 * Date: 29-May-2017
 *
 * Compilation:  javac DirectedEdge.java
 * Execution:    java DirectedEdge
 *
 * Description: This class represents a weighted edge in a EdgeWeightedGraph.
 * The graph given according to the requirement is a undirected/bidirectional 
 * graph. Hence a pair of vertices v,w can be considered as v -> w and w -> v.
 * The weight is the euclidean distance between the vertices.
 * 
 * @author Pujitha Pasham
 * @email - pashamp1@udayton.edu
 *************************************************************************/
/*
 * Sample Execution:
 * output:
 * 6->8  2.00
 */

public class DirectedEdge { 
    private final int v;
    private final int w;
    private final double weight;

    /**
     * Initializes an edge from vertex v to vertex w with
     * the given weight.
     */
    public DirectedEdge(int v, int w, double weight) {
        this.v = v;
        this.w = w;
        this.weight = weight;
    }

    /**
     * Returns the from vertex of the edge.
     */
    public int from() {
        return v;
    }

    /**
     * Returns the to vertex of the edge.
     */
    public int to() {
        return w;
    }

    /**
     * Returns the weight of the edge.
     */
    public double weight() {
        return weight;
    }

    /**
     * Returns a string representation of the edge.
     */
    public String toString() {
        return v + "->" + w + " " + String.format("%5.2f", weight);
    }
    
    /*
     * For testing purpose.
     */
    public static void main(String[] args) {
        DirectedEdge e = new DirectedEdge(6, 8, 2.0);
        System.out.println(e.toString()); //print out the edge
    }
}
