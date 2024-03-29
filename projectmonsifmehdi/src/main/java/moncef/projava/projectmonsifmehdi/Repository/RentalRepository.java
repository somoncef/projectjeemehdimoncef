package moncef.projava.projectmonsifmehdi.Repository;



import moncef.projava.projectmonsifmehdi.model.Rental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Long> {

}