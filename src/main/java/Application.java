import business.Status;
import business.library.Library;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"controllers"})
public class Application {
    public static void main(String[] args) {
        // Launch REST services
        SpringApplication.run(Application.class, args);

        Status.status = "Spring application launched";

        // Get library from cached library
        // todo

        // Assemble library
        Library library = new Library("C:/Users/vlado/Music");
        Library.files = library.ListFilesRecursively(null, "mp3");

        Status.status = "Library read";

        // Get Metadata
        Library.GetMetadata(Library.files[0]);
    }
}
