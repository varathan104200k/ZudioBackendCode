package SpringBoot.basicAuth.repo;

import SpringBoot.basicAuth.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserInfoRepo extends JpaRepository<UserInfo,Integer> {
    Optional<UserInfo> findByName(String username);
}
