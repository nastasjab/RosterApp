package server.service.roster;


import server.service.user.User;
import service.exception.AdminAccessRequiredException;

import java.io.IOException;

public interface IRosterService {
    Roster generateRoster(User currentUser, RosterRequest rosterRequest) throws IOException, AdminAccessRequiredException;
}
