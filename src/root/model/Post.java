package root.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Post extends Entity {

    private User author;
    private Category parent;

    private String title;
    private String content;

    private Timestamp createdDate;
    private Timestamp updatedDate;

    private boolean banned;

    private List<Answer> answers = new ArrayList<>();
    private List<Tag> tags = new ArrayList<>();

    public Post() {}

    public Post(int id) {
        super(id);
    }

    public Post(int id, User author, Category parent, String title, String content, Timestamp createdDate) {
        super(id);
        this.author = author;
        this.parent = parent;
        this.title = title;
        this.content = content;
        this.createdDate = createdDate;
    }

    public Post(int id, User author, Category parent, String title,
                String content, Timestamp createdDate, Timestamp updatedDate,
                boolean banned) {

        this(id, author, parent, title, content, createdDate);
        this.updatedDate = updatedDate;
        this.banned = banned;
    }

    public Post(int id, User author, Category parent, String title, String content, Timestamp createdDate,
                Timestamp updatedDate, boolean banned, List<Tag> tags) {

        this(id, author, parent, title, content, createdDate, updatedDate, banned);
        this.tags = tags;
    }

    public Post(int id, User author, Category parent, String title, String content, Timestamp createdDate,
                Timestamp updatedDate, boolean banned, List<Tag> tags, List<Answer> answers) {

        this(id, author, parent, title, content, createdDate, updatedDate, banned, tags);
        this.answers = answers;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Category getParent() {
        return parent;
    }

    public void setParent(Category parent) {
        this.parent = parent;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
        return banned;
    }

    public void setBanned(boolean banned) {
        this.banned = banned;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public boolean addAnswer(Answer answer){
        if (answer != null){
            return answers.add(answer);
        }
        return false;
    }

    public boolean removeAnswer(Answer answer){
        if (answer != null){
            return answers.remove(answer);
        }
        return false;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public boolean addTag(Tag tag){
        if (tag != null){
            return tags.add(tag);
        }
        return false;
    }

    public boolean removeTag(Tag tag){
        if (tag != null){
            return tags.add(tag);
        }
        return false;
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

        Post post = (Post) o;

        if (banned != post.banned) {
            return false;
        }
        if (author != null ? !author.equals(post.author) : post.author != null) {
            return false;
        }
        if (parent != null ? !parent.equals(post.parent) : post.parent != null) {
            return false;
        }
        if (title != null ? !title.equals(post.title) : post.title != null) {
            return false;
        }
        if (content != null ? !content.equals(post.content) : post.content != null) {
            return false;
        }
        if (createdDate != null ? !createdDate.equals(post.createdDate) : post.createdDate != null) {
            return false;
        }
        if (updatedDate != null ? !updatedDate.equals(post.updatedDate) : post.updatedDate != null) {
            return false;
        }
        if (answers != null ? !answers.equals(post.answers) : post.answers != null) {
            return false;
        }
        return tags != null ? tags.equals(post.tags) : post.tags == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (author != null ? author.hashCode() : 0);
        result = 31 * result + (parent != null ? parent.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (createdDate != null ? createdDate.hashCode() : 0);
        result = 31 * result + (updatedDate != null ? updatedDate.hashCode() : 0);
        result = 31 * result + (banned ? 1 : 0);
        result = 31 * result + (answers != null ? answers.hashCode() : 0);
        result = 31 * result + (tags != null ? tags.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Post{" +
                "author=" + author +
                ", parent=" + parent +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", createdDate=" + createdDate +
                ", updatedDate=" + updatedDate +
                ", banned=" + banned +
                ", answers=" + answers +
                ", tags=" + tags +
                '}';
    }
}