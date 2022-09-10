package com.hynial.assistant.db.entity;

public class TableColumn {
    public String table;
    public String column;

    public TableColumn(String table, String column) {
        this.table = table;
        this.column = column;
    }

    public String toString() {
        return "\"" + table + "\".\"" + column + "\"";
    }

    public boolean equals(Object obj) {
        if (obj instanceof TableColumn) {
            TableColumn tableColumn = (TableColumn) obj;
            return ((table.equals(tableColumn.table)) && (column.equals(tableColumn.column)));
        } else {
            return false;
        }
    }

    public int hashCode() {
        int hashCode = 1;
        hashCode = 31 * hashCode + (table == null ? 0 : table.hashCode());
        hashCode = 31 * hashCode + (column == null ? 0 : column.hashCode());
        return hashCode;
    }
}