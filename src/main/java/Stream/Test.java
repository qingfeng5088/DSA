package Stream;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {
    public static void main(String[] args) {
  String str = "\n" +
          "\n" +
          "<!DOCTYPE html>\n" +
          "<!-- [ published at 2021-01-09 10:34:26 ] -->\n" +
          "<!-- LLTJ_MT:name =\"影艺独舌\" -->\n" +
          "\n" +
          "<html>\n" +
          "<head>\n" +
          "<meta charset=\"utf-8\"/>\n" +
          "<meta http-equiv=\"Content-type\" content=\"text/html; charset=utf-8\" />\n" +
          "<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\"/>\n" +
          "<meta name=\"sudameta\" content=\"allCIDs:186870,258,171240,185494,223658,218742,171239,257,254050,7198\">\n" +
          "\n" +
          "<title>戏好却“难搞”，李梦还能翻身吗？|白鹿原|隐秘的角落|李梦_新浪新闻</title>\n" +
          "<meta name=\"keywords\" content=\"白鹿原,隐秘的角落,李梦\" />\n" +
          "<meta name=\"tags\" content=\"白鹿原,隐秘的角落,李梦\" />\n" +
          "<meta name=\"description\" content=\"\" />\n" +
          "<link rel=\"mask-icon\" sizes=\"any\" href=\"//www.sina.com.cn/favicon.svg\" color=\"red\">\n" +
          "<meta property=\"og:type\" content=\"news\" />\n" +
          "<meta property=\"og:title\" content=\"戏好却“难搞”，李梦还能翻身吗？\" />\n" +
          "<meta property=\"og:description\" content=\"戏好却“难搞”，李梦还能翻身吗？\" />\n" +
          "<meta property=\"og:url\" content=\"https://k.sina.cn/article_5724000880_1552d527001900s8tr.html?from=ent&subch=oent\" />\n" +
          "<meta property=\"og:image\" content=\"http://n.sinaimg.cn/sinakd20210107s/223/w1280h543/20210107/9de0-kherpxy6877841.jpg\" />\n" +
          "<meta name=\"weibo: article:create_at\" content=\"2021-01-07 22:00:00\" />\n" +
          "<meta name=\"weibo: article:update_at\" content=\"2021-01-08 09:35:19\" />\n" +
          "</html>";
      // System.out.println(str.replaceAll("<script\\b[^<]*(?:(?!<\\/script>)<[^<]*)*<\\/script>", "").replace("\n",""));
       System.out.println(str.replaceAll("[\\s*\\t\\n\\r]", "").length());


       str = str.replaceAll("[\\s*\\t\\n\\r]", "");

       String regex= "(https?|ftp|file)://[-A-Za-z0-9+&@#/%?=~_|!:,.;]+[-A-Za-z0-9+&@#/%=~_|]";

        //编译正则字符串
        Pattern p = Pattern.compile(regex);
        //利用正则去匹配
        Matcher matcher = p.matcher(str);
        //如果找到了我们正则里要的东西
        while (matcher.find()) {
            //保存到sb中，"\r\n"表示找到一个放一行，就是换行
            String httpstr = matcher.group();
            if (httpstr.contains(".jpg") || httpstr.contains(".png")) continue;
            if (httpstr.contains("?")) {
                System.out.println(httpstr.substring(0,httpstr.indexOf("?")));
            } else {
                System.out.println(httpstr);
            }
        }

     //   PrintWriter pw = new PrintWriter("", StandardCharsets.UTF_8);

    }
}
