package anton.dev.test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private int count = 1;
    public static final String TEXTDATA = "textData";
    public static final String WEBDATA = "webData";
    private final String LOG_MAIN = "Log main activity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(LOG_MAIN, "start async task");
        new MyTask().execute();

    }

    public class MyTask extends AsyncTask<Void, Void, Map<Integer, Map<JSONUtils.TypeView, String>>> {

        @Override
        protected Map<Integer, Map<JSONUtils.TypeView, String>> doInBackground(Void... voids) {
            JSONUtils jsonUtils = new JSONUtils();
            try {
                List<Integer> integerList = jsonUtils.getResponseA();
                Map<Integer, Map<JSONUtils.TypeView, String>> mapMap = jsonUtils.getResponseB(integerList);

                return mapMap;

            } catch (IOException e) {
                Log.d(LOG_MAIN, e.getMessage() + " in doInBackground");
                throw new RuntimeException();
            }
        }


        @Override
        protected void onPostExecute(final Map<Integer, Map<JSONUtils.TypeView, String>> integerMapMap) {

            final Button button = findViewById(R.id.button);

            final List<Integer> integerList = new ArrayList<>(integerMapMap.keySet());


            button.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (count <= integerMapMap.size()) {
                        Integer number = integerList.get(count - 1);
                        setDataOnFragment(integerMapMap.get(number));
                        count++;
                    } else {
                        count = 1;
                        Integer number = integerList.get(count - 1);
                        setDataOnFragment(integerMapMap.get(number));
                        count++;
                    }

                }

            });
        }


        /**
         * Determining the type of object and setting value to the appropriate fragments
         * @param map
         */
        private void setDataOnFragment(Map<JSONUtils.TypeView, String> map) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            Set<JSONUtils.TypeView> typeViews = map.keySet();
            for (JSONUtils.TypeView typeView : typeViews) {
                switch (typeView) {
                    case textView:
                        Bundle bundle = new Bundle();
                        String data = map.get(typeView);
                        bundle.putString(TEXTDATA, data);
                        FragmentTextView fragmentTextView = new FragmentTextView();
                        fragmentTextView.setArguments(bundle);
                        fragmentTransaction.replace(R.id.media_actions, fragmentTextView);
                        fragmentTransaction.commit();
                        break;
                    case webView:
                        Bundle bundleWeb = new Bundle();
                        String dataWeb = map.get(typeView);
                        System.out.println(dataWeb);
                        bundleWeb.putString(WEBDATA, dataWeb);
                        FragmentWebView fragmentWebView = new FragmentWebView();
                        fragmentWebView.setArguments(bundleWeb);
                        fragmentTransaction.replace(R.id.media_actions, fragmentWebView);
                        fragmentTransaction.commit();
                        break;
                }

            }
        }
    }

}



