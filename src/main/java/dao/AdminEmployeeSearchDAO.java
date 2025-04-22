package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.EmployeeSearchDTO;

public class AdminEmployeeSearchDAO {

    // 従業員情報一覧を取得
    public static List<EmployeeSearchDTO> getAllEmployees() throws SQLException {
        List<EmployeeSearchDTO> employeeList = new ArrayList<>();
        String sql = "SELECT * FROM employees"; // employeesテーブルから全従業員情報を取得するクエリ

        try (Connection connection = DBManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                EmployeeSearchDTO employee = new EmployeeSearchDTO();
                employee.setEmployeeId(rs.getInt("employee_id"));
                employee.setName(rs.getString("name"));
                employee.setDepartment(rs.getString("department"));
                employee.setPosition(rs.getString("position"));
                employee.setEmploymentType(rs.getString("employment_type"));
                employee.setUserId(rs.getString("user_id"));
                employeeList.add(employee);
            }
        } catch (SQLException e) {
            throw new SQLException("従業員情報の取得中にエラーが発生しました", e); // 詳細なエラーメッセージとともにSQLExceptionをスロー
        }

        return employeeList;
    }

    // 特定の従業員ID情報を取得
    public static EmployeeSearchDTO getEmployeeById(int employeeId) throws SQLException {
        String sql = "SELECT * FROM employees WHERE employee_id = ?";
        EmployeeSearchDTO employee = null;

        try (Connection connection = DBManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            
            statement.setInt(1, employeeId); // プレースホルダーにemployeeIdをセット

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    employee = new EmployeeSearchDTO();
                    employee.setEmployeeId(resultSet.getInt("employee_id"));
                    employee.setName(resultSet.getString("name"));
                    employee.setDepartment(resultSet.getString("department"));
                    employee.setPosition(resultSet.getString("position"));
                    employee.setEmploymentType(resultSet.getString("employment_type"));
                    employee.setUserId(resultSet.getString("user_id"));
                }
            }
        } catch (SQLException e) {
            throw new SQLException("従業員情報の取得中にエラーが発生しました", e); // 詳細なエラーメッセージとともにSQLExceptionをスロー
        }

        return employee; // 従業員情報を返す
    }

    // 特定の従業員ID情報を削除
    public static boolean deleteEmployee(int employeeId) throws SQLException {
        try (Connection connection = DBManager.getConnection()) {
            String sql = "DELETE FROM employees WHERE employee_id = ?";

            try (PreparedStatement delStatement = connection.prepareStatement(sql)) {
                delStatement.setInt(1, employeeId);
                int rowsDeleted = delStatement.executeUpdate();
                System.out.println(rowsDeleted + " 行が削除されました。");
            }
        } catch (SQLException e) {
            throw new SQLException("従業員情報の削除中にエラーが発生しました", e); // 詳細なエラーメッセージとともにSQLExceptionをスロー
        }
		return false;
    }

    // 従業員情報を検索
    public List<EmployeeSearchDTO> searchEmployees(String employeeId, String employeeName, String department,
                                                  String position, String employmentType, String userId) throws SQLException {
        List<EmployeeSearchDTO> employeeList = new ArrayList<>();
        StringBuilder searchEmployeeSql = new StringBuilder("SELECT * FROM employees WHERE 1=1");

        // 条件付きでクエリを組み立てる
        if (employeeId != null && !employeeId.isEmpty()) {
            searchEmployeeSql.append(" AND employee_id = ? ");
        }
        if (employeeName != null && !employeeName.isEmpty()) {
            searchEmployeeSql.append(" AND name LIKE ? ");
        }
        if (department != null && !department.isEmpty()) {
            searchEmployeeSql.append(" AND department LIKE ? ");
        }
        if (position != null && !position.isEmpty()) {
            searchEmployeeSql.append(" AND position LIKE ? ");
        }
        if (employmentType != null && !employmentType.isEmpty()) {
            searchEmployeeSql.append(" AND employment_type LIKE ? ");
        }
        if (userId != null && !userId.isEmpty()) {
            searchEmployeeSql.append(" AND user_id LIKE ? ");
        }

        try (Connection connection = DBManager.getConnection();
             PreparedStatement searchEmployeeStatement = connection.prepareStatement(searchEmployeeSql.toString())) {

            int index = 1;

            // パラメータを設定する
            if (employeeId != null && !employeeId.isEmpty()) {
                searchEmployeeStatement.setInt(index++, Integer.parseInt(employeeId));
            }
            if (employeeName != null && !employeeName.isEmpty()) {
                searchEmployeeStatement.setString(index++, "%" + employeeName + "%");
            }
            if (department != null && !department.isEmpty()) {
                searchEmployeeStatement.setString(index++, "%" + department + "%");
            }
            if (position != null && !position.isEmpty()) {
                searchEmployeeStatement.setString(index++, "%" + position + "%");
            }
            if (employmentType != null && !employmentType.isEmpty()) {
                searchEmployeeStatement.setString(index++, "%" + employmentType + "%");
            }
            if (userId != null && !userId.isEmpty()) {
                searchEmployeeStatement.setString(index++, "%" + userId + "%");
            }

            // クエリを実行
            try (ResultSet resultSet = searchEmployeeStatement.executeQuery()) {
                while (resultSet.next()) {
                    EmployeeSearchDTO employee = new EmployeeSearchDTO();
                    employee.setEmployeeId(resultSet.getInt("employee_id"));
                    employee.setName(resultSet.getString("name"));
                    employee.setDepartment(resultSet.getString("department"));
                    employee.setPosition(resultSet.getString("position"));
                    employee.setEmploymentType(resultSet.getString("employment_type"));
                    employee.setUserId(resultSet.getString("user_id"));
                    employeeList.add(employee);
                }
            }
        } catch (SQLException e) {
            throw new SQLException("従業員情報の検索中にエラーが発生しました", e); // 詳細なエラーメッセージとともにSQLExceptionをスロー
        }

        return employeeList;
    }

    // 従業員情報を編集
    public static boolean updateEmployee(EmployeeSearchDTO employee) throws SQLException {
        String editEmployeeSql = "UPDATE employees SET name = ?, department = ?, position = ?, employment_type = ?, user_id = ? WHERE employee_id = ?";

        try (Connection connection = DBManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(editEmployeeSql)) {

            statement.setString(1, employee.getName());
            statement.setString(2, employee.getDepartment());
            statement.setString(3, employee.getPosition());
            statement.setString(4, employee.getEmploymentType());
            statement.setString(5, employee.getUserId());
            statement.setInt(6, employee.getEmployeeId());

            int rowsUpdated = statement.executeUpdate(); // UPDATE文を実行
            return rowsUpdated > 0; // 更新が成功した場合はtrueを返す
        } catch (SQLException e) {
            throw new SQLException("従業員情報の更新中にエラーが発生しました", e); // 詳細なエラーメッセージとともにSQLExceptionをスロー
        }
    }
    
 // 従業員情報を削除
    public static boolean deleteEmployee1(int employeeId) throws SQLException {
        String sql = "DELETE FROM employees WHERE employee_id = ?";

        try (Connection connection = DBManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, employeeId); // employeeIdをセット
            int rowsAffected = statement.executeUpdate(); // DELETE文を実行

            // 削除された行数が1以上の場合は削除成功
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new SQLException("従業員情報の削除中にエラーが発生しました", e); // エラーメッセージ
        }
    }
}

