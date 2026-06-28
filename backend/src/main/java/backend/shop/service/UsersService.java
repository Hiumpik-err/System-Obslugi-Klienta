package backend.shop.service;

import backend.shop.dto.UserRegistrationDTO;
import backend.shop.dto.UserUpdateDTO;
import backend.shop.exceptions.UserAlreadyExistsException;
import backend.shop.exceptions.UserNotFoundException;
import backend.shop.model.Users;
import backend.shop.repo.UsersRepo;
import jakarta.transaction.Transactional;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;
import java.util.List;

@Service
public class UsersService {
    private final UsersRepo repo;
    private final JavaMailSender mailSender;
    private final BCryptPasswordEncoder bcryptPasswordEncoder;

    public UsersService(UsersRepo repo, JavaMailSender sender, BCryptPasswordEncoder bcryptPasswordEncoder){
        this.repo = repo;
        this.mailSender = sender;
        this.bcryptPasswordEncoder = bcryptPasswordEncoder;
    }

    @Transactional
    public Users registerUser(UserRegistrationDTO userDTO){
        if(this.repo.existsByEmail(userDTO.email())){
            throw new UserAlreadyExistsException("Uzytkownik o podanym emailu juz istnieje");
        }
        Users user = Users.builder()
                .firstName(userDTO.firstName())
                .lastName(userDTO.lastName())
                .email(userDTO.email())
                .password(bcryptPasswordEncoder.encode(userDTO.password()))
                .build();

        this.repo.save(user);
        return user;
    }

    @Transactional
    public void deleteUser(int id){
        if(!this.repo.existsById(id)){
            throw new UserNotFoundException("Nie znaleziono uzytkownika w bazie danych");
        }
        this.repo.deleteById(id);
    }

    public Users updateUser(int id, UserUpdateDTO user) {
        Users u = this.repo.findById(id).orElseThrow(() -> new UserNotFoundException("Nie znaleziono uzytkownika w bazie danych"));

        if(StringUtils.hasText(user.firstName())) u.setFirstName(user.firstName());
        if(StringUtils.hasText(user.lastName())) u.setLastName(user.lastName());
        if(StringUtils.hasText(user.email())) u.setEmail(user.email());
        if(StringUtils.hasText(user.password())) u.setPassword(bcryptPasswordEncoder.encode(user.password()));

        this.repo.save(u);
        return u;
    }

    public boolean restartPassword(String email){
        try{
            var containsUser = this.repo.getByEmail(email);

            if(containsUser.isEmpty()) throw new Exception("user Not Found");

            String newPassword = generateNewPassword();
            var user = containsUser.get();
            user.setPassword(new BCryptPasswordEncoder().encode(newPassword));
            this.repo.save(user);

            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom("myprogroad@gmail.com");
            mailMessage.setTo(email);
            mailMessage.setSubject("Nowe Haslo");
            mailMessage.setText("Witaj dostaliśmy prośbę o restart hasła z tego konta email. Twoje hasło to: " + newPassword +
                    "Jeśli to ty, nic sie nie dzieje, jesli to nie ty, prosimy o natychmiastową zmianę hasła w celach bezpieczeństwa twojego Konta");

            mailSender.send(mailMessage);
            return true;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    private String generateNewPassword(){
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < 10; i++){
            char c = (char)((Math.random() * 95) + 33);
            if(c == ' ') c = (char)((Math.random() * 95) + 33);
            sb.append(c);
        }
        return sb.toString();
    }

    public boolean setActive(int id){
        try{
            Optional<Users> userFound = this.repo.findById(id);
            if(userFound.isPresent()) {
                Users u = userFound.get();
                u.setActive(true);
                this.repo.save(u);
                return true;
            }
            throw new Exception("user Not found");
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
            return false;
        }
    }

    public Optional<List<Users>> getActiveAdmins() {
        try{
            List<Users> activeAdmins = this.repo.findAllActiveByRoleWithRoles("ADMIN");
            if(!activeAdmins.isEmpty()){
                return Optional.of(activeAdmins);
            }
            throw new Exception("Blad");
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return Optional.empty();
        }
    }
}
