package kz.edy.astanait.product.utils.facade;

import kz.edy.astanait.product.dto.responseDto.security.AuthoritiesDtoResponse;
import kz.edy.astanait.product.dto.responseDto.security.RoleDtoResponse;
import kz.edy.astanait.product.dto.responseDto.UserDtoResponse;
import kz.edy.astanait.product.model.security.Authority;
import kz.edy.astanait.product.model.security.Role;
import kz.edy.astanait.product.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserFacade {

    public UserDtoResponse userToUserDtoResponse(User user) {
        UserDtoResponse userDtoResponse = new UserDtoResponse();
        userDtoResponse.setId(user.getId());
        userDtoResponse.setName(user.getName());
        userDtoResponse.setSurname(user.getSurname());
        if (user.getPatronymic() != null) {
            userDtoResponse.setPatronymic(user.getPatronymic());
        }
        userDtoResponse.setEmail(user.getEmail());
        userDtoResponse.setPhoneNumber(user.getPhoneNumber());
        userDtoResponse.setRole(roleToRoleDtoResponse(user.getRole()));
        userDtoResponse.setIsActive(user.isActive());
        return userDtoResponse;
    }

    public RoleDtoResponse roleToRoleDtoResponse(Role role) {
        RoleDtoResponse roleDtoResponse = new RoleDtoResponse();

        List<AuthoritiesDtoResponse> authorities = new ArrayList<>();
        role.getAuthorities().forEach(authority ->
                authorities.add(authoritiesToAuthoritiesDtoResponse(authority)));

        roleDtoResponse.setId(role.getId());
        roleDtoResponse.setRoleName(role.getRoleName());
        return roleDtoResponse;
    }

    private AuthoritiesDtoResponse authoritiesToAuthoritiesDtoResponse(Authority authority) {
        AuthoritiesDtoResponse authoritiesDtoResponse = new AuthoritiesDtoResponse();
        authoritiesDtoResponse.setId(authority.getId());
        authoritiesDtoResponse.setAuthoritiesName(authority.getAuthorityName());
        return authoritiesDtoResponse;
    }
}
