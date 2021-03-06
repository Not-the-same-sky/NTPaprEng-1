package com.weapes.ntpaprseng.crawler.follow;

import com.weapes.ntpaprseng.crawler.extract.AdvSearchedWebPage;
import com.weapes.ntpaprseng.crawler.util.Helper;

import java.io.IOException;

public class AdvSearchedLink extends Link {

    public AdvSearchedLink(final String url) {
        super(url);
    }

    @Override
    public AdvSearchedWebPage follow() throws IOException {

        final String advSearchedWebPage =
                Helper.fetchWebPage(getUrl());
        return new AdvSearchedWebPage(advSearchedWebPage, getUrl());

    }
}
