package com.github.barjb.gummyducks.Algorithms;

import com.github.barjb.gummyducks.Graph.Graph;
import com.github.barjb.gummyducks.Graph.Matrix;
import com.github.barjb.gummyducks.exceptions.SolutionNotFound;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Queue;

public class BAB {
  private final Graph graph;
  private final Queue<Node> queue;

  public BAB(Graph graph) {
    this.graph = graph;
    this.queue = new PriorityQueue<>(Comparator.comparingInt(Node::getCost));
  }

  public int compute() {
    int startingIndex = graph.getCities().indexOf(graph.getStartingCity());
    Node root =
        new Node(0, -1, startingIndex, 0, new LinkedList<>(), Integer.toString(startingIndex));
    root.setBound(Integer.MAX_VALUE);
    root.setMatrices(new Matrix(graph.getMatrix()));
    root.reduce();
    root.addItselfToVisited();
    queue.add(root);
    int nodeNumber = 1;
    Node solution = null;
    while (!queue.isEmpty()) {
      var current = queue.poll();
      if (current.isLeaf() && current.getCost() < root.getBound()) {
        current.appendPath(startingIndex);
        root.setBound(current.getCost());
        solution = current;
        continue;
      }
      if (current.getCost() > root.getBound()) break;
      var list = current.step(nodeNumber);
      nodeNumber += list.size();
      queue.addAll(list);
    }
    if (Objects.isNull(solution)) throw new SolutionNotFound("Solution has not been found");
    graph.setSolution(solution.getPath());
    graph.setCost(solution.getCost());
    return solution.getCost();
  }
}
