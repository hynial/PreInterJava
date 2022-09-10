package com.hynial.assistant.db;

import com.hynial.assistant.db.entity.InsertStatement;
import com.hynial.assistant.db.entity.TableColumn;

import java.sql.*;
import java.util.*;

public class SqlGenerator2 {

    // constraintMap<<table, Map<column, referencedTable>>
    static Map<String, Map<String, TableColumn>> constraintMap = new HashMap<String, Map<String, TableColumn>>();

    static String columnsWeDoNotFillInAutomatically[] = {
            "voided_by"
    };

    static void insertIntoDatabaseHelper(Connection conn,
                                         Map<String, Map<String, String>> columnValueMapByTable,
                                         Map<TableColumn, String> given) {
        Vector<InsertStatement> insertStatements = new Vector<InsertStatement>();

        // Output
        System.out.println("columnValueMapByTable:");
        for (Map.Entry<String, Map<String, String>> entry : columnValueMapByTable.entrySet()) {
            String table = (String) entry.getKey();
            System.out.println(table);
            Map<String, String> columnValueMap = (Map<String, String>) entry.getValue();
            if (columnValueMap != null) {
                for (Map.Entry<String, String> columnValue : columnValueMap.entrySet()) {
                    String column = columnValue.getKey();
                    String value = columnValue.getValue();
                    System.out.println("\t" + column + " = \"" + value + "\"");
                }
            }
        }
        System.out.println("");

        List<String> insertedTables = new Vector<String>();

        for (Map.Entry<String, Map<String, String>> entry : columnValueMapByTable.entrySet()) {
            InsertStatement is = new InsertStatement();
            is.table = (String) entry.getKey();

            Map<String, TableColumn> constraintColumnValueMap = constraintMap.get(is.table);
            Map<String, String> columnValueMap = entry.getValue();

            // Create statement
            if (columnValueMap != null) {
                for (Map.Entry<String, String> columnValue : columnValueMap.entrySet()) {
                    String column = columnValue.getKey();
                    String value = "'" + columnValue.getValue() + "'";

                    if (constraintColumnValueMap != null && constraintColumnValueMap.containsKey(column)) {
                        System.out.println("Constrained columns should never be present in form data");
                        System.exit(1);
                    }

                    is.columnValues2.put(column, value);
                }
            }

            if (constraintColumnValueMap != null) {
                // Find given columns in constrained columns - add those
                Map<String, TableColumn> remainingConstraints = new HashMap<String, TableColumn>();
                for (Map.Entry<String, TableColumn> columnTableColumn : constraintColumnValueMap.entrySet()) {
                    String column = columnTableColumn.getKey();
                    TableColumn tableColumn = columnTableColumn.getValue();
                    if (Arrays.asList(columnsWeDoNotFillInAutomatically).contains(column)) {
                        // don't fill in automatically
                    } else if (given.containsKey(tableColumn)) {
                        String givenValue = given.get(tableColumn);

                        is.columnValues2.put(column, givenValue);
                    } else {
                        remainingConstraints.put(column, tableColumn);
                    }
                }
                constraintColumnValueMap = remainingConstraints;

                // Remove columns named table_id for each table in map
                remainingConstraints = new HashMap<String, TableColumn>();
                for (Map.Entry<String, TableColumn> columnTableColumn : constraintColumnValueMap.entrySet()) {

                    String column = columnTableColumn.getKey();
                    TableColumn tableColumn = columnTableColumn.getValue();
                    if (columnValueMapByTable.containsKey(tableColumn.table) &&
                            tableColumn.column.equals(tableColumn.table + "_id")) {
                        if (!insertedTables.contains(tableColumn.table)) {

                            // Is table included at all
                            if (!columnValueMapByTable.containsKey(tableColumn.table)) {
                                try {
                                    throw new Exception("Unhandled case!!!");
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                System.out.println("tableColumn: " + tableColumn);
                                System.exit(1);
                            } else {

                                Map<String, Map<String, String>> newMap = new LinkedHashMap<String, Map<String, String>>();
                                for (Map.Entry<String, Map<String, String>> newEntry : columnValueMapByTable.entrySet()) {
                                    String table = newEntry.getKey();
                                    Map<String, String> map = newEntry.getValue();

                                    if (!table.equals(is.table) && !table.equals(tableColumn.table)) {
                                        // preserve order
                                        newMap.put(table, map);
                                    } else if (table.equals(is.table)) {
                                        newMap.put(tableColumn.table, columnValueMapByTable.get(tableColumn.table));
                                        newMap.put(table, map);
                                    }
                                }
                                insertIntoDatabaseHelper(conn, newMap, given);
                                return;
                            }

                            System.out.println("insertedTables:");
                            for (String table : insertedTables) {
                                System.out.println("\t" + table);
                            }
                        } else {
                            String value = "!@#$%" + tableColumn.toString();

                            is.columnValues2.put(column, value);
                        }
                    } else {
                        remainingConstraints.put(column, tableColumn);
                    }
                }
                constraintColumnValueMap = remainingConstraints;
            }

            // Any constrained columns? If so, add those tables to map (empty) and restart
            // (ordered map)
            if (constraintColumnValueMap != null &&
                    !constraintColumnValueMap.isEmpty()) {
                Map<String, Map<String, String>> newMap = new LinkedHashMap<String, Map<String, String>>();
                for (Map.Entry<String, TableColumn> columnTableColumn : constraintColumnValueMap.entrySet()) {
                    String column = columnTableColumn.getKey();
                    TableColumn tableColumn = columnTableColumn.getValue();
                    System.out.println("\tConstraint: " + column + " = \"" + tableColumn.toString() + "\"");

                    if (!newMap.containsKey(tableColumn.table)) {
                        newMap.put(tableColumn.table, null);
                    } else {
                        try {
                            throw new Exception("Unhandled case!!!");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        System.exit(1);
                    }
                }
                newMap.putAll(columnValueMapByTable);
                insertIntoDatabaseHelper(conn, newMap, given);
                return;
            }

            insertStatements.add(is);
            insertedTables.add(is.table);
        }

        // Execute inserts
        Map<String, String> tableColumnValueMap = new HashMap<String, String>();
        for (InsertStatement is : insertStatements) {
            try {
                Statement s = conn.createStatement();
                String sql = is.toString(tableColumnValueMap);
                System.out.println("Sql: \"" + sql + "\"");
                int rows = s.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
                ResultSet rs = s.getGeneratedKeys();
                if (!rs.next()) {
                    try {
                        throw new Exception("Unhandled case!!!");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    System.exit(1);
                }
                tableColumnValueMap.put(is.table + "." + is.table + "_id", rs.getString("GENERATED_KEY"));
                rs.close();
                s.close();
            } catch (SQLException sqle) {
                sqle.printStackTrace();
                System.exit(1);
            }
        }
    }

    // map - TableColumn, value to insert
    // given - TableColumn, value which are known (like user_id)
    public static void insertIntoDatabase(Connection conn,
                                          Map<TableColumn, String> map,
                                          Map<TableColumn, String> given) {
        Map<String, Map<String, String>> columnValueMapByTable = new HashMap<String, Map<String, String>>();

        // Find all columns that are in a single table
        for (Map.Entry<TableColumn, String> entry : map.entrySet()) {
            Map<String, String> columnValueMap = null;

            // Does table exist?
            TableColumn tableColumn = (TableColumn) entry.getKey();
            if (columnValueMapByTable.containsKey(tableColumn.table)) {
                columnValueMap = (Map<String, String>) columnValueMapByTable.get(tableColumn.table);
            } else {
                columnValueMap = new HashMap<String, String>();
            }
            columnValueMap.put(tableColumn.column, (String) entry.getValue());
            columnValueMapByTable.put(tableColumn.table, columnValueMap);
        }

        // Helper
        insertIntoDatabaseHelper(conn, columnValueMapByTable, given);
    }

    public static void main(String[] args) {
        Connection conn = null;
        boolean exception = false;

        String user = "root";
        String passwd = "glory2020";
        try {
            String userName = user;
            String password = passwd;
            String url = "jdbc:mysql://localhost:3306/information_schema";
//            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection(url, userName, password);
            constraintMap = RawTool.getConstraintMap(conn, "diy");
            Map<String, Map<String, List<String>>> keyMap = RawTool.getKeyMap(conn, "diy");
            System.out.println(keyMap);
        } catch (Exception e) {
            e.printStackTrace();
            exception = true;
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) { /* ignore close errors */ }
            }
        }

        if (exception) System.exit(1);

        boolean commit = true;
        try {
            String userName = user;
            String password = passwd;
            String url = "jdbc:mysql://localhost:3306/diy";
//            Class.forName("com.mysql.jdbc.Driver").getDeclaredConstructor().newInstance();
            conn = DriverManager.getConnection(url, userName, password);
            conn.setAutoCommit(false);

            // Test data
            Map<TableColumn, String> map = new HashMap<TableColumn, String>();
            map.put(new TableColumn("ncc_currency", "code"), "1234");
            map.put(new TableColumn("ncc_currency", "name"), "Bob");

            // Given columns
//            Map<TableColumn, String> given = new HashMap<TableColumn, String>();
//            given.put(new TableColumn("ncc_currency", "nxc_code"), "1");

            insertIntoDatabase(conn, map, null);
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
