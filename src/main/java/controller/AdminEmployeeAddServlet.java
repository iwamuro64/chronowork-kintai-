package controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import dao.AdminEmployeeAddDAO;
import dto.EmployeeDTO;

@WebServlet("/AdminEmployeeAddServlet")
public class AdminEmployeeAddServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 入力パラメータの取得
        String name = request.getParameter("addName");
        String department = request.getParameter("addDepartment");
        String position = request.getParameter("addPosition");
        String employmentType = request.getParameter("addEmploymentType");
        String userId = request.getParameter("addUserId");
        String password = request.getParameter("addPassword");

        // EmployeeDTOオブジェクトを作成
        EmployeeDTO employee = new EmployeeDTO();
        employee.setName(name);
        employee.setDepartment(department);
        employee.setPosition(position);
        employee.setEmploymentType(employmentType);
        employee.setUserId(userId);
        employee.setPassword(password);

        // EmployeeAddDAOを使ってDBに追加
        AdminEmployeeAddDAO dao = new AdminEmployeeAddDAO();
        boolean success = dao.addEmployee(employee);

        // 結果に基づいてリダイレクト
        if (success) {
            response.sendRedirect("mainJsp/adminEmployeeAddConfirmation.jsp?success=true");
        } else {
            response.sendRedirect("mainJsp/adminEmployeeAddConfirmation.jsp?success=false");
        }
    }
}