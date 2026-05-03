package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import ngrams.NGramMap;

import java.util.List;

public class HistoryTextHandler extends NgordnetQueryHandler {
    NGramMap ngm;

    public HistoryTextHandler(NGramMap map) {
        ngm = map;
    }

    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        int start = q.startYear();
        int end = q.endYear();
        String res = "";
        for (int i = 0; i < words.size(); i++) {
            res += words.get(i) + ": ";
            res += ngm.weightHistory(words.get(i), start, end).toString() + "\n";
        }
        return res;
    }
}
