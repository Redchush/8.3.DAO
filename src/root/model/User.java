package root.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class User extends Entity {

	private Role role;

	private String login;
	private String password;
	private String email;

	private String lastName;
	private String firstName;

	private Timestamp createdDate;
	private Timestamp updatedDate;

	private boolean isBanned;

	private List<Post> publishedPosts = new ArrayList<>();
	private List<Post> readedPosts = new ArrayList<>();
	private List<FavoritePost> favoritePosts = new ArrayList<>();
	private List<Tag> favoriteTags = new ArrayList<>();

	public User() {
	}

	public User(int id) {
		super(id);
	}

	public User(int id, Role role, String login, String password,
				String email, Timestamp createdDate) {
		super(id);
		this.role = role;
		this.login = login;
		this.password = password;
		this.email = email;
		this.createdDate = createdDate;
	}

	public User(int id, Role role, String login, String password, String email, String lastName, String firstName,
				Timestamp createdDate, Timestamp updatedDate, boolean isBanned) {

		this(id, role, login, password, email, createdDate);
		this.lastName = lastName;
		this.firstName = firstName;
		this.updatedDate = updatedDate;
		this.isBanned = isBanned;
	}

	public User(int id, Role role, String login, String password, String email, String lastName, String firstName,
				Timestamp createdDate, Timestamp updatedDate, boolean isBanned, List<Post> publishedPosts,
				List<Post> readedPosts, List<FavoritePost> favoritePosts, List<Tag> favoriteTags) {

		this(id, role, login, password, email, lastName, firstName,
				createdDate, updatedDate, isBanned);
		this.publishedPosts = publishedPosts;
		this.readedPosts = readedPosts;
		this.favoritePosts = favoritePosts;
		this.favoriteTags = favoriteTags;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
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

	public List<Post> getPublishedPosts() {
		return publishedPosts;
	}

	public void setPublishedPosts(List<Post> publishedPosts) {
		this.publishedPosts = publishedPosts;
	}

	public List<Post> getReadedPosts() {
		return readedPosts;
	}

	public void setReadedPosts(List<Post> readedPosts) {
		this.readedPosts = readedPosts;
	}

	public List<FavoritePost> getFavoritePosts() {
		return favoritePosts;
	}

	public void setFavoritePosts(List<FavoritePost> favoritePosts) {
		this.favoritePosts = favoritePosts;
	}

	public List<Tag> getFavoriteTags() {
		return favoriteTags;
	}

	public void setFavoriteTags(List<Tag> favoriteTags) {
		this.favoriteTags = favoriteTags;
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

		User user = (User) o;

		if (isBanned != user.isBanned) {
			return false;
		}
		if (role != null ? !role.equals(user.role) : user.role != null) {
			return false;
		}
		if (login != null ? !login.equals(user.login) : user.login != null) {
			return false;
		}
		if (password != null ? !password.equals(user.password) : user.password != null) {
			return false;
		}
		if (email != null ? !email.equals(user.email) : user.email != null) {
			return false;
		}
		if (lastName != null ? !lastName.equals(user.lastName) : user.lastName != null) {
			return false;
		}
		if (firstName != null ? !firstName.equals(user.firstName) : user.firstName != null) {
			return false;
		}
		if (createdDate != null ? !createdDate.equals(user.createdDate) : user.createdDate != null) {
			return false;
		}
		if (updatedDate != null ? !updatedDate.equals(user.updatedDate) : user.updatedDate != null) {
			return false;
		}
		if (publishedPosts != null ? !publishedPosts.equals(user.publishedPosts) : user.publishedPosts != null) {
			return false;
		}
		if (readedPosts != null ? !readedPosts.equals(user.readedPosts) : user.readedPosts != null) {
			return false;
		}
		if (favoritePosts != null ? !favoritePosts.equals(user.favoritePosts) : user.favoritePosts != null) {
			return false;
		}
		return favoriteTags != null ? favoriteTags.equals(user.favoriteTags) : user.favoriteTags == null;

	}

	@Override
	public int hashCode() {
		int result = super.hashCode();
		result = 31 * result + (role != null ? role.hashCode() : 0);
		result = 31 * result + (login != null ? login.hashCode() : 0);
		result = 31 * result + (password != null ? password.hashCode() : 0);
		result = 31 * result + (email != null ? email.hashCode() : 0);
		result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
		result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
		result = 31 * result + (createdDate != null ? createdDate.hashCode() : 0);
		result = 31 * result + (updatedDate != null ? updatedDate.hashCode() : 0);
		result = 31 * result + (isBanned ? 1 : 0);
		result = 31 * result + (publishedPosts != null ? publishedPosts.hashCode() : 0);
		result = 31 * result + (readedPosts != null ? readedPosts.hashCode() : 0);
		result = 31 * result + (favoritePosts != null ? favoritePosts.hashCode() : 0);
		result = 31 * result + (favoriteTags != null ? favoriteTags.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "User{" +
				"role=" + role +
				", login='" + login + '\'' +
				", password='" + password + '\'' +
				", email='" + email + '\'' +
				", lastName='" + lastName + '\'' +
				", firstName='" + firstName + '\'' +
				", createdDate='" + createdDate + '\'' +
				", updatedDate='" + updatedDate + '\'' +
				", isBanned=" + isBanned +
				", publishedPosts=" + publishedPosts +
				", readedPosts=" + readedPosts +
				", favoritePosts=" + favoritePosts +
				", favoriteTags=" + favoriteTags +
				'}';
	}
}