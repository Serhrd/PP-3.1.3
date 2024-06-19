package ru.kata.spring.boot_security.demo.dbInit;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;


@Component
public class DBinit {

    UserService us;
    RoleService rs;

    @Autowired
    public DBinit(UserService userService, RoleService roleService) {
        this.us = userService;
        this.rs = roleService;
    }
    @Transactional
    @PostConstruct
    public void run() {

        rs.addRole(new Role("ROLE_ADMIN"));
        rs.addRole(new Role("ROLE_USER"));

        Set<Role> adminRole = new HashSet<>();
        Set<Role> userRole = new HashSet<>();
        adminRole.add(rs.getRoleById(1L));
        userRole.add(rs.getRoleById(2L));

        us.addUser(new User("admin","ad", "admin", "admin", adminRole));
        us.addUser(new User("user", "us", "user", "user", userRole));
    }
}
