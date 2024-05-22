import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    FileCache fileCache = new FileCache();

    public Main() {
        System.out.println("Enter a string to search for in the file names:");

        Scanner scanner = new Scanner(System.in);
        String input = "";

        while (!input.equals("/exit")) {
            input = scanner.nextLine();
            if(input.startsWith("/")) {
                this.executeCommand(input);
            } else {
                ArrayList<String> results = fileCache.searchForStringInFile(input);
                if(results.isEmpty())
                   return;

                for (String result : results) {
                    System.out.println(result);
                }
            }
        }

        scanner.close();
        System.out.println("Exiting program...");
    }

    public static void main(String[] args) {
        new Main();
    }

    public void executeCommand(String command){
        switch (command) {
            case "/exit":
                System.out.println("Exiting program...");
                System.exit(0);
                break;
            
            case "/help":
                this.helpMessage();
                break;
            default:
            this.helpMessage();
                break;
        }
    }

    public void helpMessage(){
        System.out.println("Available commands:");
        System.out.println("/exit - Exits the program");
        System.out.println("/help - A list of all commands");
    }
}
