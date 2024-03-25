package com.github.barjb.gummyducks.Algorithms;

import com.github.barjb.gummyducks.Graph.Matrix;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Node {
  private final int nodeId;
  private final int source;
  private final int destination;
  private final int fatherCost;
  private int bound;
  private Matrix incomingMatrix;
  private Matrix opportunityMatrix;
  private int cost;
  private List<Integer> visited;
  private String path;

  public Node(
      int nodeId, int source, int destination, int fatherCost, List<Integer> visited, String path) {
    this.nodeId = nodeId;
    this.source = source;
    this.destination = destination;
    this.fatherCost = fatherCost;
    this.cost = 0;
    this.visited = new LinkedList<>(visited);
    this.path = path;
  }

  public int getDestination() {
    return destination;
  }

  public int getSource() {
    return source;
  }

  public List<Integer> getVisited() {
    return visited;
  }

  public void setBound(int bound) {
    this.bound = bound;
  }

  public void setMatrices(Matrix matrix) {
    incomingMatrix = new Matrix(matrix);
    opportunityMatrix = new Matrix(matrix);
  }

  public int getBound() {
    return this.bound;
  }

  public int getCost() {
    return this.cost;
  }

  public String getPath() {
    return this.path;
  }

  public Matrix getOpportunityMatrix() {
    return opportunityMatrix;
  }

  public void appendPath(int p) {
    path = path.concat("-" + p);
  }

  public List<Node> step(int startingId) {

    List<Node> children = new LinkedList<>();
    for (int i = 0; i < opportunityMatrix.getSize(); i++) {
      if (opportunityMatrix.get(destination, i) != Integer.MAX_VALUE) {
        Node current = new Node(startingId, destination, i, cost, visited, path + "-" + i);
        current.setMatrices(opportunityMatrix);
        current.makeVisited(destination, i);
        current.reduce();
        current.countCost();
        current.addItselfToVisited();
        children.add(current);
      }
    }
    return children;
  }

  public boolean isLeaf() {
    return Arrays.stream(opportunityMatrix.getMatrix())
        .flatMapToInt(Arrays::stream)
        .allMatch(value -> value == Integer.MAX_VALUE);
  }

  public void countCost() {
    cost += fatherCost + incomingMatrix.get(source, destination);
  }

  public void addItselfToVisited() {
    visited.add(destination);
  }

  public void reduce() {
    opportunityMatrix.reduce();
    cost += opportunityMatrix.getReduceCost();
  }

  public void makeVisited(int i, int j) {
    opportunityMatrix.makeVisited(i, j, visited);
  }
}
