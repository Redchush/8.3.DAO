package root.model;

import java.sql.Timestamp;

public class Comment extends Entity{

    private User author;
    private Answer parent;

    private String content;

    private Timestamp createdDate;
    private Timestamp updatedDate;

    private boolean isBanned;

    public Comment() {    }

    public Comment(int id) {
        super(id);
    }

    public Comment(int id, User author, Answer parent, String content, Timestamp createdDate) {
        super(id);
        this.author = author;
        this.parent = parent;
        this.content = content;
        this.createdDate = createdDate;
    }

    public Comment(int id, User author, Answer parent, String content, Timestamp createdDate,
                   Timestamp updatedDate, boolean isBanned) {
        this(id, author, parent, content, createdDate);
        this.updatedDate = updatedDate;
        this.isBanned = isBanned;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Answer getParent() {
        return parent;
    }

    public void setParent(Answer parent) {
        this.parent = parent;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    public Timestamp getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Timestamp updatedDate) {
        this.updatedDate = updatedDate;
    }

    public boolean isBanned() {
        return isBanned;
    }

    public void setBanned(boolean banned) {
        isBanned = banned;
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

        Comment comment = (Comment) o;

        if (isBanned != comment.isBanned) {
            return false;
        }
        if (author != null ? !author.equals(comment.author) : comment.author != null) {
            return false;
        }
        if (parent != null ? !parent.equals(comment.parent) : comment.parent != null) {
            return false;
        }
        if (content != null ? !content.equals(comment.content) : comment.content != null) {
            return false;
        }
        if (createdDate != null ? !createdDate.equals(comment.createdDate) : comment.createdDate != null) {
            return false;
        }
        return updatedDate != null ? updatedDate.equals(comment.updatedDate) : comment.updatedDate == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (author != null ? author.hashCode() : 0);
        result = 31 * result + (parent != null ? parent.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (createdDate != null ? createdDate.hashCode() : 0);
        result = 31 * result + (updatedDate != null ? updatedDate.hashCode() : 0);
        result = 31 * result + (isBanned ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id = " + super.getId() +
                " author=" + author +
                ", parent=" + parent +
                ", content='" + content + '\'' +
                ", createdDate=" + createdDate +
                ", updatedDate=" + updatedDate +
                ", isBanned=" + isBanned +
                '}';
    }
}