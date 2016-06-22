package root.model;

public class RatingComment extends Entity {

    private String comment;
    private Rating parent;
    private boolean isPositive = true;
    private boolean isBanned = false;

    public RatingComment() {}

    public RatingComment(int id) {
        super(id);
    }

    public RatingComment(int id, String comment) {
        super(id);
        this.comment = comment;
    }

    public RatingComment(int id, Rating parent,  String comment, boolean isPositive, boolean isBanned) {
        super(id);
        this.comment = comment;
        this.isPositive = isPositive;
        this.isBanned = isBanned;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean isPositive() {
        return isPositive;
    }

    public void setPositive(boolean positive) {
        isPositive = positive;
    }

    public boolean isBanned() {
        return isBanned;
    }

    public void setBanned(boolean banned) {
        isBanned = banned;
    }

    public Rating getParent() {
        return parent;
    }

    public void setParent(Rating parent) {
        this.parent = parent;
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

        RatingComment that = (RatingComment) o;

        if (isPositive != that.isPositive) {
            return false;
        }
        if (isBanned != that.isBanned) {
            return false;
        }
        return comment != null ? comment.equals(that.comment) : that.comment == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (comment != null ? comment.hashCode() : 0);
        result = 31 * result + (isPositive ? 1 : 0);
        result = 31 * result + (isBanned ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "RatingComment{" +
                "comment='" + comment + '\'' +
                ", isPositive=" + isPositive +
                ", isBanned=" + isBanned +
                '}';
    }
}
