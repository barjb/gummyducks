package com.github.barjb.gummyducks.Graph;

import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MatrixTest {

  private int [][] mat = {
          {Integer.MAX_VALUE, 118, 104, 229},
          {118, Integer.MAX_VALUE, 189, 341},
          {104, 189, Integer.MAX_VALUE, 206},
          {229, 341, 206, Integer.MAX_VALUE}
  };

  @BeforeEach
  public void setMat(){
    mat = new int[][]{
      {Integer.MAX_VALUE, 118, 104, 229},
      {118, Integer.MAX_VALUE, 189, 341},
      {104, 189, Integer.MAX_VALUE, 206},
      {229, 341, 206, Integer.MAX_VALUE}
    };
  }

  @Test
  void canCreateDeepCopyOfAnArray() {
    Matrix matrix = new Matrix(mat);
    assertArrayEquals(mat, matrix.getMatrix());
    mat[0][1] = 100;
    assertNotEquals(mat[0][1], matrix.get(0, 1));
  }

  @Test
  void canCreateDeepCopyOfAnMatrix() {
    Matrix matrix = new Matrix(mat);

    Matrix copy = new Matrix(matrix);
    assertArrayEquals(matrix.getMatrix(), copy.getMatrix());
    matrix.set(0, 1, 100);
    assertNotEquals(matrix.get(0, 1), copy.get(0, 1));
  }

  @Test
  void canSetMatrixToADifferentSizeArray() {
    Matrix matrix = new Matrix(mat);
    int[][] m2 = {
      {Integer.MAX_VALUE, 118, 104},
      {118, Integer.MAX_VALUE, 189},
      {104, 189, Integer.MAX_VALUE},
      {229, 341, 205}
    };
    matrix.setMatrix(m2);
    assertArrayEquals(matrix.getMatrix(), m2);
    m2[0][0] = 1;
    assertNotEquals(m2[0][0], matrix.get(0, 0));
  }

  @Test
  void canReduceMatrix() {
    Matrix matrix = new Matrix(mat);
    matrix.reduce();
    int[][] reduced = {
      {Integer.MAX_VALUE, 0, 0, 23},
      {0, Integer.MAX_VALUE, 71, 121},
      {0, 71, Integer.MAX_VALUE, 0},
      {23, 121, 0, Integer.MAX_VALUE}
    };
    assertArrayEquals(matrix.getMatrix(), reduced);
    assertEquals(matrix.getReduceCost(), 648);
  }

  @Test
  void canMakeNodeVisited() {

    Matrix matrix = new Matrix(mat);
    LinkedList<Integer> visitedNodes0 = new LinkedList<>();
    visitedNodes0.add(0);
    matrix.makeVisited(0, 1, visitedNodes0);
    int[][] visited = {
      {Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE},
      {Integer.MAX_VALUE, Integer.MAX_VALUE, 189, 341},
      {104, Integer.MAX_VALUE, Integer.MAX_VALUE, 206},
      {229, Integer.MAX_VALUE, 206, Integer.MAX_VALUE}
    };
    assertArrayEquals(visited, matrix.getMatrix());
    int[][] visited2 = {
      {Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE},
      {Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE},
      {Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, 206},
      {229, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE}
    };
    LinkedList<Integer> visitedNodes1 = new LinkedList<>();
    visitedNodes1.add(0);
    visitedNodes1.add(1);
    matrix.makeVisited(1, 2, visitedNodes1);
    assertArrayEquals(visited2, matrix.getMatrix());
  }
}
