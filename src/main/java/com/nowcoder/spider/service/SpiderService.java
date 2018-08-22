package com.nowcoder.spider.service;

import com.nowcoder.spider.service.spider.DefaultSpider;
import com.nowcoder.spider.service.spider.strategy.OneBookProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.pipeline.ConsolePipeline;

/**
 * Created by nowcoder on 2018/08/16 下午5:23
 */
@Service
public class SpiderService {

  @Autowired
  private DefaultSpider defaultSpider;

  public void getLotsOfBooks(String beginUrl) {
    try {
      defaultSpider.setProcessStrategy(new OneBookProcessor());
      defaultSpider.getSpider()
          .addUrl(beginUrl)
          .addPipeline(new ConsolePipeline())
          .thread(1)
          .run();

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
