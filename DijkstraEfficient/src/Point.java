
/*************************************************************************
 * Name: Point.java
 * Date: 29-May-2016
 *
 * Compilation:  javac Point.java
 *
 * Description: An immutable data type for points in the plane.
 *
 * @author Pujitha Pasham
 * @email - pashamp1@udayton.edu
 *************************************************************************/


public class Point implements Comparable<Point> {

    
    public final int x;                              // x coordinate
    public final int y;                              // y coordinate

    // create the point (x, y)
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // is this point lexicographically smaller than that one?
    // comparing y-coordinates and breaking ties by x-coordinates
    public int compareTo(Point that) {
    	if(this.y > that.y)   //compare y coordinates first and return 1,0,-1 accordingly.
    		return 1;
    	else if(this.y < that.y)
    		return -1;
    	else                  //for a tie with y coordinates, compare x coordinates.
    		if(this.x > that.x)
    			return 1;
    		else if(this.x < that.x)
    			return -1;
    		else
    			return 0;
    	
    }

    // return string representation of this point (x, y)
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
    //this method is used whenever we need to compare two points.
    //this returns true or false. If the points under comparison are equal, it returns true, else false.
    @Override
    public boolean equals(Object obj) {
    	if(obj instanceof Point)
    		return (this.x == ((Point)obj).x  && this.y == ((Point)obj).y); //return true if both x's and y's are equal for both the points
    	return false; //else return false
    }
    
    
    
    /*
     * **********************************
     */
    public void draw() {
    	StdDraw.point(x, y); //this method draws the invoking point onto the canvas.
    }

    // draw line between this point and that point to standard drawing
    // this method is not required, but for receiving bonus points 
    public void drawTo(Point that) {
         /* YOUR CODE HERE */
    	StdDraw.line(x, y, that.x, that.y); //this method StdDraw.line draws a line from the invoking point to the end point
    	//this draws a line, given a start point and end point
    }

    
    
    
}