package com.wyf.common.domain;


import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class Organization implements Serializable {

    private Long id;

    private List<Organization> child;




    public Organization() {

    }

    @Override
    public String toString() {
        return "Organization{" +
                "id=" + id +
                ", childs=" + child +
                '}';
    }
}
