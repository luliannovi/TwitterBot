package BBS;


import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import outputWriter.JavaWriter;
import twitter4j.*;
import twitter4j.api.SavedSearchesResources;
import twitter4j.conf.ConfigurationBuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class TwitterBot implements Runnable{
    private String consumerKey = null;
    private String consumerSecret = null;
    private String accessToken = null;
    private String accessTokenSecret = null;
    private ConfigurationBuilder cb=null;
    private static int MAX_PAGES=1;
    private String mainSearchQuery;
    private Query mainQuery;
    private String subSearchQuery;
    private Query subQuery;
    public List<String> filters = new ArrayList<>();
    private int tweetsForPage = 2;
    private int repliesForPage=20;
    private String mainLang = null;
    private String subLang = null;
    private Query.ResultType typeOfResult = null;
    private Date sinceDate = null;
    private Twitter twitter;
    private List<SavedSearch> savedSearches = null;
    private int maxSavedSearches = 15;
    List<TweetForExcel> tweetForExcels = new ArrayList<>();
    private String filename;

    public TwitterBot(){
    }

    private void configurate(){
        cb = new ConfigurationBuilder();
        File file   = new File("twitter4j.properties");
        boolean a = file.exists();
        try {
            Scanner reader = new Scanner(file);
            int lineNumber=0;
            while(reader.hasNextLine()){
                String line = reader.nextLine();
                lineNumber++;
                if(line.contains("consumerKey=")){
                    String[] ret = line.split("consumerKey=");
                    this.consumerKey=ret[1];
                }else if(line.contains("consumerSecret=")){
                    String[] ret = line.split("consumerSecret=");
                    this.consumerSecret=ret[1];
                }else if(line.contains("accessToken=")){
                    String[] ret = line.split("accessToken=");
                    this.accessToken=ret[1];
                }else if(line.contains("accessTokenSecret=")){
                    String[] ret = line.split("accessTokenSecret=");
                    this.accessTokenSecret=ret[1];
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("FileNotFoundException per il file twitter4j.properties");
            e.printStackTrace();
        }
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(consumerKey)
                .setOAuthConsumerSecret(consumerSecret)
                .setOAuthAccessToken(accessToken)
                .setOAuthAccessTokenSecret(accessTokenSecret);

    }

    public void addFilter(String filter){
        this.filters.add(filter);
    }
    public void setQuery(String queryString) {
        this.mainQuery.setQuery(queryString);
    }
    public void setQuery(Query query){
        this.mainQuery=query;
    }
    public void setConsumerKey(String consumerKey) {
        this.consumerKey = consumerKey;
    }
    public void setConsumerSecret(String consumerSecret) {
        this.consumerSecret = consumerSecret;
    }
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
    public void setAccessTokenSecret(String accessTokenSecret) {
        this.accessTokenSecret = accessTokenSecret;
    }
    public void setSearchQuery(List<String> filters) {
        this.mainSearchQuery="";
        for(String filter : filters){
            mainSearchQuery = mainSearchQuery + filter + " ";
        }
    }
    public void setSearchQuery(String searchQuery){
        this.mainSearchQuery=searchQuery;
    }
    public Query getMainQuery() {
        return mainQuery;
    }
    public void setMainQuery(Query mainQuery) {
        this.mainQuery = mainQuery;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public List<SavedSearch> getSavedSearches() {
        return savedSearches;
    }

    public void setSavedSearches(List<SavedSearch> savedSearches) {
        this.savedSearches = savedSearches;
    }

    public int getMaxSavedSearches() {
        return maxSavedSearches;
    }

    public void setMaxSavedSearches(int maxSavedSearches) {
        this.maxSavedSearches = maxSavedSearches;
    }

    public String getMainSearchQuery() {
        return mainSearchQuery;
    }

    public int getRepliesForPage() {
        return repliesForPage;
    }

    public void setRepliesForPage(int repliesForPage) {
        this.repliesForPage = repliesForPage;
    }

    public int getTweetsForPage() {
        return tweetsForPage;
    }
    public void setTweetsForPage(int tweetsForPage) {
        this.tweetsForPage = tweetsForPage;
    }
    public String getMainLang() {
        return mainLang;
    }
    public void setMainLang(String lang) {
        this.mainLang = lang;
    }

    public String getSubLang() {
        return subLang;
    }

    public void setSubLang(String subLang) {
        this.subLang = subLang;
    }

    public Query.ResultType getTypeOfResult() {
        return typeOfResult;
    }
    public void setTypeOfResult(Query.ResultType typeOfResult) {
        this.typeOfResult = typeOfResult;
    }
    public List<Tweet> getTweetsFromResult(QueryResult result){
        List<Tweet> tweets = new ArrayList<>();
        for(Status t : result.getTweets()){
            tweets.add(new Tweet(t));
        }
        return tweets;
    }

    public void getandsaveSavedSearches()  {
        try {
            savedSearches = this.twitter.getSavedSearches();
        } catch (TwitterException e) {
            e.printStackTrace();
        }
    }

    public void destroySavedSearches(){
        try {
            savedSearches = this.twitter.getSavedSearches();
        } catch (TwitterException e) {
            e.printStackTrace();
        }
        if(savedSearches.size()>0) {
            for (SavedSearch s : savedSearches) {
                try {
                    twitter.destroySavedSearch(s.getId());

                } catch (TwitterException e) {
                    e.printStackTrace();
                }
            }
        }else{
            System.out.println("no saved search saved..\n");
        }
        savedSearches = null;
    }

    public void removeSavedSearch(){
        if(this.savedSearches.size()>0) {
            this.savedSearches.remove(1);
        }
    }

    public void cleanSavedSearches(){
        if(this.savedSearches.size()>this.getMaxSavedSearches()){
            while(savedSearches.size()<=this.getMaxSavedSearches())
                this.savedSearches.remove(1);
        }
    }

    public void createSavedSearch(Query query){
        try {
            SavedSearch savedSearch = twitter.createSavedSearch(query.toString());
        } catch (TwitterException e) {
            e.printStackTrace();
            System.out.println("Failed to create a saved search: " + e.getMessage());
        }
    }

    public Date getSinceDate() {
        return sinceDate;
    }

    public void setSinceDate(Date sinceDate) {
        this.sinceDate = sinceDate;
    }
    public void setSinceDate(String sinceDate) {
        try {
            this.sinceDate = new SimpleDateFormat("dd/MM/yyyy").parse(sinceDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public String getSubSearchQuery() {
        return subSearchQuery;
    }

    public void setSubSearchQuery(String subSearchQuery) {
        this.subSearchQuery = subSearchQuery;
    }

    public Query getSubQuery() {
        return subQuery;
    }

    public void setSubQuery(Query subQuery) {
        this.subQuery = subQuery;
    }

    public void setSubQuery(String subQuery){
        this.subQuery.setQuery(subQuery);
    }

    public void querySettings(){
        this.mainQuery.setCount(this.getTweetsForPage());
        if(this.getTypeOfResult()!=null)
            this.mainQuery.setResultType(this.getTypeOfResult());
        if(this.getMainLang()!=null)
            this.mainQuery.setLang(this.getMainLang());
        if(this.getSinceDate() != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            this.mainQuery.setSince(dateFormat.format(this.getSinceDate()));
        }
    }

    public void subQuerySettings(){
        if(this.getTypeOfResult()!=null)
            this.subQuery.setResultType(this.getTypeOfResult());
        if(this.getSubLang()!=null)
            this.subQuery.setLang(this.getSubLang());
        this.subQuery.setCount(this.getRepliesForPage());
    }

    public void addOrStatement(String statement){
        if(this.filters.size()>0){
            this.addFilter("OR " + statement);
        }else{
            this.addFilter(statement);
        }
    }

    public void addAndStatement(String statement){
        if(this.filters.size()>0){
            this.addFilter(" " + statement);
        }else{
            this.addFilter(statement);
        }
    }

    public void putInGroup(){
        this.filters.add(0,"(");
        this.filters.add(this.filters.size(),")");
    }

    public String createRetweetStatement(boolean isRetweet){
        if(isRetweet==true){
            return "is:retweet";
        }else{
            return "-is:retweet";
        }
    }  //crea statement da inserire in query di ricerca per verificare se si tratta o meno di retweet

    public String createReplyStatement(boolean isReply){
        if(isReply==true){
            return "is:reply";
        }else{
            return "-is:reply";
        }
    }

    public String createFromStatement(boolean isFrom, String screenName){
        if(isFrom==true){
            return "from:" + screenName;
        }else{
            return "-from:" + screenName;
        }
    } //crea statement per verificare se il tweet proviene o meno da un user

    public String createVerifiedStatement(boolean isVerified){
        if(isVerified==true){
            return "is:verified";
        }else{
            return "-is:verified";
        }
    }  //crea statement per verificare se il tweet proviene da un user verificato

    public String createHashtagStatement(boolean hasHashtag){
        if(hasHashtag){
            return "has:hashtags";
        }else{
            return "-has:hashtags";
        }
    }

    public String createLinkStatement(boolean hasLinks){
        if(hasLinks){
            return "has:links";
        }else{
            return "-has:links";
        }
    }

    public String createMentionsStatement(boolean hasMentions){
        if(hasMentions){
            return "has:mentions";
        }else{
            return "-has:mentions";
        }
    }

    public String createMediaStatement(boolean hasMedia){
        if(hasMedia){
            return "has:media";
        }else{
            return "-has:media";
        }
    }


    @Override
    public void run() {
        this.configurate();
        TwitterFactory tf = new TwitterFactory(cb.build());
        this.twitter = tf.getInstance();
        QueryResult result;
        try {
            this.mainQuery=new Query();
            this.setSearchQuery(this.filters);
            this.querySettings();
            this.setQuery(this.mainSearchQuery);
            int pageNumber=1;
            do {
                System.out.println("Query di ricerca di riferimento: "+ this.mainSearchQuery+"\n");
                result = twitter.search(mainQuery);                                     //search restituisce l'insieme di pagine contenenti tweet filtrati da query
                List<Tweet> tweetList = getTweetsFromResult(result);
                System.out.println("tweet trovati: " + tweetList.size());

                for (Tweet mainTweet : tweetList) {
                    TweetForExcel tweetForExcel = new TweetForExcel(mainTweet.getText(),mainTweet.getLinkToTweet());
                    tweetForExcel.setNumberOfReplies(0);
                    this.subQuery = new Query();
                    subQuerySettings();
                    if(mainTweet.getConversationId()>0){
                        setSubQuery("conversation_id:" + mainTweet.getConversationId());
                    }else {
                        setSubQuery("conversation_id:" + mainTweet.getTweetId());
                    }
                    int subPage=1;
                    List<String> replies = new ArrayList<>();
                    do {
                        QueryResult subResult = twitter.search(subQuery);
                        List<Tweet> subTweets = getTweetsFromResult(subResult);
                        for (Tweet subTweet : subTweets) {
                            replies.add(subTweet.getText());
                        }
                        subPage++;
                    }while((subQuery = result.nextQuery()) != null && subPage <=  MAX_PAGES);
                    tweetForExcel.setNumberOfReplies(replies.size());
                    tweetForExcels.add(tweetForExcel);
                }
                pageNumber++;
            } while((mainQuery = result.nextQuery()) != null && pageNumber <=  MAX_PAGES);

            JavaWriter javaWriter = new JavaWriter(new String[]{"Foglio 1" , "Testi"});
            javaWriter.writerFromTweets(tweetForExcels,"prova");
        } catch (TwitterException e) {
            e.printStackTrace();
        }
    }
}
