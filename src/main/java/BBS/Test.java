package BBS;

import outputWriter.JavaWriter;
import twitter4j.Query;
import twitter4j.SavedSearch;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        TwitterBot twitterBot=new TwitterBot();
        twitterBot.setTweetsForPage(4);
        twitterBot.setTypeOfResult(Query.ResultType.mixed);
        twitterBot.setMainLang("it");
        twitterBot.setSubLang("it");

        twitterBot.addAndStatement("from:zaiapresidente");
        twitterBot.addFilter("RISORSE STRAORDINARIE");
        twitterBot.setSinceDate("18/08/2022");

        twitterBot.run();
        /*twitterBot.destroySavedSearches();
        twitterBot.createSavedSearch(twitterBot.getMainQuery());
        List<SavedSearch> a = twitterBot.getSavedSearches();
        System.out.println(a.size());
        for(SavedSearch b : a){
            System.out.println(b.getName());
        }*/
    }
}
