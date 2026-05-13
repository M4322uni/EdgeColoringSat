package edgeColoring;

import it.uniroma1.di.tmancini.teaching.ai.SATCodec.SATModelDecoder;
import it.uniroma1.di.tmancini.utils.CmdLineOptions;

import java.io.IOException;

public class EdgeColoringDecoder {

    public static void main(String[] args) throws IOException {
        SATModelDecoder dec = new SATModelDecoder(args);
        CmdLineOptions cmd = new CmdLineOptions("Edge Coloring Decoder", "13/05/26",
                "Matteo Piscitello", "A simple SAT decoder for the Edge Coloring problem");
        cmd.addOption("file", "The name of the file containing the graph", true);
        cmd.parse(args);
        dec.run();

    }
}
