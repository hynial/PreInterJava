package com.hynial.assistant.db.entity;

import java.sql.ResultSet;

public class ImportedKey {
    public TableColumn primary;
    public TableColumn foreign;
    public ImportedKey(ResultSet rs) throws Exception {
        primary = new TableColumn(rs.getString("PKTABLE_NAME"), rs.getString("PKCOLUMN_NAME"));
        foreign = new TableColumn(rs.getString("FKTABLE_NAME"), rs.getString("FKCOLUMN_NAME"));
    }
    public String toString() {
        return "primary " + primary.toString() + ", foreign " + foreign.toString();
    }
    public boolean equals(Object obj) {
        if (obj instanceof ImportedKey) {
            ImportedKey importedKey = (ImportedKey)obj;
            return ((primary == importedKey.primary) && (foreign == importedKey.foreign));
        } else {
            return false;
        }
    }
    public int hashCode() {
        int hashCode = 1;
        hashCode = 31*hashCode + (primary==null ? 0 : primary.hashCode());
        hashCode = 31*hashCode + (foreign==null ? 0 : foreign.hashCode());
        return hashCode;
    }
}
