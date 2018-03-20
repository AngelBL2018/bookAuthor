package bookauthor.bookauthor.rest;

import bookauthor.bookauthor.model.Author;
import bookauthor.bookauthor.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/authors")
public class AuthorRestController {

    @Autowired
    AuthorRepository authorRepository;


    @GetMapping
    public ResponseEntity  getAllAuthors(){
       return ResponseEntity.ok(authorRepository.findAll());

    }

    @GetMapping("/{id}")
    public ResponseEntity getAuthor(@PathVariable("id") int id){
      Author author= authorRepository.findOne(id);
      if (author == null){
        return   ResponseEntity.status(HttpStatus.NOT_FOUND).body("Author whith " + id + " id not found" );

      }

     return ResponseEntity.ok(author);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteAuthor(@PathVariable("id") int id){
        authorRepository.delete(id);
        return ResponseEntity.ok("deleted");

    }

    @PostMapping
   public ResponseEntity addAuther(@RequestBody Author author){
        authorRepository.save(author);
        return ResponseEntity.ok("saved");
    }
    @PutMapping
    public ResponseEntity updateAuthor(@RequestBody Author author){


        if (!authorRepository.exists(author.getId())){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Author not found");
        }
        authorRepository.save(author);
        return ResponseEntity.ok("updated");
    }


}
