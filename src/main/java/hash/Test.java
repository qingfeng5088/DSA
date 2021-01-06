package hash;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Test {
    private static final String PATH = "./hashFile/2.txt";

    public static void main(String[] args) throws IOException {
        Path path = Path.of(PATH);
        System.out.println(Files.isDirectory(path));
        System.out.println(Files.exists(path));
        Files.createFile(path);
    }
}
