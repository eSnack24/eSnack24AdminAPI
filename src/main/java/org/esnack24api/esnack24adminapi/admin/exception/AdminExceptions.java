package org.esnack24api.esnack24adminapi.admin.exception;

public enum AdminExceptions {

    BAD_AUTH(400, "ID/PW incorrect"),
    TOKEN_NOT_ENOUGH(401, "More Tokens requeired"),
    ACCESSTOKEN_TOO_SHORT(402, "Access Token Too short"),
    REQUIRE_SIGN_IN(403, "Require sign in");

    private AdminTaskException exception;

    AdminExceptions(int status, String msg) {

        this.exception = new AdminTaskException(status, msg);
    }

    public AdminTaskException get() {
        return exception;
    }
}
