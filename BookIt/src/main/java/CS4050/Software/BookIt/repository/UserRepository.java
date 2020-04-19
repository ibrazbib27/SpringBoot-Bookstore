package CS4050.Software.BookIt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import CS4050.Software.BookIt.model.User;

@Repository
public interface UserRepository extends  JpaRepository<User, Long> {
	User findByEmail(String email);

}
