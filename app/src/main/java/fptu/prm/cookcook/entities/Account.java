package fptu.prm.cookcook.entities;

import com.google.gson.annotations.SerializedName;

public class Account {

    @SerializedName("userId")
    private String userId;
    @SerializedName("userName")
    private String userName;
    @SerializedName("email")
    private String email;
    @SerializedName("account")
    private String account;
    @SerializedName("password")
    private String password;
    @SerializedName("avatar")
    private String avatar;
    @SerializedName("address")
    private String address;
    @SerializedName("phone")
    private String phone;

    public Account() {
    }

    public Account(String userId, String userName, String email, String account, String password, String avatar, String address, String phone) {
        this.userId = userId;
        this.userName = userName;
        this.email = email;
        this.account = account;
        this.password = password;
        this.avatar = avatar;
        this.address = address;
        this.phone = phone;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", avatar='" + avatar + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
