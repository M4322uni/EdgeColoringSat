package edgeColoring;

import it.uniroma1.di.tmancini.teaching.ai.SATCodec.SATModelDecoder;
import utils.GraphInfo;
import utils.Pair;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class EdgeColoringDecoder {

    private static Pair<String[]> argsPartition(String[] args) {
        String[] left = null, right = new String[args.length-2];
        int rIdx = 0;
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("--file")) {
                left = new String[]{args[++i]};
            }
            else
                try {
                    right[rIdx++] = args[i];
                }
                catch (IndexOutOfBoundsException e) {
                    throw new IllegalArgumentException("Missing --file argument (the file containing " +
                            "the graph)");
                }
        }
        return new Pair<>(left, right);
    }

    public static void main(String[] args) throws IOException {
        Pair<String[]> partition = argsPartition(args);
        SATModelDecoder dec = new SATModelDecoder(partition.getRight());
        dec.run();
        Path path = Paths.get(partition.getLeft()[0]);
        GraphInfo save = new GraphInfo();
        save.getGraph(path);
        int maxVar = dec.getMaxVar();
        SATModelDecoder.Var var;
        Pair<Integer> coords;
        String leftNode, rightNode;
        for  (int i = 1; i <= maxVar; i++) {
            var = dec.decodeVariable(i);
            if (dec.getModelValue(i)) {
                coords = save.getEdgeMapInv().get(var.getIndices().getFirst()-1);
                leftNode = save.getVertexMap().get(coords.getLeft());
                rightNode = save.getVertexMap().get(coords.getRight());
                System.out.println("(" + leftNode + ", " + rightNode + ") = " + var.getIndices().getLast());
            }
        }
    }
}
