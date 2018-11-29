package c.example.speechtobsl;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;
import java.util.Properties;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.parser.nndep.DependencyParser;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.process.DocumentPreprocessor;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import edu.stanford.nlp.trees.GrammaticalStructure;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations;
import edu.stanford.nlp.util.CoreMap;


public class StanfordParser {

    DependencyParser parser;
    MaxentTagger tagger;
    String modelPath;
    StanfordCoreNLP pipeline;

    public StanfordParser() throws IOException, ClassNotFoundException{
        /*modelPath = DependencyParser.DEFAULT_MODEL;
        tagger = new MaxentTagger("taggers/english-left3words-distsim.tagger");
        parser = DependencyParser.loadFromModelFile(modelPath);*/


        Properties props = new Properties();
        props.setProperty("annotators", "tokenize,ssplit,pos,lemma,parse");
        //props.setProperty("pos.model", "russian-ud-pos.tagger");
        props.setProperty("pos.model", "english-left3words-distsim.tagger");
        pipeline = new StanfordCoreNLP(props);

    }

    public void parseString(String text) {
        /*DocumentPreprocessor tokenizer = new DocumentPreprocessor(new StringReader(text));
        for (List<HasWord> sentence : tokenizer) {
            List<TaggedWord> tagged = tagger.tagSentence(sentence);
            GrammaticalStructure parsed = parser.predict(tagged);
            System.out.println(parsed);
        }*/
        Annotation doc = new Annotation(text);
        pipeline.annotate(doc);
        List<CoreMap> sentences = doc.get(CoreAnnotations.SentencesAnnotation.class);
        Tree tree = sentences.get(0).get(TreeCoreAnnotations.TreeAnnotation.class);
        System.out.println("parse tree:\n" + tree);
    }
}
