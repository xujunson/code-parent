package com.atu.excel.handle;

import com.atu.excel.pojo.ActionContext;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * 链入口
 */
public class Init<T> {

    private ActionContext<T> act = new ActionContext<T>();

    private VerificationTable<T> ver;

    public Init(File file, Class<? extends T> t, boolean distinct) {
        super();
        act.setFile(file);
        act.setObjects(t);
        act.setDistinct(distinct);
    }

    public Init(InputStream inputStream, Class<? extends T> t, boolean distinct) throws IOException {
        super();
        act.setInputStream(inputStream);
        act.setObjects(t);
        act.setDistinct(distinct);
    }

    public ActionContext<T> start() {
        ver = new VerificationTable<T>();
        ver.prepare(act);
        return act;
    }

    public VerificationTable<T> getVer() {
        return ver;
    }

    public void setVer(VerificationTable<T> ver) {
        this.ver = ver;
    }

    public ActionContext<T> getAct() {
        return act;
    }

    public void setAct(ActionContext<T> act) {
        this.act = act;
    }

}