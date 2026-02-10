package fr.eni.ludotheque.bll;

import fr.eni.ludotheque.bo.Location;

import java.util.List;
import java.util.Optional;

public interface LocationService {
    public void enregistrerLocation(Location location);
    public List<Location> obtenirLocationsNonRetournees(Integer clientId);
    public Optional<Location> obtenirLocationParExemplaireNonRetourne(Integer exemplaireId);
    public void enregistrerRetourLocation(Integer locationId);
}

