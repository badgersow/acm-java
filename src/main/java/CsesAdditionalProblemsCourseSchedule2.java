import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class CsesAdditionalProblemsCourseSchedule2 {

    public static void main(String[] args) throws Exception {
        new CsesAdditionalProblemsCourseSchedule2().solve();
    }

    private final FastReader in = new FastReader();

    private final PrintWriter out = new PrintWriter(System.out);

    @SuppressWarnings("unchecked")
    public void solve() throws Exception {
        final int n = in.nextInt();
        final int m = in.nextInt();

        final int[] degrees = new int[n];
        final List<Integer>[] reverseAdjacency = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            reverseAdjacency[i] = new ArrayList<>();
        }

        for (int i = 0; i < m; i++) {
            final int u = in.nextInt() - 1, v = in.nextInt() - 1;
            reverseAdjacency[v].add(u);
            degrees[u]++;
        }

        final PriorityQueue<Integer> tails = new PriorityQueue<>(Comparator.reverseOrder());
        ArrayDeque<Integer> result = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            if (degrees[i] == 0) {
                tails.add(i);
            }
        }

        while (!tails.isEmpty()) {
            final Integer leastInterestingNode = tails.poll();
            result.addLast(leastInterestingNode);

            reverseAdjacency[leastInterestingNode].forEach(node -> {
                degrees[node]--;
                if (degrees[node] == 0) {
                    tails.add(node);
                }
            });
        }

        out.print(result.removeLast() + 1);
        while (!result.isEmpty()) {
            out.print(" ");
            out.print(result.removeLast() + 1);
        }

        out.println();
        out.flush();
    }

    private static class FastReader {

        private final int BUFFER_SIZE = 1 << 24;
        private final DataInputStream din;
        private final byte[] buffer;
        private int bufferPointer, bytesRead;

        public FastReader() {
            din = new DataInputStream(System.in);
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
        }

        public FastReader(String fileName) {
            try {
                din = new DataInputStream(new FileInputStream(fileName));
                buffer = new byte[BUFFER_SIZE];
                bufferPointer = bytesRead = 0;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        public String readLine() {
            byte[] buf = new byte[1001]; // line length
            int cnt = 0, c;
            while ((c = read()) != -1) {
                if (c == '\n')
                    break;
                buf[cnt++] = (byte) c;
            }
            return new String(buf, 0, cnt);
        }

        public int nextInt() {
            int ret = 0;
            byte c = read();
            while (c <= ' ')
                c = read();
            boolean neg = (c == '-');
            if (neg)
                c = read();
            do {
                ret = ret * 10 + c - '0';
            } while ((c = read()) >= '0' && c <= '9');

            if (neg)
                return -ret;
            return ret;
        }

        public long nextLong() {
            long ret = 0;
            byte c = read();
            while (c <= ' ')
                c = read();
            boolean neg = (c == '-');
            if (neg)
                c = read();
            do {
                ret = ret * 10 + c - '0';
            } while ((c = read()) >= '0' && c <= '9');
            if (neg)
                return -ret;
            return ret;
        }

        public double nextDouble() {
            double ret = 0, div = 1;
            byte c = read();
            while (c <= ' ')
                c = read();
            boolean neg = (c == '-');
            if (neg)
                c = read();

            do {
                ret = ret * 10 + c - '0';
            } while ((c = read()) >= '0' && c <= '9');

            if (c == '.') {
                while ((c = read()) >= '0' && c <= '9') {
                    ret += (c - '0') / (div *= 10);
                }
            }

            if (neg)
                return -ret;
            return ret;
        }

        private void fillBuffer() {
            try {
                bytesRead = din.read(buffer, bufferPointer = 0, BUFFER_SIZE);
                if (bytesRead == -1)
                    buffer[0] = -1;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        private byte read() {
            if (bufferPointer == bytesRead)
                fillBuffer();
            return buffer[bufferPointer++];
        }

        public void close() {
            try {
                din.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        public int[] fillIntegerArray(int n) {
            int[] array = new int[n];
            for (int i = 0; i < n; i++)
                array[i] = nextInt();
            return array;
        }

        public long[] fillLongArray(int n) {
            long[] array = new long[n];
            for (int i = 0; i < n; i++)
                array[i] = nextLong();
            return array;
        }

        public <T extends List<Long>> T fillList(T list, int n) {
            for (int i = 0; i < n; i++)
                list.add(nextLong());
            return list;
        }
    }
}