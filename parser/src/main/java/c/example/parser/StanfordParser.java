package c.example.parser;

import java.util.List;
import java.util.Properties;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations;
import edu.stanford.nlp.util.CoreMap;



public class StanfordParser {

    static StanfordCoreNLP pipeline;

    public StanfordParser() {
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize,ssplit,pos,lemma,parse");
        System.out.println("start 1");
        pipeline = new StanfordCoreNLP(props);
        System.out.println("start2");
    }


    public static void main(String[] args) {
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize,ssplit,pos,lemma,parse");
        System.out.println("start 1");
        pipeline = new StanfordCoreNLP(props);
        System.out.println("start2");


        String text = args[0];
        parseString(text);
    }

    public static void parseString(String text) {
        System.out.println(text);
        Annotation doc = new Annotation(text);
        System.out.println("starting");
        pipeline.annotate(doc);
        System.out.println("finished");
        List<CoreMap> sentences = doc.get(CoreAnnotations.SentencesAnnotation.class);
        Tree tree = sentences.get(0).get(TreeCoreAnnotations.TreeAnnotation.class);
        System.out.println("parse tree:\n" + tree);
    }
}