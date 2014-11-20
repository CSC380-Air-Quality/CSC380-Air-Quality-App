package com.example.mustard.airqualityapplication;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Mark on 10/19/2014.
 */


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphViewDataInterface;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.LineGraphView;
import com.jjoe64.graphview.ValueDependentColor;

public class GraphTab extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View V = inflater.inflate(R.layout.fragment_graph, container, false);

        GraphViewSeries noSeries = new GraphViewSeries("NO", new GraphViewSeries.GraphViewSeriesStyle(Color.rgb(255, 0, 0), 3), new GraphView.GraphViewData[]{
           new GraphView.GraphViewData(1,1.0d)
           ,new GraphView.GraphViewData(2,1.5d)
           ,new GraphView.GraphViewData(3,2.0d)
           ,new GraphView.GraphViewData(4,2.5d)
           ,new GraphView.GraphViewData(5,1.0d)
        });

        GraphViewSeries coSeries = new GraphViewSeries("CO2", new GraphViewSeries.GraphViewSeriesStyle(Color.rgb(0, 255, 0), 3), new GraphView.GraphViewData[]{
                new GraphView.GraphViewData(1,2.0d)
                ,new GraphView.GraphViewData(2,2.0d)
                ,new GraphView.GraphViewData(3,3.0d)
                ,new GraphView.GraphViewData(4,4.0d)
                ,new GraphView.GraphViewData(5,1.0d)
        });

        GraphViewSeries tempSeries = new GraphViewSeries("Temp", new GraphViewSeries.GraphViewSeriesStyle(Color.rgb(0, 0, 255), 3), new GraphView.GraphViewData[]{
                new GraphView.GraphViewData(1,2.4d)
                ,new GraphView.GraphViewData(2,2.1d)
                ,new GraphView.GraphViewData(3,2.2d)
                ,new GraphView.GraphViewData(4,2.6d)
                ,new GraphView.GraphViewData(5,2.4d)
        });

        GraphViewSeries humidSeries = new GraphViewSeries("Humid", new GraphViewSeries.GraphViewSeriesStyle(Color.rgb(93, 0, 73), 3), new GraphView.GraphViewData[]{
                new GraphView.GraphViewData(1,3.0d)
                ,new GraphView.GraphViewData(2,3.0d)
                ,new GraphView.GraphViewData(3,3.0d)
                ,new GraphView.GraphViewData(4,3.0d)
                ,new GraphView.GraphViewData(5,3.0d)
        });

        GraphView noGraphView = new LineGraphView(getActivity(),"Test");
        /*GraphView coGraphView = new LineGraphView(getActivity(),"Test");
        GraphView tempGraphView = new LineGraphView(getActivity(),"Test");
        GraphView humidGraphView = new LineGraphView(getActivity(),"Test");*/

        noGraphView.addSeries(noSeries);
        /*coGraphView.addSeries(coSeries);
        tempGraphView.addSeries(tempSeries);
        humidGraphView.addSeries(humidSeries);*/

        noGraphView.addSeries(coSeries);
        noGraphView.addSeries(tempSeries);
        noGraphView.addSeries(humidSeries);
        noGraphView.setTitle("");

        noGraphView.setVerticalLabels(new String[]{"High","Med","Low"});
        noGraphView.setHorizontalLabels(new String[]{"", "", ""});
        noGraphView.getGraphViewStyle().setNumHorizontalLabels(0);
        noGraphView.getGraphViewStyle().setNumVerticalLabels(3);
        noGraphView.getGraphViewStyle().setVerticalLabelsWidth(100);
        noGraphView.setShowLegend(true);
        noGraphView.setLegendAlign(GraphView.LegendAlign.TOP);
        noGraphView.setLegendWidth(200);


        /*coGraphView.setVerticalLabels(new String[]{"High","Med","Low"});
        coGraphView.setHorizontalLabels(new String[]{"", "", ""});
        coGraphView.getGraphViewStyle().setNumHorizontalLabels(0);
        coGraphView.getGraphViewStyle().setNumVerticalLabels(3);
        coGraphView.getGraphViewStyle().setVerticalLabelsWidth(100);
        coGraphView.setShowLegend(true);
        coGraphView.setLegendAlign(GraphView.LegendAlign.TOP);
        coGraphView.setLegendWidth(200);


        tempGraphView.setVerticalLabels(new String[]{"High","Med","Low"});
        tempGraphView.setHorizontalLabels(new String[]{"", "", ""});
        tempGraphView.getGraphViewStyle().setNumHorizontalLabels(0);
        tempGraphView.getGraphViewStyle().setNumVerticalLabels(3);
        tempGraphView.getGraphViewStyle().setVerticalLabelsWidth(100);
        tempGraphView.setShowLegend(true);
        tempGraphView.setLegendAlign(GraphView.LegendAlign.TOP);
        tempGraphView.setLegendWidth(200);


        humidGraphView.setVerticalLabels(new String[]{"High","Med","Low"});
        humidGraphView.setHorizontalLabels(new String[]{"", "", ""});
        humidGraphView.getGraphViewStyle().setNumHorizontalLabels(0);
        humidGraphView.getGraphViewStyle().setNumVerticalLabels(3);
        humidGraphView.getGraphViewStyle().setVerticalLabelsWidth(100);
        humidGraphView.setShowLegend(true);
        humidGraphView.setLegendAlign(GraphView.LegendAlign.TOP);
        humidGraphView.setLegendWidth(200);*/

        LinearLayout layout = (LinearLayout) V.findViewById(R.id.graphFragLayout);
        layout.addView(noGraphView);
        //layout.addView(coGraphView);
        //layout.addView(tempGraphView);
        //layout.addView(humidGraphView);

        return V;
    }
}