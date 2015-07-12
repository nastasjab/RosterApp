package server.service.user;

import server.service.GenericObject;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UserPattern extends GenericObject {
    private long userId;
    private long shiftPatternId;
    private Date startDay;
    private Date endDay;

    public UserPattern() {
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getShiftPatternId() {
        return shiftPatternId;
    }

    public void setShiftPatternId(long shiftPatternId) {
        this.shiftPatternId = shiftPatternId;
    }

    public Date getStartDay() {
        return startDay;
    }

    public void setStartDay(Date startDay) {
        this.startDay = startDay;
    }

    public Date getEndDay() {
        return endDay;
    }

    public void setEndDay(Date endDay) {
        this.endDay = endDay;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        Format formatter = new SimpleDateFormat("yy.MM.dd");
        String endDayStr = endDay==null? "<open>" : formatter.format(endDay);

        stringBuilder.append(String.format("%7d %7d %10s %10s",
                userId, shiftPatternId, formatter.format(startDay), endDayStr));
        return stringBuilder.toString();
    }
}
