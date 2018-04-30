package providers.status.api;

import domain.StatusPackage;
import domain.enumeration.statuses.StatusBase;
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
    StatusBase setStatus(StatusBase status);

    /**
     * Get current status, print to default output stream
     *
     * @return Current status
     */
    default StatusBase getStatus() {
        return this.getStatus(System.out);
    }

    /**
     * Get current status, print to output stream
     *
     * @param out Output stream
     * @return Current status
     */
    StatusBase getStatus(OutputStream out);

    StatusPackage statusPackage();
}
