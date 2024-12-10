package sys.tem.repository;


import org.springframework.stereotype.Repository;
import sys.tem.model.Post;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class PostRepository {
    private final ConcurrentMap<Long, Post> posts;
    private final AtomicLong counter;

    public PostRepository() {
        this.posts = new ConcurrentHashMap<>();
        this.counter = new AtomicLong();
    }

    public List<Post> all() {
        return List.copyOf(posts.values());
    }

    public Optional<Post> getById(long id) {
        return Optional.ofNullable(posts.get(id));
    }

    public Post save(Post post) {
        if (post.getId() == 0) {
            post.setId(counter.incrementAndGet());
        }
        posts.put(post.getId(), post);
        return post;
    }

    public void removeById(long id) {
        posts.remove(id);
    }
}
