package main.java.com.example.restapi;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ResultSetUtils {

    /**
     * Converts a ResultSet to a JSON string.
     *
     * @param rs The ResultSet to convert.
     * @return A JSON string representation of the ResultSet.
     * @throws SQLException if there is a database access error.
     */
    public static String toJson(ResultSet rs) throws SQLException {
        JSONArray jsonArray = new JSONArray();
        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();

        while (rs.next()) {
            JSONObject jsonObject = new JSONObject();
            for (int i = 1; i <= columnCount; i++) {
                String columnName = metaData.getColumnName(i);
                Object value = rs.getObject(i);
                jsonObject.put(columnName, value);
            }
            jsonArray.put(jsonObject);
        }

        return jsonArray.toString();
    }

    /**
     * Converts a JSON string to a simulated ResultSet.
     *
     * @param json The JSON string representing the data.
     * @return A simulated ResultSet containing the data from the JSON string.
     */
    public static ResultSet fromJson(String json) {
        JSONArray jsonArray = new JSONArray(json);

        // Prepare metadata based on the first JSON object
        List<String> columnNames = new ArrayList<>();
        if (!jsonArray.isEmpty()) {
            JSONObject firstRow = jsonArray.getJSONObject(0);
            columnNames.addAll(firstRow.keySet());
        }

        // Create a custom ResultSet implementation
        return new InMemoryResultSet(jsonArray, columnNames);
    }
}
