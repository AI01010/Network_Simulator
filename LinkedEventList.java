public class LinkedEventList implements FutureEventList 
{
    private Node head;  
    private int size;
    private int simulationTime;

    public LinkedEventList() 
    {
        this.head = null;
        this.size = 0;
        this.simulationTime = 0;
    }

    public void insert(Event e) 
    {
        e.setInsertionTime( simulationTime );

        Node temp = new Node(e);
        Node current = this.head;

        //emtpy list
        if (current == null)  
        {
            this.head = temp;
        }
        else 
        {
            // find the position to insert the new event based on the arrival time 
            while ( current.next != null && e.getArrivalTime() >= current.event.getArrivalTime()) 
            {
                current = current.next; 
            }

            // at the front of the list
            if (current == this.head && e.getArrivalTime() < current.event.getArrivalTime()) 
            {
                temp.next = current;
                current.prev = temp;
                this.head = temp; 
            }
            // at the end of the list
            else if (current.next == null && e.getArrivalTime() >= current.event.getArrivalTime()) 
            {
                current.next = temp; 
                temp.prev = current;
            } 
            // in between the prev and next of current
            else 
            {
                temp.next = current;
                current.prev.next = temp;
                temp.prev = current.prev;
                current.prev = temp;
            } 
        }
        
        size++;
    }

    @Override
    public Event removeFirst() 
    {
        // empty list
        if (this.head == null) 
        {
            return null;
        }
        
        Event removed = this.head.event;

        // single node list
        if ( head.next == null ) 
        {
            this.head = null;
        }
        else 
        {
            this.head = this.head.next;
            this.head.prev = null;
        }

        size--;
        simulationTime = removed.getArrivalTime();
        
        return removed;
    }

    @Override
    public boolean remove(Event e)
    {
        Node current = this.head;

        // empty list or null event
        if ( this.head == null || e == null )  
        {
            return false;
        }

        while ( current != null ) 
        {
            // event found
            if ( current.event == e ) 
            {
                // first node
                if ( current == this.head )  
                {
                    this.head = current.next;
                    if ( this.head != null )
                    {
                        this.head.prev = null;
                    }
                }
                // last node
                else if ( current.next == null ) 
                {
                    current.prev.next = null;
                }
                else 
                {
                    current.prev.next = current.next;
                    current.next.prev = current.prev;
                }

                // found and removed
                size--;
                return true;
            }

            // otherwise, keep checking until the list ends
            current = current.next;
        }

        // not found
        return false;
    }

    @Override
    public int size() 
    {
        return this.size;
    }

    @Override
    public int capacity() 
    {
        return this.size;
    }

    @Override
    public int getSimulationTime()
    {
        return this.simulationTime;
    } 
    
    // for debugging... 
    // public void display () 
    // { 
    //     Node temp = this.head; 
    //     while (temp != null) { 
    //         System.out.print(temp.event + " --> "); 
    //         temp = temp.next; 
    //     } 
    //     System.out.println("NULL"); 
    // } 
}