package kz.edy.astanait.product.controller.security;

import kz.edy.astanait.product.dto.requestDto.UserDtoRequest;
import kz.edy.astanait.product.dto.responseDto.UserDtoResponse;
import kz.edy.astanait.product.enviroment.EmailEnvironmentBuilder;
import kz.edy.astanait.product.exception.ExceptionHandling;
import kz.edy.astanait.product.model.User;
import kz.edy.astanait.product.model.security.ResetEmailVerification;
import kz.edy.astanait.product.service.UserService;
import kz.edy.astanait.product.service.security.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.mail.MessagingException;

import java.security.Principal;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(path = "/api/v1/account-info")
public class AccountInfoController extends ExceptionHandling {

    private final EmailService emailService;
    private final UserService userService;
    private final EmailEnvironmentBuilder emailEnvironmentBuilder;

    @Autowired
    public AccountInfoController(EmailService emailService, UserService userService, EmailEnvironmentBuilder emailEnvironmentBuilder) {
        this.emailService = emailService;
        this.userService = userService;
        this.emailEnvironmentBuilder = emailEnvironmentBuilder;
    }

    @PostMapping("/reset-password-send-email")
    public ResponseEntity<HttpStatus> resetPassword(@RequestBody UserDtoRequest userDtoRequest) throws MessagingException {
        emailService.sendEmailReset(userDtoRequest.getEmail().toLowerCase(), userDtoRequest.getPassword());
        return new ResponseEntity<>(OK);
    }

    @GetMapping("/reset-password")
    public RedirectView resetVerification(@RequestParam("token") String token) {
        ResetEmailVerification resetEmailVerification = emailService.getVerificationTokenByTokenReset(token);
        User user = resetEmailVerification.getUser();
        String password = resetEmailVerification.getPassword();

        userService.resetPassword(user, password);
        emailService.deleteUsedTokenReset(resetEmailVerification);

        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(emailEnvironmentBuilder.DOMAIN + "login");
        return redirectView;
    }

    @PostMapping("/update-account-info")
    public ResponseEntity<UserDtoResponse> updateAccountInfo(@RequestBody UserDtoRequest userDtoRequest, Principal principal) {
        UserDtoResponse userDtoResponse = userService.updateAccountInfo(userDtoRequest, principal);
        return new ResponseEntity<>(userDtoResponse, OK);
    }
}
