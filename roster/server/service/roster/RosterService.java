package server.service.roster;

import server.classifier.UserType;
import server.service.GenericObject;
import server.service.GenericService;
import server.service.shift.IShiftPatternService;
import server.service.shift.ShiftPattern;
import server.service.shift.ShiftPatternService;
import server.service.user.*;
import service.exception.AdminAccessRequiredException;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class RosterService extends GenericService implements  IRosterService {
    @Override
    public Roster generateRoster(User loggedUser, RosterRequest rosterRequest) throws IOException, AdminAccessRequiredException {
        if(loggedUser.getType().equals(UserType.ADMIN))
            checkAdminAuthUser(loggedUser);

        IUserService userService = new UserServiceImpl();
        List<User> users = userService.readUserList();

        return generateRoster(rosterRequest, users);
    }

    private Roster generateRoster(RosterRequest rosterRequest, List<User> users) throws AdminAccessRequiredException {

        Roster roster = new Roster(rosterRequest.getYear(), rosterRequest.getMonth(), rosterRequest.getShiftTimingId());
        if (users==null)
            return  roster;

        for (User user : users) {
            if (skipUser(user, rosterRequest)) continue;;

            List<UserPattern> actualUserPatterns = getActualUserPatterns(user, roster.getStartDay(), roster.getEndDay());

            // TODO sort userPattern by starting Day, now use only first pattern
            // TODO assign several patterns, if exists
            // TODO take into account patternStartDay

            Date rosterCalcDate = roster.getStartDay();
            List<String> userRoster=new ArrayList<>();

            if (actualUserPatterns!=null && !actualUserPatterns.isEmpty()){
                rosterCalcDate = fillRosterBeforeValidPattern(userRoster, rosterCalcDate, actualUserPatterns.get(0));

                ShiftPattern shiftPattern = getShiftPatternDefinition(actualUserPatterns.get(0).getShiftPatternId());

                rosterCalcDate = fillRosterWithFullPattern(userRoster, rosterCalcDate, shiftPattern, roster.getEndDay());

                rosterCalcDate = fillRosterWithPatternPart(userRoster, rosterCalcDate, shiftPattern, roster.getEndDay());
            }
            roster.addUserRoster(user, userRoster);
        }
        return roster;
    }

    private ShiftPattern getShiftPatternDefinition(long shiftPatternId) throws AdminAccessRequiredException {
        IShiftPatternService shiftPatternService = new ShiftPatternService();

        return shiftPatternService.getShiftPattern(shiftPatternId);
    }

    private Date fillRosterWithPatternPart(List<String> userRoster, Date rosterCalcDate, ShiftPattern shiftPattern, Date endDay) {
        int i=0;
        while (rosterCalcDate.compareTo(endDay)<=0) {
            userRoster.add(shiftPattern.getDayDefinitions().get(i));
            rosterCalcDate = addDays(rosterCalcDate, 1);
            i++;
        }
        return rosterCalcDate;
    }

    private Date fillRosterWithFullPattern(List<String> userRoster, Date rosterCalcDate, ShiftPattern shiftPattern, Date rosterEndDate) {
        while (getDay(rosterCalcDate)+shiftPattern.getPatternLength() <= getDay(rosterEndDate)) {
            userRoster.addAll(shiftPattern.getDayDefinitions());
            rosterCalcDate = addDays(rosterCalcDate, shiftPattern.getPatternLength());
        }
        return rosterCalcDate;
    }

    private Date fillRosterBeforeValidPattern(List<String> userRoster, Date rosterCalcDate, UserPattern firstActualUserPattern) {
        while (rosterCalcDate.compareTo(firstActualUserPattern.getStartDay())<0) {
            userRoster.add(" ");
            rosterCalcDate = addDays(rosterCalcDate, 1);
        }
        return rosterCalcDate;
    }

    private boolean skipUser(User user, RosterRequest rosterRequest) {
        if (!user.getType().equals(UserType.USER)) return true;
        if (rosterRequest.getUserId()!=0 && user.getId()!=rosterRequest.getUserId()) return true;
        return false;
    }

    private List<UserPattern> getActualUserPatterns(User user, Date startDate, Date endDate) throws AdminAccessRequiredException {
        IUserPatternService userPatternService = new UserPatternService();
        List<UserPattern> userPatterns = userPatternService.readUserPatternList();

        return
                userPatterns == null ? null :
                        userPatterns.stream().
                                filter(p -> p.getUserId() == user.getId()
                                                && p.getStartDay().compareTo(endDate) <= 0
                                                && (p.getEndDay() == null || p.getEndDay().compareTo(startDate) >= 0)
                                ).collect(Collectors.toList());
    }

    private static Date addDays(Date date, int days)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days); //minus number would decrement the days
        return cal.getTime();
    }

    private static int getDay(Date date)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_MONTH);
    }

    @Override
    protected String[] getRecord(GenericObject object) {
        return new String[0];
    }
}
