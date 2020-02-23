package com.teddy.web.crawler;

import com.teddy.web.crawler.downloader.HttpClientDownloader;

import us.codecraft.webmagic.*;
import us.codecraft.webmagic.pipeline.JsonFilePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * package com.teddy.web.crawler description: 我的第一个爬虫 Copyright 2019 teddy, Inc. All rights reserved.
 *
 * @author fanzhongwei
 * @date 2019-5-6 20:15:04
 */
public class MyFristWebCrawler implements PageProcessor {

    private Site site = Site.me().setRetryTimes(3).setSleepTime(100);

    @Override
    public void process(Page page) {
        page.putField("url", page.getUrl().toString());
        page.putField("author", page.getUrl().regex("https://github\\.com/(\\w+)/.*").toString());
        page.putField("content", page.getHtml().xpath("//h1/allText()").regex(".*996\\.ICU.*").toString());
        if (page.getResultItems().get("content")==null){
            //skip this page
            page.setSkip(true);
        }

        page.addTargetRequests(page.getHtml().links().regex("(https://github\\.com/\\w+/\\w+)").all());
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        Spider.create(new MyFristWebCrawler())
            .addUrl("https://github.com/MSWorkers/support.996.ICU")
            .setDownloader(new HttpClientDownloader())
            .addPipeline(new JsonFilePipeline("D:\\webmagic\\"))
            .thread(4)
            .run();
    }
}
