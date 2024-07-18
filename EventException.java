/**
 * Base class for EventException.
 * Manages unchecked exception by extending a base exception class 
 * 
 * @param msg the message of run time exceptions throws by the Host class 
 */
public class EventException extends RuntimeException {
   EventException(String message){
      super(message);
   }
}