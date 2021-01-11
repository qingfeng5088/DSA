package SearchEngines;

import Trie.ModernChineseDictionary;

import java.util.ArrayList;
import java.util.List;

public class Test05 {

    public static void main(String[] args) {
        ModernChineseDictionary dictionary = new ModernChineseDictionary();

        List<List<Character>> retList =  dictionary.search(dictionary.getList("计划"));
        for (List<Character> es : retList) {
            System.out.println(dictionary.ListToString(es));
        }

        List<String> words = new ArrayList<>();
        String webChineseContent = "采购计划反映了中国作为一个大国的决心";

        List<List<Character>> dicList = null;
        for (int i = 0; i < webChineseContent.length(); i++) {
            dicList = dictionary.search(List.of(webChineseContent.charAt(i)));
            for (int j = i + 1; j < webChineseContent.length(); j++) {
                if (!isCharExist(webChineseContent, dicList, j, (j - i))) {
                    words.add(webChineseContent.substring(i, j));

                    i = j - 1;
                    break;
                }
            }
        }

        System.out.println(words);

    }

    private static boolean isCharExist(String webChineseContent, List<List<Character>> dicList, int j, int startIndex) {
        boolean isFind = false;

        for (List<Character> characters : dicList) {
            if (characters.size() < startIndex + 1) continue;
            if (characters.get(startIndex) == webChineseContent.charAt(j)) {
                isFind = true;
                break;
            }

        }
        return isFind;
    }
}
