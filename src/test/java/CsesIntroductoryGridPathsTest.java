import org.junit.jupiter.api.Test;
import util.AcmTest;

public class CsesIntroductoryGridPathsTest extends AcmTest {

    @Override
    public void processInput() throws Exception {
        new CsesIntroductoryGridPaths().solve();
    }

    @Test
    public void singular() {
        compare("DRURRRRRDDDLUULDDDLDRRURDDLLLLLURULURRUULDLLDDDD", "1");
    }

    @Test
    public void fast1() {
        compare("DRURR???????UULDDDLDRRURD???????RULURRUUL?????DD", "1");
    }

    @Test
    public void fast2() {
        compare("DRURRRRRD?????LDDDLDRRURD?????LURUL????????LDDDD", "1");
    }

    @Test
    public void sample() {
        compare("??????R??????U??????????????????????????LD????D?", "201");
    }

    @Test
    public void something1() {
        compare("????????????????????????L???????????????????????", "27735");
    }

    @Test
    public void something2() {
        compare("RR??????????????????????????????????????????????", "34268");
    }

    @Test
    public void total() {
        compare("????????????????????????????????????????????????", "88418");
    }

}