package dio.digitalinnovation.personApi.service;

import dio.digitalinnovation.personApi.mapper.PersonMapper;
import dio.digitalinnovation.personApi.dto.request.PersonDTO;
import dio.digitalinnovation.personApi.dto.response.MessageResponseDto;
import dio.digitalinnovation.personApi.entity.Person;
import dio.digitalinnovation.personApi.execption.PersonNotFoundException;
import dio.digitalinnovation.personApi.repository.PersonRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PersonService {

    private final PersonRepository personRepository;

    private final PersonMapper personMapper = PersonMapper.INSTANCE;


    public MessageResponseDto createPerson(PersonDTO personDTO) {
        Person personToSave = personMapper.toModel(personDTO);
        Person savedPerson = personRepository.save(personToSave);
        return createMessageResponse("Person successfully created with ID ", savedPerson.getId());

    }

    public List<PersonDTO> listAll() {
        List<Person> allPeople = personRepository.findAll();
        return allPeople.stream().map(personMapper::toDTO).collect(Collectors.toList());
    }

    public PersonDTO findById(Long id) throws PersonNotFoundException {
        Person person = verifyIfExists(id);

        return personMapper.toDTO(person);
    }

    public MessageResponseDto update(Long id, PersonDTO personDTO) throws PersonNotFoundException {
        verifyIfExists(id);

        Person updatedPerson = personMapper.toModel(personDTO);
        Person savedPerson = personRepository.save(updatedPerson);

        return createMessageResponse("Person successfully updated with ID ", savedPerson.getId());
    }

    public void delete(Long id) throws PersonNotFoundException {
        verifyIfExists(id);
        personRepository.deleteById(id);
    }

    private Person verifyIfExists(Long id) throws PersonNotFoundException {
        return personRepository.findById(id)
                .orElseThrow(() -> new PersonNotFoundException(id));
    }

    private MessageResponseDto createMessageResponse(String s, Long id2) {
        return MessageResponseDto.builder().message(s + id2).build();
    }
}
