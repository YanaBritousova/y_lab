package org.example.models;

import lombok.Data;
import org.example.enums.Role;

import java.util.ArrayList;
import java.util.List;

@Data
public class User {
    private Long id;
    private String name;
    private String email;
    private String password;
    private Role role;
    private List<Habit> habits=new ArrayList<>();

    public User(Long id, String name, String email, String password, Role role){
        this.id=id;
        this.name=name;
        this.email=email;
        this.password=password;
        this.role = role;
    }
}
