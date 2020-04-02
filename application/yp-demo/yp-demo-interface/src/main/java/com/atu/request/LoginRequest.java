package com.atu.request;

import com.yunphant.annotation.apidoc.ApiModel;
import com.yunphant.annotation.apidoc.ApiModelField;
import com.yunphant.request.YunphantRequest;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;


/**
 * @author lidinglin
 * @version 1.0.0
 * @name LoginRequest
 * @date 2020/4/1 1:28 上午
 * @describe
 */
@Getter
@Setter
@ApiModel(value = "LoginRequest", name = "登录测试服务请求对象", modeType = ApiModel.ApiModeType.REQUEST)
public class LoginRequest extends YunphantRequest {

    @ApiModelField(value = "userName", name = "姓名", required = true)
    @NotBlank(message = "姓名[userName]不能为空")
    private String userName;

    @ApiModelField(value = "passWord", name = "密码", required = true)
    @NotBlank(message = "密码[passWord]不能为空")
    String passWord;

}
