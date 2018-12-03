package c.example.parser;


import java.io.IOException;

import edu.stanford.nlp.pipeline.StanfordCoreNLPServer;

public class ParserServer {

    StanfordCoreNLPServer server;

    public ParserServer() {
        try {
            server = new StanfordCoreNLPServer(9000, 1500, true);
            System.out.println("I'M ALL SET UP");
        } catch (IOException e) {
            System.err.println("NOOOOOOOOOOOOOOOOOOOOO Couldn't start server");
        }

    }

    public void run() {
        server.run();
    }

}
