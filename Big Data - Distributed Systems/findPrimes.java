package FindPrimes;

public class findPrimes 
{

	public static void main(String[] args) 
	{
		int limit = 2 ;
		int number = 200000 ;
		System.out.println("Finding first " + limit + " primes larger than " + number ); 
		

		int x = 3;
		int calculated =   number % x ;
		int  countOfPrimes = 0;
		
		int biggestGap = 0 ;
		int previousPrime = number ; 
	
		while  ( countOfPrimes<limit ) 	
		{
			if (x == number)
			{	//Have found next prime
				if (number - previousPrime > biggestGap)    biggestGap = number - previousPrime ; 
			
				System.out.println ("\n ===>" + + countOfPrimes + "   " +  number +  " is prime.   Biggest gap is " + biggestGap);
				previousPrime = number ; 
				number ++ ;
				x = 3 ; 
				countOfPrimes++ ;	
			}
			else
			{	//Check if current number is divisible by x
			    calculated =   number % x ;
			    if ( calculated == 0)
			    {	
			    	// Is divisible, so start checking next number
			    	//System.out.print (number +  " by " + x + ",   " ) ;
			    	number ++ ;
			    	x = 3 ;
			    }
			    else
			    {
			    	// x doesn't divide into number, so try next x
			    	x++  ;
			    };
			};
		} ;
	}
}
