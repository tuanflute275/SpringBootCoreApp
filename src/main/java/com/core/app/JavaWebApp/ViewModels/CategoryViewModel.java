package com.core.app.JavaWebApp.ViewModels;

import lombok.Getter;
import lombok.Setter;

public class CategoryViewModel {
    public String name;
    public boolean status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
