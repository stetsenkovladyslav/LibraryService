package com.example.library.mapper.user;


import com.example.library.dto.user.UserDto;
import com.example.library.model.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")

public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDto toDto(User user);

    User dtoToUser(UserDto userDto);

}
