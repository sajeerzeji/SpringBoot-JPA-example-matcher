package com.stricbiz.jpa.example.matcher.service;

import com.stricbiz.jpa.example.matcher.persistence.UserRepository;
import com.stricbiz.jpa.example.matcher.persistence.entity.UserEntity;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity getUserByID(Integer id) {
        return userRepository.findById(id).orElseGet(() -> {return null;});
    }

    public UserEntity getUserByName(String name) {
        UserEntity userEntity = new UserEntity();
        userEntity.setName(name);
        return userRepository.findOne(Example.of(userEntity)).orElseGet(() -> {return null;});
    }

    public List<UserEntity> getUserByJoinedDateRange(String fromDate, String toDate) {
        UserEntity userEntity = new UserEntity();
        return userRepository.findAll(
                userRepository.getFromDateRangeAndExample(
                        fromDate, toDate, Example.of(userEntity)
                )
        );
    }
}
