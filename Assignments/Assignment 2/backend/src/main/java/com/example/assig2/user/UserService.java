package com.example.assig2.user;


import com.example.assig2.user.dto.*;
import com.example.assig2.user.dto.UserListDTO;
import com.example.assig2.user.dto.UserMinimalDTO;
import com.example.assig2.user.mapper.*;
import com.example.assig2.user.model.ERole;
import com.example.assig2.user.model.Role;
import com.example.assig2.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder encoder;

    public List<UserMinimalDTO> allUsersMinimal() {
        return userRepository.findAll()
                .stream().map(userMapper::userMinimalFromUser)
                .collect(toList());
    }

    public List<UserListDTO> allUsersForList() {
        return userRepository.findAll()
                .stream().map(userMapper::userListDtoFromUser)
                .collect(toList());
    }

    private User findById(Long id){
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + id));
    }

    private User findByUsername(String username){
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + username));
    }

    public void delete(Long id) {
        userRepository.deleteById((id));
    }

    public UserDTO create(UserDTO userDTO) {

        User user = userMapper.fromUserDTO(userDTO);
        Set<Role> set = new HashSet<>();
        Role role = Role.builder().name(ERole.REGULAR).build();
        set.add(role);
        user.setRoles(set);
        user.setPassword(encoder.encode(user.getPassword()));

        return userMapper.toUserDTO(userRepository
                .save(user));
    }

    public UserDTO edit(Long id, UserDTO userDTO) {
        User user = findById(id);
        user.setUsername(userDTO.getName());
        user.setEmail(userDTO.getEmail());

        if(!userDTO.getPassword().equals("")){
            user.setPassword(encoder.encode(userDTO.getPassword()));
        }


        return userMapper.toUserDTO(userRepository.save(user));
    }

}
