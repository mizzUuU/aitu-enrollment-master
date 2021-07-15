package kz.edy.astanait.product.service.admin;

import kz.edy.astanait.product.dto.requestDto.admin.CreateUsersDtoRequest;
import kz.edy.astanait.product.dto.responseDto.security.RoleDtoResponse;
import kz.edy.astanait.product.exception.domain.EmailExistException;
import kz.edy.astanait.product.exception.domain.RoleNotFoundException;
import kz.edy.astanait.product.model.User;
import kz.edy.astanait.product.model.security.Authority;
import kz.edy.astanait.product.model.security.Role;
import kz.edy.astanait.product.repository.UserRepository;
import kz.edy.astanait.product.repository.security.AuthoritiesRepository;
import kz.edy.astanait.product.repository.security.RoleRepository;
import kz.edy.astanait.product.utils.facade.UserFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CreateUsersService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder encoder;
    private final AuthoritiesRepository authoritiesRepository;

    @Autowired
    public CreateUsersService(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder encoder, AuthoritiesRepository authoritiesRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
        this.authoritiesRepository = authoritiesRepository;
    }

    public void createUser(CreateUsersDtoRequest createUsersDtoRequest) {
        if (createUsersDtoRequest.getRoleId() != null) {
            Optional<Role> role = roleRepository.findById(createUsersDtoRequest.getRoleId());
            if (role.isPresent() && !role.get().getRoleName().equals("ROLE_USER")) {
                Optional<User> user = userRepository.findUserByEmail(createUsersDtoRequest.getEmail());
                if (user.isEmpty()) {
                    User createUser = new User();
                    createUser.setEmail(createUsersDtoRequest.getEmail());
                    createUser.setName(createUsersDtoRequest.getName());
                    createUser.setSurname(createUsersDtoRequest.getSurname());
                    createUser.setPatronymic(createUsersDtoRequest.getPatronymic());
                    createUser.setPassword(encoder.encode(createUsersDtoRequest.getPassword()));
                    createUser.setRole(role.get());
                    createUser.setActive(true);
                    userRepository.save(createUser);
                } else {
                    throw new EmailExistException("email already exist!");
                }
            } else {
                throw new RoleNotFoundException("Role not found");
            }
        }
        if (createUsersDtoRequest.getRole() != null && createUsersDtoRequest.getAuthoritiesId() != null) {
            Optional<User> user = userRepository.findUserByEmail(createUsersDtoRequest.getEmail());
            if (user.isEmpty()) {
                User createUser = new User();
                createUser.setEmail(createUsersDtoRequest.getEmail());
                createUser.setName(createUsersDtoRequest.getName());
                createUser.setSurname(createUsersDtoRequest.getSurname());
                createUser.setPatronymic(createUsersDtoRequest.getPatronymic());
                createUser.setPassword(encoder.encode(createUsersDtoRequest.getPassword()));
                Role role = new Role();
                role.setRoleName(createUsersDtoRequest.getRole());
                List<Authority> authorityList = new ArrayList<>();
                for (Long id : createUsersDtoRequest.getAuthoritiesId()) {
                    Optional<Authority> authority = authoritiesRepository.findById(id);
                    authority.ifPresent(authorityList::add);
                }
                role.setAuthorities(authorityList);
                createUser.setRole(roleRepository.save(role));
                createUser.setActive(true);
                userRepository.save(createUser);
            } else {
                throw new EmailExistException("email already exist!");
            }
        }
    }

    public List<RoleDtoResponse> getRoles() {
        List<Role> role = roleRepository.findAll();
        return role.stream().filter(value -> !value.getRoleName().equals("ROLE_USER"))
                .map(value -> new UserFacade().roleToRoleDtoResponse(value)).collect(Collectors.toList());
    }
}
