package com.example.demo.ut;

import com.example.demo.controllers.SecurityController;
import com.example.demo.domain.Owner;
import com.example.demo.dto.AuthResponseDto;
import com.example.demo.dto.RegisterRequestDto;
import com.example.demo.services.JwtUserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;

@SpringBootTest
@AutoConfigureMockMvc
public class SecurityControllerTest {
    @Autowired
    SecurityController securityController;
    @MockBean
    JwtUserService mockJwtUserService;

    @Test
    public void testRegister() {
        //Defining the mock with Mockito
        RegisterRequestDto dto = new RegisterRequestDto();
        dto.setUsername("testing");
        dto.setPassword("testing");
        dto.setFirstname("testing");
        dto.setLastname("testing");
        dto.setEmail("testing");
        dto.setPhoneNumber("testing");
        Owner user = new Owner();
        user.setId(1);
        user.setLogin("UserTest");
        String jwt = "jwtTokenForTesting";

        Mockito.when(mockJwtUserService.save(ArgumentMatchers.anyString(), ArgumentMatchers.anyString(), ArgumentMatchers.anyString(), ArgumentMatchers.anyString(), ArgumentMatchers.anyString(), ArgumentMatchers.anyString())).thenReturn(user);
        Mockito.when(mockJwtUserService.generateJwtForUser(ArgumentMatchers.any(UserDetails.class))).thenReturn(jwt);

        //Testing
        AuthResponseDto result = securityController.register(dto).getBody();
        assert result != null;
        Assertions.assertEquals("UserTest", result.getUser().getUsername());
        Assertions.assertEquals("jwtTokenForTesting", result.getToken());
    }
}
