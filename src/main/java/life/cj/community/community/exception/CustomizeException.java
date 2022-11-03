package life.cj.community.community.exception;

/**
 * @author cj
 * @apiNote TODO
 * @date 2022/11/3 14:41
 */
public class CustomizeException extends RuntimeException {

    private String message;

    public CustomizeException(ICustomizeErrorCode errorCode) {
        this.message = errorCode.getMessage();
    }

    @Override
    public String getMessage() {
        return message;
    }
}
