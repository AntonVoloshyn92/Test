package anton.dev.test;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static anton.dev.test.TypeResponse.*;

public class JSONUtils {

    private String LOG_JSONUtils = "LOG_JSONUtils ";
    private final String URLA = "https://demo0040494.mockable.io/api/v1/trending";
    private final String URLB = "https://demo0040494.mockable.io/api/v1/object/";

    /**
     * receives data from the server and at the url path specified in the local variable of this class as URLA
     *
     * @return List<Integer>
     */
    public List<Integer> getResponseA() {

        String response = responseFromURL(URLA);
        List<Integer> listId = new ArrayList<>();
        Log.d(LOG_JSONUtils, listId.size() + " count size object from A");

        if (!"".equals(response) && !response.isEmpty()) {
            try {
                JSONArray jsonArray = new JSONArray(response);
                for (int i = 0; i < jsonArray.length(); i++) {
                    if (jsonArray.get(i) instanceof JSONObject) {
                        JSONObject jsnObj = (JSONObject) jsonArray.get(i);
                        int idValue = jsnObj.getInt("id");
                        listId.add(idValue);
                    }
                }
            } catch (JSONException e) {
                Log.d(LOG_JSONUtils, " JSONException in getResponseA");
                throw new RuntimeException();
            }
        }


        return listId;
    }


    /**
     * receives data from the server and at the url path specified in the local variable of this class as URLB
     *
     * @param integerList
     * @return Map<Integer, Map < TypeView, String>>
     * @throws IOException
     */
    public Map<Integer, Map<TypeView, String>> getResponseB(List<Integer> integerList) throws IOException {

        Map<Integer, Map<TypeView, String>> integerMapHashMap = new HashMap<>();

        Log.d(LOG_JSONUtils, "map size from B " + integerList.size());

        for (Integer integer : integerList) {

            String response = responseFromURL(URLB + integer);

            if (!"".equals(response) && !response.isEmpty()) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String typeJSON = jsonObject.getString("type");
                    TypeResponse typeResponse = switchTypeResponse(typeJSON);

                    if (typeResponse != null) {

                        switch (typeResponse) {

                            case text:
                                Log.d(LOG_JSONUtils, "type response from B text");
                                Map<TypeView, String> mapText = new HashMap<>();
                                mapText.put(TypeView.textView, jsonObject.getString("contents"));
                                integerMapHashMap.put(integer, mapText);
                                break;

                            case webview:
                                Log.d(LOG_JSONUtils, "type response from B web");
                                Map<TypeView, String> mapWeb = new HashMap<>();
                                mapWeb.put(TypeView.webView, jsonObject.getString("url"));
                                integerMapHashMap.put(integer, mapWeb);
                                break;

                            case game:
                                Log.d(LOG_JSONUtils, "type response from B game");
                                break;
                        }
                    }

                } catch (JSONException e) {
                    Log.d(LOG_JSONUtils, e.getMessage() + " JSONException in getResponseB");
                    throw new RuntimeException();
                }
            }
        }
        return integerMapHashMap;
    }


    /**
     * returns a response string from the server to the exact address that you pass to this
     *
     * @param stringUrl
     * @return String
     */
    private String responseFromURL(String stringUrl) {
        StringBuilder stringBuilder = null;
        try {
            HttpURLConnection urlConnection = (HttpURLConnection) new URL(stringUrl).openConnection();
            Log.d(LOG_JSONUtils, "urlConnectionResponseMessage " + urlConnection.getResponseMessage());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            stringBuilder = new StringBuilder();
            while (true) {
                String line = bufferedReader.readLine();
                if (line == null) {
                    break;
                }
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            Log.d(LOG_JSONUtils, e.getMessage() + " Exception in responseFromURL");
            throw new RuntimeException();
        }
        Log.d(LOG_JSONUtils, " response from URL " + stringBuilder.toString());
        return stringBuilder.toString();
    }

    private TypeResponse switchTypeResponse(String typeResponse) {
        if (typeResponse.equals(text.name())) {
            return text;
        }
        if (typeResponse.equals(webview.name())) {
            return webview;
        }
        if (typeResponse.equals(game.name())) {
            return game;
        }
        return null;
    }

    public enum TypeView {
        textView,
        webView
    }
}
