package kz.edy.astanait.product.controller.security;

import kz.edy.astanait.product.dto.requestDto.UserDtoRequest;
import kz.edy.astanait.product.dto.responseDto.UserDtoResponse;
import kz.edy.astanait.product.enviroment.EmailEnvironmentBuilder;
import kz.edy.astanait.product.exception.ExceptionHandling;
import kz.edy.astanait.product.model.User;
import kz.edy.astanait.product.model.security.RegistrationEmailVerification;
import kz.edy.astanait.product.service.security.EmailService;
import kz.edy.astanait.product.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(path = "/api/v1/auth")
public class AuthController extends ExceptionHandling {

    private final UserService userService;
    private final EmailService emailService;
    private final EmailEnvironmentBuilder emailEnvironmentBuilder;

    @Autowired
    public AuthController(UserService userService, EmailService emailService, EmailEnvironmentBuilder emailEnvironmentBuilder) {
        this.userService = userService;
        this.emailService = emailService;
        this.emailEnvironmentBuilder = emailEnvironmentBuilder;
    }

    @PostMapping("/registration")
    public ResponseEntity<UserDtoResponse> applicantRegistration(@RequestBody UserDtoRequest userDtoRequest) {
        UserDtoResponse createUser = userService.registration(userDtoRequest.getName(), userDtoRequest.getSurname(), userDtoRequest.getPatronymic(),
                userDtoRequest.getEmail().toLowerCase(), userDtoRequest.getPassword(), userDtoRequest.getPhoneNumber());
        return new ResponseEntity<>(createUser, OK);
    }

    @PostMapping("/login")
    public ResponseEntity<UserDtoResponse> login(@RequestBody UserDtoRequest userDtoRequest, HttpServletRequest request){
        return userService.authorization(userDtoRequest.getEmail().toLowerCase(), userDtoRequest.getPassword(), request);
    }

    @PostMapping("/send-verification-email")
    public ResponseEntity<HttpStatus> sendVerificationEmail(@RequestBody UserDtoRequest userDtoRequest) throws MessagingException {
        emailService.sendEmailRegistration(userDtoRequest.getEmail().toLowerCase());
        return new ResponseEntity<>(OK);
    }

    @GetMapping("/user-verification")
    public RedirectView userVerification(@RequestParam("token") String token) {
        RegistrationEmailVerification registrationEmailVerification = emailService.getVerificationTokenByTokenRegistration(token);
        User user = registrationEmailVerification.getUser();

        userService.verifyUser(user);
        emailService.deleteUsedTokenRegistration(registrationEmailVerification);

        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(emailEnvironmentBuilder.DOMAIN + "login");
        return redirectView;
    }
}
