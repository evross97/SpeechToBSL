package c.example.parser;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.HasTag;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.SentenceUtils;
import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.parser.nndep.DependencyParser;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.DocumentPreprocessor;
import edu.stanford.nlp.process.PTBTokenizer;
import edu.stanford.nlp.process.Tokenizer;
import edu.stanford.nlp.process.TokenizerFactory;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import edu.stanford.nlp.trees.GrammaticalStructure;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreePrint;
import edu.stanford.nlp.trees.TypedDependency;
import edu.stanford.nlp.util.CoreMap;

public class StanfordParser {

    private String modelPath;
    private String taggerPath;
    private MaxentTagger tagger;
    private LexicalizedParser parser;

    public StanfordParser() {
        modelPath = "C://stanford-parser-full-2018-10-17/englishPCFG.ser.gz";
        taggerPath = "english-left3words-distsim.tagger";
        tagger = new MaxentTagger(taggerPath);
        Properties props = new Properties();
        props.setProperty("parser.model", modelPath);
        parser = LexicalizedParser.loadModel(modelPath);
        System.out.println("I've finised setup");
    }

    public String parseString(String text) {
        String text2 = "The boy saw the girl with the telescope";
        /*DocumentPreprocessor tokenizer = new DocumentPreprocessor(new StringReader(text2));
        for (List<HasWord> sentence : tokenizer) {
            List<TaggedWord> tagged = tagger.tagSentence(sentence);
            GrammaticalStructure gs = parser.predict(tagged);
            List<TypedDependency> tls = gs.typedDependenciesCCprocessed();
            System.out.println("HHHHEEEEEERRRRREEEEE" + tls);
        }*/

        TokenizerFactory<CoreLabel> tokenizerFactory =
                PTBTokenizer.factory(new CoreLabelTokenFactory(), "");
        Tokenizer<CoreLabel> tok =
                tokenizerFactory.getTokenizer(new StringReader(text2));
        //List<CoreLabel> rawWords2 = tok.tokenize();
        Tree parse = parser.apply(tok.tokenize());
        TreePrint tp = new TreePrint("penn,typedDependenciesCollapsed");
        tp.printTree(parse);
        return null;
    }
}
