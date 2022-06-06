package ma.emsi.GestionCinemas.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import ma.emsi.GestionCinemas.entities.Categorie;
import ma.emsi.GestionCinemas.entities.Cinema;
@RepositoryRestResource
@CrossOrigin("*")
public interface CategorieRepository extends JpaRepository<Categorie, Long> {
    
}
