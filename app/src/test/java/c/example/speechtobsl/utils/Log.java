package c.example.speechtobsl.utils;

public class Log {
    public static int i(String tag, String msg) {
        System.out.println("INFO: " + tag + ": " + msg);
        return 0;
    }
}