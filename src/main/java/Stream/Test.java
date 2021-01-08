package Stream;

import java.io.File;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public class Test {
    public static void main(String[] args) {
        List<String> list = List.of("linlin", "xuanxuan", "qiuyutong", "zhaoyuchen", "zhangshuyi");
//        Stream<String> randoms = list.stream().dropWhile(x -> x.length() < 9);
//        randoms.forEach(x -> System.out.println(x+":"+x.length()));
        Stream<String>   randoms = Stream.concat(list.stream(),Stream.of("shangshang","xuanxuan")).peek(System.out::println).distinct().sorted(Comparator.reverseOrder());
        randoms.forEach(x -> System.out.println(x+":"+x.length()));

        System.out.println("----77--8886666--分隔符:"+ File.separator);

    }
}
