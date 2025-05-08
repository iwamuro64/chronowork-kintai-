package dto;

import java.sql.Date;
import java.sql.Timestamp;

public class AttendanceRecord {
    private String name;
    private String department;
    private String position;
    private Date workDate;
    private Timestamp clockIn;
    private Timestamp clockOut;
    private Timestamp breakStart;
    private Timestamp breakEnd;
    private String note;

    // Getter / Setter
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }
    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPosition() {
        return position;
    }
    public void setPosition(String position) {
        this.position = position;
    }

    public Date getWorkDate() {
        return workDate;
    }
    public void setWorkDate(Date workDate) {
        this.workDate = workDate;
    }

    public Timestamp getClockIn() {
        return clockIn;
    }
    public void setClockIn(Timestamp clockIn) {
        this.clockIn = clockIn;
    }

    public Timestamp getClockOut() {
        return clockOut;
    }
    public void setClockOut(Timestamp clockOut) {
        this.clockOut = clockOut;
    }

    public Timestamp getBreakStart() {
        return breakStart;
    }
    public void setBreakStart(Timestamp breakStart) {
        this.breakStart = breakStart;
    }

    public Timestamp getBreakEnd() {
        return breakEnd;
    }
    public void setBreakEnd(Timestamp breakEnd) {
        this.breakEnd = breakEnd;
    }

    public String getNote() {
        return note;
    }
    public void setNote(String note) {
        this.note = note;
    }
}
