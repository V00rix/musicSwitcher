package domain;

import domain.enumeration.Statuses;

public class HttpResponse<T> {
    public T data;
    public Statuses status;

    public HttpResponse() {

    }

    public HttpResponse(T data) {

    }
}
