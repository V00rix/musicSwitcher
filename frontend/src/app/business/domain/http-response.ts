export class HttpResponse<T> {

  /**
   * Response data
   */
  public data: T;

  /**
   * Application status
   */
  public status: string;

  /**
   * Application status
   */
  public error: ErrorResponse;
}

class ErrorResponse {

  /**
   * Error code
   */
  public code: string;

  /**
   * Error message
   */
  public message: string;

  /**
   * Stack trace
   */
  public stack: string;

}
