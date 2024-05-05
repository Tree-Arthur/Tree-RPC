package com.tree.example.common.model;

import java.io.Serializable;

/**
 * @author : Tree Arthur
 * @date: 2024/5/4 17:04
 * @Package: com.tree.example.common.model
 * @Description: 用户实体类
 * @version: 1.0
 */
public class User implements Serializable {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
