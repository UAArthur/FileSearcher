import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class FileCache {


    private final ArrayList<File> cachedFiles;

    public FileCache(){
        this.cachedFiles = new ArrayList<>();
        File directory = new File("logFiles");
        if(directory.exists()) {
            scanFiles(directory);
        } else {
            directory.mkdir();
        }
        this.scanFiles(directory);
    }

    private void scanFiles(File directory) {
        for(File file : directory.listFiles()) {
            if(file.isDirectory()) {
                scanFiles(file);
            } else {
                cachedFiles.add(file);
            }
        }
    }

    public ArrayList<String> searchForStringInFile(String s1){
        if(this.cachedFiles.isEmpty()){
            System.out.println("There are no Files cached...");
            return null;
        }
        
        ArrayList<String> result = new ArrayList<>();
        for(File file : cachedFiles) {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                int lineNumber = 0;
                while ((line = br.readLine()) != null) {
                    lineNumber++;
                    if (line.contains(s1)) {
                        result.add("File: " + file.getName() + " Line " + lineNumber + ": " + line);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
