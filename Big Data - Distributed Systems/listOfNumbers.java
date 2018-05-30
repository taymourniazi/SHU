//   http://docs.oracle.com/javase/tutorial/essential/exceptions/catch.html

package listOfNumbers;


import java.io.*;
import java.util.List;
import java.util.ArrayList;

public class listOfNumbers 
{

 private static List<Integer> list;
 private static final int SIZE = 10;

	public static void main(String[] args)  
	{
	     list = new ArrayList<Integer>(SIZE);
	     for (int i = 0; i < SIZE; i++) 
	     {
	         list.add(new Integer(i));
	     }
	     try
	     {
	    	 writeList() ;
	     }
	     catch (IOException               e)  {System.out.println ("MAIN : caught IOException");} 
	     catch (IndexOutOfBoundsException e)  {System.out.println ("MAIN : caught IndexException");}      
         catch (Exception cause) {
	  
        	 	// Demonstrates using   getMessage   and   useing   getStackTrace
	    	    System.out.println ("MAIN : caught other Exception :  "  +   cause.getMessage() );} ;	   
	    	 
	    	    StackTraceElement elements[] = cause.getStackTrace();
	    	    for (int i = 0, n = elements.length; i < n; i++) {       
	    	        System.err.println(elements[i].getFileName()
	    	            + ":" + elements[i].getLineNumber() 
	    	            + ">> "
	    	            + elements[i].getMethodName() + "()");
	    	    } 
    }

 public static void writeList()  throws IOException, IndexOutOfBoundsException 
 {
     PrintWriter out = null ;
     
     try
     {
    	 System.out.println("Have entered TRY statement");
	     out =  new  PrintWriter(new FileWriter("OutFile.txt"));
	
	     for (int i = 0; i <= SIZE; i++) 
	     {
	         // The get(int) method throws IndexOutOfBoundsException, which must be caught.
	         out.println("Value at: " + i + " = " + list.get(i));
	     }
    	 System.out.println("Have completed TRY statement");
     }
     
     
     catch (Exception e)
     {
    	 System.out.println("INTERNAL : catch all errors") ; 
			throw e ; 
     }
     
     
     /*   Could have Catch per error type  
     catch (IndexOutOfBoundsException e)
     		{  System.out.println("Index out of bounds") ; 
     			throw e ; 
     		}
    	 
     catch (IOException e)
		{  System.out.println("IE Exception - " + e.getMessage()   ) ; 
			throw e ; 
		}
     */ 
    
     /*   Note :   From Java 7 should be able to catch two exceptions within same clause 
     catch (IndexOutOfBoundsException|IOException    ex )
     {
    	 System.out.println("Caught exception ") ;
    	 throw ex  ;
     }
     */
     
     finally 
     {
    	    if (out != null) 
    	    { 
    	        System.out.println("Closing PrintWriter");
    	        out.close(); 
    	    } else 
    	    { 
    	        System.out.println("PrintWriter not open");
    	    }
     } 

     
 }
}
