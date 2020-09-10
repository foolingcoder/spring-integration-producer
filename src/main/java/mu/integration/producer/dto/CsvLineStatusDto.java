package mu.integration.producer.dto;

import lombok.Data;

/**
 *
 * @author priteela
 */
@Data
public class CsvLineStatusDto {

    private String status = "";
    private String correlationId = "";

    @Override
    public String toString() {
        return this.status + "," + this.correlationId;
    }

}
