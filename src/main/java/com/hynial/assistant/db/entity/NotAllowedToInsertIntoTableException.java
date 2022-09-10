package com.hynial.assistant.db.entity;

public class NotAllowedToInsertIntoTableException extends Exception {
    String table;
    public NotAllowedToInsertIntoTableException(String table) {
        super(table);
        this.table = table;
    }
}
