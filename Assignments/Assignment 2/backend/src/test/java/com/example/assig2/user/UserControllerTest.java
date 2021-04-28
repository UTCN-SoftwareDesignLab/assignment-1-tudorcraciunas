package com.example.assig2.user;

import com.example.assig2.BaseControllerTest;
import com.example.assig2.TestCreationFactory;
import com.example.assig2.security.AuthService;
import com.example.assig2.user.dto.UserDTO;
import com.example.assig2.user.dto.UserListDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static com.example.assig2.TestCreationFactory.randomLong;
import static com.example.assig2.UrlMapping.ENTITY;
import static com.example.assig2.UrlMapping.USERS;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerTest extends BaseControllerTest {

    @InjectMocks
    private UserController controller;

    @Mock
    private UserService userService;

    @Mock
    private AuthService authService;

    @BeforeEach
    protected void setUp() {
        super.setUp();
        MockitoAnnotations.initMocks(this);
        controller = new UserController(userService, authService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void allUsers() throws Exception {
        List<UserListDTO> userListDTOs = TestCreationFactory.listOf(UserListDTO.class);
        when(userService.allUsersForList()).thenReturn(userListDTOs);

        ResultActions result = mockMvc.perform(get(USERS));
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(userListDTOs));
    }

//    @Test
//    void createUser() throws Exception {
//        UserDTO dto = TestCreationFactory.newUserListDTO();
//        when(userService.create(dto)).thenReturn(dto);
//
//        ResultActions result = performPostWithRequestBody(USERS, dto);
//        result.andExpect(status().isOk());
//    }

    @Test
    void edit() throws Exception {
        long id = randomLong();
        UserDTO userListDTO = TestCreationFactory.newUserListDTO();

        when(userService.edit(id, userListDTO)).thenReturn(userListDTO);

        ResultActions result = performPatchWithRequestBodyAndPathVariable(USERS + ENTITY, userListDTO, id);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(userListDTO));
    }
}