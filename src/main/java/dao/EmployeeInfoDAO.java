package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dto.EmployeeDTO;

public class EmployeeInfoDAO {

    // 従業員情報を更新するメソッド
    public static boolean updateEmployee(EmployeeDTO employee) throws SQLException {
        String sql = "UPDATE employees SET name=?, department=?, position=?, employment_type=?, user_id=? WHERE employee_id=?";

        try (Connection conn = DBManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, employee.getName());
            stmt.setString(2, employee.getDepartment());
            stmt.setString(3, employee.getPosition());
            stmt.setString(4, employee.getEmploymentType());
            stmt.setString(5, employee.getUserId());
            stmt.setInt(6, employee.getEmployeeId());

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        }
    }

    // 従業員IDから情報を取得するメソッド（← 追加）
    public static EmployeeDTO getEmployeeById(int employeeId) throws SQLException {
        String sql = "SELECT * FROM employees WHERE employee_id = ?";

        try (Connection conn = DBManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, employeeId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    EmployeeDTO employee = new EmployeeDTO();
                    employee.setEmployeeId(rs.getInt("employee_id"));
                    employee.setName(rs.getString("name"));
                    employee.setDepartment(rs.getString("department"));
                    employee.setPosition(rs.getString("position"));
                    employee.setEmploymentType(rs.getString("employment_type"));
                    employee.setUserId(rs.getString("user_id"));
                    return employee;
                }
            }
        }

        return null;
    }
}
