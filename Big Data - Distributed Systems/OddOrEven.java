
	import java.util.Scanner;
	

	public class OddOrEven 
	{
	
	
		public static void main(String[] args) 
		{
			
			int var = 0  ;
			
			while (var > -999)
			{
				var = reportOddOrEven() ;
			}
			System.out.println("Program ended");
		}
		
		private static int reportOddOrEven()
		{	
			System.out.print("Enter some number : ");
			Scanner  stdin  =  new Scanner(System.in) ; 
			int x = stdin.nextInt();
			
			if (x != -999)
			{
			if (  x % 2  == 0)
			{
				System.out.println("Number was even");
			}
			else
			{
				System.out.println("Number was odd");
			}
			}
			return x ; 
	
		}
	
	}
