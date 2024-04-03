package moncef.projava.projectmonsifmehdi.Repository;

import moncef.projava.projectmonsifmehdi.model.User;
import moncef.projava.projectmonsifmehdi.model.Vehicle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    Page<User> findByNameContains(String name, PageRequest pageable);
}
