package com.atu.validation;

import javax.validation.groups.Default;

/**
 * @Author: Tom
 * @Date: 2022/3/1 14:53
 * @Description: 分组校验
 */
public interface ValidGroup extends Default {
    interface Crud extends ValidGroup {
        interface Create extends Crud {

        }

        interface Update extends Crud {

        }

        interface Query extends Crud {

        }

        interface Delete extends Crud {

        }
    }
}
