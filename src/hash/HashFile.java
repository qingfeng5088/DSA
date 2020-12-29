package hash;

import utils.FileOperation;
import utils.Utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HashFile {
    private static final int fileCount = 8;
    private static final String PATH = "./hashFile/";

    public static void main(String[] args) throws IOException {
        deleteAllFile();
        Path path = Path.of("./现代汉语词典UTF8.txt");
        var in = new Scanner(path);
        while (in.hasNext()) {
            String word = in.next();
            Pattern pattern = Pattern.compile("(?<=\\【)(.+?)(?=\\】)");
            Matcher matcher = pattern.matcher(word);

            String searchedStr = "";
            while (matcher.find()) {
                searchedStr = matcher.group();
                System.out.println(writeToFile(searchedStr) + "|" + searchedStr + "|" + word);
            }
        }
    }

    private static String writeToFile(String content) {
        String filePath = PATH + (((fileCount - 1) & Utils.hash(content)) + 1) + ".txt";
        FileOperation.WriteFile(filePath, content);
        return filePath;
    }

    private static void deleteAllFile() throws IOException {
        FileOperation.deleteFile(new File(PATH));
    }
}
