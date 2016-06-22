package root.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


public class Rating extends Entity{

    private Answer parent;
    private User author;
    private int rating;

    private Timestamp createdDate;


    private Timestamp updatedDate;
    private boolean isBanned = false;
    private List<RatingComment> ratingComments;

    public Rating() {
    }

    public Rating(int id) {
        super(id);
        ratingComments = new ArrayList<>();
    }

    public Rating(int id, Answer parent, User author, int rating, Timestamp createdDate,
                  Timestamp updatedDate, boolean isBanned) {
        super(id);
        this.parent = parent;
        this.author = author;
        this.rating = rating;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.isBanned = isBanned;
    }

    public Rating(int id, Answer parent, User author, int rating, Timestamp createdDate,
                  Timestamp updatedDate, boolean isBanned, List<RatingComment> ratingComment) {
        this(id, parent, author, rating, createdDate, updatedDate, isBanned);
        this.ratingComments = ratingComment;
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

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
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

    public List<RatingComment> getRatingComments() {
        return ratingComments;
    }

    public void setRatingComments(List<RatingComment> ratingComments) {
        this.ratingComments = ratingComments;
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

        Rating rating1 = (Rating) o;

        if (rating != rating1.rating) {
            return false;
        }
        if (isBanned != rating1.isBanned) {
            return false;
        }
        if (author != null ? !author.equals(rating1.author) : rating1.author != null) {
            return false;
        }
        if (parent != null ? !parent.equals(rating1.parent) : rating1.parent != null) {
            return false;
        }
        if (createdDate != null ? !createdDate.equals(rating1.createdDate) : rating1.createdDate != null) {
            return false;
        }
        if (updatedDate != null ? !updatedDate.equals(rating1.updatedDate) : rating1.updatedDate != null) {
            return false;
        }
        return ratingComments != null ? ratingComments.equals(rating1.ratingComments) : rating1.ratingComments == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (author != null ? author.hashCode() : 0);
        result = 31 * result + (parent != null ? parent.hashCode() : 0);
        result = 31 * result + rating;
        result = 31 * result + (createdDate != null ? createdDate.hashCode() : 0);
        result = 31 * result + (updatedDate != null ? updatedDate.hashCode() : 0);
        result = 31 * result + (ratingComments != null ? ratingComments.hashCode() : 0);
        result = 31 * result + (isBanned ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Rating{" +
                "parent=" + parent +
                ", author=" + author +
                ", rating=" + rating +
                ", createdDate=" + createdDate +
                ", updatedDate=" + updatedDate +
                ", isBanned=" + isBanned +
                ", ratingComments=" + ratingComments +
                '}';
    }
}
