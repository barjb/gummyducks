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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Reader {
  private final String file;
  private String[] header;
  private String[] lines;
  private static final Logger logger = LogManager.getLogger(Reader.class);

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

  private void addEdgeToGraph(Graph graph, String src, String dst, String value) {
    graph.addEdge(src, dst, Integer.parseInt(value));
    graph.addEdge(dst, src, Integer.parseInt(value));
  }

  public Graph readGraph(Graph graph) {
    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
      String currentLine;
      Set<String> uniqueCities = new LinkedHashSet<>();
      int numberOfCities = -1;
      try {
        currentLine = reader.readLine();
        String[] split = currentLine.split("\\s+");
        try {
          numberOfCities = Integer.parseInt(split[0]);
        } catch (NumberFormatException e) {
          logger.error("Number of cities provided in input file cannot be parsed to an integer.");
          System.exit(1);
        }
        graph.setNumberOfCities(numberOfCities);
        graph.setStartingCity(split[1]);

        currentLine = reader.readLine();

        while (Objects.nonNull(currentLine)) {
          String[] splitted = currentLine.split("\\s+");
          try {
            Integer.parseInt(splitted[splitted.length - 1]);
          } catch (NumberFormatException e) {
            logger.error(
                "Read distance between cities provided in input cannot be parsed to an integer.");
            System.exit(1);
          }

          if (splitted.length == 3) {
            uniqueCities.add(splitted[0]);
            uniqueCities.add(splitted[1]);
            addEdgeToGraph(graph, splitted[0], splitted[1], splitted[2]);
          }
          if (splitted.length > 3) {
            if (uniqueCities.contains(splitted[0])) {
              String unique =
                  Arrays.stream(splitted)
                      .skip(1)
                      .filter(Reader::isString)
                      .collect(Collectors.joining(" "));
              uniqueCities.add(unique);
              addEdgeToGraph(graph, splitted[0], unique, splitted[splitted.length - 1]);
            } else if (uniqueCities.contains(splitted[splitted.length - 2])) {
              String unique =
                  Arrays.stream(splitted)
                      .limit(splitted.length - 2)
                      .collect(Collectors.joining(" "));
              uniqueCities.add(unique);
              addEdgeToGraph(
                  graph, unique, splitted[splitted.length - 2], splitted[splitted.length - 1]);
            }
          }
          currentLine = reader.readLine();
        }

      } catch (FileNotFoundException e) {
        logger.error("File was not found.");
        System.exit(1);
      }
      if (uniqueCities.size() != numberOfCities) {
        logger.error("Expected number of cities does not match cities provided from distances.");
        System.exit(1);
      }
      graph.setCities(uniqueCities.stream().toList());
    } catch (IOException e) {
      logger.fatal("IOException");
      System.exit(1);
    }
    return graph;
  }
}
