package service.service.user;

import service.domain.*;
import java.io.IOException;
import java.util.List;

public class UserServiceImpl extends UserService {

    private static final String fileName= "users.csv";

    @Override
    protected void saveUserList(List<User> users) throws IOException {
        saveList(fileName, users);
    }

    @Override
    public List<User> readUserList() throws IOException {
        String[] columns = new String[] {"id", "type", "login", "password", "name",
                "surname", "email", "phone"};
        return readList(fileName, columns, User.class);
    }


    @Override
    protected String[] getRecord(GenericObject object) {
        User us=(User)object;
        return new String[]{
                String.valueOf(us.getId()),
                String.valueOf(us.getType()),
                us.getLogin(),
                us.getPassword(),
                us.getName(),
                us.getSurname(),
                us.getEmail(),
                us.getPhone()
        };
    }
}
