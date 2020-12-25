package sort;

import utils.IPUtil;
import utils.Utils;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class SearchIP {
    private static final Map<Integer, String> cities = new HashMap<>();
    private static final Map<Long, Integer> IPs = new HashMap<>();

    /**
     * 输入IP找归属地，输入c推出
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        initData();
        System.out.println("---------IP库总数:" + IPs.size());
        String strIP = "";
        while (!"c".equals(strIP)) {
            System.out.println("请输入您的IP");
            var in = new Scanner(System.in);
            strIP = in.nextLine();

            if ("c".equals(strIP)) {
                return;
            }

            String check = strIP.replace(".", "");
            try {
                Long.parseLong(check);
            } catch (Exception e) {
                System.out.println("---------请输入正确的IP地址--" + check);
                continue;
            }

            long ip = IPUtil.ipToLong(strIP);
            if (IPs.containsKey(ip)) {
                System.out.println("IP地址:" + strIP + "->" + cities.get(IPs.get(ip)));
            } else {
                System.out.println("没有找到你输入的IP:" + strIP);
            }
        }
    }

    private static void initData() throws IOException {
        Path path = Path.of("./地级市.txt");

        var in = new Scanner(path);
        while (in.hasNext()) {
            String[] c = in.next().split(",");
            cities.put(Integer.valueOf(c[0]), c[2] + " " + c[1]);
        }

        int allPersonCount = 14_0000_0000;
        int cityPersonCount = (allPersonCount / 333) / 50;
        System.out.println(cityPersonCount);

        final int[] sta = {0};
        System.out.print("正在处理");
        cities.keySet().forEach(x -> {
            for (int i = 0; i < cityPersonCount; i++) {
                int ip = Utils.getRandomInt(cityPersonCount) + cityPersonCount * sta[0];
                IPs.put((long) ip, x);
            }
            sta[0]++;

            if (x % 10 == 0) {
                System.out.print(".");
            }
        });

        System.out.println();
        System.out.println("处理完毕....");
        System.out.println(IPs.size());

        System.out.println("例:");
        IPs.keySet().forEach(x -> {
            if (x % 100 == 0) {
                System.out.print(IPUtil.longToIP(x) + "->");
            }
        });

        System.out.println();
    }
}
