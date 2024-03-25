package com.github.barjb.gummyducks.Graph;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Matrix {
  private int[][] m;
  private int reduceCost;
  private int size;

  public Matrix(int[][] m) {
    this.m = new int[m.length][];
    for (int i = 0; i < m.length; i++) {
      this.m[i] = Arrays.copyOf(m[i], m[i].length);
    }
    this.reduceCost = 0;
    this.size = this.m.length;
  }

  public Matrix(Matrix m) {
    this(m.getMatrix());
  }

  public void setMatrix(int[][] m) {
    this.m = new int[m.length][];
    for (int i = 0; i < m.length; i++) {
      this.m[i] = Arrays.copyOf(m[i], m[i].length);
    }
    this.reduceCost = 0;
    this.size = this.m.length;
  }

  public int[][] getMatrix() {
    return m;
  }

  public int getReduceCost() {
    return reduceCost;
  }

  public int getSize() {
    return size;
  }

  public void reduce() {
    int minRow = Integer.MAX_VALUE;
    int minCol = Integer.MAX_VALUE;
    for (int i = 0; i < m.length; i++) {
      for (int j = 0; j < m.length; j++) {
        if (m[i][j] < minRow) minRow = m[i][j];
      }
      if (minRow > 0 && minRow != Integer.MAX_VALUE) {
        reduceCost += minRow;
        for (int j = 0; j < m.length; j++) {
          if (m[i][j] != Integer.MAX_VALUE) m[i][j] -= minRow;
        }
      }
      minRow = Integer.MAX_VALUE;
    }
    for (int i = 0; i < m.length; i++) {
      for (int j = 0; j < m.length; j++) {
        if (m[j][i] < minCol) minCol = m[j][i];
      }
      if (minCol > 0 && minCol != Integer.MAX_VALUE) {
        reduceCost += minCol;
        for (int j = 0; j < m.length; j++) {
          if (m[j][i] != Integer.MAX_VALUE) m[j][i] -= minCol;
        }
      }
      minCol = Integer.MAX_VALUE;
    }
  }

  public void makeVisited(int i, int j, List<Integer> visited) {
    for (int k = 0; k < m.length; k++) {
      m[i][k] = Integer.MAX_VALUE;
    }
    for (int k = 0; k < m.length; k++) {
      m[k][j] = Integer.MAX_VALUE;
    }
    m[j][i] = Integer.MAX_VALUE;
    if (Objects.nonNull(visited)) {
      visited.stream().forEach(index -> m[j][index] = Integer.MAX_VALUE);
    }
  }

  public int get(int i, int j) {
    return m[i][j];
  }

  public void set(int i, int j, int val) {
    m[i][j] = val;
  }
}
