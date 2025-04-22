package controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/AdminLogoutServlet")
public class AdminLogoutServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // セッションを無効化
        HttpSession session = request.getSession(false); // セッションがあれば取得
        if (session != null) {
            session.invalidate(); // セッションを無効化
        }

        // ログイン画面にリダイレクト
        response.sendRedirect("mainJsp/adminLogin.jsp");
    }
}
