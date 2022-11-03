package life.cj.community.community.exception;

/**
 * @author cj
 * @apiNote TODO
 * @date 2022/11/3 14:56
 */
public enum CustomizeErrorCode implements ICustomizeErrorCode {
    QUESTION_NOT_FOUND("你找的问题不在了，要不要换个试试？")
    ;

    private String message;

    CustomizeErrorCode(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
