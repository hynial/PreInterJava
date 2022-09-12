package com.hynial.assistant.db.entity;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class InsertStatement {
    public String table;
    public Map<String, Object> columnValues = new HashMap<String, Object>();

    public InsertStatement() {
    }

    public InsertStatement(String table) {
        this.table = table;
    }

    public void add(String column, Object value) {
        columnValues.put(column, value);
    }

    public String toSql(Map<KeyReference, Object> keys) throws Exception {
        String columns = null;
        String values = null;
        for (Map.Entry<String, Object> columnValue : columnValues.entrySet()) {
            String column = columnValue.getKey();
            if (columns == null) columns = column;
            else columns += ", " + column;
            Object value = columnValue.getValue();
            String string = null;
            if (value instanceof String) {
                string = (String) value;
            } else if (value instanceof KeyReference) {
                KeyReference keyReference = (KeyReference) value;
                if (keys.containsKey(keyReference) == false || (keys.get(keyReference) instanceof String) == false) {
                    throw new UnhandledCaseException();
                }
                string = (String) keys.get(keyReference);
            } else {
                throw new UnhandledCaseException();
            }
            if (values == null) values = string;
            else values += ", " + string;
        }
        if (columns != null && values != null) {
            return "insert into " + table + " (" + columns + ") values (" + values + ")";
        } else {
            return "insert into " + table + " () values ()";
        }
    }

    public Map<String, String> columnValues2 = new LinkedHashMap<String, String>();

    public String toString(Map<String, String> tableColumnValueMap) {
        String columns = null;
        String values = null;
        for (Map.Entry<String, String> columnValue : columnValues2.entrySet()) {
            String column = columnValue.getKey();
            String value = columnValue.getValue();

            if (value.startsWith("!@#$%") && tableColumnValueMap != null) {
                String key = value.substring("!@#$%".length());
                value = tableColumnValueMap.get(key);
                if (value == null) {
                    System.out.println("Cannot find tableColumnValueMap key \"" + key + "\"");
                    System.out.println("tableColumnMap:");
                    for (Map.Entry<String, String> tableColumnValueMapEntry : tableColumnValueMap.entrySet()) {
                        System.out.println("\t\"" + tableColumnValueMapEntry.getKey() + "\" = \"" + tableColumnValueMapEntry.getValue() + "\"");
                    }
                    try {
                        throw new Exception("Unhandled case!!!");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    System.exit(1);
                }
            }

            if (columns == null) columns = column;
            else columns += ", " + column;
            if (values == null) values = value;
            else values += ", " + value;
        }

        // http://wiki.openmrs.org/display/archive/UUIDs
//        if (!table.equals("patient")) {
//            String column = "uuid";
//            String value = "uuid()";
//
//            if (columns == null) columns = column;
//            else columns += ", " + column;
//            if (values == null) values = value;
//            else values += ", " + value;
//        }

        return "insert into " + table + " (" + columns + ") values (" + values + ")";
    }
}