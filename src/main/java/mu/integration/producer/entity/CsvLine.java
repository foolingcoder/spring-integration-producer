package mu.integration.producer.entity;

import org.springframework.data.annotation.Id;

import lombok.Data;

/**
 *
 * @author priteela
 */

@Data
public class CsvLine {

    @Id
    private String id;

    private String csvLine;

    private String fileName;

    private String status;

    private String correlationId;
}
