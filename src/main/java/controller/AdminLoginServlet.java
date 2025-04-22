package controller;

import java.io.IOException;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import dao.AdminLoginDAO;
import dto.AdminDTO;

@WebServlet("/AdminLoginServlet")
public class AdminLoginServlet extends HttpServlet {
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 文字コード設定
        request.setCharacterEncoding("UTF-8");
        
        String adminName = request.getParameter("admin_name");
        String password = request.getParameter("password");
        
        System.out.println(adminName);
        System.out.println(password);

        AdminLoginDAO loginDAO = new AdminLoginDAO();
        AdminDTO admin = null;
        
        try {
            // ユーザーIDとパスワードで認証処理
            admin = loginDAO.findByAdminNameAndPassword(adminName, password);
            
            if (admin != null) {
                // 認証成功 → セッションに管理者情報を保存
                HttpSession session = request.getSession();
                session.setAttribute("adminName", admin.getAdminName());  // 管理者名
                session.setAttribute("adminId", admin.getAdminId());  // 管理者ID

                // ログイン後のページにリダイレクト
                response.sendRedirect(request.getContextPath() + "/mainJsp/adminMenu.jsp");
            } else {
                // 認証失敗 -> エラーメッセージを設定してログインページに戻す
                String errorMsg = "管理者IDまたはパスワードが間違っています。";
                request.setAttribute("error", errorMsg);  // エラーメッセージをリクエストにセット
                request.getRequestDispatcher("/mainJsp/adminLogin.jsp").forward(request, response);  // login.jspにフォワード
            }

        } catch (SQLException e) {
            e.printStackTrace();
            String errorMsg = "システムエラーが発生しました。";
            request.setAttribute("error", errorMsg);  // エラーメッセージをセット
            request.getRequestDispatcher("mainJsp/adminLogin.jsp").forward(request, response);
        }
    }
}
