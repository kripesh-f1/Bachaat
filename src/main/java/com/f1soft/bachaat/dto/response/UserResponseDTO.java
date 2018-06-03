package com.f1soft.bachaat.dto.response;

import com.f1soft.bachaat.entity.Role;
import com.f1soft.bachaat.entity.User;

import java.util.List;

public class UserResponseDTO
{
    private Long id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String emailAddress;
    private String address;
    private String mobileNumber;
    private String password;
    private int activationCode;
    private List<Role> roles;

    public UserResponseDTO(String firstName, String middleName, String lastName, String emailAddress,
                           String address, String mobileNumber, String password) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.address = address;
        this.mobileNumber = mobileNumber;
        this.password = password;
    }

    public UserResponseDTO(){

    }

    public UserResponseDTO(User user) {
        this.id=user.getId();
        this.firstName=user.getFirstName();
        this.middleName=user.getMiddleName();
        this.lastName=user.getLastName();
        this.activationCode=user.getActivationCode();
        this.address=user.getAddress();
        this.emailAddress=user.getEmailAddress();
        this.password=user.getPassword();
        this.mobileNumber=user.getMobileNumber();
        this.roles=user.getRoles();

    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getActivationCode() {
        return activationCode;
    }

    public void setActivationCode(int activationCode) {
        this.activationCode = activationCode;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserResponseDTO that = (UserResponseDTO) o;

        return firstName != null ? firstName.equals(that.firstName) : that.firstName == null;
    }

    @Override
    public int hashCode() {
        return firstName != null ? firstName.hashCode() : 0;
    }
}
