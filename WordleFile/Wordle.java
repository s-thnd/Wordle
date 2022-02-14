import java.io.*;
import java.util.*;

public class Wordle {

	public static void main(String[] args) throws FileNotFoundException  {

		int randomNum = 1 + (int)(Math.random() * 4266);
		int count = 1;
		String target = getWord(randomNum);

		gameCode(count, target);
	}
	
	/**
	 * This is where most of the magic happens. A scanner is used to 
	 * read in from STDIN and println statements are used to print to 
	 * STDOUT. This is being iterated in a while loop that exits once
	 * the correct guess is made or once six invalid guesses have been made.
	 * @param count, the number of turns that have passed
	 * @param target, the "correct" selected word
	 * @throws FileNotFoundException
	 */

	public static void gameCode(int count, String target) throws FileNotFoundException {
		//System.out.println(target); 
		
		System.out.println("A word has been selected. Good luck!");
		System.out.print("Input: ");
		Scanner scan = new Scanner(System.in);
		String notIn = "";
		
		while(count < 7) {
			String word = scan.nextLine().toLowerCase().trim();
			String noDuplicates = removeDuplicate(word);
			
			if(word.equalsIgnoreCase("REVEAL WORD")) {
				System.out.println("The word was: " + target);
				count = 1000;
				break;
			}
			if(word.equalsIgnoreCase("GIVE HINT")) {
				int randomNum = (int)(Math.random() * 5);
				System.out.println("One of the letters is: " + target.charAt(randomNum));
				System.out.print("Input: ");
				
			}
			else if(isValid(word)) {
				String contains = "";
				String rightPlace = "";

				for(int i = 0; i < word.length(); i++) {
					String letter = word.substring(i, i+1);
					if(target.substring(i, i+1).equals(letter)) {
						rightPlace += letter + " ";
					}
					else {
						rightPlace += "- ";
					}
				}
				
				for(int i = 0; i < noDuplicates.length(); i++) {
					String letter = noDuplicates.substring(i, i+1);
					
					if(target.contains(letter) && !contains.contains(letter)) {
						contains += letter + " ";
					}
					else if(!notIn.contains(letter)) {
							notIn += letter + " ";
						}
					
				}
				
				System.out.println("Contains: " + contains);
				System.out.println("DOESN'T Contain: "  + notIn);
				System.out.println("The letters in the right place are: " + rightPlace);
				System.out.println();
				
				if(word.equals(target)) {
					System.out.println("Congrats! You got it in " + count + " tries!");
					count = 100;
				}
				else if(count == 6) {
					System.out.println("Sorry, you didn't get it. The word was: " + target);
				}
				else {
					System.out.print("Input: ");
				}
				count++;
			}
			else {
				printError(word);
			}		
		}
		scan.close();		
	}
	
	/**
	 * This is the method that prints out the
	 * error statement when an invalid word is entered.
	 * @param word, the invalid word
	 */
	public static void printError(String word){
		System.out.println(word + " is not valid. Try again.");
		System.out.println();
		System.out.print("Input: ");
	}
	
	/**
	 * Checks against the list of all words to 
	 * see if the users guess is in the list.
	 * @param s, the word that the user is guessing
	 * @return true if the word is valid and false if not
	 * @throws FileNotFoundException
	 */
	public static boolean isValid(String s) throws FileNotFoundException {
		File text = new File("FiveLetterWords.txt");
		Scanner scan = new Scanner(text);
		
		boolean out = false;
		while(scan.hasNext()) {
			if(s.equals(scan.next())) {
				out = true;
			}
		}
		
		scan.close();
		return out;
	}
	
	/**
	 * Selects the nth word in the list. 
	 * @param n, the index of the word that we are searching for
	 * @return the word in the nth index of the file
	 * @throws FileNotFoundException
	 */
	public static String getWord(int n) throws FileNotFoundException {
		File text = new File("FiveLetterWords.txt");
		Scanner scan = new Scanner(text);
		String word = "";
		for(int i = 0; i < n; i++) {
			word = scan.next();
		}
		scan.close();
		return word;
	}
	
	/**
	 * Simplifies a word by returning all duplicate letters.
	 * ex. "hoop" becomes "hop"
	 * @param string, the word that we are simplifying
	 * @return the simplified word
	 */
	public static String removeDuplicate(String string)
    {
		char str[] = string.toCharArray();
		int n = str.length;
		String output = "";
        HashSet<Character> s = new LinkedHashSet<>(n - 1);
        for (char x : str)
            s.add(x); 
        for (char x : s)
            output += x;
        return output;
    }

}
