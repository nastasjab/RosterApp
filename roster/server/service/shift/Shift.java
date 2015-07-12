package server.service.shift;

public class Shift {
    String timeFrom;
    String timeTo;
    int minEmployeeCount;

    public Shift() {
    }

    public Shift(String timeFrom, String timeTo, int minEmployeeCount) {
        this.timeFrom = timeFrom;
        this.timeTo = timeTo;
        this.minEmployeeCount = minEmployeeCount;
    }

    public String getTimeFrom() {
        return timeFrom;
    }

    public void setTimeFrom(String timeFrom) {
        this.timeFrom = timeFrom;
    }

    public String getTimeTo() {
        return timeTo;
    }

    public void setTimeTo(String timeTo) {
        this.timeTo = timeTo;
    }

    public int getMinEmployeeCount() {
        return minEmployeeCount;
    }

    public void setMinEmployeeCount(int minEmployeeCount) {
        this.minEmployeeCount = minEmployeeCount;
    }
}
