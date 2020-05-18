package anton.dev.test;

import androidx.lifecycle.ViewModelProviders;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import static anton.dev.test.MainActivity.WEBDATA;

public class FragmentWebView extends Fragment {

    private final String LOG_FRAGMENT_WEB_VIEW = "Log fragment in web view";

    @SuppressLint("SetJavaScriptEnabled")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_web_view_fragment, container, false);
        String urlFromJSON = getArguments().getString(WEBDATA);
        WebView webView = view.findViewById(R.id.webViewInFragment);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(urlFromJSON);
        return view;
    }
}
