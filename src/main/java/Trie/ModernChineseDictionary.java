package Trie;

import utils.Utils;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ModernChineseDictionary extends TrieTree<Character> {
    public ModernChineseDictionary() {
        Path path = Path.of("./现代汉语词典UTF8.txt");
        try (var in = new Scanner(path)) {
            Pattern pattern = Pattern.compile("(?<=\\【)(.+?)(?=\\】)");
            while (in.hasNext()) {
                String word = in.next();
                Matcher matcher = pattern.matcher(word);

                while (matcher.find()) {
                    super.insert(getList(matcher.group()));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Utils.countTime(this::add30wChinsesSeqDic_clean);
    }

    private void add30wChinsesSeqDic_clean() {
        Path path = Path.of("./30wChinsesSeqDic_clean.txt");
        try (var in = new Scanner(path)) {
            while (in.hasNext()) {
                String word = in.next().trim();
                if (word.isEmpty() || word.isBlank()) continue;
                super.insert(getList(word.split(" ")[0]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
