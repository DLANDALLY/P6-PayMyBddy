package com.paymybuddy.paymybuddy.repositories;

import com.paymybuddy.paymybuddy.entities.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppRoleRepository extends JpaRepository<AppRole, String> {

}
