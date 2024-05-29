import java.io.*;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class FileCache {

    public boolean isFirstStart = false;
    private final ArrayList<File> cachedFiles;
    private final ArrayList<String> logEntries;

    public FileCache() {
        this.cachedFiles = new ArrayList<>();
        this.logEntries = new ArrayList<>();
        File directory = new File("logFiles");
        if (directory.exists()) {
            scanFiles(directory);
        } else {
            directory.mkdir();
            this.isFirstStart = true;
        }
        this.scanFiles(directory);
    }

    private void scanFiles(File directory) {
        for (File file : directory.listFiles()) {
            if (file.isDirectory()) {
                scanFiles(file);
            } else if (file.getName().endsWith(".log") || file.getName().endsWith(".txt")){
                cachedFiles.add(file);
            }
        }
    }

    public ArrayList<String> searchForStringInFile(String s1) {
        if (this.cachedFiles.isEmpty()) {
            System.out.println("There are no Files cached...");
            return null;
        }

        ArrayList<String> result = new ArrayList<>();
        for (File file : cachedFiles) {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                int lineNumber = 0;
                while ((line = br.readLine()) != null) {
                    lineNumber++;
                    if (line.contains(s1)) {
                        String logEntry = "File: " + file.getName() + " Line " + lineNumber + ": " + line;
                        result.add(logEntry);
                        logEntries.add(logEntry);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public void saveLogEntries() {
        if (logEntries.isEmpty()) {
            System.out.println("No log entries to save.");
            return;
        }
        File file = logFile();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, true))) {
            for (String entry : logEntries) {
                bw.write(entry);
                bw.newLine();
            }
            logEntries.clear(); // Clear log entries after saving
            System.out.println("Log entries saved successfully. " + file.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private File logFile() {
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm").format(Calendar.getInstance().getTime());
        File file = new File("saved/Log-" + timeStamp + ".txt");
        try {
            file.createNewFile();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return file;
    }

    public ArrayList<File> getCachedFiles() {
        return cachedFiles;
    }
}
