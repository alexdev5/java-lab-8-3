package writer;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class ResultWriter {

    private final Path outputFile;

    public ResultWriter(Path outputFile) {
        this.outputFile = outputFile;
    }

    public synchronized void writeLine(String line) {
        try (BufferedWriter writer = Files.newBufferedWriter(
                outputFile,
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND
        )) {
            writer.write(line);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Failed to write to file: " + outputFile);
        }
    }
}
