package server.service.roster;

public class RosterRequest {
    private int year;
    private int month;
    private long shiftTimingId;
    private long userId;

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public long getShiftTimingId() {
        return shiftTimingId;
    }

    public void setShiftTimingId(long shiftTimingId) {
        this.shiftTimingId = shiftTimingId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
