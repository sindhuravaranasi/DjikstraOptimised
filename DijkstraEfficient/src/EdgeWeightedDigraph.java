
/*************************************************************************
 * Unit: Edge-Weighted Digraph
 * Date: 29-May-2017
 *
 * Compilation:  javac EdgeWeightedDigraph.java
 * Execution:    java EdgeWeightedDigraph digraph.txt*
 *
 * Description: An edge-weighted digraph is an implementation of adjacency lists.
 * All the coded operations take constant running time. This class constructs and
 * represents an entire graph and its adjacency list. The graph is constructed 
 * from the inputs given in the text file according to the required format.
 *   
 * @author Pujitha Pasham
 * @email - pashamp1@udayton.edu
 *************************************************************************/

/*
 * Execution:
 * input.txt:
6 9
0  1000 2400
1  2800 3000
2  2400 2500
3  4000    0
4  4500 3800
5  6000 1500
0 1
0 3
1 2
1 4
2 4
2 3
2 5
3 5
4 5
0 5
output:
6 18
0: 0->1 1897.37  0->3 3841.87  
1: 1->0 1897.37  1->2 640.31  1->4 1878.83  
2: 2->1 640.31  2->4 2469.82  2->3 2968.16  2->5 3736.31  
3: 3->0 3841.87  3->2 2968.16  3->5 2500.00  
4: 4->1 1878.83  4->2 2469.82  4->5 2745.91  
5: 5->2 3736.31  5->3 2500.00  5->4 2745.91  

 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EdgeWeightedDigraph {
    private static final String NEWLINE = System.getProperty("line.separator");

    private final int V;                // number of vertices in this digraph
    private int E;                      // number of edges in this digraph
    private List<DirectedEdge>[] adj;    // declaring adj[v] = adjacency list for vertex v
    private int source;
    private int destination;
    public HashMap<Integer, Point> map;
    /**
     * Constructs an empty edge-weighted graph with V vertices and 0 edges.
     */
    @SuppressWarnings("unchecked")
	public EdgeWeightedDigraph(int V) {
        if (V < 0) throw new IllegalArgumentException("Number of vertices in a graph must never be nonnegative");
        //no. of vertices can never be negative
        this.V = V;
        this.E = 0;
        adj = (List<DirectedEdge>[]) new ArrayList[V];  //defining the adj list 
        for (int v = 0; v < V; v++)
            adj[v] = new ArrayList<DirectedEdge>();    //constructing the array of ArrayLists.
    }

    /**Computes the Euclidean Distance between two points a & b
     * 
     * @param a
     * @param b
     * @return euclidean distance 
     */
    public double distance(Point a,Point b)
	{
			return Math.sqrt((Math.pow((b.x-a.x),2)+Math.pow((b.y-a.y),2)));  //sqrt((x1-x2)^2+(y1-y2)^2)
	}
    
    /*
     * getters and setters for source and destination.
     */
	public int getSource() {
		return source;
	}
	public void setSource(int source) {
		this.source = source;
	}
	public int getDestination() {
		return destination;
	}
	public void setDestination(int destination) {
		this.destination = destination;
	}

    /**  
     * Constructs an edge-weighted graph from the input file.
     * The format of the input file is the number of vertices V,
     * ,number of edges E followed by the x,y coordinates for the vertices points.
     */
    public EdgeWeightedDigraph(In in) {
        this(in.readInt()); //calls the default constructor where all initializes are done.
        
        Map<Integer, Point> map = new HashMap<>(); //creating a map of points
        int V = this.V; 
        int E = in.readInt();   //reading edges from input file.
        if (E < 0) throw new IllegalArgumentException("Number of edges must never be negative");
        for (int i = 0; i < V; i++) {    //reading and populating
        	int vertex = in.readInt();
        	Point value = new Point(in.readInt(), in.readInt());
			map.put(vertex, value);   //put node and its (x,y) cordinates in a map.
		}
        this.map = (HashMap<Integer, Point>) map;
        
        for (int i = 0; i < E; i++) {
            int v = in.readInt();  //read from edge
            int w = in.readInt();  //read to edge
            double weight = distance(map.get(v), map.get(w)); //computing the distance.
            
            addEdge(new DirectedEdge(v, w, weight));     //add v->w the edge to graph
            addEdge(new DirectedEdge(w, v, weight));     //add w->v edge to graph
            //both v->w and w->v is added because the given graph is undirected/bidectional graph.
        }
        try{
        this.setSource(in.readInt());
        this.setDestination(in.readInt());
        }
        catch (Exception e) {
        	System.err.println("Source and destination not specified or number of V/E are wrongly specified");
        }
        if (source < 0 || source >= this.V)
            System.err.println("source vertex " + getSource() + " is not between 0 and " + (V-1));
        if (destination < 0 || destination >= this.V)
        	System.err.println("destination vertex " + getDestination() + " is not between 0 and " + (V-1));
    }

    /**
     * Returns the number of vertices in the edge-weighted graph.
     */
    public int V() {
        return V;
    }

    /**
     * Returns the number of edges in the edge-weighted graph.
     */
    public int E() {
        return E;
    }
    /**
     * Adds the directed edge e to this edge-weighted graph.
     */
    public void addEdge(DirectedEdge e) {
        int v = e.from();
        adj[v].add(e); //add to the adjacency list
        E++; //to have the final count of edges forward+backward (bidirectional)
    }


    /**
     * Returns the directed edges from vertex v.
     */
    public Iterable<DirectedEdge> adj(int v) {
        return adj[v];
    }

    /**
     * Returns string representation of the edge-weighted graph.
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(V + " " + E + NEWLINE);
        for (int v = 0; v < V; v++) {
            s.append(v + ": ");
            for (DirectedEdge e : adj[v]) {
                s.append(e + "  ");
            }
            s.append(NEWLINE);
        }
        return s.toString();
    }

    /**
     *Testing the functionality with the given input file.
     */
    public static void main(String[] args) {
        In in = new In(args[0]);
        EdgeWeightedDigraph G = new EdgeWeightedDigraph(in);
        System.out.println(G.toString()); //print the entire adjacency list.
    }

}
