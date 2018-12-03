package c.example.parser;

import java.util.Properties;


import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLPClient;


public class StanfordParser {

    private StanfordCoreNLPClient pipeline;

    public StanfordParser() {
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize,ssplit,pos, depparse, udfeats");
        pipeline = new StanfordCoreNLPClient(props, "localhost", 9000, 2);

        System.out.println("I've finised setup");
    }

    public String parseString(String text) {
        String text2 = "The boy saw the girl with the telescope.";
        //Annotation ann = new Annotation(text2);
        pipeline.process(text2);

        return null;
    }
}
