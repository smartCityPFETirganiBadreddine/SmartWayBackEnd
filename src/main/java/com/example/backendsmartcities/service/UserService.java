package com.example.backendsmartcities.service;

import com.example.backendsmartcities.dto.RoleDto;
import com.example.backendsmartcities.dto.UserDto;
import com.example.backendsmartcities.entity.Role;
import com.example.backendsmartcities.entity.User;
import com.example.backendsmartcities.enums.ERole;
import com.example.backendsmartcities.exception.UserNotFoundException;
import com.example.backendsmartcities.repository.RoleRepository;
import com.example.backendsmartcities.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.beans.FeatureDescriptor;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
/**
 * Author: Badreddine TIRGANI
 */
@Service
public class UserService implements BaseService<UserDto> {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public UserDto save(UserDto user) throws Exception {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new Exception("this Email is already in use");
        }
        if (userRepository.existsByUserName(user.getUserName())) {
            throw new Exception("this Username is already in use");
        }

        User userSaved = userRepository.save(modelMapper.map(user, User.class));
        return modelMapper.map(userSaved, UserDto.class);
    }

    @Override
    public UserDto update(long id, UserDto userRequest) throws Exception {
        User user = userRepository.findById(id).orElseThrow(() -> new Exception("No user registered with id " + id));
        User userUpdated = modelMapper.map(userRequest, User.class);
        if (userRequest != null) {
            BeanUtils.copyProperties(userRequest, user, Stream.of(new BeanWrapperImpl(userRequest).getPropertyDescriptors()).map(FeatureDescriptor::getName).filter(propertyName -> new BeanWrapperImpl(userRequest).getPropertyValue(propertyName) == null).toArray(String[]::new));
            //

            List<RoleDto> strRoles = userRequest.getRoles();
            List<Role> roles = new ArrayList<>();

            if (strRoles == null) {
                Role userRole = roleRepository.findByName(ERole.ROLE_USER).orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                roles.add(userRole);
            } else {
                strRoles.forEach(role -> {
                    if ("ROLE_USER".equals(role.getName().name())) {
                        Role modRole = roleRepository.findByName(ERole.ROLE_USER).orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);
                    } else if ("ROLE_ADMIN".equals(role.getName().name())) {
                        Role userRole = roleRepository.findByName(ERole.ROLE_ADMIN).orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                    }
                    else if ("ROLE_OKSA_OR".equals(role.getName().name())) {
                        Role userRole = roleRepository.findByName(ERole.ROLE_OKSA_OR).orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                    }
                    else if ("ROLE_OKSA_CHEF_DIRECTION".equals(role.getName().name())) {
                        Role userRole = roleRepository.findByName(ERole.ROLE_OKSA_CHEF_DIRECTION).orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                    }
                    else if ("ROLE_OKSA_CHEF_DEP".equals(role.getName().name())) {
                        Role userRole = roleRepository.findByName(ERole.ROLE_OKSA_CHEF_DEP).orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                    }
                    else if ("ROLE_OKSA_OR_INT".equals(role.getName().name())) {
                        Role userRole = roleRepository.findByName(ERole.ROLE_OKSA_OR_INT).orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                    }
                    else if ("ROLE_OKSA_OPERATOR".equals(role.getName().name())) {
                        Role userRole = roleRepository.findByName(ERole.ROLE_OKSA_OPERATOR).orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                    }
                    else if ("ROLE_OKSA_CHEF".equals(role.getName().name())) {
                        Role userRole = roleRepository.findByName(ERole.ROLE_OKSA_CHEF).orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                    }
                    else if ("ROLE_OKSA_CHEF_EQP".equals(role.getName().name())) {
                        Role userRole = roleRepository.findByName(ERole.ROLE_OKSA_CHEF_EQP).orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                    }
                    else if ("ROLE_OKSA_EQP_MEMBER".equals(role.getName().name())) {
                        Role userRole = roleRepository.findByName(ERole.ROLE_OKSA_EQP_MEMBER).orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                    }
                    else{
                        Role userRole = roleRepository.findByName(ERole.ROLE_CLIENT).orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                    }
                });
            }
            userUpdated.setRoles(roles);
            //
        }
        User u1 = userRepository.save(userUpdated);
        return modelMapper.map(u1, UserDto.class);
    }

    @Override
    public void softDelete(Long id) {
        Optional<User> existingUser = userRepository.findById(id);
        if (existingUser.isPresent()) {
            User user = existingUser.get();
            user.setDeleted(true);
            // user.setDeleteAt(new Date());
            userRepository.save(user);
        }
    }

    @Override
    public Optional<UserDto> findById(Long id) {
        Optional<User> sc = userRepository.findById(id);
        return sc.map(user -> modelMapper.map(user, UserDto.class));
    }

    @Override
    public List<UserDto> getAll() {

        List<User> users = userRepository.findAll(); // Assuming UserRepository is an interface extending JpaRepository<User, Long>
        List<UserDto> userDos = new ArrayList<>();
        for(User user:users){
            UserDto userDto1 = modelMapper.map(user,UserDto.class);
            userDos.add(userDto1);
        }
        return userDos;
    }

    public User getUser(String username) {
        return userRepository.findByUserName(username);
    }

    public void changePassword(Long userId, String oldPassword, String newPassword) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new RuntimeException("Old password is incorrect");
        }

        user.setPassword(passwordEncoder.encode(newPassword));

        userRepository.save(user);
    }


    public Optional<UserDto> findByRole(String role) {
        Optional<Role> r = roleRepository.findByName(ERole.valueOf(role));
        if (r.isPresent()) {
            List<User> sc = userRepository.findByRoles(r.get());
            if (sc != null) {
                return sc.stream().map(user -> modelMapper.map(user, UserDto.class)).findFirst();
            }
        }
        return Optional.empty();
    }
    public List<UserDto> findAllByRole(String role) {
        Optional<Role> r = roleRepository.findByName(ERole.valueOf(role));
        List<User> userList = userRepository.findByRoles(r.get());
        return userList.stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());
    }

    public  UserDto findByUserName(String userName) {
        User user = userRepository.findByUserName(userName);
        UserDto userDto = modelMapper.map(user,UserDto.class);
        return userDto;
    }
}