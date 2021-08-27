package dio.digitalinnovation.personApi.repository;

import dio.digitalinnovation.personApi.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
