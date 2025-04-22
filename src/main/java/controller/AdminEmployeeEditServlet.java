package controller;

import java.io.IOException;
import java.sql.SQLException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import dao.AdminEmployeeSearchDAO;
import dto.EmployeeSearchDTO;

@WebServlet("/AdminEmployeeEditServlet")
public class AdminEmployeeEditServlet extends HttpServlet {

    // doGetメソッドで従業員情報を取得して編集フォームに渡す
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String employeeIdStr = request.getParameter("employeeId");

        if (employeeIdStr != null) {
            try {
                int employeeId = Integer.parseInt(employeeIdStr);
                EmployeeSearchDTO employee = AdminEmployeeSearchDAO.getEmployeeById(employeeId); // staticメソッドを使用

                if (employee != null) {
                    // employee オブジェクトをJSPに渡す
                    request.setAttribute("employeeId", employee.getEmployeeId());
                    request.setAttribute("employeeName", employee.getName());
                    request.setAttribute("employeeDepartment", employee.getDepartment());
                    request.setAttribute("employeePosition", employee.getPosition());
                    request.setAttribute("employeeEmploymentType", employee.getEmploymentType());
                    request.setAttribute("employeeUserId", employee.getUserId());

                    RequestDispatcher dispatcher = request.getRequestDispatcher("/mainJsp/adminEmployeeEdit.jsp");
                    dispatcher.forward(request, response);
                } else {
                    request.setAttribute("errorMessage", "従業員が見つかりませんでした。");
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/mainJsp/adminEmployeeSearchResult.jsp");
                    dispatcher.forward(request, response);
                }
            } catch (NumberFormatException e) {
                // employeeId が不正な形式の場合
                request.setAttribute("errorMessage", "従業員IDが不正です。");
                RequestDispatcher dispatcher = request.getRequestDispatcher("/mainJsp/adminEmployeeSearchResult.jsp");
                dispatcher.forward(request, response);
            } catch (SQLException e) {
                e.printStackTrace();
                request.setAttribute("errorMessage", "データベースエラーが発生しました。");
                RequestDispatcher dispatcher = request.getRequestDispatcher("/mainJsp/adminEmployeeSearchResult.jsp");
                dispatcher.forward(request, response);
            }
        } else {
            request.setAttribute("errorMessage", "従業員IDが指定されていません。");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/mainJsp/adminEmployeeSearchResult.jsp");
            dispatcher.forward(request, response);
        }
    }

    // doPostメソッドで従業員情報を更新する処理
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String employeeId = request.getParameter("employeeId");
        String name = request.getParameter("name");
        String department = request.getParameter("department");
        String position = request.getParameter("position");
        String employmentType = request.getParameter("employmentType");
        String userId = request.getParameter("userId");

        try {
            // 更新する従業員情報をDTOにセット
            EmployeeSearchDTO employee = new EmployeeSearchDTO();
            employee.setEmployeeId(Integer.parseInt(employeeId));
            employee.setName(name);
            employee.setDepartment(department);
            employee.setPosition(position);
            employee.setEmploymentType(employmentType);
            employee.setUserId(userId);

            // EmployeeSearchDAOで従業員情報を更新
            boolean isUpdated = AdminEmployeeSearchDAO.updateEmployee(employee); // staticメソッドを使用

            if (isUpdated) {
                // 更新が成功した場合、検索結果ページにリダイレクト
                response.sendRedirect("AdminEmployeeSearchResultServlet");
            } else {
                // 更新失敗の場合
                request.setAttribute("errorMessage", "従業員情報の更新に失敗しました。");
                RequestDispatcher dispatcher = request.getRequestDispatcher("/mainJsp/adminEmployeeEdit.jsp");
                dispatcher.forward(request, response);
            }

        } catch (NumberFormatException e) {
            // employeeId が不正な形式の場合
            request.setAttribute("errorMessage", "従業員IDが不正です。");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/mainJsp/adminEmployeeEdit.jsp");
            dispatcher.forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "データベースエラーが発生しました。");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/mainJsp/adminEmployeeEdit.jsp");
            dispatcher.forward(request, response);
        }
    }
}
