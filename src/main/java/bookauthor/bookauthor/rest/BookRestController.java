package bookauthor.bookauthor.rest;


import bookauthor.bookauthor.dto.BookDto;
import bookauthor.bookauthor.model.Book;
import bookauthor.bookauthor.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/books")
public class BookRestController {

    @Autowired
    BookRepository bookRepository;

    @GetMapping
    public ResponseEntity getBooks(){
        return ResponseEntity.ok(bookRepository.findAll());
    }

    @GetMapping("/title")
    public List<BookDto> getBooksDto(){

      List<Book> books =   bookRepository.findAll();
      List<BookDto> bookDto =  new LinkedList<>();
      books.forEach(e->bookDto.add(new BookDto(e.getTitle())));
      return bookDto;
    }

    @GetMapping("/{id}")
    public ResponseEntity getBook(@PathVariable("id") int id){
        Book book = bookRepository.findOne(id);
        if (book == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book with " + id+ " id not found");

        }
        return ResponseEntity.ok(book);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteBook(@PathVariable("id")int id){
         bookRepository.delete(id);
       return ResponseEntity.ok("deleted");

    }

    @PostMapping
    public ResponseEntity addBook(@RequestBody Book book){
        if (bookRepository.findBookByTitle(book.getTitle())== null){
            bookRepository.save(book);
            return ResponseEntity.ok("added");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Book already exists");
    }

    @PutMapping
    public ResponseEntity updateBook(@RequestBody Book book){
        if (bookRepository.exists(book.getId())){
        bookRepository.save(book);
            return ResponseEntity.ok("updated");}
     return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found");
    }

}
