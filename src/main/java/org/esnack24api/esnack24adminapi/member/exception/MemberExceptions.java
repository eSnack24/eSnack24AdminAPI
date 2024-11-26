package org.esnack24api.esnack24adminapi.member.exception;

public enum MemberExceptions {

    BAD_AUTH(400, "ID/PW incorrect"),
    TOKEN_NOT_ENOUGH(401, "More Tokens requeired"),
    ACCESSTOKEN_TOO_SHORT(401, "Access Token Too short"),
    REQUIRE_SIGN_IN(401, "Require sign in");

    private MemberTaskException exception;

    MemberExceptions(int status, String msg) {

        this.exception = new MemberTaskException(status, msg);
    }

    public MemberTaskException get() {
        return exception;
    }
}
