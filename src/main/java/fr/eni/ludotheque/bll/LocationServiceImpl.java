package fr.eni.ludotheque.bll;

import fr.eni.ludotheque.bo.Location;
import fr.eni.ludotheque.dal.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class LocationServiceImpl implements LocationService {

    private LocationRepository locationRepository;

    @Autowired
    public LocationServiceImpl(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @Override
    public void enregistrerLocation(Location location) {
        location.setDateLocation(LocalDate.now());
        locationRepository.save(location);
    }

    @Override
    public List<Location> obtenirLocationsNonRetournees(Integer clientId) {
        return locationRepository.findByClientIdAndRetournéFalse(clientId);
    }

    @Override
    public Optional<Location> obtenirLocationParExemplaireNonRetourne(Integer exemplaireId) {
        return locationRepository.findByExemplaireIdAndRetournéFalse(exemplaireId);
    }

    @Override
    public void enregistrerRetourLocation(Integer locationId) {
        Optional<Location> optionalLocation = locationRepository.findById(locationId);
        if (optionalLocation.isPresent()) {
            Location location = optionalLocation.get();
            location.setDateRetour(LocalDate.now());
            location.setRetourné(true);

            // Calculer la facture : nombre de jours * tarif journalier du jeu
            long jours = ChronoUnit.DAYS.between(location.getDateLocation(), location.getDateRetour());
            Float tarifJour = location.getExemplaire().getJeu().getTarifJour();
            location.setMontantFacture((float) jours * tarifJour);

            locationRepository.save(location);
        }
    }
}

