package c.example.parser;

import java.io.StringReader;
import java.util.List;
import java.util.Properties;

import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.parser.nndep.DependencyParser;
import edu.stanford.nlp.process.DocumentPreprocessor;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import edu.stanford.nlp.trees.GrammaticalStructure;

public class StanfordParser {

    private String modelPath;
    private String taggerPath;
    private MaxentTagger tagger;
    private  DependencyParser parser;

    public StanfordParser() {
        modelPath = "englishPCFG.ser.gz";
        taggerPath = "english-left3words-distsim.tagger";
        tagger = new MaxentTagger(taggerPath);
        Properties props = new Properties();
        props.setProperty("parse.model", modelPath);
        parser = new DependencyParser(props);
    }

    public void parseString(String text) {
        DocumentPreprocessor tokenizer = new DocumentPreprocessor(new StringReader(text));
        for (List<HasWord> sentence : tokenizer) {
            List<TaggedWord> tagged = tagger.tagSentence(sentence);
            GrammaticalStructure gs = parser.predict(tagged);
            System.out.println(gs.typedDependenciesCCprocessed());
        }
    }
}
