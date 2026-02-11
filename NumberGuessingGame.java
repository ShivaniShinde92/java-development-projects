import java.util.Random;
import java.util.Scanner;

class NumberGuessingGame {

    Scanner sc = new Scanner(System.in);
    Random random = new Random();

    int number;
    int attempts;
    String playerName;

    void startGame() {
        System.out.println("Enter your name:");
        playerName = sc.nextLine();

        System.out.println("\nWelcome " + playerName + "!");
        chooseDifficulty();

        number = random.nextInt(100) + 1;
        playGame();
    }

    void chooseDifficulty() {
        System.out.println("\nChoose Difficulty Level:");
        System.out.println("1. Easy (12 attempts)");
        System.out.println("2. Medium (8 attempts)");
        System.out.println("3. Hard (5 attempts)");

        int choice = sc.nextInt();

        if (choice == 1) {
            attempts = 12;
        } else if (choice == 2) {
            attempts = 8;
        } else {
            attempts = 5;
        }
    }

    void playGame() {
        int guess;
        boolean isGuessed = false;

        System.out.println("\nI have selected a number between 1 and 100.");
        System.out.println("Start guessing!");

        for (int i = 1; i <= attempts; i++) {
            System.out.print("\nAttempt " + i + ": Enter your guess: ");
            guess = sc.nextInt();
 
            int diff = Math.abs(number - guess);

            if (guess == number) {
                System.out.println("\nCongratulations " + playerName + "!");
                System.out.println("You guessed the correct number in " + i + " attempts.");
                isGuessed = true;
                break;
            } else if (guess > number) {
                System.out.println("Your guess is higher.");
            } else {
                System.out.println("Your guess is lower.");
            }

            if (diff <= 2) {
                System.out.println("Hint: You are very close!");
            } else if (diff <= 5) {
                System.out.println("Hint: You are near.");
            }

            if (i >= 3) {
                if (number % 2 == 0) {
                    System.out.println("Hint: The number is even.");
                } else {
                    System.out.println("Hint: The number is odd.");
                }
            }
        }

        if (!isGuessed) {
            System.out.println("\nGame Over!");
            System.out.println("The correct number was: " + number);
            System.out.println("Better luck next time, " + playerName + ".");
        }
    }

    public static void main(String[] args) {
        NumberGuessingGame game = new NumberGuessingGame();
        game.startGame();
    }
}