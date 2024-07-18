/******************************************************************************
Ashraful Islam axi220026
Project 2 - Network Host-Pinging Simulation
CS 2336.003
*******************************************************************************/
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;

public class Main 
{
    public static void main(String[] args) 
    {     
        String [] parts;
        String line; 
        int srcAddress, destAddress, interval, duration, distance;
       
        SimpleHost host, neighbor;
        LinkedEventList eventList = new LinkedEventList();

        Map <Integer, SimpleHost> cachedSimpleHosts = new HashMap<>();
        
        try {
            Scanner scanner = new Scanner(new File("simulation4.txt"));  // open the input file
            if ( !scanner.hasNextLine() ) 
            {
                System.out.println("File is Empty");
                System.exit(0);
            }
        
            // create the network Host
            line = scanner.nextLine();
            srcAddress = Integer.parseInt(line);
            
            host = new SimpleHost( srcAddress, eventList ); 
            cachedSimpleHosts.put( srcAddress, host );
            
            // read the lines from the file until it hits "-1" 
            // to create the network topology around the Host
            while ( scanner.hasNextLine() ) 
            {
                line = scanner.nextLine();
                
                if ( line.equals("-1") ) 
                {
                    break; 
                }

                parts = line.split(" ");
                destAddress = Integer.parseInt(parts[0]); 
                distance = Integer.parseInt(parts[1]); 
               
                neighbor = new SimpleHost( destAddress, eventList );  
                host.addNeighbor( neighbor, distance );
                // set bi-directional network connectivity
                neighbor.addNeighbor( host, distance ); 
                
                cachedSimpleHosts.put( destAddress, neighbor );                
            } 
            

            // read the remiaing lines from the file to bootstrap the simulation
            // by sending and receiving messages (aka ping requests) 
            while ( scanner.hasNextLine() ) 
            {
                line = scanner.nextLine(); 
            
                parts = line.split(" ");
                srcAddress = Integer.parseInt(parts[0]); 
                destAddress = Integer.parseInt(parts[1]); 
                interval = Integer.parseInt(parts[2]); 
                duration = Integer.parseInt(parts[3]);                 

                cachedSimpleHosts.get( srcAddress ).sendPings( destAddress, interval, duration );
            }             

            // remove events from the list until it is empty           
            while ( eventList.size() > 0 ) 
            {  
                eventList.removeFirst().handle();
            } 

            scanner.close();
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("Error: FileNotFoundException");  
        }   
    }
}