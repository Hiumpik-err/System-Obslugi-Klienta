package backend.shop.service;

import backend.shop.model.Users;
import backend.shop.repo.UsersRepo;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.List;

@Service
public class UsersService {
    private final UsersRepo repo;
    private final JavaMailSender mailSender;

    public UsersService(UsersRepo repo, JavaMailSender sender){
        this.repo = repo;
        this.mailSender = sender;
    }

    public Optional<Users> registerUser(Users user){
        try{
            Users userCopy = new Users(user);
            user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
            repo.save(user);
            return Optional.of(userCopy);

        }
        catch (Exception ex){
            return Optional.empty();
        }
    }

    public boolean deleteUser(int id){
        try{
            this.repo.deleteById(id);
            return true;
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
            return false;
        }
    }

    public Optional<Users> updateUser(int id, Users user) {
        try{
            Optional<Users> u = this.repo.findById(id);
            if(u.isPresent()){
                Users selectedUser = u.get();
                selectedUser.setFirstName(user.getFirstName());
                selectedUser.setLastName(user.getLastName());
                selectedUser.setEmail(user.getEmail());
                selectedUser.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
                selectedUser.setRole(user.getRole());

                System.out.println(selectedUser.toString());
                this.repo.save(selectedUser);
                return Optional.of(selectedUser);
            }
            throw new Exception("User not found");
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
            return Optional.empty();
        }
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
