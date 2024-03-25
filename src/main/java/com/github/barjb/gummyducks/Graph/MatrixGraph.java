package com.github.barjb.gummyducks.Graph;

import com.github.barjb.gummyducks.exceptions.DataParsingException;

import java.util.Arrays;

public class MatrixGraph extends Graph {
  private Matrix matrix;

  @Override
  public void setNumberOfCities(int numberOfCities) {
    this.numberOfCities = numberOfCities;
    int[][] m = new int[numberOfCities][numberOfCities];
      for (int[] ints : m) {
          Arrays.fill(ints, Integer.MAX_VALUE);
      }
    this.matrix = new Matrix(m);
  }

  @Override
  public void addEdge(String src, String dst, int weight) {
    if (!cities.contains(src)) cities.add(src);
    if (!cities.contains(dst)) cities.add(dst);

    if(cities.size() > numberOfCities){
      throw new DataParsingException("Number of cities does not match parsed data");
    }

    int srcIndex = cities.indexOf(src);
    int dstIndex = cities.indexOf(dst);

    matrix.set(srcIndex, dstIndex, weight);
    matrix.set(dstIndex, srcIndex, weight);
  }

  @Override
  public int getWeight(String src, String dst) {
    int srcIndex = cities.indexOf(src);
    int dstIndex = cities.indexOf(dst);
    return matrix.get(srcIndex, dstIndex);
  }

  @Override
  public int[][] getMatrix() {
    return matrix.getMatrix();
  }
}
