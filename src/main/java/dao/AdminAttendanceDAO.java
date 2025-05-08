package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import dto.AttendanceDTO;

public class AdminAttendanceDAO {

    // DB接続のためのメソッドはDBManagerで行うので、ここでは変更不要

    public List<AttendanceDTO> findByEmployeeAndMonth(int employeeId, LocalDate monthStart) {
        List<AttendanceDTO> attendanceList = new ArrayList<>();

        // SQLクエリ：指定した従業員IDと月の範囲で勤怠データを取得
        String sql = "SELECT employee_id, work_date, clock_in, clock_out, work_hours, break_hours, overtime_hours, note "
                   + "FROM attendance "
                   + "WHERE employee_id = ? AND work_date BETWEEN ? AND ?";

        // 月初日から月末日までの期間を計算
        LocalDate monthEnd = monthStart.withDayOfMonth(monthStart.lengthOfMonth());

        try (Connection conn = DBManager.getConnection(); // DBManagerを使用して接続
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // パラメータの設定
            stmt.setInt(1, employeeId); // 従業員ID
            stmt.setDate(2, Date.valueOf(monthStart)); // 月初日
            stmt.setDate(3, Date.valueOf(monthEnd));   // 月末日

            try (ResultSet rs = stmt.executeQuery()) {
                // 結果セットを処理してAttendanceDTOリストに追加
                while (rs.next()) {
                    AttendanceDTO attendance = new AttendanceDTO();
                    attendance.setEmployeeId(rs.getInt("employee_id"));
                    attendance.setWorkDate(rs.getDate("work_date").toLocalDate());
                    attendance.setClockIn(rs.getTimestamp("clock_in") != null ? rs.getTimestamp("clock_in").toLocalDateTime() : null);
                    attendance.setClockOut(rs.getTimestamp("clock_out") != null ? rs.getTimestamp("clock_out").toLocalDateTime() : null);
                    attendance.setWorkHours(rs.getString("work_hours"));
                    attendance.setBreakHours(rs.getString("break_hours"));
                    attendance.setOvertimeHours(rs.getString("overtime_hours"));
                    attendance.setNote(rs.getString("note"));

                    attendanceList.add(attendance);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace(); // 例外処理
        }

        return attendanceList; // 該当する勤怠情報が無ければ空リストが返されます
    }
}
