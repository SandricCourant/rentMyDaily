package com.example.demo.ut;

import com.example.demo.controllers.ProfileController;
import com.example.demo.domain.Owner;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;

@SpringBootTest
@AutoConfigureMockMvc
public class ProfileControllerTest {
    @Autowired
    ProfileController profileController;

    @Test
    public void testGetUserInfo() {
        //Defining the mock
        Owner owner = new Owner();
        owner.setId(1);
        owner.setLogin("UserTest");

        //Testing
        UserDetails result = profileController.me(owner).getBody();
        assert result != null;
        Assertions.assertEquals("UserTest", result.getUsername());
    }
}
