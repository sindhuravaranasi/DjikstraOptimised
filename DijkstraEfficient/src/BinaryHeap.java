/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author zyao01
 */
 
import java.lang.Comparable; 


public class BinaryHeap<Key extends Comparable<Key>>
{      
    private class Pair<Key extends Comparable<Key>> {
        Key key; 
        int node;  // node ID for graphs, 0, 1, ..., V-1
        public Pair(Key k, int v)
        {	
            this.key = k; 
            this.node = v; 
        }
    }
  
    private int N = 0;  // number of elements in BH 
    private Pair[] a; 	  // the heap array    
    private int[] location; // index where a node’s key is saved
   
    // heap can hold up to V key-value pairs
    public BinaryHeap(int V) {  // V is number of nodes on the graph   
       a = new Pair[V + 1];   // index 0 is not used    
       location = new int[V]; 
    }    
  
  public Key getKey(int v)
  {
      if (v < 0 || v >= location.length)
          throw new ArrayIndexOutOfBoundsException("Invalid node ID");
      
      int i = location[v]; 
      if (i == -1)
          throw new RuntimeException("this node's key has been removed"); 
          
      return (Key) a[i].key; 
  }

  // insert <key, v> onto heap, v is a node ID
  public void insert(Key x, int v) 
  {   
      if (v < 0 || v >= location.length) // node v must be 0, 1, ..., V-1
          throw new ArrayIndexOutOfBoundsException("Invalid node ID");
      if (N > a.length - 1)  // can insert up to V keys onto heap only
          throw new ArrayIndexOutOfBoundsException("Heap full already");
      
      Pair p = new Pair(x, v); 
      a[++N] = p;   //increase N first
      location[v] = N; // node v's key is saved at index N of the heap
      percolateUp(N); // move smaller key up and save new location
  }
  private void percolateUp(int i) 
  {    
      while (i > 1 && less(i, i/2)) 
      {
        exchange(i, i/2);  // move up smaller key
        i = i/2; // up one level      
      }      
  }
  
  private boolean less(int i, int j) 
  {
    return a[i].key.compareTo(a[j].key) < 0; 
  }
  
  private void exchange(int i, int parent)  
  {    
      Pair temp = a[i];
      int node = a[i].node; 
      int nodeP = a[parent].node; 
      a[i] = a[parent];
      a[parent] = temp; 
      location[node] = parent;  // new location for node’s key    
      location[nodeP] = i;      // new location for parent's key
  }   
  
  public boolean isEmpty()
  {      return N == 0;   }
  
  // delete the minimum key and return its node ID
  public int deleteMin() {    
    if (isEmpty())  throw new RuntimeException("Heap empty");             
            
    int v = a[1].node;  // root’s value
    exchange(1, N--); // aft exch with last item, then reduce N 
    percolateDown(1); // move larger keys down
    a[N+1] = null; // prevent loitering
    location[v] = -1;   // node v's key removed
    return v;   // return nodeID
  }
  
  // return the minimum key in the heap
  public Key min()
  {
      return (Key) a[1].key;  
  }
  
  public int nodeMinKey()
  {
      return a[1].node; 
  }
  private void percolateDown(int i) 
  {    
      while (2 * i <= N) {  // while node i has children      
          int j = 2 * i;   // j: index for left child
          
          // if node at i has two children, pick smaller child:      
          if (j < N && less(j + 1, j)) 
              j++;       
          if (less(i, j)) //if parent i’s key is smaller, break             
              break;      
          exchange(i, j); 
      
          i = j; // go down on level;
    } 
  }

  public void decreaseKey(int v, Key newK) 
  {
       int i = location[v]; // find index where v’s key is saved    
      
       if (a[i].key.compareTo(newK) < 0)        
           throw new RuntimeException("new key is not smaller");   
      
       if (a[i].node != v)  // nodeID should be node v       
           throw new RuntimeException("bug: node IDs are not consistent"); 
      
       a[i].key = newK;  // update’s key   
       percolateUp(i);  // move smaller key up 
  }   
   
  public boolean contains(int i) {
      if (i < 0 || i >= a.length) throw new IndexOutOfBoundsException();
      return location[i] == -1;
  }
}
