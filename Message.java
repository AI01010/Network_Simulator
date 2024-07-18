public class Message extends Event 
{
    private String message;
    private int srcAddress, destAddress;

    private Host nextHop;
    private int distance;
    

    public Message( String message, int srcAddress, int destAddress ) 
    {
        this.message = message;
        this.srcAddress = srcAddress;
        this.destAddress = destAddress;
    }

    public String getMessage() 
    {
        return this.message;
    }

    public int getSrcAddress() 
    {
        return this.srcAddress;
    }

    public int getDestAddress() 
    {
        return this.destAddress;
    }

    public void setNextHop( Host destination, int  distance ) 
    {
        this.nextHop = destination;
        this.distance = distance;
    }

    /**
     * Sets the insertion time and arrival time for this Event.
     * <br>
     * It is assumed that any information needed to compute the arrival time from the insertion time is passed into
     * the Event's constructor (for example a duration).  This method should be called from within the FutureEventList's
     * insert method.
     *
     * @param currentTime the current simulation time
     */
    @Override    
    public void setInsertionTime(int currentTime)
    {
        this.insertionTime = currentTime;
        this.arrivalTime = currentTime + this.distance;  // 1 distance = 1 simulation time
    }

    /**
     * Cancel the Event.
     * <br>
     * This occurs after the Event has been removed from the future event list, probably before the arrival time has
     * been reached.
     */
    @Override
    public void cancel()
    {
        //not needed
    }

    /**
     * Handle (or execute) the Event.
     * <br>
     * This occurs after the Event has been removed from the future event list, due to the arrival time being reached.
     * For example, if this Event represents a network message, then calling handle() will 'process' the message at the
     * destination host.  If the Event is a Timer, then this will execute whatever needs to be done upon timer expiry.
     */
    @Override
    public void handle() 
    {
        nextHop.receive(this);
    }

    @Override
    public String toString() 
    {
        return "Message [message=" + message + ", srcAddress=" + srcAddress + ", destAddress=" + destAddress +"]";
    }
}