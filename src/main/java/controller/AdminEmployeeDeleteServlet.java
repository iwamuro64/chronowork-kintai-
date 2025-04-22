package controller;

import java.io.IOException;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import dao.AdminEmployeeSearchDAO;

@WebServlet("/AdminEmployeeDeleteServlet")
public class AdminEmployeeDeleteServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int employeeId = Integer.parseInt(request.getParameter("employeeId"));

        try {
            AdminEmployeeSearchDAO.deleteEmployee(employeeId);
        } catch (SQLException e) {
            e.printStackTrace(); // エラーはログに出力するだけにして、
        }

        // 成功・失敗にかかわらず確認画面にリダイレクト
        response.sendRedirect("mainJsp/adminEmployeeDeleteConfirmation.jsp");
    }
}
