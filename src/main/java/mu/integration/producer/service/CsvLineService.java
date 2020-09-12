package mu.integration.producer.service;

import org.springframework.messaging.Message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import mu.integration.producer.entity.CsvLineInformation;

/**
 * Exposes service operations related to csv line {@link ObjectMapper the exception error}
 *
 * @author Priteela
 */
public interface CsvLineService {

    CsvLineInformation saveCsvLine(Message<String> message);

    Message updateCsvLine(Message<String> message) throws JsonProcessingException;

    CsvLineInformation findById(Long id);
}
