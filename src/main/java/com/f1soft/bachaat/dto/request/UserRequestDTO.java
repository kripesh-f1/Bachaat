package com.f1soft.bachaat.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

import static com.f1soft.bachaat.utils.MessageConstant.*;


public class UserRequestDTO implements Serializable {

    private Long id;

    @NotNull(message = FIRST_NAME_REQUIRED)
    @Size(min = 1, message = NAME_LENGTH_MIN)
    @Size(max = 20, message = NAME_LENGTH_MAX)
    @Pattern(regexp = "^[a-zA-Z]+$", message = ALPHABET_MESSAGE)
    private String firstName;

    @Size(max = 20, message = NAME_LENGTH_MAX)
    @Pattern(regexp = "^[a-zA-Z.]*$", message = ALPHABET_MESSAGE)
    private String middleName;

    @NotNull(message = LAST_NAME_REQUIRED)
    @Size(min = 1, message = NAME_LENGTH_MIN)
    @Size(max = 20, message = NAME_LENGTH_MAX)
    @Pattern(regexp = "^[a-zA-Z]+$", message = ALPHABET_MESSAGE)
    private String lastName;

    @NotNull(message = EMAIL_ADDRESS_REQUIRED)
    @Size(min = 5, message = EMAIL_LENGTH_MIN)
    @Pattern(regexp = "^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+$", message = EMAIL_MESSAGE)
    private String emailAddress;

    @NotNull(message = ADDRESS_REQUIRED)
    @Size(min = 1, message = NAME_LENGTH_MIN)
    @Size(max = 20, message = NAME_LENGTH_MAX)
    @Pattern(regexp = "^[a-zA-Z]+$", message = ALPHABET_MESSAGE)
    private String address;

    @Size(min = 10, message = MOBILE_NUMBER_INVALID)
    @NotNull(message = MOBILE_NUMBER_REQUIRED)
    @Pattern(regexp = "^[0-9]+$", message = NUMBER_MESSAGE)
    private String mobileNumber;

    @NotNull(message = PASSWORD_REQUIRED)
    @Size(min = 1, message = NAME_LENGTH_MIN)
    @Size(max = 20, message = NAME_LENGTH_MAX)
    @Pattern(regexp = "(?=^.{8,}$)((?=.*\\d)(?=.*\\W+))(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*$", message = PASSWORD_MESSAGE)
    private String password;


    public UserRequestDTO(Long id, String firstName, String middleName,
                          String lastName,
                          String emailAddress,
                          String address,
                          String mobileNumber,
                          String password) {
        this.id = id;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.address = address;
        this.mobileNumber = mobileNumber;
        this.password = password;
    }

    public UserRequestDTO() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserRequestDTO that = (UserRequestDTO) o;

        return firstName != null ? firstName.equals(that.firstName) : that.firstName == null;
    }

    @Override
    public int hashCode() {
        return firstName != null ? firstName.hashCode() : 0;
    }
}
