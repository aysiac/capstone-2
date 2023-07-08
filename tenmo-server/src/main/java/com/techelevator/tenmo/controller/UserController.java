package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.User;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
//@RequestMapping("/users")
public class UserController {

    private UserDao userDao;

    public UserController(UserDao userDao) {
        this.userDao = userDao;
    }

    @GetMapping("/users")
    public List<User> getUsers() {
        List<User> userList = null;
        userList = userDao.findAll();
        return userList;
    }

    @GetMapping("/view-user/{username}")
    public User getUserByUserName(@PathVariable String username) {
        User searchedUser = null;
        searchedUser = userDao.findByUsername(username);
        return searchedUser;
    }

    @GetMapping("/user/view-id/{name}")
    public int getUserId(@PathVariable String name) {
        int userId;
        userId = userDao.findIdByUsername(name);
        return userId;
    }
}
