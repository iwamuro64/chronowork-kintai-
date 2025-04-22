package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // フォームデータの取得
        String name = request.getParameter("name");
        String department = request.getParameter("department");
        String position = request.getParameter("position");
        String employmentType = request.getParameter("employment_type");
        String userId = request.getParameter("user_id");
        String password = request.getParameter("password");

        // データベースに接続して登録処理を実行
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            // データベース接続（DBの接続情報を変更する必要あり）
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/your_database", "username", "password");

            String sql = "INSERT INTO employees (name, department, position, employment_type, user_id, password) VALUES (?, ?, ?, ?, ?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setString(2, department);
            stmt.setString(3, position);
            stmt.setString(4, employmentType);
            stmt.setString(5, userId);
            stmt.setString(6, password);

            int rowsInserted = stmt.executeUpdate();

            // 登録成功時にfinalRegistration.jspへリダイレクト
            if (rowsInserted > 0) {
                response.sendRedirect("finalRegistration.jsp");
            } else {
                response.sendRedirect("error.jsp");  // エラーの場合のページ
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");  // エラーの場合のページ
        } finally {
            // リソースの解放
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

