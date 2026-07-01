package com.cart.demo.service.impl;

import com.cart.demo.model.dto.user.UserInfoResponse;
import com.cart.demo.model.dto.user.UserResponse;
import com.cart.demo.model.dto.user.UserSaveRequest;
import com.cart.demo.model.dto.user.UserUpdateRequest;
import com.cart.demo.model.entity.Cart;
import com.cart.demo.model.entity.User;
import com.cart.demo.model.entity.UserInfo;
import com.cart.demo.repository.CartRepository;
import com.cart.demo.repository.UserRepository;
import com.cart.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.cart.demo.model.enumeration.CartStatus.ACTIVE;

@Service
public class UserServiceImpl  implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    @Override
    public List<UserResponse> findAll() {
        List<User>  users = userRepository.findAll();
        List<UserResponse>  userResponses = new ArrayList<>();
        for (User user : users) {
            UserInfoResponse userInfoResponse = new UserInfoResponse(
                    user.getUserInfo().getId(),
                    user.getUserInfo().getFirstname(),
                    user.getUserInfo().getLastname(),
                    user.getUserInfo().getCity(),
                    user.getUserInfo().getCountry()
            );
            UserResponse userResponse = new UserResponse(
              user.getId(),
              user.getUsername(),
              userInfoResponse
            );
            userResponses.add(userResponse);
        }
        return userResponses;
    }

    @Override
    public UserResponse findById(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            throw new ResourceNotFoundException("User not found");
        }
        UserInfoResponse userInfoResponse = new UserInfoResponse(
                user.getUserInfo().getId(),
                user.getUserInfo().getFirstname(),
                user.getUserInfo().getLastname(),
                user.getUserInfo().getCity(),
                user.getUserInfo().getCountry()
        );
        return new UserResponse(
          user.getId(),
          user.getUsername(),
          userInfoResponse
        );
    }

    @Override
    public UserResponse save(UserSaveRequest request) {
        UserInfo userInfo = new UserInfo(
                request.firstname(),
                request.lastname(),
                request.city(),
                request.country()
        );
        User user = new User(
                request.username(),
                request.password(),
                userInfo
        );
        User savedUser = userRepository.save(user);
        UserInfoResponse userInfoResponse = new UserInfoResponse(
                savedUser.getUserInfo().getId(),
                savedUser.getUserInfo().getFirstname(),
                savedUser.getUserInfo().getLastname(),
                savedUser.getUserInfo().getCity(),
                savedUser.getUserInfo().getCountry()
        );

        // Create user cart
        Cart cart = new Cart(ACTIVE, savedUser);
        cartRepository.save(cart);

        return new UserResponse(
                savedUser.getId(),
                savedUser.getUsername(),
                userInfoResponse
        );
    }

    @Override
    public UserResponse update(Long id, UserUpdateRequest request) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            throw new ResourceNotFoundException("User not found");
        }
        if (request.username() != null && !request.username().trim().isEmpty()) {
            user.setUsername(request.username());
        }
        if (request.password() != null && !request.password().trim().isEmpty()) {
            user.setPassword(request.password());
        }
        if (request.firstname() != null && !request.firstname().trim().isEmpty()) {
            user.getUserInfo().setFirstname(request.firstname());
        }
        if (request.lastname() != null && !request.lastname().trim().isEmpty()) {
            user.getUserInfo().setLastname(request.lastname());
        }
        if (request.city() != null && !request.city().trim().isEmpty()){
            user.getUserInfo().setCity(request.city());
        }
        User userUpdated = userRepository.save(user);
        UserInfoResponse userInfoResponse = new UserInfoResponse(
                userUpdated.getUserInfo().getId(),
                userUpdated.getUserInfo().getFirstname(),
                userUpdated.getUserInfo().getLastname(),
                userUpdated.getUserInfo().getCity(),
                userUpdated.getUserInfo().getCountry()
        );
        return new UserResponse(
                userUpdated.getId(),
                userUpdated.getUsername(),
                userInfoResponse
        );
    }

    @Override
    public void deleteById(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            throw new ResourceNotFoundException("User not found");
        }
        userRepository.deleteById(id);
    }
}