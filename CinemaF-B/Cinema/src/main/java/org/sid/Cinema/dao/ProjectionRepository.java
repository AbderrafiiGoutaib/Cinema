package org.sid.Cinema.dao;


import org.sid.Cinema.entities.Cinema;
import org.sid.Cinema.entities.Projection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@RepositoryRestResource
@CrossOrigin("*")
public interface ProjectionRepository extends JpaRepository<Projection,Long> {

    @Query("select p from  Projection p where p.film.id = :x and p.salle.cinema.ville.name like CONCAT('%',:y,'%') ")
    public Page<Projection> getProjectionByFilmId(@Param("x")Long idf,@Param("y")String kw , Pageable pageable);
//    @Query("select p from  Projection p where p.salle.cinema.ville.name like :x")
//    public Page<Projection> getProjectionBy(@Param("x")Long kw , Pageable pageable);
}
