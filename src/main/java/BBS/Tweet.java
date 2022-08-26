package BBS;

import twitter4j.GeoLocation;
import twitter4j.Place;
import twitter4j.Status;
import twitter4j.UserMentionEntity;

import java.util.Date;

public class Tweet {
    private TwitterUser user;
    private String text;
    private Date createdAt;
    private int likes;
    private String language;
    private int retweets;
    private boolean isPossiblySensitive;
    private boolean isRetweet;
    private long conversationId;
    private long tweetId;
    private String source;
    private TwitterUser[] usersMentioned;
    private Place place;
    private GeoLocation geoLocation;
    private String linkToTweet;

    public Tweet(TwitterUser user, String text, Date createdAt, boolean isRetweet) {
        this.user = user;
        this.text = text;
        this.createdAt = createdAt;
        this.isRetweet = isRetweet;
    }

    public Tweet(TwitterUser user, String text, Date createdAt, int likes, long tweetId, String language, int retweets, boolean isRetweet) {
        this.user = user;
        this.text = text;
        this.createdAt = createdAt;
        this.likes = likes;
        this.tweetId = tweetId;
        this.language = language;
        this.retweets = retweets;
        this.isRetweet = isRetweet;
    }

    public Tweet(Status status) {
        this.user = new TwitterUser(status.getUser());
        this.text = status.getText();
        this.createdAt = status.getCreatedAt();
        this.likes = status.getFavoriteCount();
        this.tweetId = status.getId();
        this.conversationId = status.getInReplyToStatusId();
        this.language = status.getLang();
        this.retweets = status.getRetweetCount();
        this.isPossiblySensitive = status.isPossiblySensitive();
        this.isRetweet = status.isRetweet();
        this.usersMentioned = getMentionedList(status.getUserMentionEntities());
        this.place = status.getPlace();
        this.geoLocation = status.getGeoLocation();
        this.linkToTweet="https://twitter.com/" + status.getUser().getScreenName() + "/status/" + status.getId();
    }

    public TwitterUser[] getMentionedList(UserMentionEntity[] users){
        TwitterUser[] list = new TwitterUser[users.length];
        int cont=0;
        for(UserMentionEntity u : users){
            list[cont] = new TwitterUser(u.getName(),u.getScreenName(),u.getId());
            cont++;
        }
        return list;

    }

    public String getMentioned(){
        String returnString="";
        for(TwitterUser user:this.usersMentioned){
            returnString = returnString + "\n--> @" + user.getScreenName() + "\n";
        }
        return returnString;
    }

    public String getLinkToTweet() {
        return linkToTweet;
    }

    public void setLinkToTweet(String linkToTweet) {
        this.linkToTweet = linkToTweet;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public GeoLocation getGeoLocation() {
        return geoLocation;
    }

    public void setGeoLocation(GeoLocation geoLocation) {
        this.geoLocation = geoLocation;
    }

    public long getConversationId() {
        return this.conversationId;
    }

    public void setConversationId(long conversationId) {
        this.conversationId = conversationId;
    }

    public TwitterUser getUser() {
        return user;
    }

    public void setUser(TwitterUser user) {
        this.user = user;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public long getTweetId() {
        return tweetId;
    }

    public void setTweetId(long tweetId) {
        this.tweetId = tweetId;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getRetweets() {
        return retweets;
    }

    public void setRetweets(int retweets) {
        this.retweets = retweets;
    }

    public boolean isPossiblySensitive() {
        return isPossiblySensitive;
    }

    public void setPossiblySensitive(boolean possiblySensitive) {
        isPossiblySensitive = possiblySensitive;
    }

    public boolean isRetweet() {
        return isRetweet;
    }

    public void setRetweet(boolean retweet) {
        isRetweet = retweet;
    }

}
