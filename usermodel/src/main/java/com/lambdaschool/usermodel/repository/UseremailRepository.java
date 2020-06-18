package com.lambdaschool.usermodel.repository;

import com.lambdaschool.usermodel.models.Useremail;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * The CRUD Repository connecting Useremail to the rest of the application
 */
public interface UseremailRepository extends CrudRepository<Useremail, Long> {
	/*
	 *
	 * The following are new from initial
	 *
	 */

	/**
	 * Return a list of user email combinations based on the given username
	 *
	 * @param name The username of the user email combinations you seek
	 * @return A list of user email combinations based on the given username
	 */
	List<Useremail> findAllByUser_Username(String name);
}
