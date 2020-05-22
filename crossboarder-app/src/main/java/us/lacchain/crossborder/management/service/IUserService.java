package us.lacchain.crossborder.management.service;

import us.lacchain.crossborder.management.model.User;
import us.lacchain.crossborder.management.model.UserView;
import us.lacchain.crossborder.management.clients.request.AddUserRequest;

import java.util.List;

public interface IUserService {

    boolean insert(AddUserRequest request);
    UserView getUser(String dltAddress);

}