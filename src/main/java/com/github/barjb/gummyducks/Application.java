package com.github.barjb.gummyducks;

import com.github.barjb.gummyducks.Algorithms.BAB;
import com.github.barjb.gummyducks.Graph.Graph;
import com.github.barjb.gummyducks.Graph.MatrixGraph;
import com.github.barjb.gummyducks.utils.Reader;

public class Application {

  public static void main(String[] args) {
    String file = "src/main/java/com/github/barjb/gummyducks/input.txt";
    Reader reader = new Reader(file);
    Graph graph = new MatrixGraph();
    reader.readGraph(graph);
    BAB bound = new BAB(graph);
    bound.compute();
    System.out.println("Cost: " + graph.getCost());
    System.out.println("Solution: " + graph.getSolution());
  }
}
