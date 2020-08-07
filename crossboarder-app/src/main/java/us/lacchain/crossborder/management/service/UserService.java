package us.lacchain.crossborder.management.service;

import us.lacchain.crossborder.management.model.UserView;
import us.lacchain.crossborder.management.model.User;
import us.lacchain.crossborder.management.model.Bank;
import us.lacchain.crossborder.management.model.Account;
import us.lacchain.crossborder.management.model.PasswordToken;
import us.lacchain.crossborder.management.repository.PasswordTokenRepository;
import us.lacchain.crossborder.management.repository.UserRepository;
import us.lacchain.crossborder.management.repository.BankRepository;
import us.lacchain.crossborder.management.repository.AccountRepository;
import us.lacchain.crossborder.management.repository.UserViewRepository;
import us.lacchain.crossborder.management.clients.request.AddUserRequest;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.PostConstruct;
import com.google.common.hash.Hashing;
import java.nio.charset.StandardCharsets;

import org.springframework.dao.DataAccessException;
import us.lacchain.crossborder.management.exception.UserExistsException;
import us.lacchain.crossborder.management.exception.DLTAddressExistsException;

import org.springframework.mail.SimpleMailMessage;

import java.util.UUID;
import java.time.LocalDateTime;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BankRepository bankRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserViewRepository userViewRepository;

    @Autowired
    private PasswordTokenRepository passwordTokenRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public boolean insert(AddUserRequest request)throws UserExistsException, DLTAddressExistsException, Exception{
        String hashId = Hashing.sha256().hashString(request.getAccountDetails().getFullname()+request.getAccountDetails().getEmail(), StandardCharsets.UTF_8).toString();
        User user = new User(hashId, request.getAccountDetails().getFullname(), request.getAccountDetails().getEmail(), request.getAccountDetails().getPassword(), request.getAccountDetails().getCompany(),"ROLE_USER");
        if (!userRepository.existsById(user.getId())){
            if (accountRepository.existsById(request.getAccountDetails().getDltAddress())){
                throw new DLTAddressExistsException("DLT Address is already in use");    
            }
            userRepository.save(user);
       
            if (!bankRepository.existsById(request.getBankDetails().getBankTaxId().toUpperCase())){
                Bank bank = new Bank(request.getBankDetails().getBankTaxId().toUpperCase(), request.getBankDetails().getBankName().toUpperCase(), request.getBankDetails().getBankCity().toUpperCase());
                bankRepository.save(bank);
            }
        
            Account account = new Account(request.getAccountDetails().getDltAddress().toUpperCase(), request.getBankDetails().getBankAccount().toUpperCase(), null,0,0,request.getBankDetails().getBankTaxId().toUpperCase(), user.getId());
            accountRepository.save(account);
        }else{
            throw new UserExistsException("User already exists");
        }

        return true;
    }

    public UserView getUser(String dltAddress)throws DataAccessException{
        return userViewRepository.findUserByDltAddress(dltAddress.toUpperCase());
    }

    public UserView getUser(String dltAddress, String accountNumber)throws DataAccessException{
        return userViewRepository.findUserByAddressAccount(dltAddress.toUpperCase(), accountNumber);
    }

    public SimpleMailMessage generatePasswordToken(String email)throws DataAccessException{
        User user = userRepository.findByEmail(email);
        if (user!=null){
            PasswordToken passwordToken = new PasswordToken();
            passwordToken.setToken(UUID.randomUUID().toString());
            passwordToken.setExpiryDate(LocalDateTime.now());
            passwordToken.setUser_id(user.getId());
            passwordTokenRepository.save(passwordToken);

 //           String appUrl = request.getScheme() + "://" + request.getServerName();
            String appUrl = "http://www.monarca.org/api/user";

            SimpleMailMessage passwordResetEmail = new SimpleMailMessage();
			passwordResetEmail.setFrom("support@demo.com");
			passwordResetEmail.setTo(user.getEmail());
			passwordResetEmail.setSubject("Password Reset Request");
			passwordResetEmail.setText("To reset your password, click the link below:\n" + appUrl
                    + "/reset?token=" + passwordToken.getToken());
                    
            return passwordResetEmail;
        }
        return null;
    }

}