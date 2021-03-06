package com.weapes.ntpaprseng.crawler.search;

import com.weapes.ntpaprseng.crawler.store.MetricsPaper;
import com.weapes.ntpaprseng.crawler.store.Paper;
import com.weapes.ntpaprseng.crawler.util.DateHelper;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Calendar;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

public class ESClient {
    private static final String IP = "172.29.108.136";
    private static final int PORT = 9300;
    private static final String INDEX = "ntpaprseng";
    private static final String PAPER_TYPE = "PAPER";
    private static final String METRICS_PAPER_TYPE = "METRICS_PAPER";
    private static TransportClient transportClient = null;
    private static ESClient singleton =new ESClient();
    private ESClient() {
    }
    static {
        try {
            transportClient = TransportClient.builder().settings(Settings.EMPTY).build()
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(IP), PORT));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
    public static ESClient getInstance() {
        return singleton;
    }

    private boolean putPaperIntoES(String id,XContentBuilder json) {
        IndexResponse response = transportClient.prepareIndex(INDEX,PAPER_TYPE,id)
                .setSource(json)
                .get();
        return response.isCreated();
    }

    private boolean putMetricsPaperIntoES(XContentBuilder json) {
        IndexResponse response = transportClient.prepareIndex(INDEX,METRICS_PAPER_TYPE)
                .setSource(json)
                .get();
        return response.isCreated();
    }
    public boolean savePaperIntoES(Paper paper) {
        XContentBuilder json = null;
        Calendar calendar= DateHelper.formatPublishTime(paper.getPublishTime());
        try {
            json = jsonBuilder().startObject()
                    .field("URL", paper.getUrl())
                    .field("Title", paper.getTitle())
                    .field("Authors", paper.getAuthors())
                    .field("SourceTitle", paper.getSourceTitle())
                    .field("ISSN", paper.getIssn())
                    .field("EISSN",paper. getEissn())
                    .field("DOI", paper.getDoi())
                    .field("Volume", paper.getVolume())
                    .field("Issue", paper.getIssue())
                    .field("PageBegin", paper.getPageBegin())
                    .field("PageEnd", paper.getPageEnd())
                    .field("Affiliation", paper.getAffiliation())
                    .field("CrawlTime", paper.getCrawlTime())
                    .field("Year", calendar.get(Calendar.YEAR))
                    .field("Month", calendar.get(Calendar.MONTH)+1)
                    .field("Day", calendar.get(Calendar.DAY_OF_MONTH))
                    .field("PublishTime", paper.getPublishTime()).endObject();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ESClient.getInstance().putPaperIntoES(paper.getUrl(),json);
    }
    public boolean saveInitMetricsPaperIntoES(Paper paper) {
        XContentBuilder json = null;
        Calendar calendar=DateHelper.formatPublishTime(paper.getPublishTime());
        try {
            json = jsonBuilder().startObject()
                    .field("URL", paper.getMetricsUrl())
                    .field("UpdateTime", DateHelper.getUpdateTime())
                    .field("Year", calendar.get(Calendar.YEAR))
                    .field("Month", calendar.get(Calendar.MONTH)+1)
                    .field("Day", calendar.get(Calendar.DAY_OF_MONTH))
                    .field("Page_views", 0)
                    .field("Web_Of_Science", 0)
                    .field("CrossRef", 0)
                    .field("Scopus", 0)
                    .field("News_outlets", 0)
                    .field("Reddit", 0)
                    .field("Blog", 0)
                    .field("Tweets", 0)
                    .field("FaceBook", 0)
                    .field("Google", 0)
                    .field("Pinterest", 0)
                    .field("Wikipedia", 0)
                    .field("Mendeley", 0)
                    .field("CiteUlink", 0)
                    .field("Zotero", 0)
                    .field("F10000", 0)
                    .field("Video", 0)
                    .field("Linkedin", 0)
                    .field("Q_A", 0)
                    .field("FinalIndex", 0)
                    .endObject();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return singleton.putMetricsPaperIntoES(json);
    }

    public boolean updateMetricsPaperIntoES(MetricsPaper metricsPaper) {
        XContentBuilder json = null;
        Calendar calendar = DateHelper.formatUpdateTime(DateHelper.getUpdateStartDate());
        try {
            json = jsonBuilder().startObject()
                    .field("URL", metricsPaper.getUrl())
                    .field("UpdateTime", DateHelper.getUpdateStartDate())
                    .field("Year", calendar.get(Calendar.YEAR))
                    .field("Month", calendar.get(Calendar.MONTH)+1)
                    .field("Day", calendar.get(Calendar.DAY_OF_MONTH))
                    .field("Page_views", metricsPaper.getPageViews())
                    .field("Web_Of_Science", metricsPaper.getWebOfScience())
                    .field("CrossRef", metricsPaper.getCrossRef())
                    .field("Scopus", metricsPaper.getScopus())
                    .field("News_outlets", metricsPaper.getNewsOutlets())
                    .field("Reddit", metricsPaper.getReddit())
                    .field("Blog", metricsPaper.getBlog())
                    .field("Tweets", metricsPaper.getTweets())
                    .field("FaceBook", metricsPaper.getFacebook())
                    .field("Google", metricsPaper.getGoogle())
                    .field("Pinterest", metricsPaper.getPinterest())
                    .field("Wikipedia", metricsPaper.getWikipedia())
                    .field("Mendeley", metricsPaper.getMendeley())
                    .field("CiteUlink", metricsPaper.getCiteUlink())
                    .field("Zotero", metricsPaper.getZotero())
                    .field("F10000", metricsPaper.getF1000())
                    .field("Video", metricsPaper.getVideo())
                    .field("Linkedin", metricsPaper.getLinkedin())
                    .field("Q_A", metricsPaper.getQ_a())
                    .field("FinalIndex", 0).endObject();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return singleton.putMetricsPaperIntoES(json);
    }
}
