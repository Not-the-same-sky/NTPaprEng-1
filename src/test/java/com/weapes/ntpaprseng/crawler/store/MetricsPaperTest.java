package com.weapes.ntpaprseng.crawler.store;

import com.weapes.ntpaprseng.crawler.extract.NatureMetricsWebPage;
import com.weapes.ntpaprseng.crawler.util.Helper;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

public class MetricsPaperTest {
    @Test
    @Ignore
    public void store() throws Exception {
        final String url = "http://www.nature.com/nature/journal/v529/n7584/nature16490/metrics";
        final String html = Helper.fetchWebPage(url);
        final NatureMetricsWebPage natureMetricsWebPage =
                new NatureMetricsWebPage(html, url);
        final boolean store =
                natureMetricsWebPage.extract().store();
        Assert.assertTrue(store);
    }
}
