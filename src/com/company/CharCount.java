package com.company;

import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.style.PieStyler;
import org.knowm.xchart.style.Styler;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

import static java.lang.Character.valueOf;
import static java.util.stream.Collectors.toMap;

@SuppressWarnings("all")
public class CharCount {

    private static String readFile(String path) throws IOException {
        return new String(Files.readAllBytes(Paths.get(path)), Charset.forName("UTF-8"));
    }

    public static void main(String... args) throws IOException {
        Map<Character, Integer> frequencies = readFile("big.txt").chars().filter(x -> !Character.isWhitespace(x)).filter(Character::isLetterOrDigit).boxed().collect(toMap(k -> valueOf((char) k.intValue()), v -> 1, Integer::sum));
        PieChart chart = new PieChartBuilder().width(1024).height(768).title("Char occurences").theme(Styler.ChartTheme.GGPlot2).build();
        chart.getStyler().setLegendVisible(false);
        chart.getStyler().setAnnotationType(PieStyler.AnnotationType.LabelAndPercentage);
        chart.getStyler().setAnnotationDistance(1.15);
        chart.getStyler().setPlotContentSize(.7);
        chart.getStyler().setStartAngleInDegrees(90);
        for (Map.Entry<Character, Integer> entry : frequencies.entrySet()) {
            chart.addSeries(entry.getKey().toString(), entry.getValue());
        }
        new SwingWrapper(chart).displayChart();
    }
}
