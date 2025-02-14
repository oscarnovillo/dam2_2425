package org.example.backendspring.dao;


import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends ListCrudRepository<UserEntity, UUID> {

//    @Query("select u from UserEntity u where u.id = UUID_TO_BIN(:id)")
//    @Override
//    Optional<UserEntity> findById(@Param(value = "id") UUID id);

    @EntityGraph(attributePaths = {"roles"})
    Optional<UserEntity> findByName(String name);

    @EntityGraph(attributePaths = {"roles"})
    @Query("select u from UserEntity u")
    List<UserEntity> getAllWithPermisos();

    @EntityGraph(attributePaths = {"visitas"})
    Optional<UserEntity> findWithVisitasById(Long id);

//    @Query("""
//    select u from UserEntity u
//    JOIN FETCH u.roles permisos
//    JOIN FETCH u.visitas v
//    JOIN FETCH v.pois p
//    JOIN FETCH p.ciudad c
//    where u.id = :id
//    """)
//    Optional<UserEntity> findEnteroById(Long id);
}
