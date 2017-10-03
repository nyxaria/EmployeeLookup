package com.nyxaria.apps.Model;

import com.nyxaria.apps.Controller.ExcellWriter;
import com.nyxaria.apps.Main;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DataHandler {

    private Main main;

    public DataHandler(Main main) {
        this.main = main;
    }

    public HashMap<String, String>[] query(HashMap<String, JTextField> fields) {
        HashMap<String, String>[] raw = main.dbHandler.getData();
        ArrayList<HashMap<String, String>> processed = new ArrayList<HashMap<String, String>>();

        if (raw != null) {
            for (Map.Entry<String, JTextField> field : fields.entrySet()) {
                for (HashMap<String, String> map : raw) {
                    if (map.containsKey(field.getKey())) {
                        if (!processed.contains(map) && !field.getValue().getText().equals("")) {
                            if (map.get(field.getKey()).toLowerCase().contains(field.getValue().getText().trim().toLowerCase())) {
                                processed.add(map);
                            }
                        }
                    }
                }
            }
        }
        if (processed.size() == 0) return raw;

        return processed.toArray(new HashMap[0]);
    }

    public void updateUI(HashMap<String, JTextField> fields) {

        HashMap<String, String>[] query = query(fields);

        main.dataFrame.dataPane.populate(getColumns(), processHashmap(query));
    }

    public String[][] processHashmap(HashMap<String, String>[] raw) {
        String[][] out = new String[raw.length][getColumns().length];

        int r = 0;
        for (HashMap<String, String> map : raw) {
            out[r][0] = map.get("name");
            out[r][1] = map.get("region").substring(0, 1).toUpperCase() + map.get("region").substring(1);
            out[r][2] = map.get("department");
            out[r][3] = map.get("age");
            out[r++][4] = map.get("sex");
        }

        return out;
    }

    public String[] getColumns() {
        return main.dbHandler.getColumnNames();
    }

    public void export(JTable table, String path, String name) {
        ExcellWriter writer = new ExcellWriter(table, null, name);
//optional -> tte.setCustomTitles(colTitles);
        try {
            writer.generate(new File(path + "/" + name + ".xlsx"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
