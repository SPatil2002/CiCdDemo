package com.example.demo.service.impl;

import com.example.demo.config.ModelMapperConfig;
import com.example.demo.dto.UserDto;
import com.example.demo.entity.User;
import com.example.demo.repository.DemoRepository;
import com.example.demo.service.DemoService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DemoServiceImpl implements DemoService {

    private DemoRepository demoRepository;
    private ModelMapper modelMapper;

    public DemoServiceImpl(DemoRepository demoRepository, ModelMapper modelMapper) {
        this.demoRepository = demoRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = modelMapper.map(userDto, User.class);
        user.setStudentId(UUID.randomUUID().toString());
        User savedUser = demoRepository.save(user);
        return modelMapper.map(savedUser, UserDto.class);
    }

    @Override
    public UserDto updateUser(UserDto userDto,String id) {
        User user = demoRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        User savedUser = demoRepository.save(user);
        return modelMapper.map(savedUser, UserDto.class);
    }

    @Override
    public UserDto findUserById(String id) {
        User singleUser = demoRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        return modelMapper.map(singleUser, UserDto.class);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> allUsers = demoRepository.findAll();
        return allUsers.stream().map(u -> modelMapper.map(u, UserDto.class)).collect(Collectors.toList());
    }

    @Override
    public void deleteUserById(String id) {
        User singleUser = demoRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        demoRepository.deleteById(id);
    }

}
