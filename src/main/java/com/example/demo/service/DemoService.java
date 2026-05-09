package com.example.demo.service;

import com.example.demo.dto.UserDto;

import java.util.List;

public interface DemoService {

    UserDto createUser(UserDto userDto);

    UserDto updateUser(UserDto userDto);

    UserDto findUserById(String id);

    List<UserDto> getAllUsers();

    void deleteUserById(String id);
}
