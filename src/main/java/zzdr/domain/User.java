package zzdr.domain;

/*
用户实体类
* */
public class User {
    private int id;
    private String username;
    private String password;
    private String email;
    private String telephone;
    private int borrowNumber;
    private int landNumber;

    public int getBorrowNumber() {
        return borrowNumber;
    }

    public void setBorrowNumber(int borrowNumber) {
        this.borrowNumber = borrowNumber;
    }

    public int getLandNumber() {
        return landNumber;
    }

    public void setLandNumber(int landNumber) {
        this.landNumber = landNumber;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setLandNumber() {
    }
}
