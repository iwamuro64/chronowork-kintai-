package controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import dao.TimeCardDAO;
import dto.EmployeeDTO;
import dto.TimeCardStatus;

@WebServlet("/TimeCardServlet")
public class TimeCardServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");
        HttpSession session = request.getSession();
        EmployeeDTO employee = (EmployeeDTO) session.getAttribute("employee");

        if (employee == null) {
            response.sendRedirect("mainJsp/employeeLogin.jsp");
            return;
        }

        int employeeId = employee.getEmployeeId();
        LocalDate today = LocalDate.now();
        LocalDateTime now = LocalDateTime.now();

        try {
            TimeCardDAO dao = new TimeCardDAO();

            switch (action) {
                case "clock_in":
                    dao.recordClockIn(employeeId, today, now);
                    break;
                case "clock_out":
                    dao.recordClockOut(employeeId, today, now);
                    break;
                case "break_start":
                    dao.recordBreakStart(employeeId, today, now);
                    break;
                case "break_end":
                    dao.recordBreakEnd(employeeId, today, now);
                    break;
            }

            response.sendRedirect("mainJsp/timeCard.jsp");

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "勤怠記録に失敗しました");
        }
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        EmployeeDTO employee = (EmployeeDTO) session.getAttribute("employee");

        if (employee == null) {
            response.sendRedirect("mainJsp/employeeLogin.jsp");
            return;
        }

        int employeeId = employee.getEmployeeId();
        LocalDate today = LocalDate.now();

        try {
            TimeCardDAO dao = new TimeCardDAO();
            TimeCardStatus status = dao.getTodayStatus(employeeId, today);

            request.setAttribute("status", status);
            request.getRequestDispatcher("mainJsp/timeCard.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "ステータス取得失敗");
        }
    }

}

