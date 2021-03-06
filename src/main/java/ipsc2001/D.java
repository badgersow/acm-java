package ipsc2001;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class D {

    private static int n;
    private static List<Integer>[] children;
    private static int[] a;
    private static int[][][] solveDP;
    private static int[][][][] composeDP;
    private static int[] count;
    private static int[] colors;

    public static void main(String[] args) {
        final Scanner in = new Scanner(System.in);
        n = in.nextInt();
        children = new List[n + 1];
        a = new int[n + 1];

        for (int i = 0; i < children.length; i++) {
            children[i] = new ArrayList<>();
        }

        int root = -1;

        for (int i = 1; i <= n; i++) {
            int parent = in.nextInt(), weight = in.nextInt();
            a[i] = weight;

            if (parent == 0 && weight == 0) {
                root = i;
                continue;
            }

            children[parent].add(i);
        }

        solveDP = new int[n + 1][n / 2 + 1][];
        for (int i = 0; i < solveDP.length; i++) {
            for (int j = 0; j < solveDP[i].length; j++) {
                solveDP[i][j] = new int[]{-1, -1, -1};
            }
        }

        composeDP = new int[n + 1][25][n / 2 + 1][]; // 25 is the maximum degree of the node from problem input
        for (int i = 0; i < composeDP.length; i++) {
            for (int j = 0; j < composeDP[i].length; j++) {
                for (int k = 0; k < composeDP[i][j].length; k++) {
                    composeDP[i][j][k] = new int[]{-1, -1, -1};
                }
            }
        }

        count = new int[n + 1];
        calculateCount(root);

        solve(root, n / 2, 0);

        colors = new int[n + 1];
        solveRestore(root, n / 2, 0);

        for (int i = 1; i < colors.length; i++) {
            if (colors[i] == 1) {
                System.out.println(i);
            }
        }
    }

    private static void solveRestore(int root, int firstMembers, int parentColor) {
        // Color the leaf
        if (children[root].isEmpty()) {
            colors[root] = (firstMembers == 1 ? 1 : 2);
            return;
        }

        // What is more optimal? Coloring this to 1 or 2?
        boolean color1isBetter = solve(root, firstMembers, parentColor) ==
                (parentColor == 2 ? a[root] : 0) +
                        compose(root, 0, firstMembers - 1, 1);

        if (color1isBetter) {
            colors[root] = 1;
            composeRestore(root, 0, firstMembers - 1, 1);
        } else {
            colors[root] = 2;
            composeRestore(root, 0, firstMembers, 2);
        }
    }

    private static void composeRestore(int root, int position, int sum, int parentColor) {
        if (position == children[root].size()) {
            return;
        }

        final int child = children[root].get(position);
        final int bestSolution = compose(root, position, sum, parentColor);
        for (int firstMembers = 0; firstMembers <= Math.min(sum, count[child]); firstMembers++) {
            final int currentSolution = solve(child, firstMembers, parentColor) + // current child node
                    compose(root, position + 1, sum - firstMembers, parentColor);

            if (currentSolution == bestSolution) {
                solveRestore(child, firstMembers, parentColor);
                composeRestore(root, position + 1, sum - firstMembers, parentColor);
                break;
            }
        }
    }


    private static int calculateCount(int root) {
        return count[root] = 1 +
                children[root].stream().mapToInt(D::calculateCount).sum();
    }

    private static int solve(int root, int firstMembers, int parentColor) {
        // If we already have the solution
        if (solveDP[root][firstMembers][parentColor] != -1) {
            return solveDP[root][firstMembers][parentColor];
        }

        // Color the leaf
        if (children[root].isEmpty()) {
            if (firstMembers > 1) {
                return solveDP[root][firstMembers][parentColor] = Integer.MIN_VALUE / 2; // Impossible
            }

            // If the leaf employee is happy
            if (firstMembers == 1 && parentColor == 2 || firstMembers == 0 && parentColor == 1) {
                return solveDP[root][firstMembers][parentColor] = a[root];
            }

            // Leaf employee has the same color as the boss
            return solveDP[root][firstMembers][parentColor] = 0;
        }

        int bestSolution = Integer.MIN_VALUE / 2;

        // Color the current node to 1 if this is possible
        if (firstMembers > 0) {
            bestSolution =
                    Math.max(bestSolution,
                            (parentColor == 2 ? a[root] : 0) +
                                    compose(root, 0, firstMembers - 1, 1));
        }

        // Color the current node to 2
        bestSolution =
                Math.max(bestSolution,
                        (parentColor == 1 ? a[root] : 0) +
                                compose(root, 0, firstMembers, 2));

        return solveDP[root][firstMembers][parentColor] = bestSolution;
    }

    // Implement weak k-composition of the current node into subtrees
    private static int compose(int root, int position, int sum, int parentColor) {
        if (composeDP[root][position][sum][parentColor] != -1) {
            return composeDP[root][position][sum][parentColor];
        }

        if (position == children[root].size()) {
            if (sum == 0) {
                // Successful!
                return composeDP[root][position][sum][parentColor] = 0;
            }

            // Failed. We should return -infinity
            return composeDP[root][position][sum][parentColor] = Integer.MIN_VALUE / 2;
        }

        int bestSolution = Integer.MIN_VALUE;
        final int child = children[root].get(position);
        for (int firstMembers = 0; firstMembers <= Math.min(sum, count[child]); firstMembers++) {
            bestSolution = Math.max(bestSolution,
                    solve(child, firstMembers, parentColor) + // current child node
                            compose(root, position + 1, sum - firstMembers, parentColor)); // rest of children
        }

        return composeDP[root][position][sum][parentColor] = bestSolution;
    }
}
