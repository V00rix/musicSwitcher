const main = () => {
    const rsv = new RequestService();

    console.log(rsv.baseUrl)

    const statusCallback = (data) => {
        console.log("Status:", data);
        $("#status").text(data);
    }

    const statusInterval = setInterval(() => {
        rsv.Status(statusCallback);
    }, 500);

    const libraryCallback = (data) => {
        console.log(data);
    };

    $("#getLibrary").on("click", () => {
        rsv.GetLibrary(libraryCallback)
    });
};

main();