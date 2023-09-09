package com.example.todoTask.controllers;

import com.example.todoTask.models.User;
import com.example.todoTask.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.service.annotation.DeleteExchange;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable Long id) {
        User userObj = this.userService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(userObj);
    }

    @PostMapping
    @Validated(User.CreateUser.class)
    public ResponseEntity<User> create(@Valid @RequestBody User userObj) {
        this.userService.create(userObj);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(userObj.getId())
                .toUri();

        return ResponseEntity.created(uri).body(userObj);
    }

    @PutMapping("/{id}")
    @Validated(User.UpdateUser.class)
    public ResponseEntity<User> update(@Valid @RequestBody User userObj, @PathVariable Long id) {
        userService.findById(id);
        userObj.setId(id);
        User userUpdate = this.userService.update(userObj);
        return ResponseEntity.status(HttpStatus.OK).body(userUpdate);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        userService.findById(id);
        userService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body("Usuario deletado");
    }

}
