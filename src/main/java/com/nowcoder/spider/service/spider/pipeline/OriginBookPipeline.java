package com.nowcoder.spider.service.spider.pipeline;

import com.nowcoder.spider.model.OriginBook;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;

/**
 * OriginBookPipeline Description: CreateDate: 2018/8/15
 *
 * @author nowcoder
 */

/**
 * 关于Pipeline，一个PageProcessor被执行更新一遍所有的Pipeline 如果Page被skip掉了，则不执行Pipeline
 */
@Service
public class OriginBookPipeline implements CallablePipeline {

  private OriginBook originBook;

  public OriginBookPipeline() {
    originBook = new OriginBook();
  }

  @Override
  public void process(ResultItems resultItems, Task task) {
    originBook = resultItems.get("book");
    originBook.setOriginUrl(task.getSite().getDomain());
  }

  @Override
  public Object getResult() throws NullPointerException {

    if (!isQualifier())
      throw new NullPointerException("爬取结果不全");

    return originBook;
  }

  /**
   * 该方法决定得到的结果是否合格，如果不合格抛出 {@link NullPointerException} 可以修改isQualifier判定方法 考虑到爬虫的不确定性，该条件尽可能的宽松
   */
  protected boolean isQualifier() {
    return StringUtils.isNoneEmpty(
//        originBook.getOriginUrl(),
//        originBook.getName(),
//        originBook.getAuthor(),
//        originBook.getPublisher(),
//        originBook.getISBN(),
//        originBook.getImg()
//        String.valueOf(originBook.getScore())
        "123"
    );
  }
}
