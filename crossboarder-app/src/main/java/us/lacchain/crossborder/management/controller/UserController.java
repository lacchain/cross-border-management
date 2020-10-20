package us.lacchain.crossborder.management.controller;

import us.lacchain.crossborder.management.service.IUserService;
import us.lacchain.crossborder.management.model.UserView;
import us.lacchain.crossborder.management.service.IEmailService;
import us.lacchain.crossborder.management.clients.request.AddUserRequest;
import us.lacchain.crossborder.management.clients.request.ForgotPasswordRequest;
import us.lacchain.crossborder.management.clients.request.ResetPasswordRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import us.lacchain.crossborder.management.clients.request.AccountDetail;
import us.lacchain.crossborder.management.clients.request.BankDetail;
import us.lacchain.crossborder.management.clients.response.CustomerDetail;
import us.lacchain.crossborder.management.clients.response.GetUserResponse;
import us.lacchain.crossborder.management.util.Token;
import us.lacchain.crossborder.management.exception.UserExistsException;
import us.lacchain.crossborder.management.exception.DLTAddressExistsException;
import us.lacchain.crossborder.management.exception.TokenNotFoundException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.http.HttpStatus;
import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import org.springframework.mail.SimpleMailMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping(value = "/api")
public class UserController {

    Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private IUserService userService;

    @Autowired
	private IEmailService emailService;

    @Autowired
    private Token token;
    
    @PostMapping("/user")
    public ResponseEntity addUser(@Valid @RequestBody AddUserRequest requestBody){
        try {
            logger.info("ADDUSER");
            userService.insert(requestBody);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        catch(UserExistsException bex){
            logger.warn(bex.getMessage());
            return ResponseEntity.status(601).build();    
        }
        catch(DLTAddressExistsException dex){
            logger.warn(dex.getMessage());
            return ResponseEntity.status(602).build();    
        }
        catch (EntityNotFoundException e){
            logger.warn(e.getMessage());
            return ResponseEntity.notFound().build();
        }catch  (Exception ex){
            logger.error(ex.getMessage(),ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PreAuthorize("hasRole('ROLE_USER') OR hasRole('ROLE_CITI')")
    @GetMapping("/user")
    public ResponseEntity<GetUserResponse> getUser(Authentication auth){
        logger.info("GET /user");
        OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails)auth.getDetails();
        try {
            String dltAddress = (String)(token.parseJWT(details.getTokenValue()).getBody().get("dltAddress"));
            logger.debug("--FOR DltAddress:"+dltAddress);
            UserView userView = userService.getUser(dltAddress);
            if (userView == null){
                return ResponseEntity.noContent().build();
            }
            AccountDetail accountDetail = new AccountDetail(userView.getCompany(),userView.getFullname(),userView.getEmail(),null,userView.getDlt_address(),userView.getCurrency(),userView.getBalance(),userView.getStatus());
            BankDetail bankDetail = new BankDetail(userView.getName(),userView.getTax_id(),userView.getCode(),userView.getCity(),userView.getBank_account());
            GetUserResponse response = new GetUserResponse(accountDetail,bankDetail);
            logger.debug("response:"+response);
            return ResponseEntity.ok().body(response);
        }
        catch (EntityNotFoundException e){
            logger.warn(e.getMessage());
            return ResponseEntity.notFound().build();
        }catch  (Exception ex){
            logger.error(ex.getMessage(),ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PreAuthorize("hasRole('ROLE_CITI')")
    @GetMapping("/user/{dltAddress}")
    public ResponseEntity<GetUserResponse> getUserByDltAddress(@PathVariable String dltAddress){
        try {
            logger.info("GET /user/"+dltAddress);
            UserView userView = userService.getUser(dltAddress);
            if (userView == null){
                return ResponseEntity.noContent().build();
            }
            AccountDetail accountDetail = new AccountDetail(userView.getCompany(),userView.getFullname(),userView.getEmail(),null,userView.getDlt_address(),userView.getCurrency(),userView.getBalance(),userView.getStatus());
            BankDetail bankDetail = new BankDetail(userView.getName(),userView.getTax_id(),userView.getCode(),userView.getCity(),userView.getBank_account());
            GetUserResponse response = new GetUserResponse(accountDetail,bankDetail);
            logger.debug("response:"+response);
            return ResponseEntity.ok().body(response);
        }
        catch (EntityNotFoundException e){
            logger.warn(e.getMessage());
            return ResponseEntity.notFound().build();
        }catch  (Exception ex){
            logger.error(ex.getMessage(),ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/user/{dltAddress}/{accountNumber}")
    public ResponseEntity<CustomerDetail> getUserRecipient(@PathVariable String dltAddress,@PathVariable String accountNumber){
        try {
            logger.info("GET /user/"+dltAddress+"/"+accountNumber);
            UserView userView = userService.getUser(dltAddress, accountNumber);
            if (userView == null){
                return ResponseEntity.noContent().build();
            }
            CustomerDetail response = new CustomerDetail(userView.getFullname(), userView.getName(), userView.getBank_account(), userView.getDlt_address()); 
            logger.debug("response:"+response);
            return ResponseEntity.ok().body(response);
        }
        catch (EntityNotFoundException e){
            logger.warn(e.getMessage());
            return ResponseEntity.notFound().build();
        }catch  (Exception ex){
            logger.error(ex.getMessage(),ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/user/forgot")
    public ResponseEntity forgotPassword(@Valid @RequestBody ForgotPasswordRequest requestBody){
        try {
            logger.info("FORGOT-PASSWORD");
            SimpleMailMessage passwordResetEmail = userService.generatePasswordToken(requestBody.getEmail());
            if (passwordResetEmail!=null){
                emailService.sendEmail(passwordResetEmail);
                return ResponseEntity.status(HttpStatus.OK).build();
            }
            return ResponseEntity.notFound().build();
        }
        catch (EntityNotFoundException e){
            logger.warn(e.getMessage());
            return ResponseEntity.notFound().build();
        }catch  (Exception ex){
            logger.error(ex.getMessage(),ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/user/forgot/validate/{token}")
    public ResponseEntity<GetUserResponse> validatePasswordToken(@PathVariable String token){
        try {
            logger.info("VALIDATE-PASSWORD-TOKEN");
            logger.info("token:"+token);
            if (userService.validatePasswordToken(token)){
                return ResponseEntity.status(HttpStatus.OK).build();
            }else{
                return ResponseEntity.status(HttpStatus.GONE).build();
            }  
        }catch(TokenNotFoundException bex){
            logger.warn(bex.getMessage());
            return ResponseEntity.notFound().build();    
        }catch  (Exception ex){
            logger.error(ex.getMessage(),ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/user/reset")
    public ResponseEntity resetPassword(@Valid @RequestBody ResetPasswordRequest requestBody){
        try {
            logger.info("RESET-PASSWORD");
            if(userService.resetPassword(requestBody.getToken(),requestBody.getPassword())){
                return ResponseEntity.status(HttpStatus.OK).build();
            }else{
                return ResponseEntity.status(HttpStatus.GONE).build();
            }
        }catch(TokenNotFoundException bex){
            logger.warn(bex.getMessage());
            return ResponseEntity.notFound().build();    
        }catch (EntityNotFoundException e){
            logger.warn(e.getMessage());
            return ResponseEntity.notFound().build();
        }catch  (Exception ex){
            logger.error(ex.getMessage(),ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}