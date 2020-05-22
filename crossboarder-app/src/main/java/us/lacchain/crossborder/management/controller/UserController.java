package us.lacchain.crossborder.management.controller;

import us.lacchain.crossborder.management.service.IUserService;
import us.lacchain.crossborder.management.service.UserService;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;


import javax.persistence.EntityNotFoundException;

@RestController
@RequestMapping(value = "/api")
public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired
    private Token token;
    
    @PostMapping("/user")
    public ResponseEntity addUser(@RequestBody AddUserRequest requestBody){
        try {
            System.out.println("ADDUSER");
            userService.insert(requestBody);
            return ResponseEntity.ok().build();
        }
        catch (EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @PreAuthorize("hasRole('ROLE_USER') OR hasRole('ROLE_CITI')")
    @GetMapping("/user")
    public ResponseEntity<GetUserResponse> getUser(Authentication auth){
        System.out.println("GETUser");
        OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails)auth.getDetails();
        try {
            String dltAddress = (String)(token.parseJWT(details.getTokenValue()).getBody().get("dltAddress"));
            System.out.println("--FOR DltAddress:"+dltAddress);
            UserView userView = userService.getUser(dltAddress);
            AccountDetail accountDetail = new AccountDetail(userView.getCompany(),userView.getFullname(),userView.getEmail(),null,userView.getDlt_address(),userView.getCurrency(),userView.getBalance(),userView.getStatus());
            BankDetail bankDetail = new BankDetail(userView.getName(),userView.getTax_id(),userView.getCity(),userView.getBank_account());
            GetUserResponse response = new GetUserResponse(accountDetail,bankDetail);
            System.out.println(userView.toString());
            return ResponseEntity.accepted().body(response);
        }
        catch (EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @PreAuthorize("hasRole('ROLE_CITI')")
    @GetMapping("/user/{dltAddress}")
    public ResponseEntity<GetUserResponse> getUserByDltAddress(@PathVariable String dltAddress){
        try {
            System.out.println("GETUSERRR"+dltAddress);
            UserView userView = userService.getUser(dltAddress);
            AccountDetail accountDetail = new AccountDetail(userView.getCompany(),userView.getFullname(),userView.getEmail(),null,userView.getDlt_address(),userView.getCurrency(),userView.getBalance(),userView.getStatus());
            BankDetail bankDetail = new BankDetail(userView.getName(),userView.getTax_id(),userView.getCity(),userView.getBank_account());
            GetUserResponse response = new GetUserResponse(accountDetail,bankDetail);
            System.out.println(userView.toString());
            return ResponseEntity.accepted().body(response);
        }
        catch (EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }
}