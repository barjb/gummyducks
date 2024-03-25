package com.github.barjb.gummyducks.Algorithms;

import static org.junit.jupiter.api.Assertions.*;

import com.github.barjb.gummyducks.Graph.Matrix;
import java.util.LinkedList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class NodeTest {

  private final int[][] mat = {
    {Integer.MAX_VALUE, 118, 104, 229},
    {118, Integer.MAX_VALUE, 189, 341},
    {104, 189, Integer.MAX_VALUE, 206},
    {229, 341, 206, Integer.MAX_VALUE}
  };
  private Node node = null;
  private Matrix matrix = null;

  @BeforeEach
  public void initNode() {
    int destination = 0;
    node = new Node(0, -1, destination, 0, new LinkedList<>(),Integer.toString(destination));
    matrix = new Matrix(mat);
    node.setMatrices(matrix);
  }

  @Test
  void canMakeStep() {
    node.reduce();
    node.addItselfToVisited();
    List<Node> children = node.step(1);
    assertEquals(3,children.size());
  }

  @Test
  void isLeaf() {
    assertEquals(false, node.isLeaf());
    int destination = 1;
    List<Integer> l = new LinkedList<>();
    l.add(0);
    Node leaf = new Node(1, 0, destination, 1, l,Integer.toString(destination));
    Matrix matrix =
        new Matrix(
            new int[][] {
              {Integer.MAX_VALUE, Integer.MAX_VALUE},
              {Integer.MAX_VALUE, Integer.MAX_VALUE}
            });
    leaf.setMatrices(matrix);
    assertEquals(true, leaf.isLeaf());
  }

  @Test
  void isNotLeaf() {
    assertEquals(false, node.isLeaf());
    int destination = 1;
    List<Integer> l = new LinkedList<>();
    l.add(0);
    Node leaf = new Node(1, 0, destination, 1, l,Integer.toString(destination));
    Matrix matrix =
            new Matrix(
                    new int[][] {
                            {Integer.MAX_VALUE, 1},
                            {1, Integer.MAX_VALUE}
                    });
    leaf.setMatrices(matrix);
    assertEquals(false, leaf.isLeaf());
  }

  @Test
  void canCountCost() {
    node.reduce();
    node.addItselfToVisited();
    assertEquals(648,node.getCost());
    Node n2 = new Node(1,0,3,node.getCost(),node.getVisited(),"3");
    n2.setMatrices(node.getOpportunityMatrix());
    n2.countCost();
    assertEquals(671,n2.getCost());
  }

  @Test
  void addItselfToVisited() {
    assertEquals(node.getVisited().size(),0);
    node.makeVisited(0,1);
    node.addItselfToVisited();
    assertEquals(node.getVisited().size(),1);
  }

  @Test
  void canReduceMatrix() {
    node.reduce();
    int[][] reduced = {
            {Integer.MAX_VALUE, 0, 0, 23},
            {0, Integer.MAX_VALUE, 71, 121},
            {0, 71, Integer.MAX_VALUE, 0},
            {23, 121, 0, Integer.MAX_VALUE}
    };
    assertArrayEquals(reduced, node.getOpportunityMatrix().getMatrix());
    assertEquals(648,node.getCost());
  }

  @Test
  void canMakeNodeVisited() {
    node.makeVisited(0,1);
    int[][] visited = {
            {Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE},
            {Integer.MAX_VALUE, Integer.MAX_VALUE, 189, 341},
            {104, Integer.MAX_VALUE, Integer.MAX_VALUE, 206},
            {229, Integer.MAX_VALUE, 206, Integer.MAX_VALUE}
    };
    assertArrayEquals(visited, node.getOpportunityMatrix().getMatrix());
  }
}
