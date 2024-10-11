package org.example;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
@Data
public class User {
    private Long id;
    private String name;
    private String email;
    private String password;
    private List<Habit> habits=new ArrayList<>();

    public User(Long id, String name, String email, String password){
        this.id=id;
        this.name=name;
        this.email=email;
        this.password=password;
    }
}
