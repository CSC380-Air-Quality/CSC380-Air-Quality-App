package com.example.mustard.airqualityapplication.settings;

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
public class SettingsContent {

    public static List<SettingsItem> ITEMS = new ArrayList<SettingsItem>();

    /**
     * A map of sample items, by ID.
     */
    public static Map<String, SettingsItem> ITEM_MAP = new HashMap<String, SettingsItem>();

    static {
        // Add 3 sample items.
        addItem(new SettingsItem("1", "Duration"));

    }

    private static void addItem(SettingsItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    public static class SettingsItem {
        public String id;
        public String content;

        public SettingsItem(String id, String content) {
            this.id = id;
            this.content = content;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}
