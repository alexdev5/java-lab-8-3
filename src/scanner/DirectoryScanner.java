package scanner;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class DirectoryScanner {

    public void scan(Path dir) {
        if (!Files.isDirectory(dir)) {
            return;
        }

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
            for (Path entry : stream) {

                if (Files.isDirectory(entry)) {
                    // рекурсивний виклик
                    scan(entry);

                } else if (isTextFile(entry)) {
                    // тут пізніше буде підрахунок слів
                    System.out.println("Found text file: " + entry);
                }
            }
        } catch (IOException e) {
            System.err.println("Failed to scan directory: " + dir);
        }
    }

    private boolean isTextFile(Path file) {
        return file.toString().toLowerCase().endsWith(".txt");
    }
}
