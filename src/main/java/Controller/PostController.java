package Controller;

import CustomException.ResourceNotFoundException;
import Model.Post;
import Model.User;
import Repository.PostRepository;
import Repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/{userId}")
    public Post createPost(@PathVariable(value = "userId") Long userId, @Valid @RequestBody Post post) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        post.setUser(user);
        return postRepository.save(post);
    }

    @GetMapping("/{userId}")
    public List<Post> getPostsByUser(@PathVariable(value = "userId") Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        return postRepository.findByUser(user);
    }

    @PutMapping("/{postId}")
    public Post updatePost(@PathVariable(value = "postId") Long postId, @Valid @RequestBody Post postDetails) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
        post.setContent(postDetails.getContent());
        return postRepository.save(post);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable(value = "postId") Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
        postRepository.delete(post);
        return ResponseEntity.ok().build();
    }
}
