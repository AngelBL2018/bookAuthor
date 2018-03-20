package bookauthor.bookauthor.repository;

import bookauthor.bookauthor.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author,Integer> {
}
