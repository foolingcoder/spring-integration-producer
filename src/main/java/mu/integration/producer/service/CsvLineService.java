package mu.integration.producer.service;

import mu.integration.producer.entity.CsvLine;

/**
 * Exposes service operations related to csv line
 *
 * @author Priteela
 */
public interface CsvLineService {

    void saveCsvLine(CsvLine csvLine);

}
