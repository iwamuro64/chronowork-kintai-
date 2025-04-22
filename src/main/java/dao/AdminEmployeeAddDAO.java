package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import dto.EmployeeDTO; // ← ここを修正！

public class AdminEmployeeAddDAO {

    // 従業員情報を追加（引数を EmployeeDTO に変更）
    public boolean addEmployee(EmployeeDTO employee) {
        String sql = "INSERT INTO employees (name, department, position, employment_type, user_id, password) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = DBManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, employee.getName());
            statement.setString(2, employee.getDepartment());
            statement.setString(3, employee.getPosition());
            statement.setString(4, employee.getEmploymentType());
            statement.setString(5, employee.getUserId());
            statement.setString(6, employee.getPassword());

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
