package c.example.speechtobsl.models;

import c.example.speechtobsl.outer_framework.Client;

/**
 * Gets a Stanford parse of the English sentence from a server
 */
public class StanfordParserModel {

    Client client;
    String parsedResponse;

    /**
     * Instantiates a new Stanford parser model.
     */
    public StanfordParserModel() {
        this.client = new Client();
        this.parsedResponse = null;
    }

    /**
     * Gets Stanford parse of sentence.
     *
     * @param speech the speech
     * @return the parse
     */
    public String getParse(String speech) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                parsedResponse = client.sendRequest(new String[]{
                        "POST",
                        "http://192.168.0.15",
                        "9000",
                        "/?properties=%7B%22annotators%22%3A%20%22tokenize%2Cssplit%2Cpos%2Cner%2Cdepparse%2Copenie%22%2C%20%22date%22%3A%20%222019-01-26T16%3A46%3A19%22%7D",
                        speech}
                );
            }
        });
        try {
            thread.start();
            thread.join();
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        return this.parsedResponse;
    }
}
