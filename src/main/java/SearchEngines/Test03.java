package SearchEngines;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test03 {
    private static   final  String exceptRegex = "<%s\\b[^<]*(?:(?!<\\/script>)<[^<]*)*<\\/%s>";
    public static void regxChinese(){
        // 要匹配的字符串
        String source = "中国Chinasdf人民sdfasd阿斯大法";
        // 将上面要匹配的字符串转换成小写
        // source = source.toLowerCase();
        // www.111cn.net 匹配的字符串的正则表达式
        //String reg_charset = "<span[^>]*?title='([0-9]*[\s|\S]*[u4E00-u9FA5]*)'[\s|\S]*class='[a-z]*[\s|\S]*[a-z]*[0-9]*'";
        String reg_charset = "[\\u4e00-\\u9fa5]*";

        Pattern p = Pattern.compile(reg_charset);
        Matcher m = p.matcher(source);
        while (m.find()) {
            System.out.print(m.group(0));
        }
    }
    public static void main(String[] args) {
        regxChinese();
    }
}
