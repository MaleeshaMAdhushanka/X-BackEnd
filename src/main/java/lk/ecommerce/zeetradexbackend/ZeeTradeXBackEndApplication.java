package lk.ecommerce.zeetradexbackend;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class ZeeTradeXBackEndApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZeeTradeXBackEndApplication.class, args);
    }
    @Bean
    public static ModelMapper modelMapper(){
        return new ModelMapper();
    }

//    @Bean
//    public PasswordEncoder passwordEncoder(){
//        return new BCryptPasswordEncoder();
//    }

}
