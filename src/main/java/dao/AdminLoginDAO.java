package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dto.AdminDTO;  // 管理者情報を格納するDTOクラスを使用

public class AdminLoginDAO {

    /**
     * 管理者IDとパスワードを使用して管理者を認証する
     * 
     * @param adminName 管理者ID
     * @param password パスワード
     * @return 認証成功時はAdminDTOオブジェクト、失敗時はnull
     * @throws SQLException データベース接続エラー発生時
     */
    public AdminDTO findByAdminNameAndPassword(String adminName, String password) throws SQLException {
        AdminDTO admin = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DBManager.getConnection();

            // パスワードをそのまま照合（ハッシュ化しない場合）
            String sql = "SELECT admin_id, admin_name, password " +
                         "FROM admin WHERE admin_name = ? AND password = ?";
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, adminName);  // 管理者ID
            pstmt.setString(2, password);  // 平文のパスワードをそのまま照合
            rs = pstmt.executeQuery();

            if (rs.next()) {
                admin = new AdminDTO();
                admin.setAdminId(rs.getInt("admin_id"));
                admin.setAdminName(rs.getString("admin_name"));
                admin.setPassword(rs.getString("password"));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if (rs != null) try { rs.close(); } catch (SQLException e) {}
            if (pstmt != null) try { pstmt.close(); } catch (SQLException e) {}
            if (conn != null) try { conn.close(); } catch (SQLException e) {}
        }
        return admin;
    }
}
