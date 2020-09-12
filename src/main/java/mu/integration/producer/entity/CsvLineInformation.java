package mu.integration.producer.entity;

import java.io.Serializable;

import org.springframework.data.annotation.Id;

import lombok.Data;

/**
 *
 * @author priteela
 */
@Data
public class CsvLineInformation implements Serializable {

    @Id
    private String id;

    private String line;

    private String fileName;

    private String status;

    @Override
    public String toString() {
        return this.id + "," + this.line + "," + this.status + "," + this.fileName;
    }
}
