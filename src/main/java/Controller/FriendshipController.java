package Controller;

import CustomException.ResourceNotFoundException;
import Model.Friendship;
import Model.User;
import Repository.FriendshipRepository;
import Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/friendships")
public class FriendshipController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FriendshipRepository friendshipRepository;

    @PostMapping("{id}")
    public ResponseEntity<Friendship> createFriendship(@RequestParam String userEmail, @RequestParam String friendEmail) {
        User user = userRepository.findByEmail(userEmail);
        User friend = userRepository.findByEmail(friendEmail);
        if (user == null || friend == null) {
            return ResponseEntity.badRequest().build();
        }
        Friendship existingFriendship = friendshipRepository.findByUserAndFriend(user, friend);
        if (existingFriendship != null) {
            return ResponseEntity.badRequest().build();
        }
        Friendship newFriendship = new Friendship(user, friend);
        friendshipRepository.save(newFriendship);
        return ResponseEntity.ok(newFriendship);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteFriendship(@PathVariable(value = "id") Long friendshipId) {
        Friendship friendship = friendshipRepository.findById(friendshipId)
                .orElseThrow(() -> new ResourceNotFoundException("Friendship", "id", friendshipId));
        friendshipRepository.delete(friendship);
        return ResponseEntity.noContent().build();
    }
}
