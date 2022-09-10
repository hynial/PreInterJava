package com.hynial.assistant.db.entity;

import java.util.List;
import java.util.Vector;

public class InsertStatementList {
    public List<InsertStatement> insertStatements = new Vector<InsertStatement>();
    public InsertStatementList() {
    }
    public InsertStatementList(InsertStatementList insertStatementList) {
        this.insertStatements.addAll(insertStatementList.insertStatements);
    }
    public int getTableIndex(String table) {
        int idx = 0;
        for (InsertStatement is: insertStatements) {
            if (is.table.equals(table)) return idx;
            idx++;
        }
        return -1;
    }

    public void add(String table, String column, Object value) {
        int idx = getTableIndex(table);
        InsertStatement is = null;
        if (idx != -1) {
            is = insertStatements.get(idx);
        } else {
            is = new InsertStatement(table);
        }
        is.add(column, value);
        if (idx != -1) {
            insertStatements.set(idx, is);
        } else {
            insertStatements.add(is);
        }
    }
    public void add(TableColumn tableColumn, Object value) {
        add(tableColumn.table, tableColumn.column, value);
    }
}