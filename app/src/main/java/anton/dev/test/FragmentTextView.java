package anton.dev.test;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import static anton.dev.test.MainActivity.TEXTDATA;


public class FragmentTextView extends Fragment {

    private final String LOG_FRAGMENT_TEXT_VIEW = "Log fragment text view";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_text_view_fragment, container, false);
        String dataFromJson = getArguments().getString(TEXTDATA);
        Log.d(LOG_FRAGMENT_TEXT_VIEW, dataFromJson + " data for text view");
        TextView textView = view.findViewById(R.id.textViewFragment);
        textView.setText(dataFromJson);
        return view;
    }
}
