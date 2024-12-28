package ngrams;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * An object for mapping a year number (e.g. 1996) to numerical data. Provides
 * utility methods useful for data analysis.
 *
 * @author Josh Hug
 */
public class TimeSeries extends TreeMap<Integer, Double> {

    /** If it helps speed up your code, you can assume year arguments to your NGramMap
     * are between 1400 and 2100. We've stored these values as the constants
     * MIN_YEAR and MAX_YEAR here. */
    public static final int MIN_YEAR = 1400;
    public static final int MAX_YEAR = 2100;

    /**
     * Constructs a new empty TimeSeries.
     */
    public TimeSeries() {
        super();
    }

    /**
     * Creates a copy of TS, but only between STARTYEAR and ENDYEAR,
     * inclusive of both end points.
     */
    public TimeSeries(TimeSeries ts, int startYear, int endYear) {
        super();
        for (Map.Entry<Integer, Double> entry : ts.entrySet()) {
            int k = entry.getKey();
            double v = entry.getValue();
            if (k >= startYear && k <= endYear) {
                this.put(k, v);
            }
        }
    }

    /**
     * Returns all years for this TimeSeries (in any order).
     */
    public List<Integer> years() {
        Set<Integer> ks = this.keySet();
        return new ArrayList<Integer>(ks);
    }

    /**
     * Returns all data for this TimeSeries (in any order).
     * Must be in the same order as years().
     */
    public List<Double> data() {
        Collection<Double> vs = this.values();
        return new ArrayList<>(vs);
    }

    /**
     * Returns the year-wise sum of this TimeSeries with the given TS. In other words, for
     * each year, sum the data from this TimeSeries with the data from TS. Should return a
     * new TimeSeries (does not modify this TimeSeries).
     *
     * If both TimeSeries don't contain any years, return an empty TimeSeries.
     * If one TimeSeries contains a year that the other one doesn't, the returned TimeSeries
     * should store the value from the TimeSeries that contains that year.
     */
    public TimeSeries plus(TimeSeries ts) {
        TimeSeries result = new TimeSeries();
        for (Map.Entry<Integer, Double> entry : ts.entrySet()) {
            int inputK = entry.getKey();
            double inputV = entry.getValue();
            if (this.containsKey(inputK)) {
                double thisV = this.get(inputK);
                result.put(inputK, inputV + thisV);
            } else {
                result.put(inputK, inputV);
            }
        }
        for (Map.Entry<Integer, Double> entry : this.entrySet()) {
            int thisK = entry.getKey();
            if (!result.containsKey(thisK)) {
                result.put(thisK, entry.getValue());
            }
        }
        return result;
    }

    /**
     * Returns the quotient of the value for each year this TimeSeries divided by the
     * value for the same year in TS. Should return a new TimeSeries (does not modify this
     * TimeSeries).
     *
     * If TS is missing a year that exists in this TimeSeries, throw an
     * IllegalArgumentException.
     * If TS has a year that is not in this TimeSeries, ignore it.
     */
    public TimeSeries dividedBy(TimeSeries ts) {
        TimeSeries result = new TimeSeries();
        for (Map.Entry<Integer, Double> entry : this.entrySet()) {
            int thisK = entry.getKey();
            if (!ts.containsKey(thisK)) {
                throw new IllegalArgumentException();
            } else {
                result.put(thisK, entry.getValue() / ts.get(thisK));
            }
        }
        return result;
    }
}
