package entity;

import entity.enums.Role;

public class Player {
    private String name;
    private Role role;

    public Player(String name, Role role) {
        this.name = name;
        this.role = role;
    }

    public Player() {
        this.name = "";
        this.role = Role.USER;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
