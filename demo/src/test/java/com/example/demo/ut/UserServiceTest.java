package com.example.demo.ut;

import com.example.demo.domain.Owner;
import com.example.demo.exceptions.AccountExistsException;
import com.example.demo.repositories.OwnerRepository;
import com.example.demo.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
public class UserServiceTest {
    @Autowired
    UserService userService;

    @MockBean
    OwnerRepository mockOwnerRepository;
    @MockBean
    AuthenticationConfiguration mockAuthenticationConfiguration;
    @MockBean
    AuthenticationManager mockAuthenticationManager;

    @Test
    public void testAuthenticate() throws Exception{
        //Defining the mock with Mockito
        Authentication authentication = new UsernamePasswordAuthenticationToken("Hello", "Word");
        Mockito.when(mockAuthenticationConfiguration.getAuthenticationManager()).thenReturn(mockAuthenticationManager);
        Mockito.when(mockAuthenticationManager.authenticate(ArgumentMatchers.any())).thenReturn(authentication);

        //Assertions
        Authentication result = userService.authenticate("JeanMiche", "Azerty1234!");
        Assertions.assertEquals("Word",result.getCredentials());
    }

    @Test
    public void testSaveWithoutOwnerExist() throws Exception{
        //Defining the mock with Mockito
        Owner user = new Owner();
        user.setId(1);
        user.setLogin("Hello");

        Mockito.when(mockOwnerRepository.findByLoginOrEmailOrPhoneNumber(ArgumentMatchers.anyString(), ArgumentMatchers.anyString(), ArgumentMatchers.anyString())).thenReturn(null);
        Mockito.when(mockOwnerRepository.save(ArgumentMatchers.any(Owner.class))).thenReturn(user);

        //Assertions
        UserDetails result = userService.save("jeanmichel", "Azerty1234!", "Jean", "Michel", "jean.michel@gmail.com", "0606060606");
        Assertions.assertEquals("Hello", result.getUsername());
    }

    @Test
    public void testSaveWithOwnerExistThrowsAccountExistsException() throws Exception{
        //Defining the mock with Mockito
        Owner user = new Owner();
        user.setId(1);
        user.setLogin("Hello");

        Mockito.when(mockOwnerRepository.findByLoginOrEmailOrPhoneNumber(ArgumentMatchers.anyString(), ArgumentMatchers.anyString(), ArgumentMatchers.anyString())).thenReturn(user);

        //Assertions
        Assertions.assertThrows(AccountExistsException.class, () -> {
            userService.save("jeanmichel", "H3lLo_w0Rd!", "Jean", "Michel", "jean.michel@gmail.com", "0606060606");
        });
    }

    @Test
    public void testGetWithUser() throws Exception{
        //Defining the mock with Mockito
        Owner user = new Owner();
        user.setId(1);
        user.setLogin("Hello");
        Optional<Owner> optionalOwner = Optional.of(user);

        Mockito.when(mockOwnerRepository.findById(ArgumentMatchers.anyInt())).thenReturn(optionalOwner);

        //Assertions
        UserDetails result = userService.get(9);
        Assertions.assertEquals("Hello", result.getUsername());
    }
    @Test
    public void testGetWithoutUser() throws Exception{
        //Defining the mock with Mockito
        Optional<Owner> optionalOwner = Optional.empty();

        Mockito.when(mockOwnerRepository.findById(ArgumentMatchers.anyInt())).thenReturn(optionalOwner);

        //Assertions
        UserDetails result = userService.get(9);
        Assertions.assertNull(result);
    }

    @Test
    public void testLoadByUsername() throws Exception{
        //Define the Mockito
        Owner user = new Owner();
        user.setId(1);
        user.setLogin("Hello");

        Mockito.when(mockOwnerRepository.findByLogin(ArgumentMatchers.anyString())).thenReturn(user);

        //Assertions
        UserDetails result = userService.loadUserByUsername("World");
        Assertions.assertEquals("Hello", result.getUsername());
    }
    @Test
    public void testLoadByUsernameThrowsUsernameNotFoundException() throws Exception{
        //Define the Mockito
        Mockito.when(mockOwnerRepository.findByLogin(ArgumentMatchers.anyString())).thenReturn(null);

        //Assertions
        Assertions.assertThrows(UsernameNotFoundException.class, () ->{
            userService.loadUserByUsername("HelloWorld");
                });
    }
}
