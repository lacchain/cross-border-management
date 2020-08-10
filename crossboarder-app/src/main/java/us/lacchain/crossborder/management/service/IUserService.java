package us.lacchain.crossborder.management.service;

import us.lacchain.crossborder.management.model.UserView;
import us.lacchain.crossborder.management.model.User;
import us.lacchain.crossborder.management.clients.request.AddUserRequest;
import us.lacchain.crossborder.management.exception.UserExistsException;
import us.lacchain.crossborder.management.exception.TokenNotFoundException;
import org.springframework.dao.DataAccessException;
import org.springframework.mail.SimpleMailMessage;

public interface IUserService {

    boolean insert(AddUserRequest request)throws UserExistsException,Exception;
    UserView getUser(String dltAddress);
    UserView getUser(String dltAddress, String accountNumber);
    SimpleMailMessage generatePasswordToken(String email);
    boolean validatePasswordToken(String token)throws TokenNotFoundException, Exception;
    boolean resetPassword(String token, String newPassword)throws TokenNotFoundException, DataAccessException, Exception;
}