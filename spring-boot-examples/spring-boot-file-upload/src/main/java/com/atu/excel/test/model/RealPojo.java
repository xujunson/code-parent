package com.atu.excel.test.model;

import com.alibaba.fastjson.JSON;
import com.atu.excel.annotation.Mapping;
import com.atu.excel.handle.Init;
import com.atu.excel.pojo.ActionContext;

import java.io.File;

public class RealPojo {
    @Mapping(key = "门店编号", length = 60)
    private String shopCode;
    @Mapping(key = "门店名称", length = 60)
    private String shopName;

    public String getShopCode() {
        return shopCode;
    }

    public void setShopCode(String shopCode) {
        this.shopCode = shopCode;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public static void main(String[] args) {
        ActionContext<RealPojo> act = null;
        Init<RealPojo> init = null;
        File file = new File("/Users/xujunson/Downloads/test.xlsx");
        init = new Init<RealPojo>(file, RealPojo.class, false);
        act = init.start();
        System.out.println(JSON.toJSONString(act.getSheets().get(0).getInfo()));
    }
}

