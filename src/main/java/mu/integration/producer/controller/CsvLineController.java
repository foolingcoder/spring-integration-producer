package mu.integration.producer.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mu.integration.producer.entity.CsvLineInformation;
import mu.integration.producer.service.CsvLineInformationSender;
import mu.integration.producer.service.CsvLineService;

@RequiredArgsConstructor
@Slf4j
@RestController
public class CsvLineController {

    private final ObjectMapper mapper;
    private final CsvLineInformationSender sender;
    private final CsvLineService csvLineService;

    @PostMapping("csvLines")
    public CsvLineInformation process(@RequestBody CsvLineInformation order) throws JsonProcessingException {
        log.info("Order saved: {}", mapper.writeValueAsString(order));
        sender.send(order);
        return order;
    }

    @GetMapping("csvLines/{id}")
    public CsvLineInformation findById(@PathVariable("id") Long id) {
        return null;
    }


}










