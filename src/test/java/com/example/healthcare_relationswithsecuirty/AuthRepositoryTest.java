package com.example.healthcare_relationswithsecuirty;

import com.example.healthcare_relationswithsecuirty.Model.User;
import com.example.healthcare_relationswithsecuirty.Repository.AuthRepository;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AuthRepositoryTest {

    @Autowired
    AuthRepository authRepository;

    User user;


    @BeforeEach
    void setUp() {
        /////           I put the null as id for user to success the test
        user = new User(null,"RehamS", "123","ADMIN",null,null);
    }

    @Test
    public void findUserByIdTest(){
        authRepository.save(user);
        User checkUser= authRepository.findUserById(user.getId());
        Assertions.assertThat(checkUser).isEqualTo(user);
    }

}
