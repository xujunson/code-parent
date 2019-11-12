package com.atu.senior.Jersey;

/**
 * @author ：mark
 * @date ：Created in 2019/11/12 18:00
 * @description：${description}
 */
public class PersonEntity {
    private String id;
    private String name;
    private String addr;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public PersonEntity() {
    }

    public PersonEntity(String id, String name, String addr) {
        this.id = id;
        this.name = name;
        this.addr = addr;
    }
}
