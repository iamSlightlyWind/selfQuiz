import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class termQuiz {

    static String currentFile = "";
    static ArrayList<QnA> questionList = new ArrayList<>();
    static Scanner scan = new Scanner(System.in);

    static int questionSize = 0;

    public static void main(String[] args) throws FileNotFoundException {
        currentFile = System.getProperty("user.dir");

        System.out.println("---- iamSlightlywind/selfQuiz ----\n");
        System.out.println("Select question library:");

        File current = new File(currentFile);

        while (!current.isFile()) {
            chooseFile();
            current = new File(currentFile);
        }

        readFile();

        getQuizModifiers();
    }

    public static int random() {
        Random random = new Random();
        return random.nextInt(questionSize);
    }

    public static ArrayList<String> getRandomAnswer(QnA currentQuestion) {
        ArrayList<String> answerList = new ArrayList<>();

        answerList.add(currentQuestion.answer);

        while (answerList.size() < 4) {
            boolean duplicated = true;
            String newAnswer = "";

            while (duplicated) {
                duplicated = false;
                newAnswer = questionList.get(random()).answer;
                for (int i = 0; i < answerList.size(); i++) {
                    if (newAnswer.equals(answerList.get(i))) {
                        duplicated = true;
                    }
                }
            }
            answerList.add(newAnswer);

        }

        Collections.shuffle(answerList);
        return answerList;
    }

    public static void startQuiz(boolean modifierCount, int count, boolean modifierCorrect) {
        int attempt = 0;
        int questionAsked = 0;

        System.out.println();

        if (!modifierCount) {
            int total = questionList.size() - 1;
            int current = 0;

            while (questionAsked < questionSize) {
                if(modifierCorrect && attempt == questionSize){
                    break;
                }

                System.out.println();
                current = random();

                if (questionList.get(current).correct == false) {
                    
                    if(modifierCorrect && questionList.get(current).asked){
                        continue;
                    }

                    System.out.println(questionList.get(current).question);
                    ArrayList<String> answerList = getRandomAnswer(questionList.get(current));
                    for (int i = 0; i < answerList.size(); i++) {
                        System.out.println((i + 1) + ". " + answerList.get(i));
                    }

                    int choice = Integer.parseInt(scan.nextLine());
                    if (answerList.get(choice - 1).equals(questionList.get(current).answer)) {
                        questionList.get(current).correct = true;
                        questionAsked++;
                        System.out.println("Correct!");
                    } else {
                        System.out.println("Incorrect!");
                    }
                    questionList.get(current).asked = true;
                    attempt++;
                }
            }
        }
    }

    public static void getQuizModifiers() {
        int count = 0;

        boolean modifierCount = false;
        boolean modifierCorrect = false;

        System.out.println("How would you want to be quized?\n");

        System.out.println("1. Once per question");
        System.out.println("2. Multiple times per question");
        System.out.print("Choice: ");
        modifierCount = Integer.parseInt(scan.nextLine()) == 2;

        if (modifierCount) {
            System.out.println();
            System.out.println("How many times per question?");
            System.out.print("Count: ");
            count = Integer.parseInt(scan.nextLine());
        }

        System.out.println();
        System.out.println("1. All attempt counts");
        System.out.println("2. Until all questions are correct");
        System.out.print("Choice: ");
        modifierCorrect = Integer.parseInt(scan.nextLine()) == 1;

        startQuiz(modifierCount, count, modifierCorrect);
    }

    public static void chooseFile() {
        System.out.println("\n" + currentFile);

        List<String> files = getFileList(currentFile);
        System.out.println("0. ../");

        for (int i = 1; i < files.size(); i++) {
            System.out.println(i + ". " + files.get(i));
        }

        System.out.print("Change: ");
        int choice = Integer.parseInt(scan.nextLine());

        if (choice > 0 && choice < files.size()) {
            currentFile += "/" + files.get(choice);
        } else if (choice == 0) {
            currentFile = currentFile.substring(0, currentFile.lastIndexOf("/"));
        }
    }

    public static List<String> getFileList(String directory) {
        List<String> results = new ArrayList<String>();

        File[] files = new File(directory).listFiles();

        for (File file : files) {
            if (file.isDirectory()) {
                results.add(file.getName() + "/");
            } else {
                results.add(file.getName());
            }
        }

        return results;
    }

    public static void readFile() throws FileNotFoundException {
        File file = new File(currentFile);
        Scanner reader = new Scanner(file);

        while (reader.hasNextLine()) {
            String data = reader.nextLine();

            if (data.equals("") || data.charAt(0) == '#')
                continue;

            questionList.add(new QnA(data));
        }

        questionSize = questionList.size();

        reader.close();
    }
}