# 1.项目及项目框架介绍

    git checkout step-1-project-framework

本项目基于webmagic开源项目制作一个优雅高效的爬虫。最终以豆瓣图书为例，看看怎么一步步完善我们的爬虫。

*--注意--*

**本项目仅作为技术学习研究使用，禁止用于任何商业用途，禁止任何损害网站利益的行为**

## webmagic
首先介绍一下webmagic开源项目。

> 源码：https://github.com/code4craft/webmagic

> 官网：http://webmagic.io/

比较遗憾的是webmagic项目已经没有维护了，但是这不妨碍我们的学习，因为webmagic本身就已经足够完善了，
而且其扩展性极好，是我们用java搭建爬虫的不二之选。

### PageProcessor
在webmagic中，你最需要关心的是` PageProcessor `接口，该接口中的` public void process(Page page); `方法是爬虫的核心方法，
你可以看到这个方法中传入了一个` Page `参数，` Page `封装了一次爬取的所有信息，包括页面信息和请求信息。
你需要做的是实现该接口，并将如何处理页面以及爬虫如何进行下一步迭代爬取的逻辑写入` public void process(Page page); `方法中。
webmagic会持有一个你实现了` PageProcessor `接口的类的引用，并在webmagic封装的多线程环境下循环调用` process(Page page);`方法。
在` process(Page page);`中，你可以使用` css、regex、xpath `三种方式来获取页面中你想要的元素（你甚至可以用‘$’符号去选取DOM）。
这里面xpath使用的是作者基于Jsoup自己实现的Xsoup，也是一个很棒的项目。

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
你可以调用` page.putField() `将你获取的结果存起来，` page `会持有一个` ResultItems `引用，
用来存放爬取过程中的结果。调用` page.addTargetRequest() `可以存入一个url作为下一个爬取的页面地址。

到这里你应该知道了，你的爬虫主要逻辑应该放在哪里了。

### Pipeline

` Pipeline `是又一常用接口，它帮你处理了你在` PageProcessor `中放入结果。
但是这里有一个问题：既然在` PageProcessor `的方法` process(Page page);` 中已经能够获取到所有的结果了，
为什么非要先存放起来，交由一个专门的` Pipeline `去处理呢？这里引用一段官方的解释：

> 可以看到，Pipeline其实就是将PageProcessor抽取的结果，继续进行了处理的，其实在Pipeline中完成的功能，你基本上也可以直接在PageProcessor实现，那么为什么会有Pipeline？有几个原因：

> 1.为了模块分离。“页面抽取”和“后处理、持久化”是爬虫的两个阶段，将其分离开来，一个是代码结构比较清晰，另一个是以后也可能将其处理过程分开，分开在独立的线程以至于不同的机器执行。

> 2.Pipeline的功能比较固定，更容易做成通用组件。每个页面的抽取方式千变万化，但是后续处理方式则比较固定，例如保存到文件、保存到数据库这种操作，这些对所有页面都是通用的。WebMagic中就已经提供了控制台输出、保存到文件、保存为JSON格式的文件几种通用的Pipeline。

你可以设置很多自定义的Pipeline，webmagic会在每次完成一个页面的爬取工作后，按顺序调用所有注册的Pipeline。
``` java
    private void onDownloadSuccess(Request request, Page page) {
        if (site.getAcceptStatCode().contains(page.getStatusCode())){
            pageProcessor.process(page);
            extractAndAddRequests(page, spawnUrl);
            if (!page.getResultItems().isSkip()) {
            //这里循环调用了所有注册的Pipeline
                for (Pipeline pipeline : pipelines) {
                    pipeline.process(page.getResultItems(), this);
                }
            }
        } else {
            logger.info("page status code error, page {} , code: {}", request.getUrl(), page.getStatusCode());
        }
        sleep(site.getSleepTime());
        return;
    }
```
在代码里面我已经实现了两个默认实现，结合上面的介绍相信你很容易就能看懂。
我们之后会实现自己的` Pipeline `以及对webmagic框架的扩展。

------
` PageProcessor ` 和 ` Pipeline `是我们需要重点关注的两个接口，当然webmagic还提供了各种爬虫需要的功能，
比如代理、定时任务、JSON格式解析（针对Ajax请求）等等常用功能，交给你去探索啦！

## 项目框架
如果你查看` pom.xml `文件，你一定会一目了然：我们主要使用了Spring Boot和MyBatis作为我们的主要框架。

我们预先设置了部分文件夹：
* ` dao ` 用来负责与数据库打交道
* ` model ` 数据模型都在这里
* ` service ` 爬虫的主体逻辑都在这里
* ` utils ` 各种工具类

是不是很简单！

### 设计模式
这里我们需要谈谈设计模式。

不知道你有没有发现一个问题：爬取一个页面、分析一个页面、将结果存下、寻找下一个迭代页面...甚至会包含不同层次的页面爬取...
这么多功能我们全部需要写进` PageProcessor `的` process(Page page);`方法中，
是不是想想就会觉得非常沉重，不仅沉重臃肿，而且没有可扩展性。这绝对不是优雅的解决方案。

这里我们需要先介绍设计模式中的其中一条原则：

> 1.面向接口编程，不要面向具体实现编程。

这里，我们将所有的逻辑堆砌在一个函数中，就是面向实现编程，我们太着急的去实现一个功能，
以至于将java写成了c语言。你可以想想要怎么做！

但是webmagic只提供这一个方法，我们要怎么扩展呢？接下来我们会演示。

在此之前，你需要了解至少三个设计模式

* 策略模式
* 装饰器模式
* 观察者模式

# 好了！我们开始吧！

    git checkout step-1-project-framework

