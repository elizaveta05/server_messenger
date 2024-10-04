package com.example.server_messenger.Repository;

import com.example.server_messenger.Model.UsersProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UsersProfile, String> {

}
