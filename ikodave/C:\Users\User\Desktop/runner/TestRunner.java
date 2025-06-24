    import java.util.*;
    public class TestRunner {
        public static void main(String[] args) {
            List<Integer> input = Arrays.stream(args[0].split(","))
                                       .map(Integer::parseInt)
                                       .toList();
            System.out.println(Solution.solve(input));
        }
    }
