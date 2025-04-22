package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import dao.AttendanceDAO;
import dto.AttendanceDTO;
import dto.EmployeeDTO;

@WebServlet("/AttendanceCheckServlet")
public class AttendanceCheckServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        EmployeeDTO employee = (EmployeeDTO) session.getAttribute("employee");

        if (employee == null) {
            response.sendRedirect("/mainJsp/employeeLogin.jsp");
            return;
        }

        try {
            // 表示する年月の取得（パラメータがなければ今月）
            String yearMonthParam = request.getParameter("yearMonth");
            LocalDate baseDate = (yearMonthParam != null)
                    ? LocalDate.parse(yearMonthParam + "-01")
                    : LocalDate.now();

            int year = baseDate.getYear();
            int month = baseDate.getMonthValue();

            // 勤怠データ取得
            AttendanceDAO dao = new AttendanceDAO();
            List<AttendanceDTO> list = dao.getAttendanceByEmployeeAndMonth(employee.getEmployeeId(), year, month);

            if (list == null) {
                list = new ArrayList<>();
            }

            // 合計時間初期化
            Duration totalWork = Duration.ZERO;
            Duration totalOvertime = Duration.ZERO;

            for (AttendanceDTO dto : list) {
                if (dto.getClockIn() != null && dto.getClockOut() != null) {

                    // 出退勤時間から総勤務時間
                    Duration work = Duration.between(dto.getClockIn(), dto.getClockOut());

                    // 休憩時間（ある場合のみ）
                    Duration breakDuration = Duration.ZERO;
                    if (dto.getBreakStart() != null && dto.getBreakEnd() != null) {
                        breakDuration = Duration.between(dto.getBreakStart(), dto.getBreakEnd());
                    }

                    // 実働時間 = 勤務時間 - 休憩時間
                    Duration netWork = work.minus(breakDuration);

                    // 残業時間 = 実働 - 8時間（8時間以下なら0）
                    Duration standardWork = Duration.ofHours(8);
                    Duration overtime = netWork.minus(standardWork);
                    if (overtime.isNegative()) {
                        overtime = Duration.ZERO;
                    }

                    // DTOへセット（文字列形式）
                    dto.setWorkHours(formatDuration(netWork));
                    dto.setBreakHours(formatDuration(breakDuration));
                    dto.setOvertimeHours(formatDuration(overtime));

                    // 合計時間に加算
                    totalWork = totalWork.plus(netWork);
                    totalOvertime = totalOvertime.plus(overtime);
                }
            }

            // 合計時間を HH:mm 形式に整形
            String totalWorkStr = formatDuration(totalWork);
            String totalOvertimeStr = formatDuration(totalOvertime);

            // リクエスト属性にセット
            request.setAttribute("attendanceList", list);
            request.setAttribute("displayYearMonth", baseDate);
            request.setAttribute("totalWorkHours", totalWorkStr);
            request.setAttribute("totalOvertimeHours", totalOvertimeStr);

            // JSPへフォワード
            request.getRequestDispatcher("/mainJsp/attendanceCheck.jsp").forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    // HH:mm 形式にフォーマットするヘルパーメソッド
    private String formatDuration(Duration d) {
        long hours = d.toHours();
        long minutes = d.toMinutes() % 60; // Java 8互換
        return String.format("%02d:%02d", hours, minutes);
    }
}
