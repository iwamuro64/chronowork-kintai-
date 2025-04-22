package controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/EmployeeInfoServlet")
public class EmployeeInfoServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false); // セッションがなければ null

        if (session == null || session.getAttribute("employee") == null) {
            // セッション切れ or 未ログインの場合はログイン画面にリダイレクト
            response.sendRedirect("mainJsp/employeeLogin.jsp");
            return;
        }

        // すでにセッションに格納済みの情報をリクエストスコープに渡す（必要であれば）
        request.setAttribute("employeeName", session.getAttribute("employeeName"));
        request.setAttribute("employeeDepartment", session.getAttribute("employeeDepartment"));
        request.setAttribute("employeePosition", session.getAttribute("employeePosition"));
        request.setAttribute("employmentType", session.getAttribute("employmentType"));
        request.setAttribute("userId", session.getAttribute("userId"));

        // JSP にフォワード
        request.getRequestDispatcher("/mainJsp/employeeInfo.jsp").forward(request, response);
    }
}
