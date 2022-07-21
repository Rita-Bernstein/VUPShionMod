package VUPShionMod.msic;

public class NullApiException extends RuntimeException {
    public NullApiException(){}

    @Override
    public String getMessage() {
        return "";
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return new Throwable();
    }
}
