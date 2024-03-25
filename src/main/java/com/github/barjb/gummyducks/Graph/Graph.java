package com.github.barjb.gummyducks.Graph;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class Graph {
  private String solution;
  private int cost;
  private String startingCity;
  protected int numberOfCities;
  protected List<String> cities;

  public Graph() {
    this.cities = new LinkedList<>();
  }

  public void setSolution(String solution) {
    this.solution = solution;
  }

  public String getSolution() {
    return Arrays.stream(this.solution.split("-"))
        .map(index -> cities.get(Integer.parseInt(index)))
        .collect(Collectors.joining(" - "));
  }

  public int getCost() {
    return cost;
  }

  public void setCost(int cost) {
    this.cost = cost;
  }

  public void setStartingCity(String city) {
    this.startingCity = city;
  }

  public String getStartingCity() {
    return this.startingCity;
  }

  public void setNumberOfCities(int cities) {
    this.numberOfCities = cities;
  }

  public int getNumberOfCities() {
    return this.numberOfCities;
  }

  public void setCities(List<String> cities) {
    this.cities.addAll(cities);
  }

  public List<String> getCities() {
    return this.cities;
  }

  public abstract void addEdge(String src, String destination, int weight);

  public abstract int getWeight(String src, String dst);

  public abstract int[][] getMatrix();
}
