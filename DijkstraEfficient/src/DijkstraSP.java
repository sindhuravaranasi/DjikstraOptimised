
/*************************************************************************
 * Unit: Dijksta Shortest Path 
 * Date: 29-May-2017
 *
 * Compilation:  javac DijkstraSP.java
 * Execution:    java DijkstraSP input.txt 
 *
 * Description: This class finds a shortest path from source to the destination
 * considering all the path possible between them where the weights are the
 * euclidean distances between the nodes. The program takes use of a binary heap
 * Re Running time:
 * The program takes time proportional to E'logV',
 * where V' is the number of vertices and E' is the number of edges that the algorithm examines.
 * the methods distTo() hasPathTo() methods take constant time 
 * and the methodpathTo() method takes time proportional to the number of edges in the shortest 
 * path returned.
 *  
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
0 5    <---source is 0 and destination is 5
output:
0 to 5 (6273.99)  
0->1 1897.37  
1->2 640.31  
2->5 3736.31  
*/

import java.util.Stack;

public class DijkstraSP {
    private double[] distTo;          // distTo[v] = distance  of shortest s->v path
    private DirectedEdge[] edgeTo;    // edgeTo[v] = last edge on shortest s->v path
    private BinaryHeap<Double> pq;    // using Binary heap for priority queue
    private boolean[] marked;         //to make track of vertices which are visited.

    
    /**
     * Computes a shortest-paths path from the source vertex to other vertices till
     * it encounters the destination vertex in the edge-weighted graph G.
     * Whenever it encounters the destination edge, marked[dest] becomes true and it
     * comes out of the loop
     *
     * @param  G the edge-weighted digraph
     * @param  s the source vertex
     */
    public DijkstraSP(EdgeWeightedDigraph G, int s) {
        marked = new boolean[G.V()];   //definition
        distTo = new double[G.V()];    //definition
        edgeTo = new DirectedEdge[G.V()];   //definition
        for (int v = 0; v < G.V(); v++)
            distTo[v] = Double.POSITIVE_INFINITY;  
        distTo[s] = 0.0;

        // relax vertices in order of distance from s
        pq = new BinaryHeap<Double>(G.V());   ////definition to binary heap
        pq.insert(0.0, s);
        while (!pq.isEmpty() && marked[G.getDestination()] != true) { //checking weather the destination is reached.
            int v = pq.deleteMin();   //delete the min value of min heap
            marked[v] = true;
            for (DirectedEdge e : G.adj(v))
            	if(!marked[e.to()])
            		relax(e);
        }
    }

    // relax edge e and update priority queue if changed
    private void relax(DirectedEdge e) {
        int v = e.from(), w = e.to();
        if (distTo[w] > distTo[v] + e.weight()) {   //dijkstra's principle
            distTo[w] = distTo[v] + e.weight();
            edgeTo[w] = e;
            if (pq.contains(w)) pq.decreaseKey(w, distTo[w]);   //decrease key of binay heap, O(1).
            else                pq.insert(distTo[w],w);   //insert tobinary heap.
            
        }
    }

    /**
     * Returns the length of a shortest path from the source vertex to vertex v.
     */
    public double distTo(int v) {
        return distTo[v];
    }

    /**
     * Returns true if there is a path from the source vertex to vertex v.
     */
    public boolean hasPathTo(int v) {
        return distTo[v] < Double.POSITIVE_INFINITY;
    }

    /**
     * Returns a shortest path from the source vertex s to vertex v.
     *
     */
    public Stack<DirectedEdge> pathTo(int v) {
        if (!hasPathTo(v)) return null;
        Stack<DirectedEdge> path = new Stack<DirectedEdge>();
        Stack<DirectedEdge> path2 = new Stack<DirectedEdge>();
        for (DirectedEdge e = edgeTo[v]; e != null; e = edgeTo[e.from()]) {
            path.push(e);
        }
        //reversing the stack.
        while(!path.isEmpty())
        {
        	path2.push(path.pop());
        }
        return path2;
    }

    /**
     * Testing the functionality.
     */
    public static void main(String[] args) {
        In in = new In(args[0]);
        EdgeWeightedDigraph G = new EdgeWeightedDigraph(in);
        int s = G.getSource();  //source
        int d = G.getDestination(); //destination

        DijkstraSP sp = new DijkstraSP(G, s);     
        //printing the shortest path from source to destination.
        if (sp.hasPathTo(d)) {
        	System.out.format("%d to %d (%.2f)  ", s, d, sp.distTo(d));
        	System.out.println();
            for (DirectedEdge e : sp.pathTo(d)) {
                System.out.println(e.toString() + "  ");
            }
            System.out.println();  
        }
        else {
            System.out.format("%d to %d         no path\n", s, d);
        }
        
	    StdDraw.setCanvasSize(1024, 512);
	    StdDraw.setPenRadius(0.01); //set the pen radius accodrding to the requirement.
	    StdDraw.setXscale(0, 10000); //x-axis
        StdDraw.setPenColor(StdDraw.BLACK); //pen color
        StdDraw.setYscale(0, 10000); //y-axis
        for (Integer i : G.map.keySet()) {
            Point p = G.map.get(i);
        	p.draw();   //draw the point on to the canvas, with the given x and y coordinates of p.
        }
	    StdDraw.setPenColor(StdDraw.YELLOW); //changing the pen color.
	    StdDraw.setPenRadius(0.002); //updating the pen color

        for(int i=0;i<G.V();i++)
        	for (DirectedEdge edge : G.adj(i)) {
        		 G.map.get(edge.from()).drawTo(G.map.get(edge.to()));
		}
        StdDraw.setPenColor(StdDraw.RED); //changing the pen color.
	    
        for (DirectedEdge edge : sp.pathTo(d)) {
       	 G.map.get(edge.from()).drawTo(G.map.get(edge.to()));
        }
        
    }
}
