package com.nju.graduation.project.bas.demo;

import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.plugin.berkeley.BreadthCrawler;

/**
 * @author shanhe
 * @className AutoNewsCrawler
 * @date 2020-10-26 14:08
 **/
public class AutoNewsCrawler extends BreadthCrawler {
    public AutoNewsCrawler(String crawlPath, boolean autoParse) {
        super(crawlPath, autoParse);

        /**设置爬取的网站地址
         * addSeed 表示添加种子
         * 种子链接会在爬虫启动之前加入到抓取信息中并标记为未抓取状态.这个过程称为注入*/
        this.addSeed("https://www.csdn.net/");

        /** addRegex 参数为一个 url 正则表达式, 可以用于过滤不必抓取的链接，如 .js .jpg .css ... 等
         * 也可以指定抓取某些规则的链接，如下 addRegex 中会抓取 此类地址：
         * https://blog.github.com/2018-07-13-graphql-for-octokit/
         * */
        this.addRegex("https://blog.csdn.net/[^\\s]*/article/details/[^\\s]*");

        /**设置线程数*/
        setThreads(50);
        getConf().setTopN(100);

        /**
         * 是否进行断电爬取，默认为 false
         * setResumable(true);
         */

    }

    @Override
    public void visit(Page page, CrawlDatums crawlDatums) {
        String url = page.url();
        /**如果此页面地址 确实是要求爬取网址，则进行取值
         */
        if (page.matchUrl("https://blog.csdn.net/[^\\s]*/article/details/[^\\s]*")) {

            /**
             * 通过 选择器 获取页面 标题以及 正文内容
             * */
            String title = page.select("h1[class=title-article]").first().text();
            String content = page.selectText("div.htmledit_views");

            System.out.println("URL:\n" + url);
            System.out.println("title:\n" + title);
            System.out.println("content:\n" + content);

        }


    }

    public static void main(String[] args) throws Exception {
        /**
         * AutoNewsCrawler 构造器中会进行 数据初始化，这两个参数接着会传给父类
         * super(crawlPath, autoParse);
         * crawlPath：表示设置保存爬取记录的文件夹，本例运行之后会在应用根目录下生成一个 "crawl" 目录存放爬取信息
         * */
        AutoNewsCrawler crawler = new AutoNewsCrawler("crawl", true);

        /**
         * 启动爬虫，爬取的深度为4层
         * 添加的第一层种子链接,为第1层
         */
        crawler.start(4);//启动爬虫
    }

}
