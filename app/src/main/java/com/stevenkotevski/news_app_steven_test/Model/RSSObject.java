package com.stevenkotevski.news_app_steven_test.Model;

import java.util.List;

/**
 * This class is used to create an instance of an RSS WebPage.
 * RSS is a format for delivering regularly changing web content, so many News sites use it (such as the NY Times in this case)
 */


public class RSSObject {
    public String status;
    public Feed feed;
    public List<Item> items;

    public RSSObject(String status, Feed feed, List<Item> items) {
        this.status = status;
        this.feed = feed;
        this.items = items;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Feed getFeed() {
        return feed;
    }

    public void setFeed(Feed feed) {
        this.feed = feed;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
