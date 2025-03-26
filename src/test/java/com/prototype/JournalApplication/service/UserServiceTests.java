package com.prototype.JournalApplication.service;

import com.prototype.JournalApplication.repository.UserRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

//@SpringBootTest use mock bean if using this but not advised

public class UserServiceTests {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepo userRepo;

    @BeforeEach
    void setUserRepo(){
        MockitoAnnotations.initMocks(this);// to intialize the mocks used to test
    }

    @Test
    public void testFindByUserName(){
        Assertions.assertNull(userRepo.findByUserName("ram"));
    }

    @Disabled
    @ParameterizedTest
    @ValueSource(strings ={"Ram","Sham","Bham"})
    public void testFindByUserNameParameterized(String userName){
        Assertions.assertNull(userRepo.findByUserName(userName));
    }
    //@ArgumentSource--list of arguments can be passed
    @Disabled
    @ParameterizedTest
    @CsvSource({"1,2,3","1,3,4"})
    public void testParameter(int a, int b,int expected){
        Assertions.assertEquals(expected, a+b);
    }


    //@Mock
//    @MockBean used when
}
