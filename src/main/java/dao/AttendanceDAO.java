package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import dto.AttendanceDTO;

public class AttendanceDAO {

    // 従業員の勤怠データを取得（従業員IDによる）
    public List<AttendanceDTO> getAttendanceByEmployee(int employeeId) throws SQLException {
        List<AttendanceDTO> list = new ArrayList<>();

        String sql = "SELECT * FROM timecards WHERE employee_id = ? ORDER BY work_date DESC";

        try (Connection conn = DBManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, employeeId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                AttendanceDTO dto = new AttendanceDTO();
                dto.setTimecardId(rs.getInt("timecard_id"));
                dto.setEmployeeId(rs.getInt("employee_id"));
                dto.setWorkDate(rs.getDate("work_date").toLocalDate());
                dto.setClockIn(toLocalDateTime(rs.getTimestamp("clock_in")));
                dto.setClockOut(toLocalDateTime(rs.getTimestamp("clock_out")));
                dto.setBreakStart(toLocalDateTime(rs.getTimestamp("break_start")));
                dto.setBreakEnd(toLocalDateTime(rs.getTimestamp("break_end")));

                try {
                    dto.setNote(rs.getString("note"));
                } catch (SQLException e) {
                    dto.setNote("");  // カラムが無ければ空文字列を設定
                }

                list.add(dto);
            }
        }

        return list;
    }

    // 勤怠IDを使って勤怠データを取得
    public AttendanceDTO findById(int timecardId) throws SQLException {
        String sql = "SELECT * FROM timecards WHERE timecard_id = ?";
        try (Connection conn = DBManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, timecardId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                AttendanceDTO dto = new AttendanceDTO();
                dto.setTimecardId(rs.getInt("timecard_id"));
                dto.setEmployeeId(rs.getInt("employee_id"));
                dto.setWorkDate(rs.getDate("work_date").toLocalDate());
                dto.setClockIn(toLocalDateTime(rs.getTimestamp("clock_in")));
                dto.setClockOut(toLocalDateTime(rs.getTimestamp("clock_out")));
                dto.setBreakStart(toLocalDateTime(rs.getTimestamp("break_start")));
                dto.setBreakEnd(toLocalDateTime(rs.getTimestamp("break_end")));

                try {
                    dto.setNote(rs.getString("note"));
                } catch (SQLException e) {
                    dto.setNote("");
                }

                return dto; // 取得した勤怠データを返す
            }
        }
        return null; // 見つからなかった場合はnullを返す
    }

    // 勤怠データを更新しない
    // 今回は編集機能を省略するため、このメソッドは不要です

    // TimestampをLocalDateTimeに変換
    private LocalDateTime toLocalDateTime(Timestamp ts) {
        return ts != null ? ts.toLocalDateTime() : null;
    }

    // 月別で従業員の勤怠データを取得（月の指定）
    public List<AttendanceDTO> getAttendanceByEmployeeAndMonth(int employeeId, int year, int month) throws SQLException {
        List<AttendanceDTO> list = new ArrayList<>();

        String sql = """
            SELECT * FROM timecards 
            WHERE employee_id = ? AND YEAR(work_date) = ? AND MONTH(work_date) = ?
            ORDER BY work_date DESC
        """;

        try (Connection conn = DBManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, employeeId);
            stmt.setInt(2, year);
            stmt.setInt(3, month);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                AttendanceDTO dto = new AttendanceDTO();
                dto.setTimecardId(rs.getInt("timecard_id"));
                dto.setEmployeeId(rs.getInt("employee_id"));
                dto.setWorkDate(rs.getDate("work_date").toLocalDate());
                dto.setClockIn(toLocalDateTime(rs.getTimestamp("clock_in")));
                dto.setClockOut(toLocalDateTime(rs.getTimestamp("clock_out")));
                dto.setBreakStart(toLocalDateTime(rs.getTimestamp("break_start")));
                dto.setBreakEnd(toLocalDateTime(rs.getTimestamp("break_end")));
                dto.setNote(rs.getString("note"));
                list.add(dto);
            }
        }

        return list;
    }
}
