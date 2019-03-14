package c.example.speechtobsl.outer_framework;

/**
 * The interface Success listener - implemented by Speech Activity
 */
public interface EndListener {

    /**
     * Called once sentence has been translated - ready to show images
     */
    void onSuccess();

    /**
     * Called if sentence wasn't able to be translated
     */
    void onFailure();
}
