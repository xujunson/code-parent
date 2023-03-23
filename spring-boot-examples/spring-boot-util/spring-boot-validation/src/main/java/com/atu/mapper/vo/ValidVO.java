package com.atu.mapper.vo;

import com.atu.annotation.EnumString;
import com.atu.validation.ValidGroup;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;

/**
 * @Author: Tom
 * @Date: 2022/3/1 14:29
 * @Description:
 */
@Data
public class ValidVO {

    /**
     * 同一个类 在新增和修改时进行分组校验
     */
    @Null(groups = ValidGroup.Crud.Create.class)
    @NotNull(groups = ValidGroup.Crud.Update.class, message = "应用ID不能为空")
    private String id;

    @Length(min = 6, max = 12, message = "appId长度必须位于6到12之间")
    private String appId;

    @NotBlank(message = "名字为必填项")
    private String name;

    @Email(message = "请填写正确的邮箱地址")
    private String email;

    @EnumString(value = {"F", "M"}, message = "性别只允许为F或M")
    private String sex;

    @NotEmpty(message = "级别不能为空")
    private String level;
}