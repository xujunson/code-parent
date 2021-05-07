package com.atu.design.codeoptimize.enums;

/**
 * @Author: Tom
 * @Date: 2021/5/7 5:49 下午
 * @Description:
 */
public enum CompanyEnum {
    A("A", "企业A"),
    B("B", "企业B"),
    C("C", "企业C");

    private String code;
    private String name;

    CompanyEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public static CompanyEnum getCompanyEnum(String code) {
        for (CompanyEnum c : CompanyEnum.values()) {
            if (c.code.equalsIgnoreCase(code)) {
                return c;
            }
        }
        return null;
    }
}
