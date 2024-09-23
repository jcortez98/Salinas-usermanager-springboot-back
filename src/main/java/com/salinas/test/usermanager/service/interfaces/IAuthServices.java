package com.salinas.test.usermanager.service.interfaces;

import com.salinas.test.usermanager.dto.LoginUserDto;
import com.salinas.test.usermanager.dto.RegisterUserDto;
import com.salinas.test.usermanager.model.User;

public interface IAuthServices {

    User register(RegisterUserDto registerUserDto);

    User login(LoginUserDto loginUserDto);
    
}
