
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class Main {
    FileCache fileCache;
    public Main() {
        this.fileCache = new FileCache();
        if (this.fileCache.isFirstStart) {
            System.out.println("First start detected, please add log files to the logFiles directory and restart the program.");
            System.exit(0);
        }
        if (this.fileCache.getCachedFiles().isEmpty()) {
            System.out.println("No log files found in the logFiles directory, please add log files and restart the program.");
            System.exit(0);
        }
        this.helpMessage();
        System.out.println("\nEnter a string to search for in the file names:");
        Scanner scanner = new Scanner(System.in);
        String input = "";
        while (!input.equals("/exit")) {
    input = scanner.nextLine();
    if(input.startsWith("/")) {
        this.executeCommand(input);
    } else {
        if(input.equals("") || input.isBlank() || input == null || input.length() == 0){
            System.out.println("No Input!");
            return;
        }
        List<String> results = fileCache.searchForStringInFile(input);
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
                System.out.println("\n");
                break;
            case "/save":
                this.fileCache.saveLogEntries();
                System.out.println("\n");
                break;
            default:
                this.helpMessage();
                System.out.println("\n");
                break;
        }
    }
    public void helpMessage(){
        System.out.println("Available commands:");
        System.out.println("/save - Save all Logs to a TXT File.");
        System.out.println("/exit - Exits the program.");
        System.out.println("/help - A list of all commands.");
    }
}
