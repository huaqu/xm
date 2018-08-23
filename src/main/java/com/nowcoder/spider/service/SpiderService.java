package com.nowcoder.spider.service;

import com.nowcoder.spider.service.spider.DefaultSpider;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;

/**
 * Created by nowcoder on 2018/08/16 下午5:23
 */
@Service
public class SpiderService {

  public void getLotsOfBooks() {
    String url = "";
    try {
      Spider.create(new DefaultSpider())
          .addUrl(url)
          .addPipeline(new ConsolePipeline())
          .thread(1)
          .run();

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
