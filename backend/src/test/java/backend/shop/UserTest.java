package backend.shop;

import backend.shop.dto.UserRegistrationDTO;
import backend.shop.repo.UsersRepo;
import backend.shop.service.UsersService;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
public class UserTest {
    @Mock
    private UsersRepo repo;

    @InjectMocks
    private UsersService service;

    @Test
    void registrationTest(){
        UserRegistrationDTO mockUser = new UserRegistrationDTO(
                null,
                "Kamil",
                "Nowak",
                "kamil.nowak@gmail.com",
                "1qaz@WSX3e"
        );

        var res = this.service.registerUser(mockUser);

        assertThat(res).isNotNull();
        verify(this.repo, times(1)).existsByEmail("kamil.nowak@gmail.com");
    }
}
