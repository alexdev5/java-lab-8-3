import task.DirectoryTask;
import writer.ResultWriter;

import java.nio.file.Files;
import java.nio.file.Path;

public class Main {

    public static void main(String[] args) {

        Path rootDir = Path.of("testdata");
        Path outputFile = Path.of("result.txt");
        char letter = 'a';

        // очищаємо файл перед запуском
        try {
            Files.deleteIfExists(outputFile);
        } catch (Exception ignored) {}

        ResultWriter writer = new ResultWriter(outputFile);

        Thread mainThread =
                new Thread(new DirectoryTask(rootDir, letter, writer));

        mainThread.start();

        try {
            mainThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Вивід файлу в консоль
        System.out.println("=== Result file content ===");
        try {
            Files.lines(outputFile).forEach(System.out::println);
        } catch (Exception e) {
            System.err.println("Failed to read result file");
        }
    }
}
