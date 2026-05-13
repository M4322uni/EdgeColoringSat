package edgeColoring;

import it.uniroma1.di.tmancini.teaching.ai.SATCodec.IntRange;
import it.uniroma1.di.tmancini.teaching.ai.SATCodec.SATEncoder;
import it.uniroma1.di.tmancini.utils.CmdLineOptions;
import utils.GraphInfo;
import utils.Pair;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class EdgeColoringEncoder {

    public static void main(String[] args) throws IOException {
        CmdLineOptions cmd = new CmdLineOptions("Edge Coloring Encoder", "13/05/26",
                "Matteo Piscitello", "A simple SAT encoder for the Edge Coloring problem");
        cmd.addOption("file", "The name of the file containing the graph", true);
        cmd.addOption("o", "The output file name");
        cmd.parse(args);
        Path path = Paths.get(cmd.getOptionValue("file"));
        GraphInfo save = new GraphInfo();
        save.getGraph(path);
        IntRange edges = new IntRange("edges", 1, save.getEdgeMap().size());
        SATEncoder cnf = new SATEncoder("Edge Coloring", cmd.getOptionValue("o"));
        cnf.defineFamilyOfVariables("X", edges, new IntRange("three", 1, 3));
        for (int i : edges.values()) {
            cnf.addToClause("X", i, 1);
            cnf.addToClause("X", i, 2);
            cnf.addToClause("X", i, 3);
            cnf.endClause();
            cnf.addNegToClause("X", i, 1);
            cnf.addNegToClause("X", i, 2);
            cnf.endClause();
            cnf.addNegToClause("X", i, 1);
            cnf.addNegToClause("X", i, 3);
            cnf.endClause();
            cnf.addNegToClause("X", i, 2);
            cnf.addNegToClause("X", i, 3);
            cnf.endClause();
        }
        for (int i = 0; i < save.getEdges().length-2; i++) {
            for (int j = i+1; j < save.getEdges().length-1; j++) {
                for (int k = j+1; k < save.getEdges().length; k++) {
                    if (save.getEdges()[i][j] && save.getEdges()[j][k] && save.getEdges()[k][i]) {
                        for (int x = 1; x <= 3; x++) {
                            cnf.addNegToClause("X", save.getEdgeMap().get(new Pair<>(i, j))+1, x);
                            cnf.addNegToClause("X", save.getEdgeMap().get(new Pair<>(j, k))+1, x);
                            cnf.addNegToClause("X", save.getEdgeMap().get(new Pair<>(i, k))+1, x);
                            cnf.endClause();
                        }
                    }
                }
            }
        }
        cnf.end();
    }
}
