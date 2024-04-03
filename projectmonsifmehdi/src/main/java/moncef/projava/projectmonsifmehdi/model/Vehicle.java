package moncef.projava.projectmonsifmehdi.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

 import java.util.Collection;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String make;
    private String model;
    private int year;
    private String color;
    private Boolean rented = false;
    private Float pricePerDay;

    @OneToMany()
    Collection<Rental> rentals;



}