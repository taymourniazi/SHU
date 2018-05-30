package AreaOfCircle;
import java.util.Scanner;
import java.io.*;


public class AreaOfCircle 
{

	public static void main(String[] args)     	
	{
		final double pi = 3.14159;
		double radius,  area ;	
		      
        try
        {
        Scanner scanner = new Scanner(new File ("data.txt"));
        radius = Double.parseDouble(scanner.nextLine()) ;
        } catch (FileNotFoundException e) {
          radius = -1.0 ;
        };  
        
		area = pi * radius * radius ;
		
		System.out.print("The area of circle with radius ");
		System.out.print(radius);
		System.out.print(" is ");
		System.out.println(area);
	}
}
