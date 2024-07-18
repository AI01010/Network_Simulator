public class SimpleHost extends Host 
{
    private int destAddress, interval;
    private int intervalTimerId, durationTimerId;
    private int rtt;

    public SimpleHost( int hostAddr, LinkedEventList list ) 
    {
        setHostParameters( hostAddr, list );
    }

    /**
     * This is called when a host receives a Message event from a neighboring host.
     *
     * @param msg the Message event received
     */
    @Override
    protected void receive(Message msg)
    {
        switch ( msg.getMessage() ) 
        {
            case "ping-request":
                System.out.println("["+getCurrentTime()+"ts] Host "+msg.getDestAddress()+": Ping request from host "+msg.getSrcAddress());
                // send response back to srcHost
                Message newMsg = new Message("ping-response", msg.getDestAddress(), msg.getSrcAddress() );
                sendToNeighbor(newMsg);
                break;
            case "ping-response":
                this.rtt = getCurrentTime() - this.rtt; // rtt calculation
                System.out.println("["+getCurrentTime()+"ts] Host "+msg.getDestAddress()+": Ping response from host "+msg.getSrcAddress()+" (RTT = "+rtt+"ts)");
                break;
            default:  
                break;
        }
    }

    /**
     * This is called after a Timer event expires, and enables you to write code to do something upon timer
     * expiry.  A timer expires when the duration set for the timer is reached.
     *
     * @param eventId the event id of the Timer event which expired
     */
    @Override
    protected void timerExpired(int eventId)
    {
        if (eventId == durationTimerId) // if host has reached it max duration cancel ping timers
        {
            timerCancelled(intervalTimerId); // stop sending ping-requests
        }
        else if (eventId == intervalTimerId) 
        {          
            // send ping-request
            this.rtt = getCurrentTime(); //rtt at time sent
            Message newMsg = new Message("ping-request", getHostAddress(), destAddress );
            sendToNeighbor(newMsg);     //adds to fel list
            System.out.println("["+getCurrentTime()+"ts] Host "+ getHostAddress() +": Sent ping to host "+ destAddress );
            
            //new timer
            this.intervalTimerId = newTimer(interval);  
        }    
    }

    /**
     * This is called after a Timer event is cancelled, and enables you to write code to do something upon timer
     * cancellation.
     *
     * @param eventId the event id of the Timer event which was cancelled
     */
    @Override
    protected void timerCancelled(int eventId)
    {
        System.out.println("[" + getCurrentTime() + "ts] Host " + getHostAddress() + ": Stopped sending pings" );
        cancelTimer(eventId); //stop timer
    }
 
    public void sendPings(int destAddr, int interval, int duration ) 
    {
        this.destAddress = destAddr;
        this.interval = interval;
     
        //create timers
        this.intervalTimerId = newTimer(interval);
        this.durationTimerId = newTimer(duration);
    }
}