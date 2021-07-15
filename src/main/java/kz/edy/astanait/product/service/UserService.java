package kz.edy.astanait.product.service;

import kz.edy.astanait.product.constant.SecurityConstant;
import kz.edy.astanait.product.dto.requestDto.UserDtoRequest;
import kz.edy.astanait.product.dto.responseDto.UserDtoResponse;
import kz.edy.astanait.product.exception.domain.EmailExistException;
import kz.edy.astanait.product.exception.domain.UserNotFoundException;
import kz.edy.astanait.product.model.secretary.ConfirmBlocks;
import kz.edy.astanait.product.repository.secretary.ConfirmBlocksRepository;
import kz.edy.astanait.product.repository.security.RoleRepository;
import kz.edy.astanait.product.utils.facade.UserFacade;
import kz.edy.astanait.product.model.User;
import kz.edy.astanait.product.repository.UserRepository;
import kz.edy.astanait.product.security.JWTTokenProvider;
import kz.edy.astanait.product.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JWTTokenProvider jwtTokenProvider;
    private final BCryptPasswordEncoder encoder;
    private final RoleRepository roleRepository;
    private final ConfirmBlocksRepository confirmBlocksRepository;

    @Autowired
    public UserService(UserRepository userRepository, @Lazy AuthenticationManager authenticationManager,
                       JWTTokenProvider jwtTokenProvider, BCryptPasswordEncoder encoder, RoleRepository roleRepository, ConfirmBlocksRepository confirmBlocksRepository) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.encoder = encoder;
        this.roleRepository = roleRepository;
        this.confirmBlocksRepository = confirmBlocksRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findUserByEmail(email);
        if (user.isPresent()) {
            return new UserPrincipal(user.get());
        } else {
           throw new UserNotFoundException("User not found by username: " + email);
        }
    }

    public UserDtoResponse registration(String name, String surname, String patronymic,
                                                   String email, String password, String phoneNumber)  {
        Optional<User> user = userRepository.findUserByEmail(email);
        if (user.isPresent()) {
            throw new EmailExistException("This email is already exist");
        } else {
            User createUser = new User();
            createUser.setName(name);
            createUser.setSurname(surname);
            createUser.setPatronymic(patronymic);
            createUser.setEmail(email);
            createUser.setPassword(encoder.encode(password));
            createUser.setPhoneNumber(phoneNumber);
            roleRepository.findByRoleName("ROLE_USER").ifPresent(createUser::setRole);

            return new UserFacade().userToUserDtoResponse(userRepository.save(createUser));
        }
    }

    public ResponseEntity<UserDtoResponse> authorization(String email, String password, HttpServletRequest request) {
        authenticate(email, password);
        Optional<User> user = userRepository.findUserByEmail(email);
        if (user.isPresent()) {
            createConfirmBlocks(user.get());
            UserPrincipal userPrincipal = new UserPrincipal(user.get());
            String ipFromClient = jwtTokenProvider.getIpFromClient(request);
            HttpHeaders jwt = getJwtHeader(userPrincipal, ipFromClient);
            return new ResponseEntity<>(new UserFacade().userToUserDtoResponse(user.get()), jwt, HttpStatus.OK);
        }
        throw new UserNotFoundException("User not found by username: " + email);
    }

    private void createConfirmBlocks(User user) {
        if (confirmBlocksRepository.findByUserId(user.getId()).isEmpty()) {
            ConfirmBlocks confirmBlocks = new ConfirmBlocks();
            confirmBlocks.setUser(user);
            confirmBlocksRepository.save(confirmBlocks);
        }
    }

    private HttpHeaders getJwtHeader(UserPrincipal userPrincipal, String ipFromClient) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(SecurityConstant.JWT_TOKEN_HEADER, jwtTokenProvider.generateToken(userPrincipal, ipFromClient));
        return httpHeaders;
    }

    private void authenticate(String email, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
    }

    public void verifyUser(User user) {
        user.setActive(true);
        userRepository.save(user);
    }

    public void resetPassword(User user, String password) {
        user.setPassword(password);
        userRepository.save(user);
    }

    public UserDtoResponse updateAccountInfo(UserDtoRequest userDtoRequest, Principal principal) {
        Optional<User> user = userRepository.findUserByEmail(principal.getName());
        if (user.isPresent()) {
            if (userDtoRequest.getName() != null) {
                user.get().setName(userDtoRequest.getName());
            }
            if (userDtoRequest.getSurname() != null) {
                user.get().setSurname(userDtoRequest.getSurname());
            }
            if (userDtoRequest.getPatronymic() != null) {
                user.get().setPatronymic(userDtoRequest.getPatronymic());
            }
            if (userDtoRequest.getPhoneNumber() != null) {
                user.get().setPhoneNumber(userDtoRequest.getPhoneNumber());
            }
            if (userDtoRequest.getPassword() != null) {
                user.get().setPassword(encoder.encode(userDtoRequest.getPassword()));
            }
            return new UserFacade().userToUserDtoResponse(userRepository.save(user.get()));
        }
        throw new UserNotFoundException("User not found by username: " + principal.getName());
    }

}
