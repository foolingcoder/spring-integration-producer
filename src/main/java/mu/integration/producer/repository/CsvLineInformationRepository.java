package mu.integration.producer.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import mu.integration.producer.entity.CsvLineInformation;

/**
 *
 * @author priteela
 */
public interface CsvLineInformationRepository extends MongoRepository<CsvLineInformation, String> {
}