# 1.项目及项目框架介绍
本项目基于webmagic开源项目制作一个优雅高效的爬虫。最终以豆瓣图书为例，看看怎么一步步完善我们的爬虫。
*--注意--*
**本项目仅作为技术学习研究使用，禁止用于任何商业用途，禁止任何损害网站利益的行为**

## webmagic
首先介绍一下webmagic开源项目。

> 源码：https://github.com/code4craft/webmagic
> 官网：http://webmagic.io/

比较遗憾的是webmagic项目已经没有维护了，但是这不妨碍我们的学习，因为webmagic本身就已经足够完善了，
而且其扩展性极好，是我们用java搭建爬虫的不二之选。

在webmagic中，你最需要关心的是` PageProcessor `接口，该接口中的` public void process(Page page); `方法是爬虫的核心方法，
你可以看到这个方法中传入了一个` Page `参数，` Page `封装了一次爬取的所有信息，包括页面信息和请求信息。
你需要做的是实现该接口，并将如何处理页面以及爬虫如何进行下一步迭代爬取的逻辑写入` public void process(Page page); `方法中。
webmagic会持有一个你实现了` PageProcessor `接口的类的引用，并在webmagic封装的多线程环境下循环调用` process(Page page);`方法。

``` java
//示例如下：
public class GithubRepoPageProcessor implements PageProcessor {

    // 部分一：抓取网站的相关配置，包括编码、抓取间隔、重试次数等
    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000);

    @Override
    // process是定制爬虫逻辑的核心接口，在这里编写抽取逻辑
    public void process(Page page) {
        // 部分二：定义如何抽取页面信息，并保存下来
        page.putField("author", page.getUrl().regex("https://github\\.com/(\\w+)/.*").toString());
        page.putField("name", page.getHtml().xpath("//h1[@class='entry-title public']/strong/a/text()").toString());
        if (page.getResultItems().get("name") == null) {
            //skip this page
            page.setSkip(true);
        }
        page.putField("readme", page.getHtml().xpath("//div[@id='readme']/tidyText()"));

        // 部分三：从页面发现后续的url地址来抓取
        page.addTargetRequests(page.getHtml().links().regex("(https://github\\.com/[\\w\\-]+/[\\w\\-]+)").all());
    }

    @Override
    public Site getSite() {
        return site;
    }
}
```
