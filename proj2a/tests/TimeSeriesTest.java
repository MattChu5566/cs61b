import ngrams.TimeSeries;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;

/** Unit Tests for the TimeSeries class.
 *  @author Josh Hug
 */
public class TimeSeriesTest {
    @Test
    public void testFromSpec() {
        TimeSeries catPopulation = new TimeSeries();
        catPopulation.put(1991, 0.0);
        catPopulation.put(1992, 100.0);
        catPopulation.put(1994, 200.0);

        TimeSeries dogPopulation = new TimeSeries();
        dogPopulation.put(1994, 400.0);
        dogPopulation.put(1995, 500.0);

        TimeSeries totalPopulation = catPopulation.plus(dogPopulation);
        // expected: 1991: 0,
        //           1992: 100
        //           1994: 600
        //           1995: 500

        List<Integer> expectedYears = new ArrayList<>
                (Arrays.asList(1991, 1992, 1994, 1995));

        assertThat(totalPopulation.years()).isEqualTo(expectedYears);

        List<Double> expectedTotal = new ArrayList<>
                (Arrays.asList(0.0, 100.0, 600.0, 500.0));

        for (int i = 0; i < expectedTotal.size(); i += 1) {
            assertThat(totalPopulation.data().get(i)).isWithin(1E-10).of(expectedTotal.get(i));
        }

        // divide
        TimeSeries piano = new TimeSeries();
        piano.put(2024, 1000.0);
        piano.put(2023, 2000.0);
        TimeSeries violin = new TimeSeries();
        violin.put(2024, 500.0);
        violin.put(2023, 200.0);
        violin.put(2022, 100.0);

        TimeSeries result = piano.dividedBy(violin);

        List<Integer> expectedY = new ArrayList<>
                (Arrays.asList(2023, 2024));

        assertThat(result.years()).isEqualTo(expectedY);

        List<Double> expectedQ = new ArrayList<>
                (Arrays.asList(10.0, 2.0));

        for (int i = 0; i < expectedQ.size(); i += 1) {
            assertThat(result.data().get(i)).isWithin(1E-10).of(expectedQ.get(i));
        }

        piano.put(2021, 600.0);
        try {
            piano.dividedBy(violin);
        } catch (IllegalArgumentException err) {
            assertThat(err).hasMessageThat();
        }
    }

    @Test
    public void testEmptyBasic() {
        TimeSeries catPopulation = new TimeSeries();
        TimeSeries dogPopulation = new TimeSeries();

        assertThat(catPopulation.years()).isEmpty();
        assertThat(catPopulation.data()).isEmpty();

        TimeSeries totalPopulation = catPopulation.plus(dogPopulation);

        assertThat(totalPopulation.years()).isEmpty();
        assertThat(totalPopulation.data()).isEmpty();
    }
} 