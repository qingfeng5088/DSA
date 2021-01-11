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
        Set<String> setRet = analyze.SeparateWords("2.我市发展面临的形势和阶段性特征。当今世界正经历百年未有之大变局，新一轮科技革命和产业变革深入发展，和平与发展仍然是时代主题，同时国际环境日趋复杂，新冠肺炎疫情影响广泛深远，世界进入动荡变革期。我国已转向高质量发展阶段，制度优势显著，治理效能提升，经济长期向好，市场空间广阔，发展韧性强劲，继续发展具有多方面优势。进入新发展阶段，兰州正处于新型工业化、信息化、城镇化、农业现代化快速推进，全面建设现代化中心城市的关键时期，国家加快构建新发展格局总体上有利于内陆腹地中心城市发挥后发优势和内在潜力，有利于在实施扩大内需战略、形成强大国内市场中实现提质增速、转型升级。随着国家“一带一路”建设、新一轮西部大开发、黄河流域生态保护和高质量发展、兰西城市群建设等重大战略的深入实施，我市发展面临的政策机遇叠加，将迎来自身比较优势的重塑期、新旧动能转换的爬坡期、高质量发展的攻坚期，仍然处于可以大有作为、更需主动作为的重要战略机遇期。同时，我市发展不平衡不充分问题仍然突出，经济总量仍然偏小，产业结构不够合理，新旧动能转换滞后，创新支撑作用不强，县域经济发展缓慢，");

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
