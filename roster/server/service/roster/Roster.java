package server.service.roster;

import server.service.GenericObject;
import server.service.shift.IShiftTimingService;
import server.service.shift.ShiftTiming;
import server.service.shift.ShiftTimingService;
import server.service.user.User;
import service.exception.AdminAccessRequiredException;

import java.util.*;

public class Roster extends GenericObject {
    private int year;
    private int month;
    private boolean published;
    private ShiftTiming shiftTiming;
    private Map<User,List<String>> usersRoster;

    public Roster() {
        published=false;
        usersRoster = new HashMap<>();
    }

    public Roster(int year, int month, long shiftTimingId, User currentUser) throws AdminAccessRequiredException {
        super();
        this.year = year;
        this.month = month;
        IShiftTimingService shiftTimingService = new ShiftTimingService();
        shiftTiming =  shiftTimingService.getShiftTiming(currentUser, shiftTimingId);
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public Date getStartDay() {
        return (new GregorianCalendar(year, month-1, 1)).getTime();
    }

    public Date getEndDay() {
        return (new GregorianCalendar(year, month-1, getDaysInMonth())).getTime();
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public ShiftTiming getShiftTiming() {
        return shiftTiming;
    }

    public void setShiftTiming(ShiftTiming shiftTiming) {
        this.shiftTiming = shiftTiming;
    }

    public Map<User, List<String>> getUsersRoster() {
        return usersRoster;
    }

    private int getDaysInMonth(){
        int iYear = year;
        int iMonth = month-1;
        int iDay = 1;
        Calendar mycal = new GregorianCalendar(iYear, iMonth, iDay);
        return mycal.getActualMaximum(Calendar.DAY_OF_MONTH); // 28
    }

    public void addUserRoster(User user, List<String> userRoster) {
        usersRoster.put(user, userRoster);
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append(String.format("Year: %d  Month: %d\n", year, month));
        // TODO print info about shift timing
        str.append(String.format("%60s Day#", " "));
        str.append(String.format("%20s ", "Employee"));
        for (int i=1; i<getDaysInMonth(); i++)
            str.append(String.format("%2s ", i));

        Iterator it = usersRoster.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            str.append(String.format("%20s ", ((User)pair.getKey()).getNameSurname()));
            for (int i=0; i< ((List<String>)pair.getValue()).size(); i++)
                str.append(String.format("%2s ", ((List<String>)pair.getValue()).get(i)));

            it.remove();
        }
        return  str.toString();
    }
}
