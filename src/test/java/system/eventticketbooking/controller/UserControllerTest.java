package system.eventticketbooking.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import system.eventticketbooking.entity.User;
import system.eventticketbooking.service.UserService;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;

public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    private User user;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setId(1L);
        user.setUsername("testUser");
        // Set other properties as needed
    }

    @Test
    public void testGetAllUsers() {
        List<User> users = Arrays.asList(user);
        when(userService.getAllUsers()).thenReturn(users);

        ResponseEntity<List<User>> response = userController.getAllUsers();

        assert response.getStatusCode().is2xxSuccessful();
        assert response.getBody().equals(users);
    }

    @Test
    public void testCreateUser() {
        when(userService.createUser(user)).thenReturn(user);

        ResponseEntity<User> response = userController.createUser(user);

        assert response.getStatusCode().is2xxSuccessful();
        assert response.getBody().equals(user);
    }
}
