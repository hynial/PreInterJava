package com.hynial.assistant.db;

import com.hynial.assistant.db.entity.FieldType;
import com.hynial.assistant.db.entity.TableColumn;
import com.hynial.assistant.db.entity.TableStatus;

import java.sql.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RawTool {

    public static Map<String, Map<String, TableColumn>> getConstraintMap(Connection conn, String schema) throws SQLException {
        Map<String, Map<String, TableColumn>> constraintMap = new HashMap<String, Map<String, TableColumn>>();
        Statement s = conn.createStatement();
        s.executeQuery("select table_name, column_name, referenced_table_name, referenced_column_name from information_schema.key_column_usage "
                + " where table_schema = '" + schema + "' and referenced_table_schema != ''");
        ResultSet rs = s.getResultSet();
        while (rs.next()) {
            String table = rs.getString("table_name");
            String column = rs.getString("column_name");
            Map<String, TableColumn> columnValueMap = null;
            if (constraintMap.containsKey(table)) {
                columnValueMap = constraintMap.get(table);
            } else {
                columnValueMap = new HashMap<String, TableColumn>();
            }
            TableColumn referencedColumn = new TableColumn(rs.getString("referenced_table_name"), rs.getString("referenced_column_name"));
            columnValueMap.put(column, referencedColumn);
            constraintMap.put(table, columnValueMap);
        }
        rs.close();
        s.close();
        return constraintMap;
    }

    public static Map<String, Map<String, List<String>>> getKeyMap(Connection conn, String schema) throws SQLException {
        Map<String, Map<String, List<String>>> constraintMap = new HashMap<String, Map<String, List<String>>>();
        Statement s = conn.createStatement();
        s.executeQuery("select constraint_name, table_name, column_name from information_schema.key_column_usage "
                + " where table_schema = '" + schema + "'");
        ResultSet rs = s.getResultSet();
        while (rs.next()) {
            String table = rs.getString("table_name");
            String column = rs.getString("column_name");
            String constraintName = rs.getString("constraint_name");
            Map<String, List<String>> columnValueMap = null;
            if (constraintMap.containsKey(table)) {
                columnValueMap = constraintMap.get(table);
            } else {
                columnValueMap = new HashMap<String, List<String>>();
            }

            if (columnValueMap.containsKey(constraintName)) {
                columnValueMap.get(constraintName).add(column);
            } else {
                columnValueMap.put(constraintName, Stream.of(column).collect(Collectors.toList()));
            }

            constraintMap.put(table, columnValueMap);
        }
        rs.close();
        s.close();

        return constraintMap;
    }

    public static List<String> getDbList(Connection conn) throws SQLException {
        List<String> dbList = new ArrayList<>();
        Statement stmt = conn.createStatement();
        for (ResultSet rs = stmt.executeQuery("show databases;"); rs.next(); ) {
            dbList.add(rs.getString("Database"));
        }

        return dbList;
    }

    public static List<TableStatus> getTableStatus(Connection conn, String db) throws SQLException {
        List<TableStatus> tableStatuses = new ArrayList<>();
        Statement stmt = conn.createStatement();

        for (ResultSet rs = stmt.executeQuery("SHOW TABLE STATUS FROM `" + db + "`;"); rs.next(); ) {
            TableStatus tableStatus = new TableStatus();
            tableStatus.name = rs.getString("Name");
            tableStatus.engine = rs.getString("Engine");
            tableStatus.rows = rs.getString("Rows");
            tableStatus.createTime = rs.getString("Create_time");
            tableStatus.collation = rs.getString("Collation");
            tableStatus.dataLength = rs.getString("data_length");
            tableStatus.indexLength = rs.getString("index_length");

            tableStatuses.add(tableStatus);
        }

        return tableStatuses;
    }

    public static List<String> getTableList(Connection conn, String db) throws SQLException {
        List<String> tableList = new ArrayList<>();
        Statement stmt = conn.createStatement();
        for (ResultSet rs = stmt.executeQuery("SHOW TABLE STATUS FROM `" + db + "`;"); rs.next(); ) {
            tableList.add(rs.getString("Name"));
        }
        return tableList;
    }

    public static String showTable(Connection conn, String table) throws SQLException {
        String html = "";
        String qtbl;
        String query;

        Statement stmt = conn.createStatement();
        for (ResultSet rs = stmt.executeQuery("SHOW CREATE TABLE " + table + ";"); rs.next(); ) {
            qtbl = rs.getString(1);
            query = rs.getString(2);
            html = html + "--\r\n";
            html = html + "-- Table dump : " + qtbl + "\r\n";
            html = html + "--\r\n\r\n\r\n";
            html = html + alterQuery(query, qtbl) + ";\r\n\r\n";
        }

        return html;
    }

    public static String random_string(int top) {
        Random ran = new Random();
        String dat = "";

        for (int i = 0; i <= top; ++i) {
            char data = (char) (ran.nextInt(25) + 97);
            dat = data + dat;
        }

        return dat;
    }

    public static String alterQuery(String txt, String table) {
        String rand;
        for (rand = random_string(8); txt.contains(rand); rand = random_string(8)) {
        }

        txt = txt.replace("\n", rand);
        Pattern pattern0 = Pattern.compile("CONSTRAINT(.*?)\\)", 8);
        Matcher matcher0 = pattern0.matcher(txt);
        Pattern pattern1 = Pattern.compile("REFERENCES(.*?)\\)", 8);

        String alter = "";
        String alter1;
        for (Matcher matcher1 = pattern1.matcher(txt); matcher0.find(); txt = txt.replace(alter1, "")) {
            alter = alter + "\n\n--\n-- Constraints for table `" + table + "`\n" + "--\n\n";
            matcher1.find();
            String alter0 = matcher0.group(0);
            alter1 = matcher1.group(0);
            alter = alter + "ALTER TABLE `" + table + "` ADD " + alter0 + " " + alter1 + ";\n\n";
            txt = txt.replace(alter0, "");
            txt = txt.replace(alter0, "");
            txt = txt.replace(alter1 + ",", "");
        }

        txt = txt.replace(rand, "\n");
        Pattern pattern2 = Pattern.compile(",(\\s+)\\)", 8);

        String alter2;
        for (Matcher matcher2 = pattern2.matcher(txt); matcher2.find(); txt = txt.replace(alter2, "\n)")) {
            alter2 = matcher2.group(0);
        }

        return txt;
    }

    public static List<Map<String, String>> getCollation(Connection conn) throws SQLException {
        List<Map<String, String>> collationList = new ArrayList<>();

        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SHOW COLLATION;");
        while (rs.next()) {
            Map<String, String> collationMap = new HashMap<>();
            collationMap.put("Charset", rs.getString("Charset"));
            collationMap.put("Collation", rs.getString("Collation"));

            collationList.add(collationMap);
        }

        return collationList;
    }

    public static List<String> getTableKey(Connection conn, String db, String table) throws SQLException {
        List<String> keyList = new ArrayList<>();

        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SHOW KEYS FROM " + db + "." + table + " WHERE Key_name = 'PRIMARY';");
        while (rs.next()) {
            keyList.add(rs.getString("Column_name"));
        }

        return keyList;
    }

    public static List<FieldType> getColumns(Connection conn, String db, String table) throws SQLException {
        List<FieldType> fieldTypes = new ArrayList<>();

        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SHOW FIELDS FROM " + db + "." + table + ";");
        while (rs.next()) {
            FieldType fieldType = new FieldType();
            fieldType.field = rs.getString("Field");
            fieldType.type = rs.getString("Type");
            fieldTypes.add(fieldType);
        }

        return fieldTypes;
    }

    public static List<String> getColumn(Connection conn, String db, String table) throws SQLException {
        List<String> fields = new ArrayList<>();

        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SHOW FIELDS FROM " + db + "." + table + ";");
        while (rs.next()) {
            fields.add(rs.getString("Field"));
        }

        return fields;
    }

    public static Map<String, String> getResult(Connection conn, String db, String table) throws SQLException {
        Statement stmt = conn.createStatement();

        String query = "SELECT * FROM " + db + "." + table;
        ResultSet rs = stmt.executeQuery(query);
        ResultSetMetaData rsmd = rs.getMetaData();
        int cols = rsmd.getColumnCount();

        Map<String, String> resultMap = new HashMap<>();
        while (rs.next()) {
            for (int i = 0; i < cols; ++i) {
                resultMap.put(rsmd.getColumnName(i + 1), rs.getString(rsmd.getColumnName(i + 1)));
            }
        }

        return resultMap;
    }

    public static Map<String, String> getResultLimit(Connection conn, String db, String table, int limits, int offset) throws SQLException {
        Statement stmt = conn.createStatement();

        String query = "SELECT * FROM " + db + "." + table + " limit " + limits + " offset " + offset + ";";
        ResultSet rs = stmt.executeQuery(query);
        ResultSetMetaData rsmd = rs.getMetaData();
        int cols = rsmd.getColumnCount();

        Map<String, String> resultMap = new HashMap<>();
        while (rs.next()) {
            for (int i = 0; i < cols; ++i) {
                resultMap.put(rsmd.getColumnName(i + 1), rs.getString(rsmd.getColumnName(i + 1)));
            }
        }

        return resultMap;
    }

    public boolean isValidConnection(String user, String pass, String host, String port) {
        String url = "jdbc:mysql://" + host + ":" + port + "/";
        boolean res = false;

        try {
            if (user != null) {
                Connection c = DriverManager.getConnection(url, user, pass);
                res = true;
                c.close();
            }
        } catch (SQLException var8) {
            res = false;
        }

        return res;
    }

    public void createDb(Connection conn, String db, String Collation) throws SQLException {
        Statement stmt = conn.createStatement();
        String collationTail = "";
        try {
            String[] character = Collation.split("_");
            if (Collation != "") {
                collationTail = " CHARACTER SET " + character[0] + " COLLATE " + Collation;
            }

            stmt.executeUpdate("CREATE DATABASE `" + db + "`" + collationTail + ";");
        } catch (SQLException var4) {
            throw var4;
        }
    }

    public String generateRandomString() {
        StringBuffer randStr = new StringBuffer();

        for (int i = 0; i < 64; ++i) {
            int number = getRandomNumber();
            char ch = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890".charAt(number);
            randStr.append(ch);
        }

        return randStr.toString();
    }

    private int getRandomNumber() {
        Random randomGenerator = new Random();
        int randomInt = randomGenerator.nextInt("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890".length());
        return randomInt - 1 == -1 ? randomInt : randomInt - 1;
    }

    public static void execute(Connection conn, String sqls) throws SQLException {
        String[] var2 = sqls.split(";");
        int var3 = var2.length;
        Statement stmt = conn.createStatement();
        for (int var4 = 0; var4 < var3; ++var4) {
            String sql = var2[var4];

            if (sql != null) {
                stmt.execute(sql + ";");
            }
        }
    }

    public static void executeSql(Connection conn, String sql) throws SQLException {
        Statement stmt = conn.createStatement();
        stmt.executeUpdate(sql);
    }

    public static int getRowCount(Connection conn, String tbl) throws SQLException {
        int count = 0;

        Statement stmt = conn.createStatement();
        ResultSet res = stmt.executeQuery("SELECT COUNT(*) FROM " + tbl);
        res.next();
        count = res.getInt(1);

        return count;
    }

    public static String showInsert(Connection conn, String db, String table) throws SQLException {
        String html = "";
        html = html + "\r\n--\r\n";
        html = html + "-- Inserts of " + table + "\r\n";
        html = html + "--\r\n\r\n";

        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("select * from " + db + "." + table + ";");
        ResultSetMetaData rsmd = rs.getMetaData();
        int cols = rsmd.getColumnCount();
        String[] c = new String[cols];
        Object[] row = new Object[cols];
        html = html + "INSERT INTO `" + table + "` (";

        for (int i = 0; i < cols; ++i) {
            c[i] = rsmd.getColumnName(i + 1);
            if (i != 0) {
                html = html + ",";
            }

            html = html + "`" + c[i] + "`";
        }

        html = html + ") VALUES\r\n";

        for (boolean rn = true; rs.next(); html = html + ")") {
            if (rn) {
                rn = false;
            } else {
                html = html + ",\r\n";
            }

            html = html + "(";

            for (int i = 0; i < cols; ++i) {
                try {
                    row[i] = rs.getString(i + 1);
                } catch (Exception var10) {
                    row[i] = "0000-00-00 00:00:00";
                }

                if (i != 0) {
                    html = html + ",";
                }

                if (row[i] == null) {
                    html = html + row[i];
                } else if (row[i].toString().matches("[0-9]+")) {
                    html = html + toSql(row[i].toString());
                } else {
                    html = html + "'" + toSql(row[i].toString()) + "'";
                }
            }
        }

        html = html + ";\r\n\r\n\r\n";

        return html;
    }

    public static String toSql(String text) {
        text = text.replace("\\", "\\\\");
        text = text.replaceAll("'", "''");
        return text;
    }

    public String toInput(String text) {
        text = text.replaceAll("&", "&amp;");
        text = text.replaceAll("\"", "&quot;");
        text = text.replaceAll("<", "&lt;");
        text = text.replaceAll(">", "&gt;");
        return text;
    }


    public static boolean isNumeric(String str) {
        try {
            double var1 = Double.parseDouble(str);
            return true;
        } catch (NumberFormatException var3) {
            return false;
        }
    }

    public static void main(String[] args) throws SQLException {
        String user = "root";
        String passwd = "glory2020";
        String userName = user;
        String password = passwd;
        String url = "jdbc:mysql://localhost:3306/diy";
//            Class.forName("com.mysql.jdbc.Driver").getDeclaredConstructor().newInstance();
        Connection conn = DriverManager.getConnection(url, userName, password);
        conn.setAutoCommit(false);

//        String tableCreate = showTable(conn, "ncc_currency2");
        String tableInsert = showInsert(conn, "diy", "ncc_currency");
        System.out.println(tableInsert);
    }
}
