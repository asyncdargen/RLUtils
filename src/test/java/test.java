import javax.net.ssl.HttpsURLConnection;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class test {

    public static void main(String[] args) {

        ScriptEngineManager m = new ScriptEngineManager();
        ScriptEngine e = m.getEngineByName("js");
        m.getEngineFactories().forEach(f -> {
            System.out.println(f.getNames());
        });
    }
}
