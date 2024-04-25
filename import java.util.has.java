import java.util.HashMap;
import java.util.Map;

public class PersonController {
    private Map<Integer, Person> persons;
    private int lastId;

    public PersonController() {
        this.persons = new HashMap<>();
        this.lastId = 0;
    }

    public Person createPerson(String fullName, String birthDate) {
        lastId++;
        Person person = new Person(fullName, birthDate);
        person.setId(lastId);
        persons.put(lastId, person);
        return person;
    }

    public Person updatePerson(int personId, String fullName, String birthDate) {
        Person person = persons.get(personId);
        if (person != null) {
            person.setFullName(fullName);
            person.setBirthDate(birthDate);
            return person;
        } else {
            return null;
        }
    }

    public Person getPerson(int personId) {
        return persons.get(personId);
    }
}
