package sys.tem.repository;


import org.springframework.stereotype.Repository;
import sys.tem.exception.NotFoundException;
import sys.tem.model.Post;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class PostRepository {
    private final ConcurrentMap<Long, Post> posts;
    private final AtomicLong counter;

    public PostRepository() {
        this.posts = new ConcurrentHashMap<>();
        this.counter = new AtomicLong();
    }

    public List<Post> all() {
        return posts.values().stream()
                .filter(w -> !w.isRemoved())
                .collect(Collectors.toList());
    }

    public Optional<Post> getById(long id) {
        var post = posts.get(id);
        if (post == null || post.isRemoved()) {
            throw new NotFoundException();
        }
        return Optional.ofNullable(post);
    }

    public Post save(Post post) {
        if (posts.get(post.getId()) != null) {
            if (posts.get(post.getId()).isRemoved()) {
                throw new NotFoundException();
            }
        }
        if (post.getId() == 0) {
            post.setId(counter.incrementAndGet());

        }
        posts.put(post.getId(), post);
        return post;
    }

    public void removeById(long id) {
        var post = posts.get(id);
        if (post != null) {
            post.setRemoved(true);
        }
    }
}
