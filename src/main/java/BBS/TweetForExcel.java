package BBS;

import java.util.ArrayList;
import java.util.List;

public class TweetForExcel {
    private int numberOfReplies;
    private String tweet;
    private List<String> replies = new ArrayList<>();
    private String linkToTweet;

    public TweetForExcel(int numberOfReplies, String tweet, List<String> replies, String linkToTweet) {
        this.numberOfReplies = numberOfReplies;
        this.tweet = tweet;
        this.replies = replies;
        this.linkToTweet = linkToTweet;
    }

    public TweetForExcel(String tweet, String linkToTweet){
        this.tweet=tweet;
        this.linkToTweet=linkToTweet;
    };

    public int getNumberOfReplies() {
        return numberOfReplies;
    }

    public void setNumberOfReplies(int numberOfReplies) {
        this.numberOfReplies = numberOfReplies;
    }

    public String getTweet() {
        return tweet;
    }

    public void setTweet(String tweet) {
        this.tweet = tweet;
    }

    public List<String> getReplies() {
        return replies;
    }

    public void setReplies(List<String> replies) {
        this.replies = replies;
    }

    public String getLinkToTweet() {
        return linkToTweet;
    }

    public void setLinkToTweet(String linkToTweet) {
        this.linkToTweet = linkToTweet;
    }
}
