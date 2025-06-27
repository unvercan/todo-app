package tr.unvercanunlu.todoapp.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import tr.unvercanunlu.todoapp.model.ToDo;

@Repository
public interface ToDoRepository extends MongoRepository<ToDo, Long> {

}
