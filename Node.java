public class Node 
{ 
    Event event; 
    Node prev; 
    Node next; 
  
    public Node( Event e ) 
    { 
        this.event = e; 
        this.prev = null; 
        this.next = null; 
    } 
} 