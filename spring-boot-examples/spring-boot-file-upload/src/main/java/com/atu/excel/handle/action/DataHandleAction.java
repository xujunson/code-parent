package com.atu.excel.handle.action;

import com.atu.excel.pojo.ActionContext;

public interface DataHandleAction<T> {

    void prepare(ActionContext<T> act);

    boolean commit(ActionContext<T> act);

    boolean rollback(ActionContext<T> act);
}
