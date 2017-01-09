package net.ramonsilva;

import javafx.util.Pair;

import java.util.LinkedList;

/**
 * Created by ramonsilva on 08/01/17.
 */
public class AdjacencyList {
    private final LinkedList< Pair<Integer, Integer> >[] adjacencyList;

    // Constructor
    public AdjacencyList(int vertices) {
        adjacencyList = (LinkedList< Pair<Integer, Integer> >[]) new LinkedList[vertices];

        for (int i = 0; i < adjacencyList.length; ++i) {
            adjacencyList[i] = new LinkedList<>();
        }
    }

    // Appends a new Edge to the linked list
    public void addEdge(int startVertex, int endVertex, int weight) {
        adjacencyList[startVertex].add(new Pair<>(endVertex, weight));
    }

    // Returns number of vertices
    // Does not change for an object
    public int getNumberOfVertices() {
        return adjacencyList.length;
    }

    // Returns number of outward edges from a vertex
    public int getNumberOfEdgesFromVertex(int startVertex) {
        return adjacencyList[startVertex].size();
    }

    // Returns a copy of the Linked List of outward edges from a vertex
    public LinkedList<Pair<Integer, Integer>> getEdgesFromVertex(int startVertex) {
        LinkedList< Pair<Integer, Integer> > edgeList
                = (LinkedList< Pair<Integer, Integer> >) new LinkedList(adjacencyList[startVertex]);

        return edgeList;
    }

    // Prints the Adjaency List
    public void printAdjacencyList() {
        int i = 0;

        for (LinkedList< Pair<Integer, Integer> > list : adjacencyList) {
            System.out.print("adjacencyList[" + i + "] -> ");

            for (Pair<Integer, Integer> edge : list) {
                System.out.print(edge.getKey() + "(" + edge.getValue() + ")");
            }

            ++i;
            System.out.println();
        }
    }

    // Removes an edge and returns true if there
    // was any change in the collection, else false
    public boolean removeEdge(int startVertex, Pair<Integer, Integer> edge) {
        return adjacencyList[startVertex].remove(edge);
    }
}
