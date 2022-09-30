package com.cybertech.farmcheck.web.rest.vm;

public class FarmUserRoleVM {

    private Long farmId;

    private String userLogin;

    private Short role;

    public FarmUserRoleVM() {
    }

    public FarmUserRoleVM(
        Long farmId,
        String userLogin,
        Short role
    ) {
        this.farmId = farmId;
        this.userLogin = userLogin;
        this.role = role;
    }

    public Long getFarmId() {
        return farmId;
    }

    public void setFarmId(Long farmId) {
        this.farmId = farmId;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public Short getRole() {
        return role;
    }

    public void setRole(Short role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "FarmUserRoleVM{" +
            "farmId=" + farmId +
            ", userLogin='" + userLogin + '\'' +
            ", role=" + role +
            '}';
    }
}
