import java.util.*;
public class Solution {
    public static int solve(List<Integer> nums) {
        int sum = 0;
        for (int x : nums) sum += x;
        return sum;
    }
}
