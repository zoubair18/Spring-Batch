package demo.processor;

import org.springframework.batch.item.ItemProcessor;

import demo.model.Person;

public class PersonItemProcessor implements ItemProcessor<Person, Person> {
    @Override
    public Person process(final Person person) throws Exception {
    	
    	  final String firstName = person.getFirstName();
        final String lastName = person.getLastName();
       
        final Person transformedPerson = new Person();
        transformedPerson.setFirstName(firstName);
        transformedPerson.setLastName(lastName);

        return transformedPerson;
    }
}
