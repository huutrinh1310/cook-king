package fptu.prm.cookcook.entities;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

public class Account {
    private int id;
    private String address;
    private String avatar;
    private String email;
    private Map<Integer, Integer> listOfFollower;
    private Map<Integer, Integer> listOfFollowing;
    private Map<Integer, Integer> listOfRecipes;
    private String name;
    private String username;
    private String password;
    private String phone;

    public Account() {
    }

    public Account(int id, String address, String avatar, String email, Map<Integer, Integer> listOfFollower, Map<Integer, Integer> listOfFollowing, Map<Integer, Integer> listOfRecipes, String name, String username, String password, String phone) {
        this.id = id;
        this.address = address;
        this.avatar = avatar;
        this.email = email;
        this.listOfFollower = listOfFollower;
        this.listOfFollowing = listOfFollowing;
        this.listOfRecipes = listOfRecipes;
        this.name = name;
        this.username = username;
        this.password = password;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Map<Integer, Integer> getListOfFollower() {
        return listOfFollower;
    }

    public void setListOfFollower(Map<Integer, Integer> listOfFollower) {
        this.listOfFollower = listOfFollower;
    }

    public Map<Integer, Integer> getListOfFollowing() {
        return listOfFollowing;
    }

    public void setListOfFollowing(Map<Integer, Integer> listOfFollowing) {
        this.listOfFollowing = listOfFollowing;
    }

    public Map<Integer, Integer> getListOfRecipes() {
        return listOfRecipes;
    }

    public void setListOfRecipes(Map<Integer, Integer> listOfRecipes) {
        this.listOfRecipes = listOfRecipes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", address='" + address + '\'' +
                ", avatar='" + avatar + '\'' +
                ", email='" + email + '\'' +
                ", listOfFollower=" + listOfFollower +
                ", listOfFollowing=" + listOfFollowing +
                ", listOfRecipes=" + listOfRecipes +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
