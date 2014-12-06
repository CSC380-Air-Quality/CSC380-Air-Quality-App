package com.example.mustard.airqualityapplication;
/*
*Brandon Agostinelli
*Keith Fosmire
*Alexander Piechowicz-Merlizzi
*Douglas Sherwood
*Mark Williams
 */
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.mustard.airqualityapplication.settings.SettingsContent;

/**
 * A fragment representing a single Settings detail screen.
 * This fragment is either contained in a {@link SettingsListActivity}
 * in two-pane mode (on tablets) or a {@link SettingsDetailActivity}
 * on handsets.
 */
public class SettingsDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private SettingsContent.SettingsItem mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public SettingsDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = SettingsContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_settings_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (mItem != null) {
            //((TextView) rootView.findViewById(R.id.settings_detail)).setText(mItem.content);
        }

        return rootView;
    }
}
