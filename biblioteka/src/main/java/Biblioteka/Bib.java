package Biblioteka;

import org.json.JSONObject;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * Created by marta on 28.05.14.
 */
public class Bib {
    public Bib() {
        JSONObject json = new JSONObject();
    }

    public static void main(String[] args) {
        Bib b = new Bib();
//        try {
//            json.put("login", "marta");
//            json.put("haslo", "abc");
//        }
//        catch (Exception e){
//
//        }
//        try {
//            URL url = new URL("http://192.168.43.5:8000/user/user/get_seed/");
//            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
//            conn.setRequestMethod("POST");
//            conn.setDoOutput(true);
//            conn.getOutputStream().write(json.toString().getBytes());
//        } catch (Exception e) {
//
//        }
    }
}
