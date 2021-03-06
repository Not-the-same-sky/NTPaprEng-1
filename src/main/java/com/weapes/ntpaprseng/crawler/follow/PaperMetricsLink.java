package com.weapes.ntpaprseng.crawler.follow;

import com.weapes.ntpaprseng.crawler.extract.Extractable;
import com.weapes.ntpaprseng.crawler.extract.NatureMetricsWebPage;
import com.weapes.ntpaprseng.crawler.util.Helper;

import java.io.IOException;

public class PaperMetricsLink extends Link {
    public PaperMetricsLink(String url) {
        super(url);
    }

    @Override
    public Extractable follow() throws IOException {
        final String html = Helper.fetchWebPage(getUrl());
        return new NatureMetricsWebPage(html, getUrl());
    }
}
