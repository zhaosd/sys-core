package com.fw121.core.domain;

import javax.persistence.*;

/**
 * Created by mvt-zhaosandong-mac on 2018/7/20.
 */
public class BaseEntity {

    @Id
    //注意，如果是老的项目，表中的主键可能不叫做id，这时可以在父类中去掉这个属性，改在子类中实现
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Transient
    private Integer page = 1;

    @Transient
    private Integer rows = 10;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

}
