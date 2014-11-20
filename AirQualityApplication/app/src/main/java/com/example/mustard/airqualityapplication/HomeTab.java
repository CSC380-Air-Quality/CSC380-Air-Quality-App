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

import com.jjoe64.graphview.BarGraphView;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphViewDataInterface;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.LineGraphView;
import com.jjoe64.graphview.ValueDependentColor;

public class HomeTab extends Fragment {
    private boolean inSession;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View V = inflater.inflate(R.layout.fragment_home, container, false);

        inSession = false;
        System.out.println("I WAS CALLED");
        GraphViewSeries.GraphViewSeriesStyle seriesStyle = new GraphViewSeries.GraphViewSeriesStyle();
        seriesStyle.setValueDependentColor(new ValueDependentColor() {
            @Override
            public int get(GraphViewDataInterface data) {
                if(data.getY() >= 75){
                    return Color.rgb(191,0,0);
                }else if(data.getY() >= 50){
                    return Color.rgb(255,140,0);
                }else if(data.getY() >= 25){
                    return Color.rgb(229,229,0);
                }else{
                    return Color.rgb(0,127,0);
                }
                //return Color.rgb((int) (150 + ((data.getY() / 3) * 100)), (int) (150 - ((data.getY() / 3) * 150)), (int) (150 - ((data.getY() / 3) * 150)));
            }
        });
        GraphViewSeries noSeries = new GraphViewSeries("NO",seriesStyle,new GraphView.GraphViewData[]{
        //GraphViewSeries noSeries = new GraphViewSeries(new GraphView.GraphViewData[]{
                new GraphView.GraphViewData(1, 0.0d)
                , new GraphView.GraphViewData(2, 0.0d)
                , new GraphView.GraphViewData(3, 0.0d)
                , new GraphView.GraphViewData(4, 0.0d)
        });

        GraphViewSeries coSeries = new GraphViewSeries("NO",seriesStyle,new GraphView.GraphViewData[]{
                //GraphViewSeries noSeries = new GraphViewSeries(new GraphView.GraphViewData[]{
                new GraphView.GraphViewData(1, 70.0d)
                , new GraphView.GraphViewData(2, 90.0d)
                , new GraphView.GraphViewData(3, 40.0d)
                , new GraphView.GraphViewData(4, 20.0d)
        });

        GraphView graphView = new BarGraphView(getActivity(),"");
        graphView.getGraphViewStyle().setNumHorizontalLabels(4);
        graphView.getGraphViewStyle().setNumVerticalLabels(10);
        graphView.getGraphViewStyle().setVerticalLabelsWidth(100);
        graphView.setManualYMinBound(0);
        graphView.setManualYMaxBound(100.0);


        graphView.setVerticalLabels(new String[]{"High","Med","Low"});
        graphView.setHorizontalLabels(new String[]{"NO","CO2","Temp","Humid"});

        graphView.addSeries(noSeries);
        graphView.addSeries(coSeries);
        LinearLayout layout = (LinearLayout) V.findViewById(R.id.lyGrid);
        if(layout == null){
            System.err.println("null");
        }else {
            layout.addView(graphView);
        }
        //GraphVew graphVew = new LineGraphView(this, "GraphViewDemo");


        return V;
    }

    public void updateBarChart(){
        System.out.println("Stuff");
    }

    public void flipSession(boolean bol){
        this.inSession = bol;
        System.out.println("Stuff");
    }
}
