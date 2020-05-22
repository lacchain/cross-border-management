package us.lacchain.crossborder.management.controller;

import us.lacchain.crossborder.management.service.IAccountService;
import us.lacchain.crossborder.management.service.AccountService;
import us.lacchain.crossborder.management.model.Movement;
import us.lacchain.crossborder.management.model.MovementDetail;
import us.lacchain.crossborder.management.model.AccountResult;
import us.lacchain.crossborder.management.model.Transaction;
import us.lacchain.crossborder.management.clients.request.AddUserRequest;
import us.lacchain.crossborder.management.clients.response.CustomerDetail;
import us.lacchain.crossborder.management.clients.response.TransferDetail;
import us.lacchain.crossborder.management.clients.response.TransactionHistory;
import us.lacchain.crossborder.management.clients.response.GetMovementDetailResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import us.lacchain.crossborder.management.model.MovementResult;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;

import javax.persistence.EntityNotFoundException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;

import us.lacchain.crossborder.management.util.Token;

@RestController
@RequestMapping(value = "/api")
public class AccountController {

    @Autowired
    private IAccountService accountService;

    @Autowired
    private Token token;

    @PreAuthorize("hasRole('ROLE_CITI')")
    @GetMapping("/account")
    public ResponseEntity getAllAccounts(){
        System.out.println("GETAllAccounts");
        
        try {
            List<AccountResult> response = accountService.getAllAccounts();
            System.out.println(response);
            return ResponseEntity.accepted().body(response);
        }
        catch (EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PreAuthorize("hasRole('ROLE_CITI')")
    @GetMapping("/account/{dltAddress}/movements")
    public ResponseEntity getMovementsByDltAddress(@PathVariable String dltAddress){
        System.out.println("GETMovementsByDltAddress");
        try {
            System.out.println("--FOR DltAddress:"+dltAddress);
            List<MovementResult> response = accountService.getMovementsByAccount(dltAddress);
            System.out.println(response);
            return ResponseEntity.accepted().body(response);
        }
        catch (EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PreAuthorize("hasRole('ROLE_USER') OR hasRole('ROLE_CITI')")
    @GetMapping("/account/movements")
    public ResponseEntity getMovementsByAccount(Authentication auth){
        System.out.println("GETMovementsByAccount");
        OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails)auth.getDetails();
        try {
            String dltAddress = (String)(token.parseJWT(details.getTokenValue()).getBody().get("dltAddress"));
            System.out.print("--FOR DltAddress:"+dltAddress);
            List<MovementResult> response = accountService.getMovementsByAccount(dltAddress);
            System.out.println(response);
            return ResponseEntity.accepted().body(response);
        }
        catch (EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PreAuthorize("hasRole('ROLE_CITI')")
    @GetMapping("/account/transactions")
    public ResponseEntity getAllTransactions(){
        System.out.println("GETAllTransactions");
        try {
            List<Transaction> response = accountService.getTransactions();
            System.out.println(response);
            return ResponseEntity.accepted().body(response);
        }
        catch (EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        }catch (Exception ex){
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PreAuthorize("hasRole('ROLE_USER') OR hasRole('ROLE_CITI')")
    @GetMapping("/account/movements/{movementId}")
    public ResponseEntity getMovementDetail(@PathVariable long movementId, Authentication auth){
        System.out.println("GETMovementDetail");
        try {
            OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails)auth.getDetails();
            String dltAddress = (String)(token.parseJWT(details.getTokenValue()).getBody().get("dltAddress"));
            System.out.print("--FOR DltAddress:"+dltAddress);
            MovementDetail movementDetail = accountService.getMovementDetail(movementId, dltAddress);
            System.out.println(movementDetail);
            TransferDetail transferDetail = new TransferDetail(movementDetail.getSent_amount(),movementDetail.getFee_applied(),movementDetail.getConverted_amount(),movementDetail.getRate_applied(),movementDetail.getRecipient_will_get());
            CustomerDetail senderDetail = new CustomerDetail(movementDetail.getSender_name(),movementDetail.getSender_bank(),movementDetail.getSender_bank_account(),movementDetail.getSender_dlt_address());
            CustomerDetail recipientDetail = new CustomerDetail(movementDetail.getReceiver_name(),movementDetail.getReceiver_bank(),movementDetail.getReceiver_bank_account(),movementDetail.getReceiver_dlt_address());
            TransactionHistory transactionHistory = new TransactionHistory(movementDetail.getOperation_requested(),movementDetail.getSet_fee(),movementDetail.getOperation_approved());
            GetMovementDetailResponse response = new GetMovementDetailResponse(movementDetail.getId(),movementDetail.getStatus(),transferDetail,senderDetail,recipientDetail,transactionHistory);
            return ResponseEntity.accepted().body(response);
        }
        catch (EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}