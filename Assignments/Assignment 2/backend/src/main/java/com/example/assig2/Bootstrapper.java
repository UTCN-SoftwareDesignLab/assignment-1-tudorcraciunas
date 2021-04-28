package com.example.assig2;

import com.example.assig2.item.BookRepository;
import com.example.assig2.item.BookService;
import com.example.assig2.item.model.DTO.BookDTO;
import com.example.assig2.security.AuthService;
import com.example.assig2.security.dto.SignupRequest;
import com.example.assig2.user.RoleRepository;
import com.example.assig2.user.UserRepository;
import com.example.assig2.user.model.ERole;
import com.example.assig2.user.model.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class Bootstrapper implements ApplicationListener<ApplicationReadyEvent> {

    private final RoleRepository roleRepository;

    private final UserRepository userRepository;

    private final AuthService authService;

    private final BookService bookService;

    private final BookRepository itemRepository;

    @Value("${app.bootstrap}")
    private Boolean bootstrap;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        if (bootstrap) {
            itemRepository.deleteAll();
            userRepository.deleteAll();
            roleRepository.deleteAll();
            for (ERole value : ERole.values()) {
                roleRepository.save(
                        Role.builder()
                                .name(value)
                                .build()
                );
            }
            authService.register(SignupRequest.builder()
                    .email("tudor@email.com")
                    .username("tudor")
                    .password("CoolPass!1")
                    .roles(Set.of("ADMIN"))
                    .build());
            authService.register(SignupRequest.builder()
                    .email("user2@email.com")
                    .username("user2")
                    .password("UserPass!2")
                    .roles(Set.of("REGULAR"))
                    .build());
            authService.register(SignupRequest.builder()
                    .email("user1@email.com")
                    .username("user1")
                    .password("UserPass!1")
                    .roles(Set.of("REGULAR"))
                    .build());
            bookService.create(BookDTO.builder()
                    .title("How to: Stop not dying.")
                    .author("Time Itself")
                    .genre("Motivation, Tutorial")
                    .price((float) 57.89)
                    .quantity(48)
                    .build());
            bookService.create(BookDTO.builder()
                    .title("Hitchhikers Guide to the Galaxy")
                    .author("Douglas Adams")
                    .genre("Science Fiction Comedy")
                    .price((float) 57.89)
                    .quantity(0)
                    .build());

        }
    }
}
