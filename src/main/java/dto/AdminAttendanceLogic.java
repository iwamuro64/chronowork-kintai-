package dto;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

import dao.AdminAttendanceDAO;

public class AdminAttendanceLogic {
    private AdminAttendanceDAO dao = new AdminAttendanceDAO();

    public List<AttendanceDTO> getAttendanceByEmployeeAndMonth(int employeeId, LocalDate monthStart) {
        return dao.findByEmployeeAndMonth(employeeId, monthStart);
    }

    public String calculateTotalWorkHours(List<AttendanceDTO> attendanceList) {
        long totalMinutes = 0;
        for (AttendanceDTO record : attendanceList) {
            if (record.getClockIn() != null && record.getClockOut() != null) {
                totalMinutes += Duration.between(record.getClockIn(), record.getClockOut()).toMinutes();
            }
        }
        return formatDuration(totalMinutes);
    }

    public String calculateTotalOvertimeHours(List<AttendanceDTO> attendanceList) {
        long totalOvertime = 0;
        // TODO: 実際の残業ロジックに応じて修正
        return formatDuration(totalOvertime);
    }

    private String formatDuration(long totalMinutes) {
        long hours = totalMinutes / 60;
        long minutes = totalMinutes % 60;
        return String.format("%02d:%02d", hours, minutes);
    }
}

