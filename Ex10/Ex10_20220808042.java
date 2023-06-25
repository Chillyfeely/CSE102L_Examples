import java.util.*;

public class Ex10_20220808042 {
}

class User {
    private int id;
    private String username;
    private String email;
    private Set<User> followers;
    private Set<User> following;
    private Set<Post> likedPosts;
    private HashMap<User, Queue<Message>> messages;

    public User(String username, String email) {
        this.username = username;
        this.email = email;
        followers = new HashSet<>();
        following = new HashSet<>();
        likedPosts = new HashSet<>();
        messages = new HashMap<>();
        this.id = this.hashCode();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<User> getFollowers() {
        return followers;
    }

    public Set<User> getFollowing() {
        return following;
    }

    public Set<Post> getLikedPosts() {
        return likedPosts;
    }

    public void message(User recipient, String content) {

        messages.putIfAbsent(recipient, new LinkedList<>());
        recipient.messages.putIfAbsent(this, new LinkedList<>());

        Message newMessage = new Message(this, content);

        messages.get(recipient).add(newMessage);
        recipient.messages.get(this).add(newMessage);
        read(recipient);
    }

    public void read(User user) {
        for (Message msg : messages.get(user)) {
            System.out.println(msg);
        }
    }

    public void follow(User user) {
        if (!this.following.contains(user)) {
            this.following.add(user);
            user.followers.add(this);
        } else {
            this.following.remove(user);
            user.followers.remove(this);
        }
    }

    public void like(Post post) {
        if (!this.likedPosts.contains(post)) {
            this.likedPosts.add(post);
            post.likedBy(this);
        } else {
            this.likedPosts.remove(post);
            post.likedBy(this);
        }
    }

    public Post post(String content) {
        Post post = new Post(content);
        return post;
    }

    public Comment comment(Post post, String content) {
        Comment comment = new Comment(content);
        post.commentBy(this, comment);
        return comment;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof User)) {
            return false;
        }
        User user = (User) object;
        return this.id == user.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}

class Message {
    private boolean seen;
    private Date dateSent;
    private String content;
    private User sender;

    public Message(User sender, String content) {
        this.sender = sender;
        this.content = content;
        this.seen = false;
        this.dateSent = new Date();
    }

    public String read(User reader) {
        if (!reader.equals(sender)) {
            seen = true;
        }
        System.out.println("Sent at " + dateSent);
        return content;
    }

    public boolean hasRead() {
        return this.seen;
    }
}

class Post {
    private Date datePosted;
    private String content;
    private HashSet<User> likes;
    private HashMap<User, ArrayList<Comment>> comments;

    public Post(String content) {
        this.content = content;
        this.datePosted = new Date();
        this.likes = new HashSet<>();
        this.comments = new HashMap<>();
    }

    public boolean likedBy(User user) {
        if (likes.contains(user)) {
            likes.remove(user);
            return false;
        } else {
            likes.add(user);
            return true;
        }
    }

    public boolean commentBy(User user, Comment comment) {
        if (!comments.containsKey(user)) {
            ArrayList<Comment> user_comments = new ArrayList<>();
            user_comments.add(comment);
            this.comments.put(user, user_comments);
            return true;
        } else {

            this.comments.get(user).add(comment);
            return true;
        }
    }

    public String getContent() {
        System.out.println("Posted at " + datePosted);
        return this.content;
    }

    public Comment getComment(User user, int index) {
        if (comments.get(user).size() > index)
            return this.comments.get(user).get(index);
        return null;
    }

    public int getCommentCount() {
        int count = 0;
        for (User user : comments.keySet()) {
            count += this.comments.get(user).size();
        }
        return count;
    }

    public int getCommentCountByUser(User user) {

        if (comments.get(user) != null) {
            return this.comments.get(user).size();
        }
        return 0;
    }
}

class Comment extends Post {
    public Comment(String content) {
        super(content);
    }
}

class SocialNetwork {

    private static HashMap<User, ArrayList<Post>> postsByUsers;

    public User register(String username, String email) {
        User user = new User(username, email);

        if (postsByUsers.containsKey(user)) {
            return null;
        } else {
            SocialNetwork.postsByUsers.put(user, new ArrayList<>());
            return user;
        }
    }

    public Post post(User user, String content) {
        if (postsByUsers.containsKey(user)) {
            Post post = new Post(content);
            postsByUsers.get(user).add(post);
            return post;
        } else {
            return null;
        }
    }

    public User getUser(String email) {
        int hash = Objects.hash(email);
        for (User user : postsByUsers.keySet()) {
            if (user.hashCode() == hash) {
                return user;
            }
        }
        return null;

    }

    public HashSet<Post> getFeed(User user) {
        HashSet<Post> allPosts = new HashSet<>();

        for (User following : postsByUsers.keySet()) {
            if (user.getFollowing().contains(following)) {
                ArrayList<Post> followingPosts = postsByUsers.get(following);
                allPosts.addAll(followingPosts);
            }
        }
        return allPosts;
    }

    public Map<User, String> search(String keyword) {
        Map<User, String> map = new HashMap<>();
        for (User user : postsByUsers.keySet()) {
            String username = user.getUsername();
            if (username.contains(keyword)) {
                map.put(user, username);
            }
        }
        return map;

    }

    public <K, V> Map<V, Set<K>> reverseMap(Map<K, V> map) {
        Map<V, Set<K>> reversed = new HashMap<>();
        for (K key : map.keySet()) {
            if (reversed.containsKey(map.get(key))) {
                reversed.get(map.get(key)).add(key);
            } else {
                Set<K> innerSet = new HashSet<>();

                innerSet.add(key);
                reversed.put(map.get(key), innerSet);
            }

        }

        return reversed;
    }
}





