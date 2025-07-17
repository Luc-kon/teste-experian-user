package teste.experian.user.security;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import teste.experian.user.entity.User;
import teste.experian.user.repository.UserRepository;

@Configuration
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) {
        if (userRepository.count() == 0) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword("admin123"); // sem encoding
            admin.setRole(User.Role.valueOf("ADMIN"));
            userRepository.save(admin);

            User user = new User();
            user.setUsername("user");
            user.setPassword("user123");
            user.setRole(User.Role.valueOf("USER"));
            userRepository.save(user);

            System.out.println("Usuários 'admin' e 'user' criados com sucesso." );
        }
    }
}