package utils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileOperation {
    static int flag = 1;//用来判断文件是否删除成功

    public static void WriteFile(String path, String content) {
        if (!Files.exists(Path.of(path))) {
            try {
                Files.createFile(Path.of(path));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try (FileWriter fw = new FileWriter(path, true);) {
            fw.write(content); //写入内容
            fw.write("\r\n");  //换行
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void WriteFile2(String path, String content) {
        try {
            if (Files.exists(Path.of(path))) {
                Files.delete(Path.of(path));
            }
            Files.createFile(Path.of(path));
        } catch (IOException e) {
            e.printStackTrace();
        }

        File file = new File(path);
        try (FileOutputStream fos = new FileOutputStream(file);
             OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);) {
            osw.write(content); //写入内容
            osw.write("\r\n");  //换行
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void ReadFile(String path) throws FileNotFoundException {
        File file = new File(path);
        try (FileInputStream fileInputStream = new FileInputStream(path);
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));) {
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void deleteFile(File file){
        //判断文件不为null或文件目录存在
        if (file == null || !file.exists()){
            flag = 0;
            System.out.println("文件删除失败,请检查文件路径是否正确");
            return;
        }
        //取得这个目录下的所有子文件对象
        File[] files = file.listFiles();
        //遍历该目录下的文件对象
        for (File f: files){
            //打印文件名
            String name = file.getName();
            System.out.println(name);
            //判断子目录是否存在子目录,如果是文件则删除
            if (f.isDirectory()){
                deleteFile(f);
            }else {
                f.delete();
            }
        }
        //删除空文件夹  for循环已经把上一层节点的目录清空。
        //file.delete();
    }
}
