package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import dao.DBManager;
import dto.AttendanceRecord;

@WebServlet("/AdminAttendanceDetailServlet")
public class AdminAttendanceDetailServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String employeeIdStr = request.getParameter("employee_id");

        if (employeeIdStr != null && !employeeIdStr.isEmpty()) {
            try {
                int employeeId = Integer.parseInt(employeeIdStr);

                try (Connection conn = DBManager.getConnection()) {
                    String query = "SELECT e.name AS 従業員名, e.department AS 部署, e.position AS 役職, t.work_date AS 勤務日, " +
                            "t.clock_in AS 出勤時間, t.clock_out AS 退勤時間, t.break_start AS 休憩開始時間, " +
                            "t.break_end AS 休憩終了時間, t.note AS メモ " +
                            "FROM timecards t " +
                            "JOIN employees e ON t.employee_id = e.employee_id " +
                            "WHERE t.employee_id = ? " +
                            "ORDER BY t.work_date DESC";

                    try (PreparedStatement stmt = conn.prepareStatement(query)) {
                        stmt.setInt(1, employeeId);
                        ResultSet rs = stmt.executeQuery();

                        List<AttendanceRecord> attendanceList = new ArrayList<>();
                        while (rs.next()) {
                            AttendanceRecord record = new AttendanceRecord();
                            record.setName(rs.getString("従業員名"));
                            record.setDepartment(rs.getString("部署"));
                            record.setPosition(rs.getString("役職"));
                            record.setWorkDate(rs.getDate("勤務日"));
                            record.setClockIn(rs.getTimestamp("出勤時間"));
                            record.setClockOut(rs.getTimestamp("退勤時間"));
                            record.setBreakStart(rs.getTimestamp("休憩開始時間"));
                            record.setBreakEnd(rs.getTimestamp("休憩終了時間"));
                            record.setNote(rs.getString("メモ"));
                            attendanceList.add(record);
                        }

                        request.setAttribute("attendanceList", attendanceList);
                        RequestDispatcher dispatcher = request.getRequestDispatcher("/adminAttendanceDetail.jsp");
                        dispatcher.forward(request, response);
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "DB接続中にエラーが発生しました。");
                }

            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "無効な従業員IDです。");
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "従業員IDが指定されていません。");
        }
    }
}
