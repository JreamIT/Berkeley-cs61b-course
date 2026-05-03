package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import ngrams.NGramMap;
import ngrams.TimeSeries;

import java.util.*;

public class HyponymsHandler extends NgordnetQueryHandler {
    WordNet nWord;
    NGramMap nGram;

    public HyponymsHandler() {
        nWord = new WordNet();
        nGram = new NGramMap();
    }

    public HyponymsHandler(WordNet words, NGramMap n) {
        nWord = words;
        nGram = n;
    }

    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        int startYr = q.startYear();
        int endYr = q.endYear();
        int kNum = q.k();

        if (kNum == 0) {
            List<String> temp = nWord.getHypWords(words);
            return temp.toString();
        } else {
            List<String> temp = getKNotZero(words, startYr, endYr, kNum);
            return temp.toString();
        }

    }

    public List<String> getKNotZero(List<String> word, int startYr, int endYr, int kNum) {
        Map<String, Long> wordCount = new HashMap<String, Long>();
        List<String> hyp = nWord.getHypWords(word);

        for (String h : hyp) {
            TimeSeries wordYearCount = nGram.countHistory(h, startYr, endYr);
            long totalCount = 0;
            for (int i = startYr; i <= endYr; i++) {
                if (wordYearCount.get(i) != null) {
                    totalCount += wordYearCount.get(i);
                }
            }
            if (totalCount > 0) {
                wordCount.put(h, totalCount);
            }
        }
        List<Map.Entry<String, Long>> sortedList = new LinkedList<>(wordCount.entrySet());
        sortedList.sort((o1, o2) -> o2.getValue().compareTo(o1.getValue()));
        List<String> result = new ArrayList<String>();
        int count = 0;
        for (Map.Entry<String, Long> entry : sortedList) {
            if (count < kNum) {
                result.add(entry.getKey());
            }
            count++;
        }
        Collections.sort(result);
        return result;
    }


}
