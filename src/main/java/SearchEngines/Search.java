package SearchEngines;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Search {
    private final String indexFile = "D:\\SearchEngines\\index.bin";
    private final String termIdFile = "D:\\SearchEngines\\term_id.bin";
    private final String docIdFile = "D:\\SearchEngines\\doc_Id.bin";

    private Map<Integer, String> idTermMap = new HashMap<>();
    private Map<String, Integer> termIdMap = new HashMap<>();
    private Map<Integer, String> indexMap = new HashMap<>();
    private Map<Integer, String> docIdMap = new HashMap<>();

    private void initData() {
        //单词ID 映射数据
        setDataToMap(termIdFile, idTermMap);
        setDataToMap2(termIdFile, termIdMap);

        // 单词ID 映射网页ID数据
        setDataToMap(indexFile, indexMap);

        // 网页ID 与网址映射数据
        setDataToMap(docIdFile, docIdMap);
    }

    private void setDataToMap(String filePath, Map<Integer, String> map) {
        try (Scanner scanner = new Scanner(new File(filePath), StandardCharsets.UTF_8)) {
            while (scanner.hasNextLine()) {
                String[] terms = scanner.nextLine().split("\t");
                if (terms.length < 2) continue;
                map.put(Integer.valueOf(terms[0]), terms[1]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setDataToMap2(String filePath, Map<String, Integer> map) {
        try (Scanner scanner = new Scanner(new File(filePath), StandardCharsets.UTF_8)) {
            while (scanner.hasNextLine()) {
                String[] terms = scanner.nextLine().split("\t");
                if (terms.length < 2) continue;
                map.put(terms[1], Integer.valueOf(terms[0]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Search search = new Search();
        search.initData();

        Analyze analyze = new Analyze();
        Set<String> setRet = analyze.SeparateWords("2.我市发展面临的形势和阶段性特征。");

        System.out.println("分析单词:"+ setRet);

        Map<Integer, Integer> urlIdCountMap = new HashMap<>();

        setRet.forEach(x -> {
            if (x.isBlank() || x.isEmpty()) return;
            if (!search.termIdMap.containsKey(x)) return;
            int termId = search.termIdMap.get(x);

            if (!search.indexMap.containsKey(termId)) return;
            String urls = search.indexMap.get(termId);

            for (String s : urls.split(",")) {
                urlIdCountMap.merge(Integer.valueOf(s), 1, Integer::sum);
            }
        });

        List<Map.Entry<Integer, Integer>> list = new ArrayList<>(urlIdCountMap.entrySet()); //转换为list
        Collections.sort(list, (o1, o2) -> o2.getValue().compareTo(o1.getValue()));

        //for循环
        for (int i = 0; i < Math.min(list.size(), 10); i++) {
//            System.out.println(list.get(i).getKey() + ": " + list.get(i).getValue());
            int termId = list.get(i).getKey();
            int wordsCount = list.get(i).getValue();

            System.out.println(search.docIdMap.get(termId) + "\t 匹配单词数:" + wordsCount);
        }

    }

}
