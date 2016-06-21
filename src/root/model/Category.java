package root.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class Category extends Entity {

    private String title;
    private String description;
    private Timestamp createdDate;

    private Category parent; //parameter can be null//

    private boolean published = true;

    //parameters that can be null in db, but in orm represents by empty list//
    private List<Post> posts = new ArrayList<>();;
    private List<Tag> tags = new ArrayList<>();;

    public Category() {}

    public Category(int id) {
        super(id);
    }

    public Category(int id, String title, String description,
                    Timestamp createdDate, Category parent,
                    boolean published) {
        super(id);
        this.title = title;
        this.description = description;
        this.createdDate = createdDate;
        this.parent = parent;
        this.published = published;
    }

    public Category(int id, String title, String description, Timestamp createdDate,
                    Category parent, boolean published, List<Post> posts) {

        this(id, title, description, createdDate, parent, published);
        this.posts = posts;
    }

    public Category(int id, String title, String description, Timestamp createdDate, Category parent, boolean published,
                    List<Post> posts, List<Tag> tags) {
        this(id, title, description, createdDate, parent, published, posts);
        this.tags = tags;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    public Category getParent() {
        return parent;
    }

    public void setParent(Category parent) {
        this.parent = parent;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
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

        Category category = (Category) o;

        if (published != category.published) {
            return false;
        }
        if (title != null ? !title.equals(category.title) : category.title != null) {
            return false;
        }
        if (description != null ? !description.equals(category.description) : category.description != null) {
            return false;
        }
        if (createdDate != null ? !createdDate.equals(category.createdDate) : category.createdDate != null) {
            return false;
        }
        if (parent != null ? !parent.equals(category.parent) : category.parent != null) {
            return false;
        }
        if (posts != null ? !posts.equals(category.posts) : category.posts != null) {
            return false;
        }
        return tags != null ? tags.equals(category.tags) : category.tags == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (createdDate != null ? createdDate.hashCode() : 0);
        result = 31 * result + (parent != null ? parent.hashCode() : 0);
        result = 31 * result + (published ? 1 : 0);
        result = 31 * result + (posts != null ? posts.hashCode() : 0);
        result = 31 * result + (tags != null ? tags.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Category{" +
                " id='"+ super.getId() +
                " title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", createdDate=" + createdDate +
                ", parent=" + parent +
                ", published=" + published +
                ", posts=" + posts +
                ", tags=" + tags +
                '}';
    }


}
