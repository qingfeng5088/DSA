package StringCompare;

import utils.Utils;

public class RK {

    public static long count = 12_0000_0000;
    static String mainStr = "";
    static char[] mainChars;

    static String searchStr = "abcegk";
    static char[] searchChars;
    static long[] pow26 = new long[searchStr.length()];

    public static void main(String[] args) throws InterruptedException {
        System.out.println("-----------开始匹配字符串准备时间------");
        Utils.countTime(RK::init);


        System.out.println("-----------RK 开始计时-----------------");
        Utils.countTime(RK::mySearch);

        System.out.println("-----------JAVA API开始计时-----------------");
        Utils.countTime(RK::CountSubString);

    }

    static void mySearch() {
        int count = 0;
        for (int i = 1; i < (mainStr.length() - searchStr.length()); i++) {
            preHC = strHash(i);
            if (preHC == searchHC) {
                boolean isOK = true;
                for (int i1 = 0; i1 < searchChars.length; i1++) {
                    if (searchChars[i1] != mainChars[i + i1]) {
                        isOK = false;
                        break;
                    }
                }

                if (isOK) {
                    System.out.println(i);
                    return;
                }
            }
        }

        System.out.println("----RK 不好意思没找到！---hash冲突次数:" + count);
    }


    private static long strHash(int charIndex) {
        int len = searchStr.length() - 1;
        return (preHC - pow26[pow26.length - 1] * ((int) mainChars[charIndex - 1] - 96)) * 26 + ((int) mainChars[charIndex + len] - 96);
    }

    static long preHC = 0;
    static long searchHC = 0;

    static void init() {
        mainStr = Utils.getRandomString(count);
        mainChars = mainStr.toCharArray();
        searchChars = searchStr.toCharArray();

        for (int i = 0; i < searchStr.length(); i++) {
            pow26[i] = (long) Math.pow(26, i);
        }

        int len = searchStr.length();
        for (int i = 0; i < searchStr.length(); i++) {
            preHC += ((int) mainChars[i] - 96) * pow26[len - i - 1];
            searchHC += ((int) searchChars[i] - 96) * pow26[len - i - 1];
        }

        System.out.println("-------preHC:" + preHC);
        System.out.println("----searchHC:" + searchHC);
    }

    static void CountSubString() {
        System.out.println(mainStr.indexOf(searchStr));
    }
}
