package providers.status.api;

import org.springframework.stereotype.Service;

import java.io.OutputStream;

/**
 * Status provider API
 */
@Service
public interface IStatusProvider {
    /**
     * Set/update current status
     *
     * @param status New status as String
     * @return Changed status
     */
    String setStatus(String status);

    /**
     * Get current status, print to default output stream
     *
     * @return Current status
     */
    default String getStatus() {
        return this.getStatus(System.out);
    }

    /**
     * Get current status, print to output stream
     *
     * @param out Output stream
     * @return Current status
     */
    String getStatus(OutputStream out);
}
