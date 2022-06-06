package ma.emsi.GestionCinemas.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import ma.emsi.GestionCinemas.entities.Cinema;
import ma.emsi.GestionCinemas.entities.Salle;
@RepositoryRestResource
@CrossOrigin("*")
public interface SalleRepository extends JpaRepository<Salle, Long> {
    
}
