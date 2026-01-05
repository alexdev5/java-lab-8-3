package task;

import counter.WordCounter;
import writer.ResultWriter;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class DirectoryTask implements Runnable {

    private final Path directory;
    private final char letter;
    private final WordCounter counter;
    private final ResultWriter writer;

    public DirectoryTask(Path directory, char letter, ResultWriter writer) {
        this.directory = directory;
        this.letter = letter;
        this.writer = writer;
        this.counter = new WordCounter();
    }

    @Override
    public void run() {
        List<Thread> subThreads = new ArrayList<>();

        try (DirectoryStream<Path> stream =
                     Files.newDirectoryStream(directory)) {

            for (Path entry : stream) {

                if (Files.isDirectory(entry)) {
                    Thread t = new Thread(
                            new DirectoryTask(entry, letter, writer));
                    t.start();
                    subThreads.add(t);

                } else if (isTextFile(entry)) {
                    int count =
                            counter.countWordsStartingWith(entry, letter);

                    writer.writeLine(entry + " -> " + count);
                }
            }

            for (Thread t : subThreads) {
                t.join();
            }

        } catch (IOException | InterruptedException e) {
            System.err.println("Error processing directory: " + directory);
        }
    }

    private boolean isTextFile(Path file) {
        return file.toString().toLowerCase().endsWith(".txt");
    }
}
