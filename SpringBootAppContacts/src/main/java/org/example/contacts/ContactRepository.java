package org.example.contacts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ContactRepository {

	private JdbcTemplate jdbc;

	@Autowired
	public ContactRepository(JdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}

	public List<Contact> findAll() {
		RowMapper<Contact> rowMapper = (rs, rowNum) -> {
			Contact contact = new Contact();
			contact.setId(rs.getLong(1));
			contact.setFirstName(rs.getString(2));
			contact.setLastName(rs.getString(3));
			contact.setPhoneNumber(rs.getString(4));
			contact.setEmail(rs.getString(5));
			return contact;
		};

		return jdbc.query("select id, firstName, lastName, phoneNumber, email " +
							"from contact order by lastName", rowMapper);
	}

	public void save(Contact contact) {
		jdbc.update("insert into contact (firstName, lastName, phoneNumber, email) values(?,?,?,?)",
				contact.getFirstName(), contact.getLastName(), contact.getPhoneNumber(), contact.getEmail());
	}

}
