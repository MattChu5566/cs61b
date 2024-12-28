package main;

import java.util.List;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import ngrams.NGramMap;
import ngrams.TimeSeries;

public class HistoryTextHandler extends NgordnetQueryHandler {
    private NGramMap ngm;

    public HistoryTextHandler(NGramMap map) {
      this.ngm = map;
    }

    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();

        String response = "";
        for (String word : words) {
          response += word + ": ";
          TimeSeries ts = this.ngm.weightHistory(word, startYear, endYear);
          response += ts.toString();
          response += "\n";
        }

        return response;
    }
}
