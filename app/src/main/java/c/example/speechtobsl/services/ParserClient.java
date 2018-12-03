package c.example.speechtobsl.services;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class ParserClient {

    private static final String BASE_URL = "http://corenlp.run/";
    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void post(RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(BASE_URL, params, responseHandler);
    }
}
