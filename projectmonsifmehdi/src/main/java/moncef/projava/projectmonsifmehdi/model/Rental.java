package moncef.projava.projectmonsifmehdi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Rental {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;
    private Double totalCost;

    private Date rentalDate;
    private Date returnDate;

    public void calculateTotalCost() {
        if (rentalDate == null || returnDate == null || vehicle == null || vehicle.getPricePerDay() == null) {
            return;
        }
        long diffInMilliseconds = returnDate.getTime() - rentalDate.getTime();
        long diffInDays = TimeUnit.MILLISECONDS.toDays(diffInMilliseconds);
        setTotalCost((double) (vehicle.getPricePerDay()* diffInDays));
    }
}

