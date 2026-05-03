package ngrams;

import edu.princeton.cs.algs4.In;

import java.util.*;

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
    private TreeMap<String, TimeSeries> totalWords;
    private TimeSeries total;
    private static final int MIN_YEAR = 1400;
    private static final int MAX_YEAR = 2100;

    /**
     * Constructs an NGramMap from WORDSFILENAME and COUNTSFILENAME.
     */
    public NGramMap(String wordsFilename, String countsFilename) {
        totalWords = new TreeMap<>();
        total = new TimeSeries();

        In wordInputFile = new In(wordsFilename);
        while (wordInputFile.hasNextLine()) {
            String lines = wordInputFile.readLine();
            String[] item = lines.split("\\s+");
            String word = item[0];
            int years = Integer.parseInt(item[1]);
            double counts = Double.parseDouble(item[2]);

            totalWords.putIfAbsent(word, new TimeSeries());
            totalWords.get(word).put(years, counts);
        }

        // Read counts file
        In countInputFile = new In(countsFilename);
        while (countInputFile.hasNextLine()) {
            String line = countInputFile.readLine();
            String[] row = line.split(",");
            int year = Integer.parseInt(row[0]);
            double count = Double.parseDouble(row[1]);
            if (year >= MIN_YEAR && year <= MAX_YEAR) {
                total.put(year, count);
            }
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
        TimeSeries timSer = totalWords.get(word);
        if (timSer != null) {
            return new TimeSeries(timSer, startYear, endYear);
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
        if (totalWords.containsKey(word)) {
            return totalWords.get(word);
        }
        return new TimeSeries();
    }

    /**
     * Returns a defensive copy of the total number of words recorded per year in all volumes.
     */
    public TimeSeries totalCountHistory()  {
        return total;
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD between STARTYEAR
     * and ENDYEAR, inclusive of both ends. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word, int startYear, int endYear) {
        if (totalWords.containsKey(word)) {
            return countHistory(word, startYear, endYear).dividedBy(total);
        }
        return new TimeSeries();
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD compared to all
     * words recorded in that year. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word) {
        if (totalWords.containsKey(word)) {
            return countHistory(word, MIN_YEAR, MAX_YEAR).dividedBy(total);
        }
        return new TimeSeries();
    }

    /**
     * Provides the summed relative frequency per year of all words in WORDS between STARTYEAR and
     * ENDYEAR, inclusive of both ends. If a word does not exist in this time frame, ignore it
     * rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words,
                                          int startYear, int endYear) {
        TimeSeries sum = new TimeSeries();
        for (String word : words) {
            if (totalWords.containsKey(word)) {
                TimeSeries add = weightHistory(word, startYear, endYear);
                sum = sum.plus(add);
            }
        }
        return sum;
    }

    /**
     * Returns the summed relative frequency per year of all words in WORDS. If a word does not
     * exist in this time frame, ignore it rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words) {
        TimeSeries sumNum = new TimeSeries();
        for (String word : words) {
            if (totalWords.containsKey(word)) {
                TimeSeries add = weightHistory(word, MIN_YEAR, MAX_YEAR);
                sumNum = sumNum.plus(add);
            }
        }
        return sumNum;
    }
}
