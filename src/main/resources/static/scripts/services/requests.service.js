class RequestService {
    constructor(baseUrl = "192.168.0.192", port = "8080"    ) {
        this.baseUrl = baseUrl;
        this.port = port;
    }

    Request(request) {
        return `http://${this.baseUrl}:${this.port}/${request}`;
    }

    Status(callback) {
        $.get(this.Request("status"), callback);
    }

    GetLibrary(callback) {
        $.get(this.Request("library"), callback);
    }
}