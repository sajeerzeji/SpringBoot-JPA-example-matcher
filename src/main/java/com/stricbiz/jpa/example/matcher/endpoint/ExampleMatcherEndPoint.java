package com.stricbiz.jpa.example.matcher.endpoint;

import com.google.gson.Gson;
import com.stricbiz.jpa.example.matcher.persistence.entity.UserEntity;
import com.stricbiz.jpa.example.matcher.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/matchby")
public class ExampleMatcherEndPoint {

    private UserService userService;

    public ExampleMatcherEndPoint(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/id")
    public ResponseEntity<String> getUserByID(@RequestParam(value = "id") Integer id){
        UserEntity userEntity = userService.getUserByID(id);
        return new ResponseEntity(new Gson().toJson(userEntity), HttpStatus.OK);
    }

    @GetMapping("/name")
    public ResponseEntity<String> getUserByID(@RequestParam(value = "name") String name) {
        UserEntity userEntity = userService.getUserByName(name);
        return new ResponseEntity(new Gson().toJson(userEntity), HttpStatus.OK);
    }

    @GetMapping("/daterange")
    public ResponseEntity<String> getUserByID(@RequestParam(value = "fromDate") String fromDate,
                                              @RequestParam(value = "fromDate") String toDate){
        List<UserEntity> users = userService.getUserByJoinedDateRange(fromDate, toDate);
        return new ResponseEntity(new Gson().toJson(users), HttpStatus.OK);
    }
}
