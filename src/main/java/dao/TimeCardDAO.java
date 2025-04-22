package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

import dto.TimeCardStatus;

public class TimeCardDAO {

    private boolean existsToday(int employeeId, LocalDate date) throws SQLException {
        String sql = "SELECT COUNT(*) FROM timecards WHERE employee_id = ? AND work_date = ?";
        try (Connection con = DBManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, employeeId);
            ps.setDate(2, Date.valueOf(date));
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1) > 0;
        }
    }

    public void recordClockIn(int employeeId, LocalDate date, LocalDateTime time) throws SQLException {
        if (existsToday(employeeId, date)) {
            String sql = "UPDATE timecards SET clock_in = ?, updated_at = NOW() WHERE employee_id = ? AND work_date = ?";
            try (Connection con = DBManager.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setTimestamp(1, Timestamp.valueOf(time));
                ps.setInt(2, employeeId);
                ps.setDate(3, Date.valueOf(date));
                ps.executeUpdate();
            }
        } else {
            String sql = "INSERT INTO timecards (employee_id, work_date, clock_in, created_at) VALUES (?, ?, ?, NOW())";
            try (Connection con = DBManager.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, employeeId);
                ps.setDate(2, Date.valueOf(date));
                ps.setTimestamp(3, Timestamp.valueOf(time));
                ps.executeUpdate();
            }
        }
    }

    public void recordClockOut(int employeeId, LocalDate date, LocalDateTime time) throws SQLException {
        String sql = "UPDATE timecards SET clock_out = ?, updated_at = NOW() WHERE employee_id = ? AND work_date = ?";
        try (Connection con = DBManager.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setTimestamp(1, Timestamp.valueOf(time));
            ps.setInt(2, employeeId);
            ps.setDate(3, Date.valueOf(date));
            ps.executeUpdate();
        }
    }

    public void recordBreakStart(int employeeId, LocalDate date, LocalDateTime time) throws SQLException {
        String sql = "UPDATE timecards SET break_start = ?, updated_at = NOW() WHERE employee_id = ? AND work_date = ?";
        try (Connection con = DBManager.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setTimestamp(1, Timestamp.valueOf(time));
            ps.setInt(2, employeeId);
            ps.setDate(3, Date.valueOf(date));
            ps.executeUpdate();
        }
    }

    public void recordBreakEnd(int employeeId, LocalDate date, LocalDateTime time) throws SQLException {
        String sql = "UPDATE timecards SET break_end = ?, updated_at = NOW() WHERE employee_id = ? AND work_date = ?";
        try (Connection con = DBManager.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setTimestamp(1, Timestamp.valueOf(time));
            ps.setInt(2, employeeId);
            ps.setDate(3, Date.valueOf(date));
            ps.executeUpdate();
        }
    }
    
//    今日の打刻状態を取得するメソッド
    public TimeCardStatus getTodayStatus(int employeeId, LocalDate date) throws SQLException {
        String sql = "SELECT clock_in, clock_out, break_start, break_end FROM timecards WHERE employee_id = ? AND work_date = ?";
        try (Connection con = DBManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, employeeId);
            ps.setDate(2, Date.valueOf(date));
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                TimeCardStatus status = new TimeCardStatus();
                status.setClockIn(rs.getTimestamp("clock_in") != null);
                status.setClockOut(rs.getTimestamp("clock_out") != null);
                status.setBreakStart(rs.getTimestamp("break_start") != null);
                status.setBreakEnd(rs.getTimestamp("break_end") != null);
                return status;
            } else {
                return new TimeCardStatus(); // 全部falseで初期化
            }
        }
    }
}

