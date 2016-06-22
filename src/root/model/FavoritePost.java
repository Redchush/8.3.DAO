package root.model;

import java.sql.Timestamp;
import java.util.List;


public class FavoritePost extends Entity {

    private String comment;
    private Post relatedPost;
    private User author;
    private int id;

    public FavoritePost() {}

    public FavoritePost(int id) {
        super(id);
    }

    public FavoritePost(int id, User author, Post relatedPost, String comment) {
        super(id);
        this.comment = comment;
        this.relatedPost = relatedPost;
        this.author = author;
    }

    @Override
    public int getId() {
        return id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Post getRelatedPost() {
        return relatedPost;
    }

    public void setRelatedPost(Post relatedPost) {
        this.relatedPost = relatedPost;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    /*properties of post is read-only */

    public User getPostAuthor() {
        return relatedPost.getAuthor();
    }

    public User getPostUser() {
        return author;
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
        if (comment != null ? !comment.equals(post.comment)
                            : post.comment != null) {
            return false;
        }
        if (relatedPost != null ? !relatedPost.equals(post.relatedPost) : post.relatedPost != null) {
            return false;
        }
        return author != null ? author.equals(post.author) : post.author == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (comment != null ? comment.hashCode() : 0);
        result = 31 * result + (relatedPost != null ? relatedPost.hashCode() : 0);
        result = 31 * result + (author != null ? author.hashCode() : 0);
        result = 31 * result + id;
        return result;
    }

    @Override
    public String toString() {
        return "FavoritePost{" +
                "comment='" + comment + '\'' +
                ", relatedPost=" + relatedPost +
                ", author=" + author +
                ", id=" + id +
                '}';
    }
}
