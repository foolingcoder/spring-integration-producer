package mu.integration.producer.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import mu.integration.producer.entity.CsvLine;

/**
 *
 * @author priteela
 */
public interface CsvLineRepository extends MongoRepository<CsvLine, String> {
}