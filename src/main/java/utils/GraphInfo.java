package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashSet;

public class GraphInfo {
    private boolean[][] edges;
    private HashMap<Integer, String> vertexMap;
    private HashMap<Pair<Integer>, Integer> edgeMap;
    private HashMap<Integer, Pair<Integer>> edgeMapInv;

    public void getGraph(Path path) throws IOException {
        HashMap<String, Integer> map = new HashMap<>();
        vertexMap = new HashMap<>();
        HashSet<Pair<String>> edges = new HashSet<>();
        String line;
        String[] split;
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            while (reader.ready()) {
                line = reader.readLine();
                line = line.strip();
                split = line.split(" ");
                if (split.length != 2) {
                    throw new IllegalArgumentException("Invalid file format: the file should represent a single edge " +
                            "at each line (<name_of_vertex_1> <name_of_vertex_2>)");
                }
                map.putIfAbsent(split[0], map.size());
                if (!map.isEmpty())
                    vertexMap.putIfAbsent(map.size()-1, split[0]);
                map.putIfAbsent(split[1], map.size());
                if (!map.isEmpty())
                    vertexMap.putIfAbsent(map.size()-1, split[1]);
                edges.add(new Pair<>(split[0], split[1]));
            }
        }
        this.edges = new boolean[map.size()][map.size()];
        for (Pair<String> edge : edges) {
            this.edges[map.get(edge.getLeft())][map.get(edge.getRight())] = true;
            this.edges[map.get(edge.getRight())][map.get(edge.getLeft())] = true;
        }
        edgeMap = new HashMap<>();
        edgeMapInv = new HashMap<>();
        Pair<Integer> temp;
        for (int i = 0; i < this.edges.length; i++) {
            for (int j = i; j < this.edges.length; j++) {
                if (this.edges[i][j])  {
                    temp = new Pair<>(i, j);
                    edgeMap.put(temp, edgeMap.size());
                    edgeMapInv.put(edgeMapInv.size(), temp);
                }
            }
        }
    }

    public boolean[][] getEdges() {
        return edges;
    }

    public HashMap<Integer, String> getVertexMap() {
        return vertexMap;
    }

    public HashMap<Pair<Integer>, Integer> getEdgeMap() {
        return edgeMap;
    }

    public HashMap<Integer, Pair<Integer>> getEdgeMapInv() {
        return edgeMapInv;
    }
}
