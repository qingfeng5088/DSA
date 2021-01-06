package bitMap;


import java.util.BitSet;

public class BitMap {
    private char[] bytes;
    private int nbits;

    public BitMap(int nbits) {
        this.nbits = nbits;
        this.bytes = new char[nbits / 8 + 1];
    }

    public void set(int k) {
        if (k > nbits) return;
        int byteIndex = k / 8;
        int bitIndex = k % 8;
        bytes[byteIndex] |= (1 << bitIndex);
    }

    public boolean get(int k) {
        if (k > nbits) return false;
        int byteIndex = k / 8;
        int bitIndex = k % 8;
        return (bytes[byteIndex] & (1 << bitIndex)) != 0;
    }

    public static void main(String[] args) {
        BitMap bmap = new BitMap(10);
        bmap.set(1);
        bmap.set(3);
        bmap.set(5);
        bmap.set(7);
        bmap.set(9);
        bmap.set(12);

        System.out.println(bmap.get(12));

        BitSet bitMap = new BitSet(10);
        bitMap.set(1);
        bitMap.set(5);
        bitMap.set(7);
        bitMap.set(12);

//        System.out.println(bitMap.get(5));
//        System.out.println(bitMap.get(6));
//        System.out.println(bitMap.get(7));
//        System.out.println(bitMap.get(8));
//        System.out.println(bitMap.get(12));
    }
}