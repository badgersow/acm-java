import org.junit.jupiter.api.Test;

public class CsesSortingSubarraySums1Test extends AcmTest {
    @Override
    void processInput() throws Exception {
        new CsesSortingSubarraySums1().solve();
    }

    @Test
    public void sample() {
        compare("5 7 2 4 1 2 7", "3");
    }
}