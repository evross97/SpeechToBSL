package c.example.speechtobsl.utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

import c.example.speechtobsl.structure_converter.entities.Adjective;
import c.example.speechtobsl.structure_converter.entities.Clause;
import c.example.speechtobsl.structure_converter.entities.NounPhrase;
import c.example.speechtobsl.structure_converter.entities.VerbPhrase;

public class TestUtils {

    public ArrayList<JSONObject> POSTags;
    public JSONObject obj1;
    public JSONObject obj2;
    public JSONObject obj3;
    public JSONObject obj4;
    public JSONObject obj5;
    public JSONObject obj6;
    public JSONObject obj7;
    public JSONObject obj8;
    public JSONObject obj9;
    public JSONObject obj10;
    public JSONObject obj11;
    public JSONObject obj12;
    public JSONObject obj13;
    public JSONObject obj14;

    public ArrayList<String> englishText;
    public String englishString;

    public ArrayList<JSONObject> parse;
    public JSONObject pobj1;
    public JSONObject pobj2;
    public JSONObject pobj3;
    public JSONObject pobj4;
    public JSONObject pobj5;
    public JSONObject pobj6;
    public JSONObject pobj7;
    public JSONObject pobj8;
    public JSONObject pobj9;
    public JSONObject pobj10;
    public JSONObject pobj11;
    public JSONObject pobj12;
    public JSONObject pobj13;

    public VerbPhrase vp1;
    public VerbPhrase vp2;
    public VerbPhrase vp3;

    public NounPhrase np1;
    public NounPhrase np2;
    public NounPhrase np3;

    public Clause c1;
    public Clause c2;

    public JSONObject parsedText;
    public ArrayList<String> parsedSentence;

    public String synResponse;


    public TestUtils() {
        this.POSTags = new ArrayList<>();
        try{
            this.obj1 = new JSONObject();
            this.obj1.put("index",1);
            this.obj1.put("word","what");
            this.obj1.put("lemma","what");
            this.obj1.put("pos","WP");
            this.obj1.put("ner","O");
            this.obj2 = new JSONObject();
            this.obj2.put("index",2);
            this.obj2.put("word","are");
            this.obj2.put("lemma","be");
            this.obj2.put("pos","VBP");
            this.obj2.put("ner","O");
            this.obj3 = new JSONObject();
            this.obj3.put("index",3);
            this.obj3.put("word","the");
            this.obj3.put("lemma","the");
            this.obj3.put("pos","DT");
            this.obj3.put("ner","O");
            this.obj4 = new JSONObject();
            this.obj4.put("index",4);
            this.obj4.put("word","names");
            this.obj4.put("lemma","name");
            this.obj4.put("pos","NNS");
            this.obj4.put("ner","O");
            this.obj5 = new JSONObject();
            this.obj5.put("index",5);
            this.obj5.put("word","of");
            this.obj5.put("lemma","of");
            this.obj5.put("pos","IN");
            this.obj5.put("ner","O");
            this.obj6 = new JSONObject();
            this.obj6.put("index",6);
            this.obj6.put("word","the");
            this.obj6.put("lemma","the");
            this.obj6.put("pos","DT");
            this.obj6.put("ner","O");
            this.obj7 = new JSONObject();
            this.obj7.put("index",7);
            this.obj7.put("word","big");
            this.obj7.put("lemma","big");
            this.obj7.put("pos","JJ");
            this.obj7.put("ner","O");
            this.obj8 = new JSONObject();
            this.obj8.put("index",8);
            this.obj8.put("word","dogs");
            this.obj8.put("lemma","dog");
            this.obj8.put("pos","NNS");
            this.obj8.put("ner","O");
            this.obj9 = new JSONObject();
            this.obj9.put("index",9);
            this.obj9.put("word","and");
            this.obj9.put("lemma","and");
            this.obj9.put("pos","CC");
            this.obj9.put("ner","O");
            this.obj10 = new JSONObject();
            this.obj10.put("index",10);
            this.obj10.put("word","could");
            this.obj10.put("lemma","could");
            this.obj10.put("pos","MD");
            this.obj10.put("ner","O");
            this.obj11 = new JSONObject();
            this.obj11.put("index",11);
            this.obj11.put("word","they");
            this.obj11.put("lemma","they");
            this.obj11.put("pos","PRP");
            this.obj11.put("ner","O");
            this.obj12 = new JSONObject();
            this.obj12.put("index",12);
            this.obj12.put("word","run");
            this.obj12.put("lemma","run");
            this.obj12.put("pos","VB");
            this.obj12.put("ner","O");
            this.obj13 = new JSONObject();
            this.obj13.put("index",13);
            this.obj13.put("word","quickly");
            this.obj13.put("lemma","quickly");
            this.obj13.put("pos","RB");
            this.obj13.put("ner","O");
            this.obj14 = new JSONObject();
            this.obj14.put("index",14);
            this.obj14.put("word","yesterday");
            this.obj14.put("lemma","yesterday");
            this.obj14.put("pos","NN");
            this.obj14.put("ner","TIME");
            this.POSTags.add(obj1);
            this.POSTags.add(obj2);
            this.POSTags.add(obj3);
            this.POSTags.add(obj4);
            this.POSTags.add(obj5);
            this.POSTags.add(obj6);
            this.POSTags.add(obj7);
            this.POSTags.add(obj8);
            this.POSTags.add(obj9);
            this.POSTags.add(obj10);
            this.POSTags.add(obj11);
            this.POSTags.add(obj12);
            this.POSTags.add(obj13);

            this.parse = new ArrayList<>();
            this.pobj1 = new JSONObject();
            this.pobj1.put("dep","ROOT");
            this.pobj1.put("governorGloss","ROOT");
            this.pobj1.put("dependentGloss","what");
            this.pobj2 = new JSONObject();
            this.pobj2.put("dep","cop");
            this.pobj2.put("governorGloss","what");
            this.pobj2.put("dependentGloss","are");
            this.pobj3 = new JSONObject();
            this.pobj3.put("dep","det");
            this.pobj3.put("governorGloss","names");
            this.pobj3.put("dependentGloss","the");
            this.pobj4 = new JSONObject();
            this.pobj4.put("dep","nsubj");
            this.pobj4.put("governorGloss","what");
            this.pobj4.put("dependentGloss","names");
            this.pobj5 = new JSONObject();
            this.pobj5.put("dep","case");
            this.pobj5.put("governorGloss","dogs");
            this.pobj5.put("dependentGloss","of");
            this.pobj6 = new JSONObject();
            this.pobj6.put("dep","det");
            this.pobj6.put("governorGloss","dogs");
            this.pobj6.put("dependentGloss","the");
            this.pobj7 = new JSONObject();
            this.pobj7.put("dep","nmod:of");
            this.pobj7.put("governorGloss","names");
            this.pobj7.put("dependentGloss","dogs");
            this.pobj8 = new JSONObject();
            this.pobj8.put("dep","nmod:cc");
            this.pobj8.put("governorGloss","what");
            this.pobj8.put("dependentGloss","and");
            this.pobj9 = new JSONObject();
            this.pobj9.put("dep","aux");
            this.pobj9.put("governorGloss","run");
            this.pobj9.put("dependentGloss","could");
            this.pobj10 = new JSONObject();
            this.pobj10.put("dep","nsubj");
            this.pobj10.put("governorGloss","run");
            this.pobj10.put("dependentGloss","they");
            this.pobj11 = new JSONObject();
            this.pobj11.put("dep","conj:and");
            this.pobj11.put("governorGloss","what");
            this.pobj11.put("dependentGloss","run");
            this.pobj12 = new JSONObject();
            this.pobj12.put("dep","advmod");
            this.pobj12.put("governorGloss","run");
            this.pobj12.put("dependentGloss","quickly");
            this.pobj13 = new JSONObject();
            this.pobj13.put("dep","amod");
            this.pobj13.put("governorGloss","dogs");
            this.pobj13.put("dependentGloss","big");

            this.parse.add(pobj1);
            this.parse.add(pobj2);
            this.parse.add(pobj3);
            this.parse.add(pobj4);
            this.parse.add(pobj5);
            this.parse.add(pobj6);
            this.parse.add(pobj7);
            this.parse.add(pobj8);
            this.parse.add(pobj9);
            this.parse.add(pobj10);
            this.parse.add(pobj11);
            this.parse.add(pobj12);
            this.parse.add(pobj13);

            String parsed = "{\n" +
                    "   \"docDate\": \"2019-01-26T16:46:19\",\n" +
                    "   \"sentences\": [\n" +
                    "     {\n" +
                    "       \"index\": 0,\n" +
                    "       \"basicDependencies\": [\n" +
                    "         {\n" +
                    "           \"dep\": \"ROOT\",\n" +
                    "           \"governor\": 0,\n" +
                    "           \"governorGloss\": \"ROOT\",\n" +
                    "           \"dependent\": 1,\n" +
                    "           \"dependentGloss\": \"what\"\n" +
                    "         },\n" +
                    "         {\n" +
                    "           \"dep\": \"cop\",\n" +
                    "           \"governor\": 1,\n" +
                    "           \"governorGloss\": \"what\",\n" +
                    "           \"dependent\": 2,\n" +
                    "           \"dependentGloss\": \"are\"\n" +
                    "         },\n" +
                    "         {\n" +
                    "           \"dep\": \"det\",\n" +
                    "           \"governor\": 4,\n" +
                    "           \"governorGloss\": \"names\",\n" +
                    "           \"dependent\": 3,\n" +
                    "           \"dependentGloss\": \"the\"\n" +
                    "         },\n" +
                    "         {\n" +
                    "           \"dep\": \"nsubj\",\n" +
                    "           \"governor\": 1,\n" +
                    "           \"governorGloss\": \"what\",\n" +
                    "           \"dependent\": 4,\n" +
                    "           \"dependentGloss\": \"names\"\n" +
                    "         },\n" +
                    "         {\n" +
                    "           \"dep\": \"case\",\n" +
                    "           \"governor\": 8,\n" +
                    "           \"governorGloss\": \"dogs\",\n" +
                    "           \"dependent\": 5,\n" +
                    "           \"dependentGloss\": \"of\"\n" +
                    "         },\n" +
                    "         {\n" +
                    "           \"dep\": \"det\",\n" +
                    "           \"governor\": 8,\n" +
                    "           \"governorGloss\": \"dogs\",\n" +
                    "           \"dependent\": 6,\n" +
                    "           \"dependentGloss\": \"the\"\n" +
                    "         },\n" +
                    "         {\n" +
                    "           \"dep\": \"amod\",\n" +
                    "           \"governor\": 8,\n" +
                    "           \"governorGloss\": \"dogs\",\n" +
                    "           \"dependent\": 7,\n" +
                    "           \"dependentGloss\": \"big\"\n" +
                    "         },\n" +
                    "         {\n" +
                    "           \"dep\": \"nmod\",\n" +
                    "           \"governor\": 4,\n" +
                    "           \"governorGloss\": \"names\",\n" +
                    "           \"dependent\": 8,\n" +
                    "           \"dependentGloss\": \"dogs\"\n" +
                    "         },\n" +
                    "         {\n" +
                    "           \"dep\": \"cc\",\n" +
                    "           \"governor\": 1,\n" +
                    "           \"governorGloss\": \"what\",\n" +
                    "           \"dependent\": 9,\n" +
                    "           \"dependentGloss\": \"and\"\n" +
                    "         },\n" +
                    "         {\n" +
                    "           \"dep\": \"aux\",\n" +
                    "           \"governor\": 12,\n" +
                    "           \"governorGloss\": \"run\",\n" +
                    "           \"dependent\": 10,\n" +
                    "           \"dependentGloss\": \"could\"\n" +
                    "         },\n" +
                    "         {\n" +
                    "           \"dep\": \"nsubj\",\n" +
                    "           \"governor\": 12,\n" +
                    "           \"governorGloss\": \"run\",\n" +
                    "           \"dependent\": 11,\n" +
                    "           \"dependentGloss\": \"they\"\n" +
                    "         },\n" +
                    "         {\n" +
                    "           \"dep\": \"conj\",\n" +
                    "           \"governor\": 1,\n" +
                    "           \"governorGloss\": \"what\",\n" +
                    "           \"dependent\": 12,\n" +
                    "           \"dependentGloss\": \"run\"\n" +
                    "         },\n" +
                    "         {\n" +
                    "           \"dep\": \"advmod\",\n" +
                    "           \"governor\": 12,\n" +
                    "           \"governorGloss\": \"run\",\n" +
                    "           \"dependent\": 13,\n" +
                    "           \"dependentGloss\": \"quickly\"\n" +
                    "         }\n" +
                    "       ],\n" +
                    "       \"enhancedDependencies\": [\n" +
                    "         {\n" +
                    "           \"dep\": \"ROOT\",\n" +
                    "           \"governor\": 0,\n" +
                    "           \"governorGloss\": \"ROOT\",\n" +
                    "           \"dependent\": 1,\n" +
                    "           \"dependentGloss\": \"what\"\n" +
                    "         },\n" +
                    "         {\n" +
                    "           \"dep\": \"cop\",\n" +
                    "           \"governor\": 1,\n" +
                    "           \"governorGloss\": \"what\",\n" +
                    "           \"dependent\": 2,\n" +
                    "           \"dependentGloss\": \"are\"\n" +
                    "         },\n" +
                    "         {\n" +
                    "           \"dep\": \"det\",\n" +
                    "           \"governor\": 4,\n" +
                    "           \"governorGloss\": \"names\",\n" +
                    "           \"dependent\": 3,\n" +
                    "           \"dependentGloss\": \"the\"\n" +
                    "         },\n" +
                    "         {\n" +
                    "           \"dep\": \"nsubj\",\n" +
                    "           \"governor\": 1,\n" +
                    "           \"governorGloss\": \"what\",\n" +
                    "           \"dependent\": 4,\n" +
                    "           \"dependentGloss\": \"names\"\n" +
                    "         },\n" +
                    "         {\n" +
                    "           \"dep\": \"case\",\n" +
                    "           \"governor\": 8,\n" +
                    "           \"governorGloss\": \"dogs\",\n" +
                    "           \"dependent\": 5,\n" +
                    "           \"dependentGloss\": \"of\"\n" +
                    "         },\n" +
                    "         {\n" +
                    "           \"dep\": \"det\",\n" +
                    "           \"governor\": 8,\n" +
                    "           \"governorGloss\": \"dogs\",\n" +
                    "           \"dependent\": 6,\n" +
                    "           \"dependentGloss\": \"the\"\n" +
                    "         },\n" +
                    "         {\n" +
                    "           \"dep\": \"amod\",\n" +
                    "           \"governor\": 8,\n" +
                    "           \"governorGloss\": \"dogs\",\n" +
                    "           \"dependent\": 7,\n" +
                    "           \"dependentGloss\": \"big\"\n" +
                    "         },\n" +
                    "         {\n" +
                    "           \"dep\": \"nmod:of\",\n" +
                    "           \"governor\": 4,\n" +
                    "           \"governorGloss\": \"names\",\n" +
                    "           \"dependent\": 8,\n" +
                    "           \"dependentGloss\": \"dogs\"\n" +
                    "         },\n" +
                    "         {\n" +
                    "           \"dep\": \"cc\",\n" +
                    "           \"governor\": 1,\n" +
                    "           \"governorGloss\": \"what\",\n" +
                    "           \"dependent\": 9,\n" +
                    "           \"dependentGloss\": \"and\"\n" +
                    "         },\n" +
                    "         {\n" +
                    "           \"dep\": \"aux\",\n" +
                    "           \"governor\": 12,\n" +
                    "           \"governorGloss\": \"run\",\n" +
                    "           \"dependent\": 10,\n" +
                    "           \"dependentGloss\": \"could\"\n" +
                    "         },\n" +
                    "         {\n" +
                    "           \"dep\": \"nsubj\",\n" +
                    "           \"governor\": 12,\n" +
                    "           \"governorGloss\": \"run\",\n" +
                    "           \"dependent\": 11,\n" +
                    "           \"dependentGloss\": \"they\"\n" +
                    "         },\n" +
                    "         {\n" +
                    "           \"dep\": \"conj:and\",\n" +
                    "           \"governor\": 1,\n" +
                    "           \"governorGloss\": \"what\",\n" +
                    "           \"dependent\": 12,\n" +
                    "           \"dependentGloss\": \"run\"\n" +
                    "         },\n" +
                    "         {\n" +
                    "           \"dep\": \"advmod\",\n" +
                    "           \"governor\": 12,\n" +
                    "           \"governorGloss\": \"run\",\n" +
                    "           \"dependent\": 13,\n" +
                    "           \"dependentGloss\": \"quickly\"\n" +
                    "         }\n" +
                    "       ],\n" +
                    "       \"enhancedPlusPlusDependencies\": [\n" +
                    "         {\n" +
                    "           \"dep\": \"ROOT\",\n" +
                    "           \"governor\": 0,\n" +
                    "           \"governorGloss\": \"ROOT\",\n" +
                    "           \"dependent\": 1,\n" +
                    "           \"dependentGloss\": \"what\"\n" +
                    "         },\n" +
                    "         {\n" +
                    "           \"dep\": \"cop\",\n" +
                    "           \"governor\": 1,\n" +
                    "           \"governorGloss\": \"what\",\n" +
                    "           \"dependent\": 2,\n" +
                    "           \"dependentGloss\": \"are\"\n" +
                    "         },\n" +
                    "         {\n" +
                    "           \"dep\": \"det\",\n" +
                    "           \"governor\": 4,\n" +
                    "           \"governorGloss\": \"names\",\n" +
                    "           \"dependent\": 3,\n" +
                    "           \"dependentGloss\": \"the\"\n" +
                    "         },\n" +
                    "         {\n" +
                    "           \"dep\": \"nsubj\",\n" +
                    "           \"governor\": 1,\n" +
                    "           \"governorGloss\": \"what\",\n" +
                    "           \"dependent\": 4,\n" +
                    "           \"dependentGloss\": \"names\"\n" +
                    "         },\n" +
                    "         {\n" +
                    "           \"dep\": \"case\",\n" +
                    "           \"governor\": 8,\n" +
                    "           \"governorGloss\": \"dogs\",\n" +
                    "           \"dependent\": 5,\n" +
                    "           \"dependentGloss\": \"of\"\n" +
                    "         },\n" +
                    "         {\n" +
                    "           \"dep\": \"det\",\n" +
                    "           \"governor\": 8,\n" +
                    "           \"governorGloss\": \"dogs\",\n" +
                    "           \"dependent\": 6,\n" +
                    "           \"dependentGloss\": \"the\"\n" +
                    "         },\n" +
                    "         {\n" +
                    "           \"dep\": \"amod\",\n" +
                    "           \"governor\": 8,\n" +
                    "           \"governorGloss\": \"dogs\",\n" +
                    "           \"dependent\": 7,\n" +
                    "           \"dependentGloss\": \"big\"\n" +
                    "         },\n" +
                    "         {\n" +
                    "           \"dep\": \"nmod:of\",\n" +
                    "           \"governor\": 4,\n" +
                    "           \"governorGloss\": \"names\",\n" +
                    "           \"dependent\": 8,\n" +
                    "           \"dependentGloss\": \"dogs\"\n" +
                    "         },\n" +
                    "         {\n" +
                    "           \"dep\": \"cc\",\n" +
                    "           \"governor\": 1,\n" +
                    "           \"governorGloss\": \"what\",\n" +
                    "           \"dependent\": 9,\n" +
                    "           \"dependentGloss\": \"and\"\n" +
                    "         },\n" +
                    "         {\n" +
                    "           \"dep\": \"aux\",\n" +
                    "           \"governor\": 12,\n" +
                    "           \"governorGloss\": \"run\",\n" +
                    "           \"dependent\": 10,\n" +
                    "           \"dependentGloss\": \"could\"\n" +
                    "         },\n" +
                    "         {\n" +
                    "           \"dep\": \"nsubj\",\n" +
                    "           \"governor\": 12,\n" +
                    "           \"governorGloss\": \"run\",\n" +
                    "           \"dependent\": 11,\n" +
                    "           \"dependentGloss\": \"they\"\n" +
                    "         },\n" +
                    "         {\n" +
                    "           \"dep\": \"conj:and\",\n" +
                    "           \"governor\": 1,\n" +
                    "           \"governorGloss\": \"what\",\n" +
                    "           \"dependent\": 12,\n" +
                    "           \"dependentGloss\": \"run\"\n" +
                    "         },\n" +
                    "         {\n" +
                    "           \"dep\": \"advmod\",\n" +
                    "           \"governor\": 12,\n" +
                    "           \"governorGloss\": \"run\",\n" +
                    "           \"dependent\": 13,\n" +
                    "           \"dependentGloss\": \"quickly\"\n" +
                    "         }\n" +
                    "       ],\n" +
                    "       \"openie\": [],\n" +
                    "       \"entitymentions\": [],\n" +
                    "       \"tokens\": [\n" +
                    "         {\n" +
                    "           \"index\": 1,\n" +
                    "           \"word\": \"what\",\n" +
                    "           \"originalText\": \"what\",\n" +
                    "           \"lemma\": \"what\",\n" +
                    "           \"characterOffsetBegin\": 0,\n" +
                    "           \"characterOffsetEnd\": 4,\n" +
                    "           \"pos\": \"WP\",\n" +
                    "           \"ner\": \"O\",\n" +
                    "           \"before\": \"\",\n" +
                    "           \"after\": \" \"\n" +
                    "         },\n" +
                    "         {\n" +
                    "           \"index\": 2,\n" +
                    "           \"word\": \"are\",\n" +
                    "           \"originalText\": \"are\",\n" +
                    "           \"lemma\": \"be\",\n" +
                    "           \"characterOffsetBegin\": 5,\n" +
                    "           \"characterOffsetEnd\": 8,\n" +
                    "           \"pos\": \"VBP\",\n" +
                    "           \"ner\": \"O\",\n" +
                    "           \"before\": \" \",\n" +
                    "           \"after\": \" \"\n" +
                    "         },\n" +
                    "         {\n" +
                    "           \"index\": 3,\n" +
                    "           \"word\": \"the\",\n" +
                    "           \"originalText\": \"the\",\n" +
                    "           \"lemma\": \"the\",\n" +
                    "           \"characterOffsetBegin\": 9,\n" +
                    "           \"characterOffsetEnd\": 12,\n" +
                    "           \"pos\": \"DT\",\n" +
                    "           \"ner\": \"O\",\n" +
                    "           \"before\": \" \",\n" +
                    "           \"after\": \" \"\n" +
                    "         },\n" +
                    "         {\n" +
                    "           \"index\": 4,\n" +
                    "           \"word\": \"names\",\n" +
                    "           \"originalText\": \"names\",\n" +
                    "           \"lemma\": \"name\",\n" +
                    "           \"characterOffsetBegin\": 13,\n" +
                    "           \"characterOffsetEnd\": 18,\n" +
                    "           \"pos\": \"NNS\",\n" +
                    "           \"ner\": \"O\",\n" +
                    "           \"before\": \" \",\n" +
                    "           \"after\": \" \"\n" +
                    "         },\n" +
                    "         {\n" +
                    "           \"index\": 5,\n" +
                    "           \"word\": \"of\",\n" +
                    "           \"originalText\": \"of\",\n" +
                    "           \"lemma\": \"of\",\n" +
                    "           \"characterOffsetBegin\": 19,\n" +
                    "           \"characterOffsetEnd\": 21,\n" +
                    "           \"pos\": \"IN\",\n" +
                    "           \"ner\": \"O\",\n" +
                    "           \"before\": \" \",\n" +
                    "           \"after\": \" \"\n" +
                    "         },\n" +
                    "         {\n" +
                    "           \"index\": 6,\n" +
                    "           \"word\": \"the\",\n" +
                    "           \"originalText\": \"the\",\n" +
                    "           \"lemma\": \"the\",\n" +
                    "           \"characterOffsetBegin\": 22,\n" +
                    "           \"characterOffsetEnd\": 25,\n" +
                    "           \"pos\": \"DT\",\n" +
                    "           \"ner\": \"O\",\n" +
                    "           \"before\": \" \",\n" +
                    "           \"after\": \" \"\n" +
                    "         },\n" +
                    "         {\n" +
                    "           \"index\": 7,\n" +
                    "           \"word\": \"big\",\n" +
                    "           \"originalText\": \"big\",\n" +
                    "           \"lemma\": \"big\",\n" +
                    "           \"characterOffsetBegin\": 26,\n" +
                    "           \"characterOffsetEnd\": 29,\n" +
                    "           \"pos\": \"JJ\",\n" +
                    "           \"ner\": \"O\",\n" +
                    "           \"before\": \" \",\n" +
                    "           \"after\": \" \"\n" +
                    "         },\n" +
                    "         {\n" +
                    "           \"index\": 8,\n" +
                    "           \"word\": \"dogs\",\n" +
                    "           \"originalText\": \"dogs\",\n" +
                    "           \"lemma\": \"dog\",\n" +
                    "           \"characterOffsetBegin\": 30,\n" +
                    "           \"characterOffsetEnd\": 34,\n" +
                    "           \"pos\": \"NNS\",\n" +
                    "           \"ner\": \"O\",\n" +
                    "           \"before\": \" \",\n" +
                    "           \"after\": \" \"\n" +
                    "         },\n" +
                    "         {\n" +
                    "           \"index\": 9,\n" +
                    "           \"word\": \"and\",\n" +
                    "           \"originalText\": \"and\",\n" +
                    "           \"lemma\": \"and\",\n" +
                    "           \"characterOffsetBegin\": 35,\n" +
                    "           \"characterOffsetEnd\": 38,\n" +
                    "           \"pos\": \"CC\",\n" +
                    "           \"ner\": \"O\",\n" +
                    "           \"before\": \" \",\n" +
                    "           \"after\": \" \"\n" +
                    "         },\n" +
                    "         {\n" +
                    "           \"index\": 10,\n" +
                    "           \"word\": \"could\",\n" +
                    "           \"originalText\": \"could\",\n" +
                    "           \"lemma\": \"could\",\n" +
                    "           \"characterOffsetBegin\": 39,\n" +
                    "           \"characterOffsetEnd\": 44,\n" +
                    "           \"pos\": \"MD\",\n" +
                    "           \"ner\": \"O\",\n" +
                    "           \"before\": \" \",\n" +
                    "           \"after\": \" \"\n" +
                    "         },\n" +
                    "         {\n" +
                    "           \"index\": 11,\n" +
                    "           \"word\": \"they\",\n" +
                    "           \"originalText\": \"they\",\n" +
                    "           \"lemma\": \"they\",\n" +
                    "           \"characterOffsetBegin\": 45,\n" +
                    "           \"characterOffsetEnd\": 49,\n" +
                    "           \"pos\": \"PRP\",\n" +
                    "           \"ner\": \"O\",\n" +
                    "           \"before\": \" \",\n" +
                    "           \"after\": \" \"\n" +
                    "         },\n" +
                    "         {\n" +
                    "           \"index\": 12,\n" +
                    "           \"word\": \"run\",\n" +
                    "           \"originalText\": \"run\",\n" +
                    "           \"lemma\": \"run\",\n" +
                    "           \"characterOffsetBegin\": 50,\n" +
                    "           \"characterOffsetEnd\": 53,\n" +
                    "           \"pos\": \"VB\",\n" +
                    "           \"ner\": \"O\",\n" +
                    "           \"before\": \" \",\n" +
                    "           \"after\": \" \"\n" +
                    "         },\n" +
                    "         {\n" +
                    "           \"index\": 13,\n" +
                    "           \"word\": \"quickly\",\n" +
                    "           \"originalText\": \"quickly\",\n" +
                    "           \"lemma\": \"quickly\",\n" +
                    "           \"characterOffsetBegin\": 54,\n" +
                    "           \"characterOffsetEnd\": 61,\n" +
                    "           \"pos\": \"RB\",\n" +
                    "           \"ner\": \"O\",\n" +
                    "           \"before\": \" \",\n" +
                    "           \"after\": \"\"\n" +
                    "         }\n" +
                    "       ]\n" +
                    "     }\n" +
                    "   ]\n" +
                    " }";
            this.parsedText = new JSONObject(parsed);

        } catch(JSONException e){
            System.out.println("Couldn't create helper object: " + e.getMessage());
        }
        this.englishText = new ArrayList<>();
        this.englishText.add("what");
        this.englishText.add("are");
        this.englishText.add("the");
        this.englishText.add("names");
        this.englishText.add("of");
        this.englishText.add("the");
        this.englishText.add("big");
        this.englishText.add("dogs");
        this.englishText.add("and");
        this.englishText.add("could");
        this.englishText.add("they");
        this.englishText.add("run");
        this.englishText.add("quickly");

        this.englishString = "what are the names of the big dogs and could they run quickly";

        this.vp1 = new VerbPhrase("of","of", false, true, false, new ArrayList<>());
        ArrayList<String> adverbs = new ArrayList<>();
        adverbs.add("quickly");
        this.vp2 = new VerbPhrase("run","run", false, false, false, adverbs);
        this.vp3 = new VerbPhrase("could", "could", true, false, false, new ArrayList<>());

        this.np1 = new NounPhrase("name",true,"the",new ArrayList<>(),"of",true);
        Adjective adj = new Adjective("big", new ArrayList<>());
        ArrayList<Adjective> adjs = new ArrayList<>();
        adjs.add(adj);
        this.np2 = new NounPhrase("dog", true, "the",adjs,"of", false);
        this.np3 = new NounPhrase("they", false,"",new ArrayList<>(), "", false);

        ArrayList<NounPhrase> nps1 = new ArrayList<>();
        nps1.add(np1);
        nps1.add(np2);
        ArrayList<VerbPhrase> vps1 = new ArrayList<>();
        vps1.add(vp1);
        this.c1 = new Clause(0,nps1,vps1,"", "what","",false);

        ArrayList<VerbPhrase> vps2 = new ArrayList<>();
        vps2.add(vp3);
        vps2.add(vp2);
        ArrayList<NounPhrase> nps2 = new ArrayList<>();
        nps2.add(np3);
        this.c2 = new Clause(1,nps2,vps2,"FUTURE","","and",false);

        String[] ordered = new String[]{"MANY", "dog", "big", "MANY", "name", "of", "what", "and", "FUTURE", "they", "could", "quickly", "run"};
        this.parsedSentence = new ArrayList<>(Arrays.asList(ordered));

        this.synResponse = "{\n" +
                "    \"metadata\": {\n" +
                "        \"provider\": \"Oxford University Press\"\n" +
                "    },\n" +
                "    \"results\": [\n" +
                "        {\n" +
                "            \"id\": \"dog\",\n" +
                "            \"language\": \"en\",\n" +
                "            \"lexicalEntries\": [\n" +
                "                {\n" +
                "                    \"entries\": [\n" +
                "                        {\n" +
                "                            \"homographNumber\": \"000\",\n" +
                "                            \"senses\": [\n" +
                "                                {\n" +
                "                                    \"examples\": [\n" +
                "                                        {\n" +
                "                                            \"text\": \"she went for long walks with her dog\"\n" +
                "                                        }\n" +
                "                                    ],\n" +
                "                                    \"id\": \"t_en_gb0004343.001\",\n" +
                "                                    \"subsenses\": [\n" +
                "                                        {\n" +
                "                                            \"id\": \"idccfb77bf-0c24-4713-a84f-ebf42c1071ea\",\n" +
                "                                            \"synonyms\": [\n" +
                "                                                {\n" +
                "                                                    \"id\": \"male_dog\",\n" +
                "                                                    \"language\": \"en\",\n" +
                "                                                    \"text\": \"male dog\"\n" +
                "                                                }\n" +
                "                                            ]\n" +
                "                                        },\n" +
                "                                        {\n" +
                "                                            \"id\": \"id677cfcb8-6e37-4be1-9d58-d5d3581d55d6\",\n" +
                "                                            \"synonyms\": [\n" +
                "                                                {\n" +
                "                                                    \"id\": \"bitch\",\n" +
                "                                                    \"language\": \"en\",\n" +
                "                                                    \"text\": \"bitch\"\n" +
                "                                                },\n" +
                "                                                {\n" +
                "                                                    \"id\": \"pup\",\n" +
                "                                                    \"language\": \"en\",\n" +
                "                                                    \"text\": \"pup\"\n" +
                "                                                },\n" +
                "                                                {\n" +
                "                                                    \"id\": \"puppy\",\n" +
                "                                                    \"language\": \"en\",\n" +
                "                                                    \"text\": \"puppy\"\n" +
                "                                                },\n" +
                "                                                {\n" +
                "                                                    \"id\": \"whelp\",\n" +
                "                                                    \"language\": \"en\",\n" +
                "                                                    \"text\": \"whelp\"\n" +
                "                                                }\n" +
                "                                            ]\n" +
                "                                        },\n" +
                "                                        {\n" +
                "                                            \"id\": \"id1706880e-1528-4539-901f-8e78a90ad1d0\",\n" +
                "                                            \"registers\": [\n" +
                "                                                \"informal\"\n" +
                "                                            ],\n" +
                "                                            \"synonyms\": [\n" +
                "                                                {\n" +
                "                                                    \"id\": \"doggy\",\n" +
                "                                                    \"language\": \"en\",\n" +
                "                                                    \"text\": \"doggy\"\n" +
                "                                                },\n" +
                "                                                {\n" +
                "                                                    \"id\": \"pooch\",\n" +
                "                                                    \"language\": \"en\",\n" +
                "                                                    \"text\": \"pooch\"\n" +
                "                                                },\n" +
                "                                                {\n" +
                "                                                    \"id\": \"mutt\",\n" +
                "                                                    \"language\": \"en\",\n" +
                "                                                    \"text\": \"mutt\"\n" +
                "                                                }\n" +
                "                                            ]\n" +
                "                                        },\n" +
                "                                        {\n" +
                "                                            \"id\": \"id212a1d84-bd0e-41c8-9b02-09eac6a672d7\",\n" +
                "                                            \"regions\": [\n" +
                "                                                \"Australian\"\n" +
                "                                            ],\n" +
                "                                            \"registers\": [\n" +
                "                                                \"informal\"\n" +
                "                                            ],\n" +
                "                                            \"synonyms\": [\n" +
                "                                                {\n" +
                "                                                    \"id\": \"mong\",\n" +
                "                                                    \"language\": \"en\",\n" +
                "                                                    \"text\": \"mong\"\n" +
                "                                                },\n" +
                "                                                {\n" +
                "                                                    \"id\": \"bitzer\",\n" +
                "                                                    \"language\": \"en\",\n" +
                "                                                    \"text\": \"bitzer\"\n" +
                "                                                }\n" +
                "                                            ]\n" +
                "                                        }\n" +
                "                                    ],\n" +
                "                                    \"synonyms\": [\n" +
                "                                        {\n" +
                "                                            \"id\": \"hound\",\n" +
                "                                            \"language\": \"en\",\n" +
                "                                            \"text\": \"hound\"\n" +
                "                                        },\n" +
                "                                        {\n" +
                "                                            \"id\": \"canine\",\n" +
                "                                            \"language\": \"en\",\n" +
                "                                            \"text\": \"canine\"\n" +
                "                                        },\n" +
                "                                        {\n" +
                "                                            \"id\": \"mongrel\",\n" +
                "                                            \"language\": \"en\",\n" +
                "                                            \"text\": \"mongrel\"\n" +
                "                                        },\n" +
                "                                        {\n" +
                "                                            \"id\": \"cur\",\n" +
                "                                            \"language\": \"en\",\n" +
                "                                            \"text\": \"cur\"\n" +
                "                                        },\n" +
                "                                        {\n" +
                "                                            \"id\": \"tyke\",\n" +
                "                                            \"language\": \"en\",\n" +
                "                                            \"text\": \"tyke\"\n" +
                "                                        }\n" +
                "                                    ]\n" +
                "                                }\n" +
                "                            ]\n" +
                "                        }\n" +
                "                    ],\n" +
                "                    \"language\": \"en\",\n" +
                "                    \"lexicalCategory\": \"Noun\",\n" +
                "                    \"text\": \"dog\"\n" +
                "                }\n" +
                "            ],\n" +
                "            \"type\": \"headword\",\n" +
                "            \"word\": \"dog\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";
    }
}
