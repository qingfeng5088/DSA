package StringCompare;

public class Test {
    static String base = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    static int[] hc = new int[base.length()];

    public static void main(String[] args) {
        System.out.println(Math.pow(26,10));
        System.out.println(Math.pow(2,32));
        System.out.println(Long.MAX_VALUE);
        System.out.println(Integer.MAX_VALUE);
    }
}
