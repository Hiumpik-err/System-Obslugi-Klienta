package backend.shop;

import backend.shop.dto.UserDTO;
import backend.shop.dto.UserRegistrationDTO;
import backend.shop.model.Users;
import backend.shop.repo.UsersRepo;
import backend.shop.service.UsersService;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@ExtendWith(MockitoExtension.class)
public class UserTests {
    @Mock
    private UsersRepo repo;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @InjectMocks
    private UsersService service;

    @Test
    void positiveRegistrationTest(){
        UserRegistrationDTO mockUser = new UserRegistrationDTO(
                null,
                "Kamil",
                "Nowak",
                "kamil.nowak@gmail.com",
                "1qaz@WSX3e",
                null
        );

        when(this.repo.existsByEmail("kamil.nowak@gmail.com")).thenReturn(false);
        when(this.bCryptPasswordEncoder.encode(any(CharSequence.class))).thenReturn("ZAHASZOWANE_HASLO_123");

        Users savedEntity = Users.builder()
                .firstName("Kamil")
                .lastName("Nowak")
                .email("kamil.nowak@gmail.com")
                .password("1qaz@WSX3e")
                .build();

        when(this.repo.save(any(Users.class))).thenAnswer(i -> i.getArgument(0));

        var res = this.service.registerUser(mockUser);
        assertThat(res).isNotNull();

        verify(repo, times(1)).existsByEmail("kamil.nowak@gmail.com");
        verify(repo, times(1)).save(any(Users.class));
        verify(this.bCryptPasswordEncoder, times(1)).encode(any(CharSequence.class));
    }

    @Test
    void negativeRegistrationTest(){
    }
}
