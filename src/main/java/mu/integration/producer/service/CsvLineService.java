package mu.integration.producer.service;

import org.springframework.messaging.Message;

import com.fasterxml.jackson.core.JsonProcessingException;

import mu.integration.producer.entity.CsvLineInformation;

/**
 * Exposes service operations related to CSV line {@link CsvLineInformation}
 *
 * @author Priteela
 */
public interface CsvLineService {

    /**
     * Saves the csv line information
     *
     * @param message - the message corresponding to the csv line information
     * @return the persisted {@link CsvLineInformation}
     */
    CsvLineInformation saveCsvLine(Message<String> message);

    /**
     * Updates the csv line information
     *
     * @param message - the message corresponding to the csv line information received from the message broker
     * @return the message corresponding to the updated csv line information
     */
    Message<CsvLineInformation> updateCsvLine(Message<String> message) throws JsonProcessingException;

    /**
     * Finds the {@link CsvLineInformation} by its identifier
     * @param id
     */
    CsvLineInformation findById(Long id);
}
