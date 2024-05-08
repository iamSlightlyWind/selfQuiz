import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class selfQuiz {
    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("---- iamSlightlywind/selfQuiz ----");
        System.out.println("Select question library");

        readFile("library");
    }

    public static void readFile(String name) throws FileNotFoundException {
        File file = new File(name);
        Scanner reader = new Scanner(file);

        while (reader.hasNextLine()) {
            String data = reader.nextLine();

            if (data.equals("") || data.charAt(0) == '#')
                continue;

            System.out.println(data);
        }

        reader.close();
    }
}