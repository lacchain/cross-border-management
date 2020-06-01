package us.lacchain.crossborder.management.service;

import us.lacchain.crossborder.management.model.UserView;
import us.lacchain.crossborder.management.model.User;
import us.lacchain.crossborder.management.model.Bank;
import us.lacchain.crossborder.management.model.Account;
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
       
            if (!bankRepository.existsById(request.getBankDetails().getBankTaxId())){
                Bank bank = new Bank(request.getBankDetails().getBankTaxId(), request.getBankDetails().getBankName(), request.getBankDetails().getBankCity());
                bankRepository.save(bank);
            }
        
            Account account = new Account(request.getAccountDetails().getDltAddress(), request.getBankDetails().getBankAccount(), "USD",0,0,request.getBankDetails().getBankTaxId(), user.getId());
            accountRepository.save(account);
        }else{
            throw new UserExistsException("User already exists");
        }

        return true;
    }

    public UserView getUser(String dltAddress)throws DataAccessException{
        return userViewRepository.findUserByDltAddress(dltAddress);
    }
}