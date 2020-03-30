package com.atu.mysql.constant;

import com.github.shyiko.mysql.binlog.event.EventType;

/**
 * @author: Tom
 * @date: 2020-03-29 17:21
 * @description:
 */
public enum OpType {
    ADD,
    UPDATE,
    DELETE,
    OTHER;

    public static OpType to(EventType eventType) {
        switch (eventType) {
            case EXT_WRITE_ROWS:
                return ADD;
            case EXT_UPDATE_ROWS:
                return UPDATE;
            case EXT_DELETE_ROWS:
                return DELETE;
            default:
                return OTHER;
        }
    }
}
