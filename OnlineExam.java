import java.util.*;
import java.io.*;

class OnlineExam {

    static Scanner sc = new Scanner(System.in);
    static boolean timeUp = false;
    static boolean submitted = false;

    static String[] questions = {
        "Java is a ____ language?\nA. Low\nB. High\nC. Machine\nD. Assembly",
        "Which keyword is used to create object?\nA. class\nB. object\nC. new\nD. this",
        "Entry point of Java program?\nA. start()\nB. main()\nC. run()\nD. init()",
        "Which is not OOP concept?\nA. Inheritance\nB. Polymorphism\nC. Compilation\nD. Encapsulation",
        "Size of int in Java?\nA. 2 bytes\nB. 4 bytes\nC. 8 bytes\nD. Depends",
        "Which loop is entry controlled?\nA. do-while\nB. while\nC. for\nD. both B and C",
        "Java programs are compiled into?\nA. Machine\nB. Bytecode\nC. Assembly\nD. Binary",
        "Which symbol is used for comments?\nA. //\nB. ##\nC. <!-- -->\nD. **",
        "Which package is imported by default?\nA. util\nB. io\nC. lang\nD. net",
        "Keyword used for inheritance?\nA. implements\nB. inherits\nC. extends\nD. super"
    };

    static char[] correctAnswers = {
        'B','C','B','C','B','D','B','A','C','C'
    };

    static char[] userAnswers = new char[10];
    static int remainingSeconds = 600;
    static boolean warningShown = false;

    public static void main(String[] args) throws Exception {

        Arrays.fill(userAnswers, '-');

        System.out.print("Enter Unique Student ID: ");
        String id = sc.next();

        if (isAttempted(id)) {
            System.out.println("Exam already attempted. Reattempt not allowed.");
            return;
        }

        showInstructions();

        Thread timer = new Thread(() -> startTimer());
        timer.start();

        int i = 0;

        while (!timeUp && !submitted) {

            showProgress(i);
            System.out.println("\nQuestion " + (i + 1));
            System.out.println(questions[i]);

            if (userAnswers[i] != '-') {
                System.out.println("Previously answered: " + userAnswers[i]);
            }

            System.out.println("\nOptions:");
            System.out.println("A/B/C/D - Answer");
            System.out.println("S - Skip");
            System.out.println("N - Next");
            System.out.println("P - Previous");
            System.out.println("SUBMIT - Submit exam");

            System.out.print("Enter choice: ");
            String input = sc.next().toUpperCase();

            if (input.equals("SUBMIT")) {
                submitted = true;
                break;
            }

            if (input.length() == 1) {
                char ch = input.charAt(0);

                if (ch == 'A' || ch == 'B' || ch == 'C' || ch == 'D') {
                    userAnswers[i] = ch;
                    if (i < questions.length - 1) i++;
                } 
                else if (ch == 'S' || ch == 'N') {
                    if (i < questions.length - 1) i++;
                } 
                else if (ch == 'P') {
                    if (i > 0) i--;
                } 
                else {
                    System.out.println("Invalid input. Try again.");
                }
            } 
            else {
                System.out.println("Invalid input. Try again.");
            }
        }

        timeUp = true;
        saveAttempt(id);
        showResult();
        System.out.println("Exam finished.");
    }

    static void startTimer() {
        try {
            while (remainingSeconds > 0 && !submitted) {
                Thread.sleep(1000);
                remainingSeconds--;

                if (remainingSeconds == 60 && !warningShown) {
                    System.out.println("\nWarning: 1 minute remaining.");
                    warningShown = true;
                }
            }
            timeUp = true;
        } catch (Exception e) {}
    }

    static void showProgress(int index) {
        int answered = 0;
        for (char c : userAnswers) {
            if (c != '-') answered++;
        }
        System.out.println("\nProgress: Question " + (index + 1) + " of 10");
        System.out.println("Answered: " + answered + " | Remaining: " + (10 - answered));
        System.out.println("Time Left: " + (remainingSeconds / 60) + " min " + (remainingSeconds % 60) + " sec");
    }

    static void showResult() {
        int correct = 0, wrong = 0;

        for (int i = 0; i < correctAnswers.length; i++) {
            if (userAnswers[i] == correctAnswers[i])
                correct++;
            else if (userAnswers[i] != '-')
                wrong++;
        }

        System.out.println("\nResult Summary");
        System.out.println("Correct Answers: " + correct);
        System.out.println("Wrong Answers: " + wrong);
        System.out.println("Total Score: " + correct + "/10");
    }

    static void showInstructions() {
        System.out.println("\nExam Instructions");
        System.out.println("1. Total Questions: 10");
        System.out.println("2. Time Limit: 10 minutes");
        System.out.println("3. One attempt only");
        System.out.println("4. You may skip and revisit questions");
        System.out.println("5. You may submit before time ends");
        System.out.println("\nPress ENTER to start exam");
        try { System.in.read(); } catch (Exception e) {}
    }

    static boolean isAttempted(String id) throws Exception {
        File file = new File("attempted.txt");
        if (!file.exists()) return false;

        Scanner fs = new Scanner(file);
        while (fs.hasNext()) {
            if (fs.next().equals(id)) {
                fs.close();
                return true;
            }
        }
        fs.close();
        return false;
    }

    static void saveAttempt(String id) throws Exception {
        FileWriter fw = new FileWriter("attempted.txt", true);
        fw.write(id + "\n");
        fw.close();
    }
}
