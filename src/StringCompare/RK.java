package StringCompare;

import utils.Utils;

public class RK {

    public static long count = 12_0000_0000;
    static String mainStr = "";
    static char[] mainChars;

    static String searchStr = "gdfetge";
    static char[] searchChars;
    static long[] pow26 = new long[searchStr.length()];

    public static void main(String[] args) throws InterruptedException {
        System.out.println("-----------开始匹配字符串准备时间------");
        Utils.countTime(RK::init);

        System.out.println("===================================================");
        System.out.println("-----------RK 开始计时-----------------");
        Utils.countTime(RK::myRK);

        System.out.println("-----------MKP 开始计时-----------------");
        Utils.countTime(RK::myMKP);

        System.out.println("-----------myMKP2 开始计时-----------------");
        Utils.countTime(RK::myMKP2);

        System.out.println("-----------Sunday 开始计时-----------------");
        Utils.countTime(RK::mySunday);

        System.out.println("-----------BF 开始计时-----------------");
        Utils.countTime(RK::myBF);

        System.out.println("-----------myKipToFirst 开始计时-----------------");
        Utils.countTime(RK::myKipToFirst);

        System.out.println("-----------JAVA API开始计时-----------------");
        Utils.countTime(RK::CountSubString);

    }

    static void myBF() {
        int slen = searchStr.length();
        int max = mainStr.length() - slen + 1;
        for (int i = 0; i < max; i++) {
            int j = 0;
            for (; j < slen && mainChars[i + j] == searchChars[j]; j++) ;
            if (j == slen) {
                System.out.println("----BF计算出的位置:" + i);
                return;
            }
        }

        System.out.println("----BF 不好意思没找到！");
    }

    static void myKipToFirst() {
        char first = searchChars[0];
        int slen = searchChars.length;
        int mlen = mainChars.length;
        int max = (mlen - slen);
        for (int i = 0; i <= max; i++) {
            // Look for first character.
            if (mainChars[i] != first) {
                while (++i <= max && mainChars[i] != first) ;
            }
            // Found first character, now look at the rest of value
            if (i <= max) {
                int j = i + 1;
                int end = j + slen - 1;
                int k = 1;
                for (; j < end && mainChars[j] == searchChars[k]; j++, k++) ;
                i = i + k;

                if (j == end) {
                    // Found whole string.
                    System.out.println("----myKipToFirst计算出的位置:" + (i - k));
                    return;
                }
            }
        }

        System.out.println("----myKipToFirst 不好意思没找到！");
    }

    static void myMKP() {
        int ret = MKP.kmp(mainChars, searchChars);
        System.out.println("----MKP计算出的位置:" + ret);
    }
    static void myMKP2() {
        int ret = MKP.qiukmp(mainChars, searchChars);
        System.out.println("----myMKP2计算出的位置:" + ret);
    }

    static void mySunday() {
        Sunday.Sunday(mainChars, searchChars);
    }

    static void myRK() {
        int count = 0;
        int max = mainStr.length() - searchStr.length();
        for (int i = 1; i < max; i++) {
            preHC = strHash(i);
            if (preHC == searchHC) {
                System.out.println(i);
                return;

//                boolean isOK = true;
//                for (int i1 = 0; i1 < searchChars.length; i1++) {
//                    if (searchChars[i1] != mainChars[i + i1]) {
//                        isOK = false;
//                        break;
//                    }
//                }
//
//                if (isOK) {
//                    System.out.println(i);
//                    return;
//                }
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
        mainStr = "acadabababhmnababacoxyz" + Utils.getRandomString(count) + "asdfewfwafewfreghsd" + searchStr + "sdfasfkw2f";
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
