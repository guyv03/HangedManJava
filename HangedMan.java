package hangedMan;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class HangedMan {
	private static final Scanner SCANNER = new Scanner(System.in);
	private static List<String> WORDS_BANK = new ArrayList<>();
	private static String USED_LETTERS = "";
	
	
	public static void main(String[] args) throws IOException {
		boolean playAgain = true;
		
		while (playAgain) {
			startGame();
			playAgain = finishMsgAndAgain();
		}
		
		System.out.println("Have a good one! :)");
	}

	private static void startGame() throws IOException {
		String chosenWord = "", temp = "";
		boolean done = false, validInput = false;
		int counter = 0;
		
		USED_LETTERS = "";
		getWordsBankFromFile();
		chosenWord = chooseWordFromBank();
		while (!done) {
			validInput = false;
			
			//Checks if the input is a single letter that has not been used yet
			while (!validInput) { 
				drawBoard(counter, chosenWord);
				System.out.print("Guess a letter: ");
				temp = SCANNER.nextLine();
				temp = temp.trim(); //Removes white spaces
				validInput = checkValidChar(temp);
			}
			
			USED_LETTERS += temp;
			if (!chosenWord.contains(temp)) {
				counter++;
			}
			
			if (checkIfWon(chosenWord)) {
				done = true;
				System.out.println("Good Job! You Won!!\n");
			}
			else if (counter > 9) {
				done = true;
				System.out.println("You Lost! Better Luck Next Time :D\n");
			}
		}
	}
	
	private static void getWordsBankFromFile() throws IOException {
		try {
			Path path = Paths.get("src//hangedMan//WordBank");
			String content = Files.readString(path);
			int currIndex = 0;
			while (currIndex >= 0) { //Loops as long as there are quotation marks in the string
				//Remove unwanted characters before word
				currIndex = content.indexOf('"');
				content = content.substring(currIndex + 1);
				
				if (currIndex >= 0) {
					//Getting the actual word
					currIndex = content.indexOf('"');
					WORDS_BANK.add(content.substring(0, currIndex));
					content = content.substring(currIndex + 1);
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
		    e.printStackTrace();
		}
	}

	private static String chooseWordFromBank() {
		Random rnd = new Random();
		int index = rnd.nextInt(WORDS_BANK.size());
		return WORDS_BANK.get(index);
	}

	private static boolean checkIfWon(String chosenWord) {
		boolean won = true;
		int i = 0;
		Character currChar = ' ';
		
		for (i = 0; i < chosenWord.length() && won; i++) {
			currChar = chosenWord.charAt(i);
			if (currChar != '-') {
				if (!USED_LETTERS.contains(currChar.toString())) {
					won = !won;
				}
			}
		}
		
		return won;
	}
	
	private static void drawBoard(int counter, String chosenWord) {
		int i = 0;
		Character currChar = ' ';
		
		printMan(counter);
		System.out.println("");
		System.out.print("The Word: ");
		for (i = 0; i < chosenWord.length(); i++) {
			currChar = chosenWord.charAt(i);
			if (USED_LETTERS.contains(currChar.toString()) || currChar == '-') {
				System.out.print(currChar);
			}
			else {
				System.out.print("_");
			}
		}
		System.out.println("     Used Letters: " + USED_LETTERS);
	}
	
	private static void printMan(int count) {
		switch (count) {
		case 0: {
			System.out.println("");
			System.out.println("");
			System.out.println("");
			System.out.println("");
			System.out.println("");
			System.out.println("");
			return;
		}
		case 1: {
			System.out.println("     _____");
			System.out.println("");
			System.out.println("");
			System.out.println("");
			System.out.println("");
			System.out.println("");
			return;
		}
		case 2: {
			System.out.println("     _____");
			System.out.println("     |");
			System.out.println("");
			System.out.println("");
			System.out.println("");
			System.out.println("");
			return;
		}
		case 3: {
			System.out.println("     _____");
			System.out.println("     |");
			System.out.println("     O");
			System.out.println("");
			System.out.println("");
			System.out.println("");
			return;
		}
		case 4: {
			System.out.println("     _____");
			System.out.println("     |");
			System.out.println("     O");
			System.out.println("     |");
			System.out.println("");
			System.out.println("");
			return;
		}
		case 5: {
			System.out.println("     _____");
			System.out.println("     |");
			System.out.println("     O");
			System.out.println("    \\|");
			System.out.println("");
			System.out.println("");
			return;
		}
		case 6: {
			System.out.println("     _____");
			System.out.println("     |");
			System.out.println("     O");
			System.out.println("    \\|/");
			System.out.println("");
			System.out.println("");
			return;
		}
		case 7: {
			System.out.println("     _____");
			System.out.println("     |");
			System.out.println("     O");
			System.out.println("    \\|/");
			System.out.println("     |");
			System.out.println("");
			return;
		}
		case 8: {
			System.out.println("     _____");
			System.out.println("     |");
			System.out.println("     O");
			System.out.println("    \\|/");
			System.out.println("     |");
			System.out.println("    /");
			return;
		}
		case 9: {
			System.out.println("     _____");
			System.out.println("     |");
			System.out.println("     O");
			System.out.println("    \\|/");
			System.out.println("     |");
			System.out.println("    / \\");
			return;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + count);
		}
	}
	
	private static boolean checkValidChar(String temp) {
		boolean isValid = false;
		
		if (temp.length() == 1) {
			if (Character.isLetter(temp.charAt(0))) {
				if (!USED_LETTERS.contains(temp)) {
					isValid = true;
				}
			}
		}
		
		return isValid;
	}
	
	private static boolean finishMsgAndAgain() {
		boolean again = false;
		String tempStr;
		
		System.out.println("Thank you for playing my Hanged Man game!");
		System.out.println("Would you like to play again?");
		System.out.println("Type y to play again or any other key to finish:");
		tempStr = SCANNER.nextLine();
		if (tempStr.equals("y")) {
			again = true;
		}
		
		return again;
	}
}
