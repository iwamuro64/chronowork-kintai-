package dto;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class AttendanceDTO {
    private int timecardId;
    private int employeeId;
    private LocalDate workDate;
    private LocalDateTime clockIn;
    private LocalDateTime clockOut;
    private LocalDateTime breakStart;
    private LocalDateTime breakEnd;
    private String note;

    // 計算済みの表示用
    private String workHours;
    private String breakHours;
    private String overtimeHours;

    // --- Getters & Setters ---
    public int getTimecardId() { return timecardId; }
    public void setTimecardId(int timecardId) { this.timecardId = timecardId; }

    public int getEmployeeId() { return employeeId; }
    public void setEmployeeId(int employeeId) { this.employeeId = employeeId; }

    public LocalDate getWorkDate() { return workDate; }
    public void setWorkDate(LocalDate workDate) { this.workDate = workDate; }

    public LocalDateTime getClockIn() { return clockIn; }
    public void setClockIn(LocalDateTime clockIn) { this.clockIn = clockIn; }

    public LocalDateTime getClockOut() { return clockOut; }
    public void setClockOut(LocalDateTime clockOut) { this.clockOut = clockOut; }

    public LocalDateTime getBreakStart() { return breakStart; }
    public void setBreakStart(LocalDateTime breakStart) { this.breakStart = breakStart; }

    public LocalDateTime getBreakEnd() { return breakEnd; }
    public void setBreakEnd(LocalDateTime breakEnd) { this.breakEnd = breakEnd; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }

    public String getWorkHours() { return workHours; }
    public void setWorkHours(String workHours) { this.workHours = workHours; }

    public String getBreakHours() { return breakHours; }
    public void setBreakHours(String breakHours) { this.breakHours = breakHours; }

    public String getOvertimeHours() { return overtimeHours; }
    public void setOvertimeHours(String overtimeHours) { this.overtimeHours = overtimeHours; }

    // JSPでfmt:formatDateを使えるように、Date形式を返すgetterを追加
    public Date getClockInAsDate() {
        return clockIn != null ? Timestamp.valueOf(clockIn) : null;
    }

    public Date getClockOutAsDate() {
        return clockOut != null ? Timestamp.valueOf(clockOut) : null;
    }
    
    public Date getWorkDateAsDate() {
        if (this.workDate != null) {
            return java.sql.Date.valueOf(this.workDate);  // LocalDate -> java.sql.Date
        }
		return null;
	}

 // 特定の日付に対する出勤記録があるか確認するメソッド
    public static AttendanceDTO findRecordByDate(List<AttendanceDTO> attendanceList, LocalDate date) {
        for (AttendanceDTO record : attendanceList) {
            if (record.getWorkDate().equals(date)) {
                return record;
            }
        }
        return null; // 見つからなければnullを返す
    }
}
