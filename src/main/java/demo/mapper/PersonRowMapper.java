package demo.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.jdbc.core.RowMapper;

import demo.configuration.BatchConfiguration;
import demo.model.Person;

public class PersonRowMapper implements RowMapper<Person> {
//	private static final Logger logger = LogManager.getLogger(PersonRowMapper.class);


	@Override
	public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
		Person person = new Person();

		
		

		/*
		 * A.add(new String[] { rs.getString("first_name"), rs.getString("last_name")
		 * });
		 * 
		 * for (String[] row : A) {
		 * 
		 * if (!row[0].equals("Mike")) { logger.info(row[0] + "->" + row[1]); } if
		 * (!row[0].equals("Lou")) { logger.info(row[0] + "->" + row[1]); } }
		 */

		person.setFirstName(rs.getString("first_name"));
		person.setLastName(rs.getString("last_name"));

		// System.out.println(rs.getString("first_name")+" "+rs.getString("last_name"));

		return person;
	}

}
