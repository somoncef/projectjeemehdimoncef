package moncef.projava.projectmonsifmehdi.Repository;

import moncef.projava.projectmonsifmehdi.model.Vehicle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepository extends JpaRepository<Vehicle,Long> {

    Page<Vehicle> findByMakeContains(String name, PageRequest pageable);

}
