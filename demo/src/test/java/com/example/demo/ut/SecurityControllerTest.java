package com.example.demo.ut;

import com.example.demo.controllers.SecurityController;
import com.example.demo.domain.Owner;
import com.example.demo.dto.AuthRequestDto;
import com.example.demo.dto.AuthResponseDto;
import com.example.demo.dto.RegisterRequestDto;
import com.example.demo.exceptions.AccountExistsException;
import com.example.demo.services.JwtUserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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

    @Test
    public void testRegisterWithOwnerAlreadyExist() {
        //Defining the mock with Mockito
        Mockito.when(mockJwtUserService.save(ArgumentMatchers.anyString(), ArgumentMatchers.anyString(), ArgumentMatchers.anyString(), ArgumentMatchers.anyString(), ArgumentMatchers.anyString(), ArgumentMatchers.anyString())).thenThrow(AccountExistsException.class);

        //Testing
        Assertions.assertThrows(AccountExistsException.class, () -> {
            RegisterRequestDto dto = new RegisterRequestDto();
            dto.setUsername("testing");
            dto.setPassword("testing");
            dto.setFirstname("testing");
            dto.setLastname("testing");
            dto.setEmail("testing");
            dto.setPhoneNumber("testing");
            securityController.register(dto);
        });
    }

    @Test
    public void testAuthorize() throws Exception {
        //Defining the mock with Mockito
        AuthRequestDto authRequestDto = new AuthRequestDto();
        authRequestDto.setUsername("UserTest");
        authRequestDto.setPassword("testingPassword");
        Owner owner = new Owner();
        owner.setId(1);
        owner.setLogin("Test");
        Authentication authentication = new UsernamePasswordAuthenticationToken(owner, "HelloWorld");

        String jwt = "jwtTokenForTesting";

        Mockito.when(mockJwtUserService.authenticate(ArgumentMatchers.anyString(), ArgumentMatchers.anyString())).thenReturn(authentication);
        Mockito.when(mockJwtUserService.generateJwtForUser(ArgumentMatchers.any(UserDetails.class))).thenReturn(jwt);

        //Testing
        AuthResponseDto result = securityController.authorize(authRequestDto).getBody();
        assert result != null;
        Assertions.assertEquals("jwtTokenForTesting", result.getToken());
    }
}
