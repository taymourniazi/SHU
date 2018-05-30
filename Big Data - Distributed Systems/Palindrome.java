import  java.util.Scanner ;

public class Palindrome {

	public static void main(String[] args) {
		String original;
		
		System.out.println("Enter a word : ") ; 
		Scanner  stdin = new Scanner(System.in);
		original = stdin.nextLine() ;
		
		System.out.println("Length of '"  +   original  + "' is " +  original.length());
		
		boolean isPalindrome = true ; 
		
		int x = 0 ;
		
		while (isPalindrome  &&   x < original.length() / 2 )
		{
			System.out.println("Position " + x + "  - character is " + original.charAt(x));
			
			if  ( original.charAt(x) != original.charAt(original.length() - x - 1))
					{
						isPalindrome = false;
					}
			x++ ; 
		};
		
		if (isPalindrome)
		{
			System.out.println (original  +  "   IS a palindrome");
		}
		else
		{
			System.out.println (original + "  isn't a palindrome");
		};

	}

}
