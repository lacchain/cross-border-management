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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.PostConstruct;
import com.google.common.hash.Hashing;
import java.nio.charset.StandardCharsets;

import org.springframework.dao.DataAccessException;
import us.lacchain.crossborder.management.exception.UserExistsException;
import us.lacchain.crossborder.management.exception.TokenNotFoundException;
import us.lacchain.crossborder.management.exception.DLTAddressExistsException;

import org.springframework.mail.SimpleMailMessage;

import java.util.UUID;
import java.time.LocalDateTime;
import java.util.Calendar;

@Service
public class UserService implements IUserService {

    @Value("${crossborder.token.hours}")
    private int hours;

    @Value("${crossborder.token.url}")
    private String appURL;

    @Value("${crossborder.mail.from}")
    private String from;

    @Value("${crossborder.mail.subject}")
    private String subject;

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
            passwordToken.setExpiryDate(LocalDateTime.now().plusHours(hours));
            passwordToken.setUser_id(user.getId());
            passwordTokenRepository.save(passwordToken);

            SimpleMailMessage passwordResetEmail = new SimpleMailMessage();
			passwordResetEmail.setFrom(from);
			passwordResetEmail.setTo(user.getEmail());
			passwordResetEmail.setSubject(subject);
			passwordResetEmail.setText("To reset your password, click the link below:\n" + appURL + "/" + passwordToken.getToken());
                    
            return passwordResetEmail;
        }
        return null;
    }

    public boolean validatePasswordToken(String token)throws TokenNotFoundException, Exception{
        PasswordToken passwordToken = passwordTokenRepository.findByToken(token);
        if (passwordToken!=null){
            if (LocalDateTime.now().isBefore(passwordToken.getExpiryDate())){    
                return true;
            }else{
                return false;
            }
        }
        throw new TokenNotFoundException("Token doesn't exist");
    }

    public boolean resetPassword(String token, String newPassword)throws TokenNotFoundException, DataAccessException, Exception{
        if (validatePasswordToken(token)){
            if (userRepository.resetPassword(token, newPassword)>0){
                passwordTokenRepository.removeToken(token);
                return true;
            }else{
                throw new TokenNotFoundException("Token doesn't exist");
            }
        }
        return false;
    }

}