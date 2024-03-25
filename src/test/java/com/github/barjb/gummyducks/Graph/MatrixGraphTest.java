package com.github.barjb.gummyducks.Graph;

import static org.junit.jupiter.api.Assertions.*;

import com.github.barjb.gummyducks.exceptions.DataParsingException;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MatrixGraphTest {
  private MatrixGraph graph;

  @Test
  public void canAddEdgeUsingCityList() {
    List<String> cities = List.of("A", "B");
    graph = new MatrixGraph();
    graph.setNumberOfCities(2);
    graph.setCities(cities);
    assertArrayEquals(
        graph.getMatrix(),
        new int[][] {
          {Integer.MAX_VALUE, Integer.MAX_VALUE}, {Integer.MAX_VALUE, Integer.MAX_VALUE}
        });
    graph.addEdge("A", "B", 1);
    assertArrayEquals(
        graph.getMatrix(), new int[][] {{Integer.MAX_VALUE, 1}, {1, Integer.MAX_VALUE}});
  }

  @Test
  public void canAddEdgeWithoutCityList() {
    graph = new MatrixGraph();
    graph.setNumberOfCities(2);
    assertArrayEquals(
        graph.getMatrix(),
        new int[][] {
          {Integer.MAX_VALUE, Integer.MAX_VALUE}, {Integer.MAX_VALUE, Integer.MAX_VALUE}
        });
    graph.addEdge("A", "B", 1);
    assertArrayEquals(
        graph.getMatrix(), new int[][] {{Integer.MAX_VALUE, 1}, {1, Integer.MAX_VALUE}});
  }

  @Test
  public void cantAddEdgeWhenCityDoesNotExist() {
    List<String> cities = List.of("A", "B");
    graph = new MatrixGraph();
    graph.setNumberOfCities(2);
    graph.setCities(cities);
    assertThrows(
        DataParsingException.class,
        () -> {
          graph.addEdge("A", "C", 1);
        });
  }
}
