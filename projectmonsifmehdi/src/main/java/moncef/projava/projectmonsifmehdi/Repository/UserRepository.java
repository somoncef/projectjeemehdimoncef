package moncef.projava.projectmonsifmehdi.Repository;

import moncef.projava.projectmonsifmehdi.model.User;
import moncef.projava.projectmonsifmehdi.model.Vehicle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {
    Page<User> findByNameContains(String name, PageRequest pageable);
    @Query("SELECT u FROM User u WHERE u.hasActiveRental = false")
    List<User> findByHasActiveRentalFalse();


}
