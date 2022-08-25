package BBS;

import twitter4j.User;

public class TwitterUser {
    private String name;
    private String screenName;
    private String description;
    private String email;
    private int followers;
    private int following;
    private long id;
    private String language;
    private String URL;
    private boolean verified;

    public TwitterUser(User user){
        this.name=user.getName();
        this.screenName=user.getScreenName();
        this.description=user.getDescription();
        this.email=user.getEmail();
        this.followers=user.getFollowersCount();
        this.following=user.getFriendsCount();
        this.id=user.getId();
        this.language=user.getLang();
        this.URL=user.getURL();
        this.verified=user.isVerified();
    }
    public TwitterUser(String name, String screenName) {
        this.name = name;
        this.screenName = screenName;
    }

    public TwitterUser(String name, String screenName, String description) {
        this.name = name;
        this.screenName = screenName;
        this.description = description;
    }

    public TwitterUser(String name, String screenName, long id) {
        this.name = name;
        this.screenName = screenName;
        this.description = description;
        this.id = id;
    }

    public TwitterUser(String name, String screenName, String description, long id) {
        this.name = name;
        this.screenName = screenName;
        this.description = description;
        this.id = id;
    }

    public TwitterUser(String name, String screenName, String description, long id, String URL) {
        this.name = name;
        this.screenName = screenName;
        this.description = description;
        this.id = id;
        this.URL = URL;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getFollowers() {
        return followers;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
    }

    public int getFollowing() {
        return following;
    }

    public void setFollowing(int following) {
        this.following = following;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }
}
