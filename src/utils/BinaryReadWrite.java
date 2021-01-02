package utils;

import java.io.*;
import java.math.BigInteger;
import java.util.Arrays;

/* @comany 阿斯达克(深圳)信息技术有限公司
 * @author steven.wu
 * @since 2009.8.11
 * @description read and write stream for binary file。
 */
public class BinaryReadWrite {
    private DataInputStream dis = null;
    private DataOutputStream dos = null;
    private String s_FilePath = "D:/newTemp.txt";
    private byte[] m_datapadding = {0x00}; //填充空白，以补足字节位数.

    public BinaryReadWrite() {
        // TODO Auto-generated constructor stub
        init();
    }

    private void init() {
        try {
            if (!new File(s_FilePath).exists()) {
                new File(s_FilePath).createNewFile();
            }
            dis = new DataInputStream(new FileInputStream(new File(s_FilePath)));
            dos = new DataOutputStream(new FileOutputStream(new File(s_FilePath)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void writeBinaryStream() {
        try {
            if (dos != null) {
                for (int i = 0; i < 2; i++) {
                    //write boolean value.
                    dos.writeBoolean(true);
                    //write a char value.
                    char c = 'a';
                    dos.writeChar((int) c);
                    Double d = 12.567d;
                    dos.writeDouble(d);
                    Float f = 56.782f;
                    dos.writeFloat(f);
                    int k = 105;
                    dos.writeInt(k);
                    long l = 98765l;
                    dos.writeLong(l);
                    short st = 12;
                    dos.writeShort(st);
                    String cs = "Java读写二进制文件";
                    String cs1 = "二进制文件";
                    if (i == 0) {
                        dos.writeUTF(cs);
                    } else {
                        dos.writeUTF(cs1);
                    }
                    dos.write(m_datapadding);
                }
                dos.flush();
                dos.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void readBinaryStream() {
        try {
            if (dis != null) {
                while (dis.available() > 0) {
                    System.out.println(dis.available());
                    System.out.println(dis.readBoolean());
                    char c = (char) dis.readChar();
                    System.out.println(c);
                    System.out.println(dis.readDouble());
                    System.out.println(dis.readFloat());
                    System.out.println(dis.readInt());
                    System.out.println(dis.readLong());
                    System.out.println(dis.readShort());
                    System.out.println(dis.readUTF());
                    System.out.println(dis.read(m_datapadding));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void write(String str) throws IOException {
        byte[] b = new BigInteger(str, 2).toByteArray();
        dos.write(b);

//        String cs = "Java读写二进制文件";
//        dos.writeUTF(cs);
        // dos.write(m_datapadding);
    }

    public void read() {
        try {
            if (dis != null) {
                byte[] b = new byte[1];
                int tempchar = 0;
                while ((tempchar = dis.read(b)) != -1) {
                    //   System.out.println(Integer.toBinaryString((int)b[0]));
                    System.out.println(Arrays.toString(b));
                    System.out.println(Integer.toBinaryString(b[0]));
                }

                //System.out.printf("%s",Integer.toBinaryString(111));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        System.out.println("----"+Integer.toBinaryString(20005));

        BinaryReadWrite bin = new BinaryReadWrite();
        bin.write("100111000100101");
        System.out.println("----------读二进制文件-------------");
        bin.read();
//        bin.writeBinaryStream();
//        System.out.println("----------读二进制文件-------------");
//     bin.readBinaryStream();
    }
}