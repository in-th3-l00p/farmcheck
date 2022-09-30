package com.cybertech.farmcheck.service.dto;

import com.cybertech.farmcheck.domain.User;

public class FarmUserDTO {

    private Long id;

    private String login;

    private Short farmRole;

    private String firstName;

    private String lastName;

    private String imageUrl;

    public FarmUserDTO() {
    }

    public FarmUserDTO(
        Long id,
        String login,
        Short farmRole,
        String firstName,
        String lastName,
        String imageUrl
    ) {
        this.id = id;
        this.login = login;
        this.farmRole = farmRole;
        this.firstName = firstName;
        this.lastName = lastName;
        this.imageUrl = imageUrl;
    }

    public FarmUserDTO(User user, Short farmRole) {
        this.id = user.getId();
        this.login = user.getLogin();
        this.farmRole = farmRole;
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.imageUrl = user.getImageUrl();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Short getFarmRole() {
        return farmRole;
    }

    public void setFarmRole(Short farmRole) {
        this.farmRole = farmRole;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "FarmUserDTO{" +
            "id=" + id +
            ", login='" + login + '\'' +
            ", farmRole=" + farmRole +
            ", fistName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", imageUrl='" + imageUrl + '\'' +
            '}';
    }
}
