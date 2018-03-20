package bookauthor.bookauthor.repository;

import bookauthor.bookauthor.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {
    User findUsersByEmail(String email);
}
