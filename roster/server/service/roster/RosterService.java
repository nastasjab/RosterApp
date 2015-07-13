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

public class RosterService extends GenericService implements  IRosterService {
    @Override
    public Roster generateRoster(User currentUser, RosterRequest rosterRequest) throws IOException, AdminAccessRequiredException {
        IUserPatternService userPatternService = new UserPatternService();
        IShiftPatternService shiftPatternService = new ShiftPatternService();
        IUserService userService = new UserServiceImpl();
        Roster roster = new Roster(rosterRequest.getYear(), rosterRequest.getMonth(), rosterRequest.getShiftTimingId(), currentUser);
        List<User> users = userService.readUserList();
        List<UserPattern> userPatterns = userPatternService.readUserPatternList(currentUser);



        

        for (User user : users) {
            if (!user.getType().equals(UserType.USER)) continue;
            if (rosterRequest.getUserId()!=0 && user.getId()!=rosterRequest.getUserId()) continue;

            List<UserPattern> actualUserPatterns =
                    userPatterns == null ? null :
                            (List<UserPattern>) userPatterns.stream().
                                    filter(p -> p.getUserId() == user.getId()
                                            && p.getStartDay().compareTo(roster.getEndDay()) <= 0
                                            && (p.getEndDay()==null || p.getEndDay().compareTo(roster.getStartDay()) >= 0)
                                    );
            // TODO sort userPattern by starting Day, now use only first pattern
            // TODO assign several patterns, if exists
            // TODO take into account patternStartDay
            Date rosterDate = roster.getStartDay();
            List<String> userRoster=new ArrayList<>();
            if (actualUserPatterns!=null){
                while (rosterDate.compareTo(actualUserPatterns.get(0).getStartDay())<0) {
                    userRoster.add("R");
                    addDays(rosterDate, 1);
                }
                ShiftPattern shiftPattern = shiftPatternService.getShiftPattern(currentUser,
                        actualUserPatterns.get(0).getShiftPatternId());

                while (getDay(rosterDate)+shiftPattern.getPatternLength() <= getDay(roster.getEndDay())) {
                    userRoster.addAll(shiftPattern.getDayDefinitions());
                    addDays(rosterDate, shiftPattern.getPatternLength());
                }

                int i=0;
                while (getDay(rosterDate) <= getDay(roster.getEndDay())) {
                    userRoster.add(shiftPattern.getDayDefinitions().get(i));
                    addDays(rosterDate, 1);
                    i++;
                }
            }
            roster.addUserRoster(user, userRoster);
        }


        return roster;
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
        return cal.get(Calendar.DAY_OF_MONTH);
    }

    @Override
    protected String[] getRecord(GenericObject object) {
        return new String[0];
    }
}
