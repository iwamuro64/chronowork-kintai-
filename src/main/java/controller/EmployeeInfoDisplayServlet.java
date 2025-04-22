package controller;

import java.io.IOException;
import java.sql.SQLException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import dao.EmployeeInfoDAO;
import dto.EmployeeDTO;

@WebServlet("/EmployeeInfoDisplayServlet")
public class EmployeeInfoDisplayServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("employeeId") == null) {
            response.sendRedirect("mainJsp/employeeLogin.jsp");
            return;
        }

        int employeeId = (int) session.getAttribute("employeeId");

        try {
            // DBから従業員情報を取得
            EmployeeDTO employee = EmployeeInfoDAO.getEmployeeById(employeeId);

            if (employee != null) {
                // JSPに渡す
                request.setAttribute("employee", employee);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/mainJsp/employeeInfo.jsp");
                dispatcher.forward(request, response);
            } else {
                request.setAttribute("errorMessage", "従業員情報が見つかりませんでした。");
                request.getRequestDispatcher("/mainJsp/employeeMyPage.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "データベースエラーが発生しました。");
            request.getRequestDispatcher("/mainJsp/employeeMyPage.jsp").forward(request, response);
        }
    }
}
