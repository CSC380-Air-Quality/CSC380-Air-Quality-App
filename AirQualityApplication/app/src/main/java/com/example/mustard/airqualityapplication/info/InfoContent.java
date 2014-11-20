package com.example.mustard.airqualityapplication.info;

import android.content.Context;
import android.media.Image;
import android.widget.ImageView;

import com.example.mustard.airqualityapplication.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class InfoContent {

    public static List<InfoItem> ITEMS = new ArrayList<InfoItem>();

    /**
     * A map of sample items, by ID.
     */
    public static Map<String, InfoItem> ITEM_MAP = new HashMap<String, InfoItem>();

    static {
        // Add 3 sample items.
        addItem(new InfoItem("1", "SUNY Oswego"));
        addItem(new InfoItem("2", "CSC 380"));
        addItem(new InfoItem("3", "Agostinelli, Brandon"));
        addItem(new InfoItem("4", "Fosmire, Keith"));
        addItem(new InfoItem("5", "Piechowicz, Alexander"));
        addItem(new InfoItem("6", "Sherwood, Douglas"));
        addItem(new InfoItem("7", "Williams, Mark"));
    }

    private static void addItem(InfoItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class InfoItem {
        public String id;
        public String strTitle;
        public String strContent;
        public ImageView photo;

        public InfoItem(String id, String title) {
            this.id = id;
            this.strTitle = title;

        }

        @Override
        public String toString() {
            return strTitle;
        }

        public void setContent(String strIn){
            this.strContent = strIn;
        }

        public void setImage(ImageView imageIn){
            this.photo = imageIn;
        }
    }
}
