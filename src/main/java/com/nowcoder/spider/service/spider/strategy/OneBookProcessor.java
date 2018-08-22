package com.nowcoder.spider.service.spider.strategy;


import java.util.HashMap;
import java.util.Map;
import us.codecraft.webmagic.Page;

/**
 * OneBookProcessor Description: CreateDate: 2018/8/14
 *
 * @author nowcoder
 */

public class OneBookProcessor implements ProcessStrategy {

  /**
   * 当前解析缓存。
   */
  private Map<String, Object> cache = new HashMap<>(16);

  /**
   * 自动类型转换
   */
  private <T> T get(String key) {
    Object o = cache.get(key);
    if (o == null) {
      return null;
    }
    return (T) cache.get(key);
  }

  /**
   * 实现主要的爬取一本书的逻辑功能
   *
   * @param page 根据domain 已经爬取到的页面，可以看做一个html页面
   */
  @Override
  public void doProcess(Page page) {

  }


}
