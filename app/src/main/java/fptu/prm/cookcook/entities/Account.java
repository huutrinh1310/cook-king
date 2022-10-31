package fptu.prm.cookcook.entities;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

public class Account {
    private String id;
    private String address;
    private String avatar;
    private String email;
    private String description;
    private List<Integer> listOfFollower;
    private List<Integer> listOfFollowing;
    private List<Integer> listOfRecipes;
    private String name;
    private String username;
    private String password;
    private String phone;

    public Account() {
    }

    public Account(String id, String address, String avatar, String email, String description, List<Integer> listOfFollower, List<Integer> listOfFollowing, List<Integer> listOfRecipes, String name, String username, String password, String phone) {
        this.id = id;
        this.address = address;
        this.avatar = avatar;
        this.email = email;
        this.description = description;
        this.listOfFollower = listOfFollower;
        this.listOfFollowing = listOfFollowing;
        this.listOfRecipes = listOfRecipes;
        this.name = name;
        this.username = username;
        this.password = password;
        this.phone = phone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Integer> getListOfFollower() {
        return listOfFollower;
    }

    public void setListOfFollower(List<Integer> listOfFollower) {
        this.listOfFollower = listOfFollower;
    }

    public List<Integer> getListOfFollowing() {
        return listOfFollowing;
    }

    public void setListOfFollowing(List<Integer> listOfFollowing) {
        this.listOfFollowing = listOfFollowing;
    }

    public List<Integer> getListOfRecipes() {
        return listOfRecipes;
    }

    public void setListOfRecipes(List<Integer> listOfRecipes) {
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
                "id='" + id + '\'' +
                ", address='" + address + '\'' +
                ", avatar='" + avatar + '\'' +
                ", email='" + email + '\'' +
                ", description='" + description + '\'' +
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
