package Trie;

import java.util.*;

public class AhoCorasick {
    private final AcNode root = new AcNode('/'); // 存储无意义字符

    private static class AcNode {
        public char data;
        public AcNode[] children = new AcNode[26]; // 字符集只包含a~z这26个字符
        public boolean isEndingChar = false; // 结尾字符为true
        public int length = -1; // 当isEndingChar=true时，记录模式串长度
        public AcNode fail; // 失败指针

        public AcNode(char data) {
            this.data = data;
        }
    }

    // 往Trie树中插入一个字符串
    public void insert(char[] text) {
        AcNode p = root;
        for (int i = 0; i < text.length; ++i) {
            int index = text[i] - 'a';
            if (p.children[index] == null) {
                AcNode newNode = new AcNode(text[i]);
                p.children[index] = newNode;
            }
            p = p.children[index];
        }
        p.length = text.length;
        p.isEndingChar = true;
    }

    // 在Trie树中查找一个字符串
    public boolean find(char[] pattern) {
        AcNode p = root;
        for (int i = 0; i < pattern.length; ++i) {
            int index = pattern[i] - 'a';
            if (p.children[index] == null) {
                return false; // 不存在pattern
            }
            p = p.children[index];
        }
        // 找到pattern
        return p.isEndingChar; // 不能完全匹配，只是前缀
    }

    public String[] keywordTips(char[] pattern) {
        AcNode p = root;
        ArrayList<String> allWords = new ArrayList<>();

        for (char c : pattern) {
            int index = c - 'a';
            if (p.children[index] == null) {
                return null;
            }
            p = p.children[index];
        }

        if (p.isEndingChar) allWords.add(String.valueOf(pattern));
        getTipsWord(p, String.valueOf(Arrays.copyOfRange(pattern, 0, pattern.length - 1)), allWords);

        return allWords.toArray(new String[0]);
    }

    private void getTipsWord(AcNode x, String preWord, List<String> allWords) {
        preWord += x.data;

        String finalPreWord = preWord;
        Arrays.stream(x.children).filter(Objects::nonNull).forEach(y -> {
            if (Arrays.stream(y.children).allMatch(Objects::isNull)) {
                allWords.add(finalPreWord + y.data);
            } else {
                if (y.isEndingChar) allWords.add(finalPreWord + y.data);
                getTipsWord(y, finalPreWord, allWords);
            }
        });
    }

    public void buildFailurePointer() {
        Queue<AcNode> queue = new LinkedList<>();
        root.fail = null;
        queue.add(root);
        while (!queue.isEmpty()) {
            AcNode p = queue.remove();
            for (int i = 0; i < 26; ++i) {
                AcNode pc = p.children[i];
                if (pc == null) continue;
                if (p == root) {
                    pc.fail = root;
                } else {
                    AcNode q = p.fail;
                    while (q != null) {
                        AcNode qc = q.children[pc.data - 'a'];
                        if (qc != null) {
                            pc.fail = qc;
                            break;
                        }
                        q = q.fail;
                    }
                    if (q == null) {
                        pc.fail = root;
                    }
                }
                queue.add(pc);
            }
        }
    }

    public List<ReplaceInfo> match(char[] text) { // text是主串
        int n = text.length;
        AcNode p = root;

        List<ReplaceInfo> rpList = new ArrayList<>();

        for (int i = 0; i < n; ++i) {
            int idx = text[i] - 'a';
            while (p.children[idx] == null && p != root) {
                p = p.fail; // 失败指针发挥作用的地方
            }
            p = p.children[idx];
            if (p == null) p = root; // 如果没有匹配的，从root开始重新匹配
            AcNode tmp = p;
            while (tmp != root) { // 打印出可以匹配的模式串
                if (tmp.isEndingChar) {
                    int pos = i - tmp.length + 1;
                    System.out.println("匹配起始下标" + pos + "; 长度" + tmp.length + " 字符串:" + String.valueOf(Arrays.copyOfRange(text, pos, pos + tmp.length)));
                    rpList.add(new ReplaceInfo(pos, pos + tmp.length));
                }
                tmp = tmp.fail;
            }
        }

        return rpList;
    }

    private static String getReplacedString(String str, String mark, List<ReplaceInfo> rpList) {
        String repStr;
        StringBuilder sb = new StringBuilder();

        int start = 0;
        for (ReplaceInfo replaceInfo : rpList) {
            if (start > replaceInfo.start && start < replaceInfo.end) {
                sb.append(mark.repeat(replaceInfo.end - start));
            } else {
                sb.append(str, start, replaceInfo.start);
                sb.append(mark.repeat(replaceInfo.end - replaceInfo.start));
            }
            start = replaceInfo.end;
        }

        if (rpList.size() == 0) {
            repStr = str;
        } else {
            if (sb.length() < str.length()) {
                sb.append(str, start, str.length());
            }
            repStr = sb.toString();
        }
        return repStr;
    }

    public static class ReplaceInfo {
        int start;
        int end;

        public ReplaceInfo(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }

    public static void main(String[] args) {
        AhoCorasick AC = new AhoCorasick();
        String[] words = {"hello", "her", "hero", "herps", "here", "hereof", "how", "hi", "see", "so", "soak"};
        for (String word : words) {
            AC.insert(word.toCharArray());
        }

        AC.buildFailurePointer();

        String str = "eeeeeheroeeeeeeeeeeeeehereeeeeeewwwwwwwwwwwhowwwwwwseewwwso";
        List<ReplaceInfo> rpList = AC.match(str.toCharArray());

        String repStr = getReplacedString(str,"^", rpList);
        System.out.println("---------------------匹配前的字符串：" + str);
        System.out.println("---------------------匹配后的字符串：" + repStr);
    }
}

