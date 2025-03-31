package com.prak.web.exceptions;

public class ActionNotAllowedException extends RuntimeException {
    public ActionNotAllowedException() {
        super("This action is not allowed for this user");
    }
}
