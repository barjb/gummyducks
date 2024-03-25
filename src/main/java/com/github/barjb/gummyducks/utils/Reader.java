package com.github.barjb.gummyducks.utils;

import com.github.barjb.gummyducks.Graph.Graph;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class Reader {
  private final String file;
  private String[] header;
  private String[] lines;

  public Reader(String file) {
    this.file = file;
  }

  private static boolean isString(String s) {
    try {
      Integer.parseInt(s);
      return false;
    } catch (NumberFormatException e) {
      return true;
    }
  }
  private void addEdgeToGraph(Graph graph, String src, String dst, String value){
    graph.addEdge(src, dst, Integer.parseInt(value));
    graph.addEdge(dst, src, Integer.parseInt(value));
  }
  public Graph readGraph(Graph graph) {
    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
      String currentLine;
      Set<String> uniqueCities = new LinkedHashSet<>();
      try {
        currentLine = reader.readLine();
        String[] split = currentLine.split("\\s+");
        int numberOfCities = Integer.parseInt(split[0]);
        graph.setNumberOfCities(numberOfCities);
        graph.setStartingCity(split[1]);

        while (Objects.nonNull(currentLine)) {
          String[] splitted = currentLine.split("\\s+");
          if (splitted.length == 3) {
            uniqueCities.add(splitted[0]);
            uniqueCities.add(splitted[1]);
            addEdgeToGraph(graph,splitted[0],splitted[1],splitted[2]);
          }
          if (splitted.length > 3) {
            if (uniqueCities.contains(splitted[0])) {
              String unique =
                  Arrays.stream(splitted)
                      .skip(1)
                      .filter(Reader::isString)
                      .collect(Collectors.joining(" "));
              uniqueCities.add(unique);
              addEdgeToGraph(graph,splitted[0],unique,splitted[splitted.length-1]);
            } else if (uniqueCities.contains(splitted[splitted.length - 2])) {
              String unique =
                  Arrays.stream(splitted)
                      .limit(splitted.length - 2)
                      .collect(Collectors.joining(" "));
              uniqueCities.add(unique);
              addEdgeToGraph(graph,unique,splitted[splitted.length-2],splitted[splitted.length-1]);
            }
          }
          currentLine = reader.readLine();
        }
      } catch (FileNotFoundException e) {
        System.out.println(e.getMessage());
        return null;
      }
      graph.setCities(uniqueCities.stream().toList());
    } catch (IOException e) {
      System.out.println(e.getMessage());
      return null;
    }
    return graph;
  }
}
