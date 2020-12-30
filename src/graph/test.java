package graph;

import utils.Utils;

public class test {

    public static long count = 12_0000_0000;
    static String aaa = "";

    public static void main(String[] args) throws InterruptedException {
        System.out.println("-----------开始匹配字符串准备时间------");
        Utils.countTime(test::init);
        System.out.println("-----------开始计时-----------------");
        Utils.countTime(test::CountSubString);
    }

    static  void init(){
        aaa = Utils.getRandomString(count);
    }

    static void CountSubString() {
        System.out.println(aaa.indexOf("asfwge"));
    }
}
