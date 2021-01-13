package exercise.SortAndBinarySearch;

public class Exercise01 {
    public int mySqrt(int x) {
        int ret = -1;
        int l = 0;
        int r = x;
        int mid;
        while (l <= r) {
            mid = l + (r-l) / 2;
            if ((long)mid * mid <= x) {
                ret = mid;
                l = mid + 1;
            } else {
                r = mid - 1;
            }
        }

        return  ret;
    }

    public int mySqrt2(int x) {
        int l = 0, r = x, ans = -1;
        while (l <= r) {
            int mid = l + (r - l) / 2;
            if ((long) mid * mid <= x) {
                ans = mid;
                l = mid + 1;
            } else {
                r = mid - 1;
            }
        }
        return ans;

    }

    public static void main(String[] args) {
        Exercise01 ex = new Exercise01();
        System.out.println(ex.mySqrt(16));
    }
}
