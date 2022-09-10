package com.hynial.assistant.db.entity;

public class KeyReference extends TableColumn {
    public KeyReference(String table, String column) {
        super(table, column);
    }
    public KeyReference(TableColumn tableColumn) {
        this(tableColumn.table, tableColumn.column);
    }
}