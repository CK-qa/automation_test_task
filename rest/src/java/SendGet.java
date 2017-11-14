import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class SendGet {
    public static final String basUrl = "http://ntanygin.pythonanywhere.com/?temperature=";
    public static int responseCode;
    public static String body;

    public static void sendGet(String temp) throws IOException {
        URL url = new URL(null, basUrl + temp, new sun.net.www.protocol.http.Handler());
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("GET");

        responseCode = connection.getResponseCode();

        InputStream inp = connection.getInputStream();
        String encoding = connection.getContentEncoding();
        encoding = encoding == null ? "UTF-8" : encoding;
        body = IOUtils.toString(inp, encoding);
        inp.close();
    }


}
