package BBS;

import outputWriter.JavaWriter;
import twitter4j.Query;

public class Test {
    public static void main(String[] args) {
        TwitterBot twitterBot=new TwitterBot();
        twitterBot.setTweetsForPage(4);
        twitterBot.setTypeOfResult(Query.ResultType.mixed);
        twitterBot.setLang("it");
        twitterBot.addAndStatement("Guerra");
        twitterBot.putInGroup();
        twitterBot.addAndStatement("from:zaiapresidente");
        twitterBot.run();
    }
}
