package mu.integration.producer.dto;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author priteela
 */


@Getter
@Setter
public class CsvLineDto {

    private String status = "";
    private String correlationId = "";

    @Override
    public String toString() {
        return this.status + "," + this.correlationId;
    }

}
