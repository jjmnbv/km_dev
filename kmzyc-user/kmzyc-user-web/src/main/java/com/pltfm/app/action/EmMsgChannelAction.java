package com.pltfm.app.action;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.pltfm.app.service.EmMsgChannelService;
import com.pltfm.app.vobject.EmMsgChannel;

@Scope("prototype")
@Component(value = "emMsgChannelAction")
public class EmMsgChannelAction extends BaseAction {
  private List<EmMsgChannel> emMsgChannelList;
  @Resource(name = "emMsgChannelService")
  private EmMsgChannelService emMsgChannelService;
  private int emMsgChannelId;
  private EmMsgChannel emMsgChannel;

  private static Logger logger = LoggerFactory.getLogger(EmMsgChannelAction.class);

  public String queryEmMsgChannelList() {
    try {
      emMsgChannelList = emMsgChannelService.selectEmMsgChannelList();
    } catch (Exception e) {
      logger.error("通道列表错误：" + e.getMessage());
      return ERROR;
    }
    return SUCCESS;
  }


  public String updateEmMsgChannel() {
    try {
      int re = emMsgChannelService.updateEmMsgChannel(emMsgChannel);
      if (re != 1) {
        return ERROR;
      }
    } catch (Exception e) {
      logger.error("修改通道信息出错", e);
    }
    return SUCCESS;
  }

  public String goToEmMsgChannelAdd() {

    return SUCCESS;
  }

  public String addEmMsgChannel() {
    try {
      int re = emMsgChannelService.insertEmMsgChannel(emMsgChannel);
      if (re < 1) {
        return ERROR;
      }
    } catch (Exception e) {
      logger.error("添加通道信息出错", e);
    }
    return SUCCESS;
  }

  /**
   * 根据ID查通道信息
   * 
   * @return
   */
  public String queryEmMsgChannelDetail() {
    try {
      emMsgChannel = emMsgChannelService.findByIdEmMsgChannel(emMsgChannelId);
    } catch (Exception e) {
      logger.error("查询通道详情出错" + e.getMessage());
    }
    return SUCCESS;
  }

  public List<EmMsgChannel> getEmMsgChannelList() {
    return emMsgChannelList;
  }

  public void setEmMsgChannelList(List<EmMsgChannel> emMsgChannelList) {
    this.emMsgChannelList = emMsgChannelList;
  }

  public int getEmMsgChannelId() {
    return emMsgChannelId;
  }

  public void setEmMsgChannelId(int emMsgChannelId) {
    this.emMsgChannelId = emMsgChannelId;
  }


  public EmMsgChannel getEmMsgChannel() {
    return emMsgChannel;
  }


  public void setEmMsgChannel(EmMsgChannel emMsgChannel) {
    this.emMsgChannel = emMsgChannel;
  }



}
