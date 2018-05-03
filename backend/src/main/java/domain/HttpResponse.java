package domain;

import domain.enumeration.ErrorCodes;
import domain.exeptions.BaseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.PrintWriter;
import java.io.StringWriter;

public class HttpResponse<T> {
    private static final StringWriter sw = new StringWriter();
    private static final PrintWriter pw = new PrintWriter(sw);

    /**
     * Response data
     */
    public T data;

    /**
     * Application status
     */
    public ErrorResponse error;


    public HttpResponse() {
    }


    public HttpResponse(T data) {
        this.data = data;
    }

//    public HttpResponse(Exception e) {
//        this.error = new ErrorResponse(e);
//    }

    public HttpResponse(BaseException e) {
        this.error = new ErrorResponse(e);
    }


    private class ErrorResponse {

        /**
         * Error code
         */
        public ErrorCodes code;

        /**
         * Error message
         */
        public String message;

        /**
         * Stack trace
         */
        public String stack;

        private ErrorResponse(BaseException e) {
            this.code = e.code;
            this.message = e.message;
            this.stack = this.stackTraceToString(e);
        }

//        private ErrorResponse(Exception e) {
//            this.code = ErrorCodes.UNKNOWN;
//            this.message = e.getMessage();
//            this.stack = this.stackTraceToString(e);
//        }

        /**
         * Get stack trace as string
         * @param e Exception
         * @return Stack trace as string
         */
        private String stackTraceToString(Exception e) {
            e.printStackTrace(pw);
            return sw.toString();
        }
    }
}
