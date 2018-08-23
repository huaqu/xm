package com.nowcoder.spider.service.spider;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 *  Description:爬虫入口的默认实现
 * CreateDate: 2018/8/14
 *
 * @author nowcoder
 */

public class DefaultSpider implements PageProcessor {

  /**
   * 如果访问超时，等两分钟
   */
  private static int ONE_MINUTES = 2 * 1000 * 60;

  private static int ONE_SECOUND = 1000;

  //重试三次，等待1秒
  private Site site = Site.me().setRetryTimes(3).setSleepTime(2 * ONE_SECOUND).setRetrySleepTime(2 * ONE_MINUTES);


  /*--- 实现PageProcessor ---*/
  @Override
  public void process(Page page) {
    //TODO : 实现爬虫主体算法

  }

  @Override
  public Site getSite() {
    return site;
  }

}
