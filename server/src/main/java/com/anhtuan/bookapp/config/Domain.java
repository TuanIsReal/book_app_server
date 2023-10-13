package com.anhtuan.bookapp.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
public class Domain {
    private int so;

    public int getSo() {
        return so;
    }

    public void setSo(int so) {
        this.so = so;
    }

    public void display(){
        System.out.println("hoang day");
    }

}
