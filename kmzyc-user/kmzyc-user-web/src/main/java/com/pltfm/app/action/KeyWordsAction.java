package com.pltfm.app.action;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.kmzyc.commons.page.Page;
import com.opensymphony.xwork2.ModelDriven;
import com.pltfm.app.dataobject.KeyWordsDO;
import com.pltfm.app.service.KeyWordsService;
import com.pltfm.app.util.DateTimeUtils;
import com.pltfm.app.util.Token;
import com.pltfm.app.vobject.KeyWordsQuery;
import com.pltfm.sys.model.SysUser;


/**
 * 账户信息Action类
 * 
 * @author cjm
 * @since 2013-7-10
 */
@Component(value = "keyWordsAction")
@Scope(value = "prototype")
public class KeyWordsAction extends BaseAction implements ModelDriven {

  /**
   * 
   */
  private static final long serialVersionUID = 1247340143369796188L;



  /**
   * 分页类
   */
  private Page page;



  @Resource(name = "keyWordsService")
  private KeyWordsService keyWordsService;
  private KeyWordsDO keyWordsDO;
  private KeyWordsQuery keyWordsQuery;
  private List<Integer> keyWordsIds;


  /**
   * 查询关键词过滤信息
   */

  public String pageList() {
    if (keyWordsDO.getKeyWords() != null) {
      String keyWords = keyWordsDO.getKeyWords();
      keyWordsQuery = new KeyWordsQuery();
      keyWordsQuery.setKeyWords(keyWords);
    }

    page = keyWordsService.findKeyWordsQueryByExample(page, keyWordsQuery);
    return "lists";
  }

  /**
   * 增加关键词过滤信息
   */
  public String addKeyWords() {


    return "addKeyWords";
  }

  /**
   * 保存增加关键词过滤信息
   */
  @Token
  public String saveKeyWords() {

    SysUser sysuser = (SysUser) session.get("sysUser");
    keyWordsDO.setCreatedId(sysuser.getUserId());
    keyWordsDO.setCreateDate(DateTimeUtils.getCalendarInstance().getTime());
    keyWordsDO.setModifieId(sysuser.getUserId());
    keyWordsDO.setModifyDate(DateTimeUtils.getCalendarInstance().getTime());
     keyWordsService.insertKeyWordsDO(keyWordsDO);



    keyWordsDO = new KeyWordsDO();
    return this.pageList();
  }

  /**
   * 查询关键词过滤信息
   * 
   * @param keyWordsId
   * @return keyWordsDO
   */
  public String selectByKeyWords() {
    Integer keyWordsId = keyWordsDO.getKeyWordsId();
    keyWordsDO = keyWordsService.findKeyWordsDOByPrimaryKey(keyWordsId);

    return "updateKeyWords";
  }

  /**
   * 修改关键词过滤信息
   * 
   * @param keyWordsDO
   * @return 受影响行数
   */
  public String updateKeyWords() {
     keyWordsService.updateKeyWordsDO(keyWordsDO);

    keyWordsDO = new KeyWordsDO();
    return this.pageList();
  }

  /**
   * 删除关键词过滤信息
   */

  public String deleteKeyWords() {
    Integer keyWordsId = keyWordsDO.getKeyWordsId();
   keyWordsService.deleteKeyWordsDOByPrimaryKey(keyWordsId);

    return this.pageList();
  }

  /**
   * 删除全部关键词过滤信息
   */

  public String deleteAllKeyWords() {

    keyWordsService.deleteAllKeyWords(keyWordsIds);
    return this.pageList();
  }

  public List<Integer> getKeyWordsIds() {
    return keyWordsIds;
  }

  public void setKeyWordsIds(List<Integer> keyWordsIds) {
    this.keyWordsIds = keyWordsIds;
  }

  public KeyWordsService getKeyWordsService() {
    return keyWordsService;
  }

  public void setKeyWordsService(KeyWordsService keyWordsService) {
    this.keyWordsService = keyWordsService;
  }

  public KeyWordsDO getKeyWordsDO() {
    return keyWordsDO;
  }

  public void setKeyWordsDO(KeyWordsDO keyWordsDO) {
    this.keyWordsDO = keyWordsDO;
  }

  public KeyWordsQuery getKeyWordsQuery() {
    return keyWordsQuery;
  }

  public void setKeyWordsQuery(KeyWordsQuery keyWordsQuery) {
    this.keyWordsQuery = keyWordsQuery;
  }

  @Override
public Page getPage() {
    return page;
  }

  @Override
public void setPage(Page page) {
    this.page = page;
  }


  @Override
  public Object getModel() {
    keyWordsDO = new KeyWordsDO();
    return keyWordsDO;
  }


}
