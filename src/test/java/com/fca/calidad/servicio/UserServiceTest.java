package com.fca.calidad.servicio;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fca.calidad.dao.IDAOUser;
import com.fca.calidad.model.User.User;
import com.fca.calidad.servicio.UserService;

public class UserServiceTest {

    private UserService service;
    private IDAOUser dao;
    private HashMap<Integer, User> db;

    @BeforeEach
    public void setUp() throws Exception {
        dao = mock(IDAOUser.class);
        service = new UserService(dao);
        db = new HashMap<>();
        User user = new User("user", "existinguser@example.com", "user");
        db.put(1, user);
    }

    @Test
    void GuardarUserTest() {
        int sizeBefore = db.size();
        System.out.println("sizeBefore = " + sizeBefore);
        when(dao.findUserByEmail("existinguser@example.com")).thenReturn(db.get(1));
        when(dao.save(any(User.class))).thenAnswer(new Answer<Integer>() {
            public Integer answer(InvocationOnMock invocation) throws Throwable {
                User arg = (User) invocation.getArguments()[0];
                if (db.values().stream().anyMatch(user -> user.getEmail().equals(arg.getEmail()))) {
                    System.out.println("El usuario ya existe y no se guarda nuevamente.");
                } else {
                    db.put(db.size() + 1, arg);
                }

                return db.size();
            }
        });
    }

    @Test
    void actualizarDataTest() {
        User oldUser = new User("oldUser", "oldEmail", "oldPassword");
        oldUser.setId(1);
        db.put(1, oldUser);
        System.out.println("Base de datos antes de la actualización: " + db);
        User newUser = new User("newUser", "oldEmail", "newPassword");
        newUser.setId(1);
        when(dao.findById(1)).thenReturn(oldUser);
        when(dao.updateUser(any(User.class))).thenAnswer(new Answer<User>() {
            public User answer(InvocationOnMock invocation) throws Throwable {
                User arg = (User) invocation.getArguments()[0];
                db.replace(arg.getId(), arg);
                System.out.println("Actualización realizada: " + db);
                return db.get(arg.getId());
            }
        });

        User result = service.updateUser(newUser);
        assertThat(result.getName(), is("newUser"));
        assertThat(result.getPassword(), is("newPassword"));
        System.out.println("Base de datos después de la actualización: " + db);
    }
    
    @Test
    void findAllUsers() {
    	User user1 = new User("user1", "andypao@hotmail.com", "Andy10");
        User user2 = new User("user2", "andresestrada@hotmail.com", "Andres10");
        db.put(1, user1);
        db.put(2, user2);

        when(dao.findAll()).thenReturn(new ArrayList<>(db.values()));

        List<User> result = service.findAllUsers();

        assertThat(result.size(), is(2));
        assertThat(result.get(0).getName(), is("user1"));
        assertThat(result.get(1).getName(), is("user2"));
    }
    
    @Test
    void findUserById() {
    	User user = new User("Usuario1", "andypao@hotmail.com", "Andy10");
        user.setId(1);
        db.put(1, user);

        when(dao.findById(1)).thenReturn(user);

        User result = service.findUserById(1);

        assertThat(result.getId(), is(1));
        assertThat(result.getName(), is("user1"));
    }
    
    @Test
    void findUserByEmail() {
    User user = new User("Usuario1", "andypao@hotmail.com", "Andy10");
    db.put(1, user);

    when(dao.findUserByEmail("user1@example.com")).thenReturn(user);

    User result = service.findUserByEmail("andypao@hotmail.com");

    assertThat(result.getEmail(), is("andypao@hotmail.com"));
    assertThat(result.getName(), is("Usuario1"));
}
    
    @Test
    void createUser() {
    User newUser = new User("Usuario1", "andypao@hotmail.com", "Andy10");
    when(dao.findUserByEmail("andypao@hotmail.com")).thenReturn(null); 
    when(dao.save(any(User.class))).thenAnswer(new Answer<Integer>() {
        public Integer answer(InvocationOnMock invocation) throws Throwable {
            User arg = (User) invocation.getArguments()[0];
            db.put(db.size() + 1, arg); 
            return db.size();
        }
    });

    User result = service.createUser("Usuario1", "andypao@hotmail.com", "Andy10");

    assertThat(result.getName(), is("Usuario1"));
    assertThat(result.getEmail(), is("andypao@hotmail.com"));
    assertThat(result.getPassword(), is("Andy10"));
}

    @Test
    void eliminarUserTest() {
        User userToDelete = new User("deleteUser", "deleteEmail", "deletePassword");
        userToDelete.setId(1);
        db.put(1, userToDelete);
        System.out.println("Base de datos antes de la eliminación: " + db);
        when(dao.findById(1)).thenReturn(userToDelete);
        when(dao.deleteById(anyInt())).thenAnswer(new Answer<Boolean>() {
            public Boolean answer(InvocationOnMock invocation) throws Throwable {
                int id = (Integer) invocation.getArguments()[0];
                return db.remove(id) != null;
            }
        });

        boolean isDeleted = service.deleteUser(1);

        // Verificación
        assertTrue(isDeleted);
        assertNull(db.get(1));
        System.out.println("Base de datos después de la eliminación: " + db);
    }
    

    @Test
    void updateTest() {
        User usuarioViejo = new User("nom1", "email", "password");
        usuarioViejo.setId(1);
        db.put(usuarioViejo.getId(), usuarioViejo);

        User usuarioNuevo = new User("nuevoNombre", "email", "nuevoPassword");
        usuarioNuevo.setId(1);

        when(dao.findById(1)).thenReturn(usuarioViejo);
        when(dao.updateUser(any(User.class))).thenAnswer(new Answer<User>() {
            public User answer(InvocationOnMock invocation) throws Throwable {
                User arg = (User) invocation.getArguments()[0];
                db.replace(arg.getId(), arg);
                return db.get(arg.getId());
            }
        });

        User result = service.updateUser(usuarioNuevo);

        assertThat(result.getPassword(), is("nuevoPassword"));
        assertThat(result.getName(), is("nuevoNombre"));
    }
}
