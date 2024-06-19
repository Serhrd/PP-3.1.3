package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;


import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final RoleService rs;
    private final UserService us;

    @Autowired
    public AdminController(RoleService roleServices, UserService userServices) {
        this.rs = roleServices;
        this.us = userServices;
    }

    @GetMapping
    public String getAllUsers(Model model) {
        model.addAttribute("users", us.getListUsers());
        return "users";
    }

    @GetMapping(value = "/{id}")
    public String getUserById(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", us.getUser(id));
        return "user";
    }

    @GetMapping(value = "/new")
    public String addUser(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("allRoles", rs.getAllRoles());
        return "new";
    }

    @PostMapping(value = "/new")
    public String add(@ModelAttribute("user") User user, @RequestParam(name = "id", required = false) List<Long> id) {
            Set<Role> role = rs.findAllRoleId(id);
            user.setRoles(role);
            us.addUser(user);
            return "redirect:/admin";
    }

    @DeleteMapping(value = "/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        us.deleteUser(id);
        return "redirect:/admin";
    }


    @GetMapping(value = "/update/{id}")
    public String updateUser(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", us.getUser(id));
        model.addAttribute("allRoles", rs.getAllRoles());
        return "update";
    }

    @PatchMapping(value = "/update")
    public String update(@ModelAttribute("user") User user,@RequestParam(name = "id", required = false) List<Long> id) {
            Set<Role> role = rs.findAllRoleId(id);
            user.setRoles(role);
            us.updateUser(user);
            return "redirect:/admin";
    }
}
