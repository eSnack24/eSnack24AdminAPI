package org.esnack24api.esnack24adminapi.admin.exception;

import lombok.Data;


@Data
public class AdminTaskException extends RuntimeException {

    private int status;

    private String msg;

    public AdminTaskException(final int status, String msg) {

        super(status + "_" + msg);

        this.status = status;
        this.msg = msg;
    }
}
