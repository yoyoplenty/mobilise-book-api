package Mobilise.bookapi.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, UUID> {
   Optional<User> findByEmail(String email);

   Optional<User> findByConfirmToken(String token);

   Optional<User> findByResetToken(String token);
}
