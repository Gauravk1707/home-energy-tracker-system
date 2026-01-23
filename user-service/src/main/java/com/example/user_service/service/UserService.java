package com.example.user_service.service;

import com.example.user_service.Entity.User;
import com.example.user_service.dto.UserDto;
import com.example.user_service.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    public UserDto createUser(UserDto input) {

               log.info("creating User : {}",input);

                User createdUser = User.builder()
                .name(input.getName())
                .surname(input.getSurname())
                .email(input.getEmail())
                .address(input.getAddress())
                .alerting(input.isAlerting())
                .energyAlertingThreshold(input.getEnergyAlertingThreshold())
                .build();

        User savedUser = userRepository.save(createdUser);

        return userToDto(savedUser);

    }

    private UserDto userToDto(User savedUser) {

        return UserDto.builder()
                .id(savedUser.getId())
                .name(savedUser.getName())
                .surname(savedUser.getSurname())
                .email(savedUser.getEmail())
                .address(savedUser.getAddress())
                .alerting(savedUser.isAlerting())
                .energyAlertingThreshold(savedUser.getEnergyAlertingThreshold())
                .build();
    }

    public UserDto findUserById(Long id) {
       return userRepository.findById(id)
               .map(this::userToDto)
               .orElse(null);
    }

    public void updateUser(Long id, UserDto dto) {

        User user = userRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("User Not Found"));

        user.setId(dto.getId());
        user.setName(dto.getName());
        user.setSurname(dto.getSurname());
        user.setEmail(dto.getEmail());
        user.setAddress(dto.getAddress());
        user.setAlerting(dto.isAlerting());
        user.setEnergyAlertingThreshold(dto.getEnergyAlertingThreshold());

        userRepository.save(user);
    }

    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("No User Found"));
        userRepository.delete(user);
    }
}
