package moncef.projava.projectmonsifmehdi;

import moncef.projava.projectmonsifmehdi.Repository.VehicleRepository;
import moncef.projava.projectmonsifmehdi.model.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public
class ProjectmonsifmehdiApplication  implements CommandLineRunner {

    @Autowired
    VehicleRepository vehicleRepository;

    public static void main(String[] args) {
        SpringApplication.run(ProjectmonsifmehdiApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
/*

        vehicleRepository.save(new Vehicle(null,"Hamid","Ahmed",null));
        vehicleRepository.save(new Vehicle(null,"solomi","khaled",null));
        vehicleRepository.save(new Vehicle(null,"dreees","moad",null));
        vehicleRepository.save(new Vehicle(null,"moncef","souri",null));
*/

    }

}
