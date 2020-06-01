package us.lacchain.crossborder.management.controller;

import us.lacchain.crossborder.management.service.IUserService;
import us.lacchain.crossborder.management.model.UserView;
import us.lacchain.crossborder.management.clients.request.AddUserRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import us.lacchain.crossborder.management.clients.request.AccountDetail;
import us.lacchain.crossborder.management.clients.request.BankDetail;
import us.lacchain.crossborder.management.clients.response.GetUserResponse;
import us.lacchain.crossborder.management.util.Token;
import us.lacchain.crossborder.management.exception.UserExistsException;
import us.lacchain.crossborder.management.exception.DLTAddressExistsException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.http.HttpStatus;
import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping(value = "/api")
public class UserController {

    Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private IUserService userService;

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
            BankDetail bankDetail = new BankDetail(userView.getName(),userView.getTax_id(),userView.getCity(),userView.getBank_account());
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
            BankDetail bankDetail = new BankDetail(userView.getName(),userView.getTax_id(),userView.getCity(),userView.getBank_account());
            GetUserResponse response = new GetUserResponse(accountDetail,bankDetail);
            logger.debug("response:"+response);
            return ResponseEntity.accepted().body(response);
        }
        catch (EntityNotFoundException e){
            logger.warn(e.getMessage());
            return ResponseEntity.notFound().build();
        }catch  (Exception ex){
            logger.error(ex.getMessage(),ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}