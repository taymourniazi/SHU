	import java.util.*;
import java.io.*;
import java.util.Scanner;
    
    
public class useNotePad 
{

	public static void main(String[] args) 
	{
		System.out.println("Type a file name    OR   enter choice (1:new pad,  2:open 'test.txt'  3:read 'text.txt' as input.   0:end)  :   ");
		Scanner stdin = new Scanner(System.in) ;
		String choice ;
		
		choice = stdin.nextLine() ;
		
		while (choice.compareToIgnoreCase("0") != 0)
		{
			switch (choice)
			{
			case "1":
				myNotePad("") ;		// open new pad
				break ;
			case "2":
				myNotePad("test.txt") ;		// open new pad of specific name
				break ;
			
			case "3":
				readNotePad("test.txt") ;
				break;
			
			default :
				myNotePad(choice) ;
				
			};
			choice = stdin.nextLine() ;
		}		
		System.out.println("Program ended.") ;

	}
	

	 
	private static void myNotePad(String fileName) 
	{
	    Runtime rs = Runtime.getRuntime();
	 
	    try 
	    {
	      rs.exec("notepad " + fileName);
	    }
	    catch (IOException e) 
	    {
	      System.out.println(e);
	    }   
	 }
	
	
	
	private static void readNotePad(String fileName) 
	{	
	    try
	    {
		    Scanner scanner = new Scanner(new File (fileName));
		    String lineOfText ;
		    while (scanner.hasNextLine())
		    {
		    	lineOfText = scanner.nextLine() ;
		    	System.out.println(lineOfText) ;
		    }
		    scanner.close(); 
	    
	    	
	    } catch (FileNotFoundException e) 
	    {
	    	e.printStackTrace() ;
	    }; 
	}
}
	
	
