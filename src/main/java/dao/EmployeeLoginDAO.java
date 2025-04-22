package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dto.EmployeeDTO;  // 従業員情報を格納するDTOクラスを使用

public class EmployeeLoginDAO {

    /**
     * ユーザーIDとパスワードを使用して従業員を認証する
     * 
     * @param userId ユーザーID
     * @param pass パスワード
     * @return 認証成功時はEmployeeDTOオブジェクト、失敗時はnull
     * @throws SQLException データベース接続エラー発生時
     */
    public EmployeeDTO findByUserIdAndPassword(String userId, String pass) throws SQLException {
        EmployeeDTO employee = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        System.out.println(userId);
        System.out.println(pass);
        try {
            conn = DBManager.getConnection();

            // パスワードをそのまま照合（ハッシュ化しない場合）
            String sql = "SELECT employee_id, name, department, position, employment_type, user_id, password " +
                         "FROM employees WHERE user_id = ? AND password = ?";
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, userId);  // ユーザーID
            pstmt.setString(2, pass);  // 平文のパスワードをそのまま照合
            System.out.println( pstmt.toString());
            rs = pstmt.executeQuery();

            if (rs.next()) {
                employee = new EmployeeDTO();
                employee.setEmployeeId(rs.getInt("employee_id"));
                employee.setName(rs.getString("name"));
                employee.setDepartment(rs.getString("department"));
                employee.setPosition(rs.getString("position"));
                employee.setEmploymentType(rs.getString("employment_type"));
                employee.setUserId(rs.getString("user_id"));
                employee.setPassword(rs.getString("password"));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if (rs != null) try { rs.close(); } catch (SQLException e) {}
            if (pstmt != null) try { pstmt.close(); } catch (SQLException e) {}
            if (conn != null) try { conn.close(); } catch (SQLException e) {}
        }
        System.out.println(employee.toString());
        return employee;
    }

    /**
     * 従業員の情報を更新する
     * 
     * @param employeeId 従業員ID
     * @param name 名前
     * @param department 部署
     * @param position 役職
     * @param employmentType 雇用形態
     * @param userId ユーザーID（識別用）
     * @param password パスワード
     * @throws SQLException データベース接続エラー発生時
     */
    public void updateEmployeeInfo(int employeeId, String name, String department, String position, String employmentType, String userId, String password) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DBManager.getConnection();  // DB接続取得

            // SQL文の準備: 従業員情報を更新する
            String sql = "UPDATE employees SET name = ?, department = ?, position = ?, employment_type = ?, user_id = ?, password = ? WHERE employee_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setString(2, department);
            pstmt.setString(3, position);
            pstmt.setString(4, employmentType);
            pstmt.setString(5, userId);
            pstmt.setString(6, password);  // パスワードはそのまま使用
            pstmt.setInt(7, employeeId);  // 従業員IDをWHERE句で使用

            // SQL実行
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            if (pstmt != null) try { pstmt.close(); } catch (SQLException e) {}
            if (conn != null) try { conn.close(); } catch (SQLException e) {}
        }
    }
}
