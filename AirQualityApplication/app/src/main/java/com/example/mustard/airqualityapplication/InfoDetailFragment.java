package com.example.mustard.airqualityapplication;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.mustard.airqualityapplication.info.InfoContent;

import org.w3c.dom.Text;

/**
 * A fragment representing a single Info detail screen.
 * This fragment is either contained in a {@link InfoListActivity}
 * in two-pane mode (on tablets) or a {@link InfoDetailActivity}
 * on handsets.
 */
public class InfoDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private InfoContent.InfoItem mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public InfoDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = InfoContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_info_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (mItem != null) {
            //((TextView) rootView.findViewById(R.id.info_detail)).setText(mItem.strContent + "Some more Text");
            ((TextView)rootView.findViewById(R.id.txtContent)).setText(mItem.strContent);

            if(mItem.strTitle.equals("CSC 380")){
                ((ImageView)rootView.findViewById(R.id.imageView)).setImageResource(R.drawable.blankphoto);

            }else if(mItem.strTitle.equals("SUNY Oswego")){
                ((ImageView)rootView.findViewById(R.id.imageView)).setImageResource(R.drawable.blankphoto);

            }else if(mItem.strTitle.equals("Agostinelli, Brandon")){
                ((ImageView)rootView.findViewById(R.id.imageView)).setImageResource(R.drawable.brandon);

            }else if(mItem.strTitle.equals("Fosmire, Keith")){
                ((ImageView)rootView.findViewById(R.id.imageView)).setImageResource(R.drawable.blankphoto);

            }else if(mItem.strTitle.equals("Piechowicz, Alexander")){
                ((ImageView)rootView.findViewById(R.id.imageView)).setImageResource(R.drawable.blankphoto);

            }else if(mItem.strTitle.equals("Sherwood, Douglas")){
                ((ImageView)rootView.findViewById(R.id.imageView)).setImageResource(R.drawable.blankphoto);

            }else if(mItem.strTitle.equals("Williams, Mark")){
                ((ImageView)rootView.findViewById(R.id.imageView)).setImageResource(R.drawable.mark);

            }
        }

        return rootView;
    }
}
