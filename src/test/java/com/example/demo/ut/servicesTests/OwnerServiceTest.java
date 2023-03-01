package com.example.demo.ut.servicesTests;

import com.example.demo.domain.Owner;
import com.example.demo.exceptions.AccountExistsException;
import com.example.demo.repositories.OwnerRepository;
import com.example.demo.services.OwnerService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
public class OwnerServiceTest {
    @Autowired
    private OwnerService ownerService;
    @MockBean
    private OwnerRepository mockOwnerRepository;
    @Test
    public void testRegisterAccountAlreadyExist(){
        Mockito.when(
                mockOwnerRepository.findByLogin(ArgumentMatchers.anyString())
        ).thenReturn(Optional.of(new Owner()));
        try{
            UserDetails result = ownerService.save("jean","mdp","jean","claude","jc@gmail.com","0606060606");
        } catch (AccountExistsException aee){

        }
    }
    @Test
    public void testRegister() throws AccountExistsException {
        Mockito.when(
                mockOwnerRepository.findByLogin(ArgumentMatchers.anyString())
        ).thenReturn(Optional.empty());
        Owner owner = new Owner();
        owner.setId(999);
        owner.setLogin("jc");
        Mockito.when(
                mockOwnerRepository.save(ArgumentMatchers.any(Owner.class))
        ).thenReturn(owner);

        UserDetails result = ownerService.save("jc","mdp","jean","claude","jc@gmail.com","0606060606");
        Assertions.assertEquals("jc", result.getUsername());
    }

    @Test
    public void testFindById(){
        Owner owner = new Owner();
        owner.setId(999);
        Mockito.when(
                mockOwnerRepository.findById(ArgumentMatchers.anyInt())
                ).thenReturn(Optional.of(owner));
        Owner result = ownerService.findById(0);
        Assertions.assertEquals(999, result.getId());
    }
    @Test
    public void testFindByIdNotFound(){
        Mockito.when(
                mockOwnerRepository.findById(ArgumentMatchers.anyInt())
        ).thenReturn(Optional.empty());

        Owner result = ownerService.findById(0);
        Assertions.assertNull(result);
    }
}
