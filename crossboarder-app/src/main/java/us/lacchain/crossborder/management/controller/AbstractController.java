package us.lacchain.crossborder.management.controller;

import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpSession;

/**
 * @author Adrian Pareja
 */
public class AbstractController {
    protected static final String VIEW__ERROR = "error";
    protected static final String VIEW__LOGIN = "login";
    protected static final String VIEW__BUSY = "busy";
    protected static final String VIEW__ERROR_SUBSCRIPTION = "error_subscription";

    protected static final String PARAMETER__ACCOUNT_ID = "accountId";

    protected static final String SPRING_SECURITY_SAVED_REQUEST = "SPRING_SECURITY_SAVED_REQUEST";

/*    @ExceptionHandler(BadRequestException.class)
    public String handleError(HttpSession session) {
        session.invalidate();
        return VIEW__ERROR;
    }

    @ExceptionHandler(Exception.class)
    public String handleUnexpectedException(HttpSession session) {
        session.invalidate();
        return VIEW__ERROR;
    }

    @ExceptionHandler(BusyException.class)
    public String handleBusy(HttpSession session) {
        session.invalidate();
        return VIEW__BUSY;
    }

    @ExceptionHandler(SubscriptionExistsException.class)
    public String handleSubscription(HttpSession session) {
        session.invalidate();
        return VIEW__ERROR_SUBSCRIPTION;
    } */

}
