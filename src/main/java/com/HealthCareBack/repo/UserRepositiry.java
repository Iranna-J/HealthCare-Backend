package com.HealthCareBack.repo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import com.HealthCareBack.dto.User;

@Repository
@EnableJpaRepositories
public interface UserRepositiry extends JpaRepository<User, Long>{
//	 Optional<User> findByPhone(String phone);


}
