package com.example.mymusic2.model;

import java.util.List;

public class TheLoais {
    private String category;
    private List<TheLoai> theLoais;

    public TheLoais(String category, List<TheLoai> theLoais) {
        this.category = category;
        this.theLoais = theLoais;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<TheLoai> getTheLoais() {
        return theLoais;
    }

    public void setTheLoais(List<TheLoai> theLoais) {
        this.theLoais = theLoais;
    }
}
