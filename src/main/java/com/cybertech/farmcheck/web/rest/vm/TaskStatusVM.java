package com.cybertech.farmcheck.web.rest.vm;

public class TaskStatusVM {
    private String user;
    private boolean status;

    public TaskStatusVM() {
    }

    public TaskStatusVM(String user, boolean status) {
        this.user = user;
        this.status = status;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
