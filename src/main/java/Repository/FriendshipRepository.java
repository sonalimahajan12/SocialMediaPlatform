package Repository;

import Model.Friendship;
import Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendshipRepository extends JpaRepository<Friendship, Long> {
    Friendship findByUserAndFriend(User user, User friend);
    List<Friendship> findByUser(User user);
}

