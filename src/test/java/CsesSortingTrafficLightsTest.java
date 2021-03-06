import org.junit.jupiter.api.Test;
import util.AcmTest;

public class CsesSortingTrafficLightsTest extends AcmTest {
    @Override
    public void processInput() throws Exception {
        new CsesSortingTrafficLights().solve();
    }

    @Test
    public void sample() {
        compare("8 3 3 6 2", "5 3 3");
    }

    @Test
    public void trivial1() {
        compare("9 1 5", "5");
    }

    @Test
    public void tle1() {
        compare(
                readFile("/cses_sorting_traffic_lights_tle_in_1.txt"),
                readFile("/cses_sorting_traffic_lights_tle_out_1.txt")
        );
    }

    @Test
    public void tle2() {
        compare(
                readFile("/cses_sorting_traffic_lights_tle_in_2.txt"),
                readFile("/cses_sorting_traffic_lights_tle_out_2.txt")
        );
    }

    @Test
    public void tle3() {
        compare(
                readFile("/cses_sorting_traffic_lights_tle_in_3.txt"),
                readFile("/cses_sorting_traffic_lights_tle_out_3.txt")
        );
    }

    @Test
    public void tle4() {
        compare(
                readFile("/cses_sorting_traffic_lights_tle_in_4.txt"),
                readFile("/cses_sorting_traffic_lights_tle_out_4.txt")
        );
    }


}
