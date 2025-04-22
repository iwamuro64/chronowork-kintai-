package dto;

public class TimeCardStatus {
    private boolean clockIn;
    private boolean clockOut;
    private boolean breakStart;
    private boolean breakEnd;

    public boolean isClockIn() {
        return clockIn;
    }

    public void setClockIn(boolean clockIn) {
        this.clockIn = clockIn;
    }

    public boolean isClockOut() {
        return clockOut;
    }

    public void setClockOut(boolean clockOut) {
        this.clockOut = clockOut;
    }

    public boolean isBreakStart() {
        return breakStart;
    }

    public void setBreakStart(boolean breakStart) {
        this.breakStart = breakStart;
    }

    public boolean isBreakEnd() {
        return breakEnd;
    }

    public void setBreakEnd(boolean breakEnd) {
        this.breakEnd = breakEnd;
    }
}
