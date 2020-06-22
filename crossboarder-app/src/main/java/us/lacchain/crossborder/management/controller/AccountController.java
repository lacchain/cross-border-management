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

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;

import us.lacchain.crossborder.management.util.Token;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping(value = "/api")
public class AccountController {

    Logger logger = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private IAccountService accountService;

    @Autowired
    private Token token;
    
    @PreAuthorize("hasRole('ROLE_CITI')")
    @GetMapping("/account")
    public ResponseEntity getAllAccounts(){
        logger.info("GET /account");
        try {
            List<AccountResult> response = accountService.getAllAccounts();
            if (response == null || response.size()==0){
                return ResponseEntity.noContent().build();
            }
            logger.debug("Response:"+response);
            return ResponseEntity.ok().body(response);
        }
        catch (EntityNotFoundException e){
            logger.warn(e.getMessage());
            return ResponseEntity.notFound().build();
        }catch (Exception ex){
            logger.error(ex.getMessage(),ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PreAuthorize("hasRole('ROLE_CITI')")
    @GetMapping("/account/{dltAddress}/movements")
    public ResponseEntity getMovementsByDltAddress(@PathVariable String dltAddress){
        logger.info("GET /account/"+dltAddress+"/movements");
        try {
            List<MovementResult> response = accountService.getMovementsByAccount(dltAddress);
            if (response == null || response.size()==0){
                return ResponseEntity.noContent().build();
            }
            logger.debug("Response:"+response);
            return ResponseEntity.ok().body(response);
        }
        catch (EntityNotFoundException e){
            logger.warn(e.getMessage());
            return ResponseEntity.notFound().build();
        }catch (Exception ex){
            logger.error(ex.getMessage(),ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PreAuthorize("hasRole('ROLE_USER') OR hasRole('ROLE_CITI')")
    @GetMapping("/account/movements")
    public ResponseEntity getMovementsByAccount(Authentication auth){
        logger.info("GET /account/movements");
        OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails)auth.getDetails();
        try {
            String dltAddress = (String)(token.parseJWT(details.getTokenValue()).getBody().get("dltAddress"));
            logger.debug("--FOR DltAddress:"+dltAddress);
            List<MovementResult> response = accountService.getMovementsByAccount(dltAddress);
            if (response == null || response.size()==0){
                return ResponseEntity.noContent().build();
            }
            logger.debug("Response"+response);
            return ResponseEntity.ok().body(response);
        }
        catch (EntityNotFoundException e){
            logger.warn(e.getMessage());
            return ResponseEntity.notFound().build();
        }catch (Exception ex){
            logger.error(ex.getMessage(),ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PreAuthorize("hasRole('ROLE_CITI')")
    @GetMapping("/account/transactions")
    public ResponseEntity getAllTransactions(){
        logger.info("GET /account/transactions");
        try {
            List<Transaction> response = accountService.getTransactions();
            if (response == null || response.size()==0){
                return ResponseEntity.noContent().build();
            }
            logger.debug("Response:"+response);
            return ResponseEntity.ok().body(response);
        }
        catch (EntityNotFoundException e){
            logger.warn(e.getMessage());
            return ResponseEntity.notFound().build();
        }catch (Exception ex){
            logger.error(ex.getMessage(),ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PreAuthorize("hasRole('ROLE_USER') OR hasRole('ROLE_CITI')")
    @GetMapping("/account/movements/{movementId}")
    public ResponseEntity getMovementDetail(@PathVariable String movementId, Authentication auth){
        logger.info("GET /account/movements/movementId");
        try {
            List<GrantedAuthority> authorities = new ArrayList<>(); 
            authorities.addAll(auth.getAuthorities());
            logger.info("Role:"+authorities.get(0));
            MovementDetail movementDetail = new MovementDetail();
            if ("ROLE_CITI".equalsIgnoreCase(authorities.get(0).toString())){
                movementDetail = accountService.getMovementDetail(movementId);
            }else{
                OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails)auth.getDetails();
                String dltAddress = (String)(token.parseJWT(details.getTokenValue()).getBody().get("dltAddress"));
                logger.debug("--FOR DltAddress:"+dltAddress);
                movementDetail = accountService.getMovementDetail(movementId, dltAddress);
            }
            if (movementDetail == null){
                return ResponseEntity.noContent().build();
            }
            TransferDetail transferDetail = new TransferDetail(movementDetail.getSent_amount(),movementDetail.getFee_applied(),movementDetail.getConverted_amount(),movementDetail.getRate_applied(),movementDetail.getRecipient_will_get(), movementDetail.getSender_currency(), movementDetail.getReceiver_currency());
            CustomerDetail senderDetail = new CustomerDetail(movementDetail.getSender_name(),movementDetail.getSender_bank(),movementDetail.getSender_bank_account(),movementDetail.getSender_dlt_address());
            CustomerDetail recipientDetail = new CustomerDetail(movementDetail.getReceiver_name(),movementDetail.getReceiver_bank(),movementDetail.getReceiver_bank_account(),movementDetail.getReceiver_dlt_address());
            TransactionHistory transactionHistory = new TransactionHistory(movementDetail.getOperation_requested(),movementDetail.getSet_fee(),movementDetail.getOperation_approved());
            GetMovementDetailResponse response = new GetMovementDetailResponse(movementDetail.getId(),movementDetail.getStatus(),transferDetail,senderDetail,recipientDetail,transactionHistory);
            logger.debug("Response:"+response);
            return ResponseEntity.ok().body(response);
        }
        catch (EntityNotFoundException e){
            logger.warn(e.getMessage());
            return ResponseEntity.notFound().build();
        }catch (Exception ex){
            logger.error(ex.getMessage(),ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}