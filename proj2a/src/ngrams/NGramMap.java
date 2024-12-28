package ngrams;

import java.util.Collection;
import java.util.HashMap;

import edu.princeton.cs.algs4.In;

import static ngrams.TimeSeries.MAX_YEAR;
import static ngrams.TimeSeries.MIN_YEAR;

/**
 * An object that provides utility methods for making queries on the
 * Google NGrams dataset (or a subset thereof).
 *
 * An NGramMap stores pertinent data from a "words file" and a "counts
 * file". It is not a map in the strict sense, but it does provide additional
 * functionality.
 *
 * @author Josh Hug
 */
public class NGramMap {
    HashMap<String, TimeSeries> wordsTimeSeriesMap = new HashMap<>();
    TimeSeries cts = new TimeSeries();

    /**
     * Constructs an NGramMap from WORDSFILENAME and COUNTSFILENAME.
     */
    public NGramMap(String wordsFilename, String countsFilename) {
        In wf = new In(wordsFilename);
        TimeSeries wts = null;
        String prevWord = null;
        while (wf.hasNextLine()) {
            String nextLine = wf.readLine();
            String[] splitLine = nextLine.split("\t");

            String word = splitLine[0];
            int year = Integer.parseInt(splitLine[1]);
            double qty = Double.parseDouble(splitLine[2]);

            if (!word.equals(prevWord)) {
                if (wts != null) wordsTimeSeriesMap.put(prevWord, wts);
                prevWord = word;
                wts = new TimeSeries();
            }
            wts.put(year, qty);
        }
        if (wts != null) wordsTimeSeriesMap.put(prevWord, wts);

        In cf = new In(countsFilename);
        while (cf.hasNextLine()) {
            String nextLine = cf.readLine();
            String[] splitLine = nextLine.split(",");
            int year = Integer.parseInt(splitLine[0]);
            double qty = Double.parseDouble(splitLine[1]);
            cts.put(year, qty);
        }
    }

    /**
     * Provides the history of WORD between STARTYEAR and ENDYEAR, inclusive of both ends. The
     * returned TimeSeries should be a copy, not a link to this NGramMap's TimeSeries. In other
     * words, changes made to the object returned by this function should not also affect the
     * NGramMap. This is also known as a "defensive copy". If the word is not in the data files,
     * returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word, int startYear, int endYear) {
        TimeSeries wordTS = wordsTimeSeriesMap.get(word);
        if (wordTS != null) {
            return new TimeSeries(wordTS, startYear, endYear);
        }
        return new TimeSeries();
    }

    /**
     * Provides the history of WORD. The returned TimeSeries should be a copy, not a link to this
     * NGramMap's TimeSeries. In other words, changes made to the object returned by this function
     * should not also affect the NGramMap. This is also known as a "defensive copy". If the word
     * is not in the data files, returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word) {
        TimeSeries wordTS = wordsTimeSeriesMap.get(word);
        if (wordTS != null) {
            return new TimeSeries(wordTS, MIN_YEAR, MAX_YEAR);
        }
        return new TimeSeries();
    }

    /**
     * Returns a defensive copy of the total number of words recorded per year in all volumes.
     */
    public TimeSeries totalCountHistory() {
        return new TimeSeries(cts, MIN_YEAR, MAX_YEAR);
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD between STARTYEAR
     * and ENDYEAR, inclusive of both ends. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word, int startYear, int endYear) {
        TimeSeries wordTs = countHistory(word, startYear, endYear);
        TimeSeries totalTs = new TimeSeries(cts, startYear, endYear);
        return wordTs.dividedBy(totalTs);
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD compared to all
     * words recorded in that year. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word) {
        return weightHistory(word, MIN_YEAR, MAX_YEAR);
    }

    /**
     * Provides the summed relative frequency per year of all words in WORDS between STARTYEAR and
     * ENDYEAR, inclusive of both ends. If a word does not exist in this time frame, ignore it
     * rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words,
                                          int startYear, int endYear) {
        TimeSeries summedTS = new TimeSeries();
        for (String word : words) {
            summedTS = summedTS.plus(countHistory(word, startYear, endYear));
        }
        TimeSeries totalTs = new TimeSeries(cts, startYear, endYear);
        return summedTS.dividedBy(totalTs);
    }

    /**
     * Returns the summed relative frequency per year of all words in WORDS. If a word does not
     * exist in this time frame, ignore it rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words) {
        return summedWeightHistory(words, MIN_YEAR, MAX_YEAR);
    }
}
