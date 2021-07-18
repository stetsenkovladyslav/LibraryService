package com.example.library.mapper.user;


import com.example.library.dto.user.UserDto;
import com.example.library.model.user.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")

public interface UserMapper {


    UserDto toDto(User user);

    User dtoToUser(UserDto userDto);

}