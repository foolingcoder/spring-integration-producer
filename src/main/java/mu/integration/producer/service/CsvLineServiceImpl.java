package mu.integration.producer.service;

import org.springframework.integration.annotation.Transformer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author priteela
 */
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
@Service
public class CsvLineServiceImpl implements CsvLineService {

    //  private final CsvLineRepository csvLineRepository;

    @Transformer
    @Override
    public String saveCsv(String payload) {
        System.out.println(payload);
        return payload;
    }
}
