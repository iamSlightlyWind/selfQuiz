import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class termQuiz {

    static String currentDirectory = "";

    public static void main(String[] args) throws FileNotFoundException {
        currentDirectory = System.getProperty("user.dir");

        System.out.println("---- iamSlightlywind/selfQuiz ----\n");
        System.out.println("Select question library:");

        chooseDirectory();
        chooseDirectory();
        chooseDirectory();
        chooseDirectory();
    }

    public static void chooseDirectory() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n" + currentDirectory);

        List<String> files = getFileList(currentDirectory);
        System.out.println("0. ../");

        for (int i = 1; i < files.size(); i++) {
            System.out.println(i + ". " + files.get(i));
        }

        System.out.print("Change: ");
        int choice = Integer.parseInt(scanner.nextLine());

        if (choice > 0 && choice < files.size()) {
            currentDirectory += "/" + files.get(choice);
        } else if (choice == 0) {
            currentDirectory = currentDirectory.substring(0, currentDirectory.lastIndexOf("/"));
        }
    }

    public static List<String> getFileList(String directory) {
        List<String> results = new ArrayList<String>();

        File[] files = new File(directory).listFiles();

        for (File file : files) {
            if(file.isDirectory()){
                results.add(file.getName() + "/");
            } else {
                results.add(file.getName());
            }
        }

        return results;
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