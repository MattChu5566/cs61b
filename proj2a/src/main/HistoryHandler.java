package main;

import java.util.ArrayList;
import java.util.List;

import org.knowm.xchart.XYChart;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import ngrams.NGramMap;
import ngrams.TimeSeries;
import plotting.Plotter;

public class HistoryHandler extends NgordnetQueryHandler {
    private NGramMap ngm;

    public HistoryHandler(NGramMap map) {
      this.ngm = map;
    }

    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();

        // TimeSeries parabola = new TimeSeries();
        // for (int i = 1400; i < 1500; i += 1) {
        //     parabola.put(i, (i - 50.0) * (i - 50.0) + 3);
        // }

        // TimeSeries sinWave = new TimeSeries();
        // for (int i = 1400; i < 1500; i += 1) {
        //     sinWave.put(i, 1000 + 500 * Math.sin(i/100.0*2*Math.PI));
        // }

        ArrayList<TimeSeries> lts = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<>();

        for (String word : words) {
          labels.add(word);
          lts.add(ngm.weightHistory(word, startYear, endYear));
        }

        // labels.add("parabola");
        // labels.add("sine wave");

        // lts.add(parabola);
        // lts.add(sinWave);

        XYChart chart = Plotter.generateTimeSeriesChart(labels, lts);
        String encodedImage = Plotter.encodeChartAsString(chart);

        return encodedImage;
    }
}
