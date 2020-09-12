package mu.integration.producer.service;

import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mu.integration.producer.entity.CsvLineInformation;
import mu.integration.producer.repository.CsvLineInformationRepository;

/**
 *
 * @author priteela
 */
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
@Service
public class CsvLineServiceImpl implements CsvLineService {

    private final CsvLineInformationRepository csvLineRepository;
    private final ObjectMapper mapper;

    @Override
    public CsvLineInformation saveCsvLine(Message<String> message) {
        CsvLineInformation csvLineInformation = new CsvLineInformation();
        csvLineInformation.setLine(message.getPayload());
        csvLineInformation.setFileName((String) message.getHeaders().get("file_name"));

        return csvLineRepository.save(csvLineInformation);
    }

    @Override
    public Message updateCsvLine(Message<String> message) throws JsonProcessingException {

        CsvLineInformation csvLineInformation = mapper.readValue(message.getPayload(), CsvLineInformation.class);

        csvLineRepository.findById(csvLineInformation.getId()).ifPresentOrElse(
                persistedCsvLineInformation -> {
                    //TODO to confirm only description and value can be updated
                    persistedCsvLineInformation.setStatus(csvLineInformation.getStatus());
                    csvLineRepository.save(persistedCsvLineInformation);
                },
                () -> {
                    //TODO throw new exception
                }

        );
        Message message2 = MessageBuilder.withPayload(csvLineInformation)
                .copyHeaders(message.getHeaders()).build();
        return message2;
    }

    @Override
    public CsvLineInformation findById(Long id) {
        return csvLineRepository.findById(id.toString()).get();
    }
}
