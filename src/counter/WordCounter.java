package counter;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class WordCounter {

    public int countWordsStartingWith(Path file, char letter) {
        int count = 0;
        char target = Character.toLowerCase(letter);

        try (BufferedReader reader = Files.newBufferedReader(file)) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] words = line.split("\\W+"); // розділяємо по не-літерах

                for (String word : words) {
                    if (!word.isEmpty()
                            && Character.toLowerCase(word.charAt(0)) == target) {
                        count++;
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Failed to read file: " + file);
        }

        return count;
    }
}
