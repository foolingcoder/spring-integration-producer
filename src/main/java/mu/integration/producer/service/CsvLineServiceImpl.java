package mu.integration.producer.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mu.integration.producer.entity.CsvLine;
import mu.integration.producer.repository.CsvLineRepository;

/**
 *
 * @author priteela
 */
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
@Service
public class CsvLineServiceImpl implements CsvLineService {

    private final CsvLineRepository csvLineRepository;

    @Override
    public void saveCsvLine(CsvLine csvLine) {
        csvLineRepository.save(csvLine);
    }
}
