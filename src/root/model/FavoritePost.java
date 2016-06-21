package root.model;

import java.sql.Timestamp;
import java.util.List;


public class FavoritePost extends Entity {

    private String favoritePostComment;
    private Post relatedPost;
    private User user;
    private int id;

    public FavoritePost() {}

    public FavoritePost(int id) {
        super(id);
    }

    public FavoritePost( int id, User user, Post relatedPost, String favoritePostComment) {
        super(id);
        this.favoritePostComment = favoritePostComment;
        this.relatedPost = relatedPost;
        this.user = user;
    }

    @Override
    public int getId() {
        return id;
    }

    public String getFavoritePostComment() {
        return favoritePostComment;
    }

    public void setFavoritePostComment(String favoritePostComment) {
        this.favoritePostComment = favoritePostComment;
    }

    public Post getRelatedPost() {
        return relatedPost;
    }

    public void setRelatedPost(Post relatedPost) {
        this.relatedPost = relatedPost;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    /*properties of post is read-only */

    public User getPostAuthor() {
        return relatedPost.getAuthor();
    }

    public User getPostUser() {
        return user;
    }

    public Category getPostParent() {
        return relatedPost.getParent();
    }

    public String getPostTitle() {
        return relatedPost.getTitle();
    }

    public String getPostContent() {
        return relatedPost.getContent();
    }

    public Timestamp getPostCreatedDate() {
        return relatedPost.getCreatedDate();
    }

    public Timestamp getPostUpdatedDate() {
        return relatedPost.getUpdatedDate();
    }

    public boolean isPostBanned() {
        return relatedPost.isBanned();
    }

    public List<Answer> getPostAnswers() {
        return relatedPost.getAnswers();
    }

    public List<Tag> getPostTags() {
        return relatedPost.getTags();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }

        FavoritePost post = (FavoritePost) o;

        if (id != post.id) {
            return false;
        }
        if (favoritePostComment != null ? !favoritePostComment.equals(post.favoritePostComment)
                                        : post.favoritePostComment != null) {
            return false;
        }
        if (relatedPost != null ? !relatedPost.equals(post.relatedPost) : post.relatedPost != null) {
            return false;
        }
        return user != null ? user.equals(post.user) : post.user == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (favoritePostComment != null ? favoritePostComment.hashCode() : 0);
        result = 31 * result + (relatedPost != null ? relatedPost.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + id;
        return result;
    }

    @Override
    public String toString() {
        return "FavoritePost{" +
                "favoritePostComment='" + favoritePostComment + '\'' +
                ", relatedPost=" + relatedPost +
                ", user=" + user +
                ", id=" + id +
                '}';
    }
}
