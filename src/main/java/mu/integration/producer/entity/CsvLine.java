package mu.integration.producer.entity;

import java.util.UUID;

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

    private String line;

    private String fileName;

    private String status;

    private UUID correlationId;
}
