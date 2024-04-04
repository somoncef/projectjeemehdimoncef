package moncef.projava.projectmonsifmehdi.exeption;

public class VehicleNotFoundException extends RuntimeException {
    public VehicleNotFoundException(Long vehicleId) {
        super("Vehicle with ID " + vehicleId + " not found");
    }
}
