package com.hynial.assistant.db;

import com.hynial.assistant.db.entity.*;

import java.sql.*;
import java.util.*;

public class SqlGenerator {
    public static final String tablesIntoWhichWeAreAllowedToInsert[] = {
            "patient", "person"
    };

    public static boolean isDebugEnabled() {
        return true;
    }

    public static final void debug(String string) {
        System.out.println(string);
    }

    public static void insertIntoDatabase(Connection conn,
                                          Map<TableColumn, Object> paramMap,
                                          Map<TableColumn, Object> given) throws Exception {
        InsertStatementList insertStatements = new InsertStatementList();

        // Make a copy of the map
        Map<TableColumn, Object> map = new HashMap<TableColumn, Object>();
        map.putAll(paramMap);

        // Add map elements
        for (Map.Entry<TableColumn, Object> mapElement : map.entrySet()) {
            TableColumn tableColumn = mapElement.getKey();
            Object value = mapElement.getValue();
            insertStatements.add(tableColumn.table, tableColumn.column, value);
        }

        // Try to satisfy foreign key constraints
        DatabaseMetaData dmd = conn.getMetaData();
        boolean satisfiedConstraints = false;
        do {

            debug("---");

            //
            // What imported keys do we need?
            //

            // Avoid ConcurrentModificationException
            InsertStatementList savedInsertStatements = new InsertStatementList(insertStatements);

            List<ImportedKey> importedKeys = new Vector<ImportedKey>();
            for (InsertStatement is : savedInsertStatements.insertStatements) {

                // Find not nullable columns
                List<TableColumn> notNullable = new Vector<TableColumn>();
                ResultSet rs = dmd.getColumns(null, null, is.table, "");
                debug("Not nullable columns:");
                while (rs.next()) {
                    if (rs.getString("IS_NULLABLE").equals("NO")) {
                        TableColumn tableColumn = new TableColumn(rs.getString("TABLE_NAME"), rs.getString("COLUMN_NAME"));
                        notNullable.add(tableColumn);
                        debug("\t" + tableColumn.toString());
                    }

                    // If we have uuid column and none in insert
                    // http://wiki.openmrs.org/display/archive/UUIDs
                    if (rs.getString("COLUMN_NAME").equals("uuid") &&
                            is.columnValues.containsValue("uuid") == false) {
                        insertStatements.add(is.table, "uuid", "uuid()");
                    }
                }

                // Find imported keys ...
                rs = dmd.getImportedKeys(null, null, is.table);
                debug("Imported keys:");
                while (rs.next()) {

                    ImportedKey importedKey = new ImportedKey(rs);

                    // ... if in given, just add
                    if (given.containsKey(importedKey.primary)) {
                        insertStatements.add(importedKey.foreign, given.get(importedKey.primary));
                    }
                    // ... if not in map and not nullable need to insert table
                    else if (map.containsKey(importedKey.primary) == false &&
                            notNullable.contains(importedKey.foreign) == true) {
                        importedKeys.add(importedKey);
                        debug("\t" + importedKey.toString());
                    }
                }
            }

            //
            // Try to add tables
            //

            for (ImportedKey ik : importedKeys) {
                TableColumn primary = ik.primary;
                if (primary.column.equals(primary.table + "_id") == false) {
                    throw new UnhandledCaseException();
                }
                if (Arrays.asList(tablesIntoWhichWeAreAllowedToInsert).contains(primary.table) == false) {
                    throw new NotAllowedToInsertIntoTableException(primary.table);
                }

                // Add to map for reference
                map.put(primary, null);

                // Add to insert statement for table
                insertStatements.add(ik.foreign.table, ik.foreign.column, new KeyReference(ik.primary));

                // Add to top of insert statements
                insertStatements.insertStatements.add(0, new InsertStatement(primary.table));
            }

            //
            // Done?
            //

            if (importedKeys.isEmpty()) {
                satisfiedConstraints = true;
            }
        } while (satisfiedConstraints == false);

        //
        // Insert into database
        //

        debug("* --- *");

        // Keys
        Map<KeyReference, Object> keys = new HashMap<KeyReference, Object>();

        for (InsertStatement is : insertStatements.insertStatements) {
            Statement s = conn.createStatement();
            String sql = is.toSql(keys);
            debug(sql);

            s.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = s.getGeneratedKeys();
            if (!rs.next()) {
                throw new UnhandledCaseException();
            }

            keys.put(new KeyReference(is.table, is.table + "_id"), rs.getString(1));

            rs.close();
            s.close();
        }
    }

    public static void main(String[] args) {
        Connection conn = null;
        boolean commit = true;
        try {
            String userName = "root";
            String password = "glory2020";
            String url = "jdbc:mysql://localhost:3306/diy";
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection(url, userName, password);
            conn.setAutoCommit(false);

            // Test data
            Map<TableColumn, Object> map = new HashMap<TableColumn, Object>();
            map.put(new TableColumn("ncc_currency", "code"), "1234");
            map.put(new TableColumn("ncc_currency", "name"), "'Bob'");

            map.put(new TableColumn("ncc_currency2", "code"), "'NO.123'");
            map.put(new TableColumn("ncc_currency2", "name"), "'Ben'");

            // Given columns
            Map<TableColumn, Object> given = new HashMap<TableColumn, Object>();
//            given.put(new TableColumn("users", "user_id"), "1");

            insertIntoDatabase(conn, map, given);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    if (commit) {
                        conn.commit();
                    } else {
                        conn.rollback();
                    }
                    conn.close();
                } catch (Exception e) { /* ignore close errors */ }
            }
        }
    }
}
