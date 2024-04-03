package moncef.projava.projectmonsifmehdi.Repository;



import moncef.projava.projectmonsifmehdi.model.Rental;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.PageRequest;


@Repository
public interface RentalRepository extends JpaRepository<Rental, Long> {
    Page<Rental> findByUserNameContainingIgnoreCase(String clientName, Pageable pageable);
}