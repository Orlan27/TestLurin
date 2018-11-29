package com.zed.lurin.domain.dto;

public class UserLdapDTO {

    private String firstName;
    private String secondName;
    private String surname;
    private String lastName;
    private String userName;
    private String email;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstNameAndSecondName(RegexSpaceDTO regexSpaceDTO, int order) {
        if(order==2){
            this.firstName = regexSpaceDTO.getFirts();
            this.secondName = regexSpaceDTO.getSecond();
        }
    }

    public String getSecondName() {
        return secondName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurnameAndLastName(RegexSpaceDTO regexSpaceDTO, int order) {
        if(order==3){
            this.surname = regexSpaceDTO.getFirts();
            this.lastName = regexSpaceDTO.getSecond();
        }
    }

    public String getLastName() {
        return lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName, int order) {
        if(order==1)
            this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email, int order) {
        if(order==4) {
            this.email = email;
        }
    }

}
