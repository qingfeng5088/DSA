package SearchEngines;

import Trie.ChineseDictionary;

import java.util.ArrayList;
import java.util.List;

public class Test06 {

    public static void main(String[] args) {
        ChineseDictionary dic = new ChineseDictionary();

        List<String> words = new ArrayList<>();
        String webChineseContent = "采购计划反映了中国作为一个大国的山东鲁能泰山队决心";

        for (int i = 0; i < webChineseContent.length(); ) {
            int j = i + 1;
            for (; j <= webChineseContent.length(); j++) {
                if (!dic.dictonary.contains(webChineseContent.substring(i, j))) {
                    boolean isRealEnd = true;
                    for (int k = 1; k <= 10; k++) {
                        if ((j + k) > webChineseContent.length()) break;
                        if (dic.dictonary.contains(webChineseContent.substring(i, j + k))) {
                            isRealEnd = false;
                            break;
                        }
                    }

                    if (isRealEnd) break;
                }
            }

            words.add(webChineseContent.substring(i, j - 1));
            if (j == i + 1) {
                i = j;
            } else {
                i = j;
                i--;
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
