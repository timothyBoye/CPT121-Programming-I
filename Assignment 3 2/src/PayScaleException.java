
/**
 * University Employee Database Program
 * CPT121 / COSC2135 Programming 1
 * Assignment 3
 * SP1 2014
 * 
 * Student ID s3482043
 * 
 * @author timothyboye
 *
 * Pay Scale Exception class
 * 
 * Called when updates to the pay scale of an employee
 * fail for various reasons.
 * 
 * Only a constructor containing a message parameter is provided
 * to ensure an error message is propegated through the system.
 */
@SuppressWarnings("serial")
public class PayScaleException extends Exception
{
   
   /**
    * Constructor with message parametre to pass on the error
    * incurred
    * 
    * @param message
    */
   public PayScaleException(String message)
   {
      super(message);
   }

}
