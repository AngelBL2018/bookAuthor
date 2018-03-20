package bookauthor.bookauthor.repository;

import bookauthor.bookauthor.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book,Integer>{
    Book findBookByTitle(String title);
}
