package com.phonon.loastesting.repository;


import com.phonon.loastesting.model.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;

import java.util.UUID;

public interface RequestRepository extends CrudRepository<Request, UUID> {

}
