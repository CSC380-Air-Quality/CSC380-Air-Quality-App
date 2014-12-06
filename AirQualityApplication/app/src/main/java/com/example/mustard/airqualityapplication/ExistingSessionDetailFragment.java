package com.example.mustard.airqualityapplication;
/*
*Brandon Agostinelli
*Keith Fosmire
*Alexander Piechowicz-Merlizzi
*Douglas Sherwood
*Mark Williams
 */
import android.graphics.Color;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;


import com.example.mustard.airqualityapplication.existingSession.ExistingSessionContent;

/**
 * A fragment representing a single ExistingSession detail screen.
 * This fragment is either contained in a {@link ExistingSessionListActivity}
 * in two-pane mode (on tablets) or a {@link ExistingSessionDetailActivity}
 * on handsets.
 */
public class ExistingSessionDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private ExistingSessionContent.ExistingSessionItem mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ExistingSessionDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = ExistingSessionContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_existingsession_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (mItem != null) {
        //((TextView) rootView.findViewById(R.id.existingsession_detail)).setText(mItem.strTitle);

            //session meta data
            ((TextView) rootView.findViewById(R.id.tvSessionID)).setText("Session ID: " + mItem.id);
            ((TextView) rootView.findViewById(R.id.tvSessionStartTime)).setText("Start Time: " + mItem.strStartTime);
            ((TextView) rootView.findViewById(R.id.tvSessionEndTime)).setText("End Time: " + mItem.strEndTime);


            //location data
            ((TextView) rootView.findViewById(R.id.tvCountry)).setText("Country: " + mItem.strCountry);
            ((TextView) rootView.findViewById(R.id.tvCity)).setText("City: " + mItem.strCity);
            ((TextView) rootView.findViewById(R.id.tvStreet)).setText("Street: " + mItem.strStreet);
            ((TextView) rootView.findViewById(R.id.tvZip)).setText("Zip: " + mItem.strZip);

            //User Note
            ((TextView) rootView.findViewById(R.id.tvUserNote)).setText(mItem.strNote);

            //readings
            TableLayout dataTable = (TableLayout) rootView.findViewById(R.id.dataTable);
            dataTable.setStretchAllColumns(true);
            dataTable.bringToFront();

            TableRow tr = new TableRow(rootView.getContext());
            TextView header1 = new TextView(rootView.getContext());
            TextView header2 = new TextView(rootView.getContext());
            TextView header3 = new TextView(rootView.getContext());
            TextView header4 = new TextView(rootView.getContext());

            header1.setText("NO2");
            header2.setText("CO");
            header3.setText("Temperature");
            header4.setText("Humidity");

            tr.addView(header1);
            tr.addView(header2);
            tr.addView(header3);
            tr.addView(header4);

            dataTable.addView(tr);

            for(int i = 0; i < mItem.alTemp.size(); i++){
                tr = new TableRow(rootView.getContext());
                TextView no2 = new TextView(rootView.getContext());
                TextView co = new TextView(rootView.getContext());
                TextView hum = new TextView(rootView.getContext());
                TextView temp = new TextView(rootView.getContext());

                no2.setText(mItem.alNo.get(i).toString());
                co.setText(mItem.alCo.get(i).toString());
                temp.setText(mItem.alTemp.get(i).toString());
                hum.setText(mItem.alHum.get(i).toString());

                tr.addView(no2);
                tr.addView(co);
                tr.addView(temp);
                tr.addView(hum);

                if((i & 1) == 0){
                    tr.setBackgroundResource(R.color.gray);
                }

                dataTable.addView(tr);


            }


        }

        return rootView;
    }
}
