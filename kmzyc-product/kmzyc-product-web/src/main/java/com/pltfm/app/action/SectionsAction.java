package com.pltfm.app.action;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSON;
import com.kmzyc.commons.page.Page;
import com.opensymphony.xwork2.ModelDriven;
import com.pltfm.app.bean.ResultMessage;
import com.pltfm.app.service.ProductService;
import com.pltfm.app.service.SectionsDetailService;
import com.pltfm.app.service.SectionsHotSellService;
import com.pltfm.app.service.SectionsService;
import com.pltfm.app.vobject.Message;
import com.pltfm.app.vobject.Product;
import com.pltfm.app.vobject.Sections;
import com.pltfm.app.vobject.SectionsDetail;

/**
 * 栏目管理Action类
 *
 * @author humy
 * @since 2013-7-10
 */
@Controller(value = "sectionsAction")
@Scope(value = "prototype")
public class SectionsAction extends BaseAction implements ModelDriven {
    /**
     * 栏目管理信息Model
     */
    private Sections sections;

    private Sections sectionsForSelectPara;

    private String[] productIds;

    private String[] sortno;

    private String[] sectionsDetailIds;

    private String msg;
    /**
     * 栏目管理业务逻辑接口
     */
    @Resource(name = "sectionsService")
    private SectionsService sectionsService;

    /**
     * 栏目明细管理业务逻辑接口
     */
    @Resource
    private SectionsDetailService sectionsDetailService;

    @Resource
    private ProductService productService;

    @Resource
    private SectionsHotSellService sectionsHotSellService;
    /**
     * 分页类
     */
    private Page page;

    private Product product;

    /**
     * 接受删除id
     */
    private String delSec_id;
    /**
     * 接受删除id
     */
    private String[] delSec_ids;

    /**
     * 要修改的section_id
     */
    private String pre_sectionId;

    /**
     * 预览状态
     */
    private String viewType;

    /**
     * 栏目明细
     */
    private SectionsDetail sectionsDetail;

    /**
     * 上传文件
     */
    private File logoFile;

    /**
     * 返回结果
     */
    private Message message = new Message();

    /**
     * 原来的图片字段
     */
    private String old_image;

    /**
     * 子 栏目sectionid
     */
    private String sectionDetailId;

    /**
     * 传递的channel渠道类型
     */
    private String sectionsChannle;

    /**
     * 栏目标识
     */
    private String identifi;

    private String[] skuIds;

    /**
     * 分页列表
     *
     * @return 返回值
     */
    public String querySectionsList() {
        try {
            if (page == null) page = new Page();
            if (sectionsForSelectPara == null) sectionsForSelectPara = new Sections();
            sectionsService.searchPage(page, sectionsForSelectPara);
        } catch (Exception e) {
            e.printStackTrace();
            return ERROR;
        }

        return SUCCESS;
    }

    /**
     * 新增栏目
     *
     * @param
     * @return String
     * @throws
     */
    public String saveSections() throws Exception {
        try {
            sections.setCreateTime(new Date());
            sectionsService.addSections(sections);
            this.setMsg("栏目添加成功!");

        } catch (Exception e) {
            e.printStackTrace();
            return ERROR;
        }
        //返回的时候清空条件
        sections = new Sections();
        return querySectionsList();
    }

    public String updateSections() throws Exception {
        try {
            sectionsService.updateSections(sections);
            this.setMsg("栏目修改成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return ERROR;
        }
        //返回的时候清空条件
        sections = new Sections();
        return querySectionsList();
    }

    /**
     * 删除栏目
     *
     * @param
     * @return String
     * @throws
     */
    public String delSections() throws Exception {
        try {
            ResultMessage rmsg = sectionsService.delSections(delSec_id);
            this.setMsg(rmsg.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            this.setMsg(e.getMessage());
            return querySectionsList();
        }
        return querySectionsList();
    }

    /**
     * 预览栏目(修改)
     *
     * @param
     * @return String
     * @throws
     */
    public String updateSection() throws Exception {
        try {
            sectionsService.updateSections(sections);
            this.setMsg("栏目修改成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return ERROR;
        }
        this.setViewType("edit");
        return SUCCESS;
    }

    /**
     * 标记查看状态为新增
     */
    public String gotoSectionsAdd() {
        if (viewType.equals("add")) {
            return SUCCESS;
        } else if (viewType.equals("edit")) {
            setCheckedId(sections.getSectionsId());
            pre_sectionId = sections.getSectionsId().toString();
            sections = sectionsService.preSectionsById(sections.getSectionsId());
        }
        return SUCCESS;
    }

    /**
     * 查看栏目明细列表
     */
    public String querySectionsDetailList() {
        try {
            setCheckedId(sections.getSectionsId());
            pre_sectionId = sections.getSectionsId().toString();
            if (page == null) {
                page = new Page();
                sectionsDetail = new SectionsDetail();
            }
            if (sections.getSectionsId() != null) {
                if (sectionsDetail == null) {
                    sectionsDetail = new SectionsDetail();
                }
                sectionsDetail.setSectionsId(sections.getSectionsId());
            }
            sectionsDetailService.searchPage(page, sectionsDetail);
            setProductBrandMap();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SUCCESS;
    }

    /**
     * 新增栏目的初始化
     */
    public String gotoSectionsDetailAdd() {
        if (viewType.equals("add")) {
            if (sectionsDetail == null) {
                sections = sectionsService.preSectionsById(Long.valueOf(pre_sectionId));
                sectionsDetail = new SectionsDetail();
                sectionsDetail.setSectionsId(Long.parseLong(pre_sectionId));
            }
        } else if (viewType.equals("edit")) {
            //查询需要预览的实体
            if (pre_sectionId != null) {
                sectionsDetail = sectionsDetailService.querySectionsDetailById(pre_sectionId);
            }
        }
        return SUCCESS;
    }

    /**
     * 新增栏目明细
     */
    public String saveSectionsDetail() {
        try {
            //如果是新增
            List<SectionsDetail> add_detailList = new ArrayList<SectionsDetail>();
            List<SectionsDetail> update_detailList = new ArrayList<SectionsDetail>();
            SectionsDetail sectionsDetail = null;
            if (sectionsDetailIds == null) {
                this.setMsg("系统问题，保存失败!");
                return querySectionsDetailList();
            }
            int count = sectionsDetailIds.length;
            for (int i = 0; i < count; i++) {
                sectionsDetail = new SectionsDetail();
                if (sortno != null && StringUtils.isNotBlank(sortno[i]))
                    sectionsDetail.setSortno(Short.valueOf(sortno[i]));
                if (Long.valueOf(sectionsDetailIds[i]).intValue() == 0) {
                    sectionsDetail.setSectionsId(Long.valueOf(pre_sectionId));
                    sectionsDetail.setProductId(Long.valueOf(productIds[i]));
                    sectionsDetail.setSkuId(Long.valueOf(skuIds[i]));
                    add_detailList.add(sectionsDetail);
                } else {
                    sectionsDetail.setSectionsDetailId(Long.valueOf(sectionsDetailIds[i]));
                    update_detailList.add(sectionsDetail);
                }
            }

            ResultMessage message = new ResultMessage();
            message.setIsSuccess(true);
            if (add_detailList != null && !add_detailList.isEmpty())
                message = sectionsDetailService.batchInsertSectionsDetail(add_detailList);
            if (!message.getIsSuccess()) {
                this.setMsg(message.getMessage());
                return querySectionsDetailList();
            }
            if (update_detailList != null && !update_detailList.isEmpty())
                message = sectionsDetailService.batchUpdateSectionsDetail(update_detailList);
            if ("REXIAO".equals(this.identifi)) {
                sectionsHotSellService.injectHotSellB2BProduct();
            }
            if ("ZYC-REXIAO".equals(this.identifi)) {
                sectionsHotSellService.injectHotSellZYCProduct();
            }
            if (!message.getIsSuccess()) {
                this.setMsg(message.getMessage());
                return querySectionsDetailList();
            }
            this.setMsg("保存成功!");
//		}
//		else if (viewType.equals("edit")) //编辑
//		{
//				 if (sectionsDetail !=null)
//				 {
//					 //图片修改了
//					 if (logoFile!=null)
//					 {
//						 String[] savePath = this.createSavePath();
//							int i = sectionsDetail.getImage().lastIndexOf("\\");
//							String a= sectionsDetail.getImage().substring(i+1);
//							//System.out.println(a);
//							File deskFile =new File(savePath[0],a);
//							Thumbnails.of(logoFile).size(50, 50).toFile(deskFile);
//							String path = savePath[1] + a;
//							path = path.replace(File.separatorChar, '/');
//							sectionsDetail.setImage(path); 
//					 }
//					 //图片没修改
//					 else
//					 {
//						 sectionsDetail.setImage(old_image);
//					 }
//					 sectionsDetailService.updateSectionsDetailById(sectionsDetail);
//				 }
//			}

        } catch (Exception e) {
            e.printStackTrace();
            return querySectionsDetailList();
        }
        message.setCode(0);
        message.setModule("保存明细栏目");
        return querySectionsDetailList();
    }

    /**
     * 创建图片的保存路径
     *
     * @return
     */
    private String[] createSavePath() {

        String[] s = new String[2];
//		System.out.println("--- BEGIN DOPOST ---");
        HttpSession session = ServletActionContext.getRequest().getSession();

        String uploadDir = "upload" + File.separatorChar + "sectionsImage"
                + File.separatorChar;

        String autoCreatedDateDirByParttern = "yyyy" + File.separatorChar
                + "MM" + File.separatorChar + "dd" + File.separatorChar;
        String autoCreatedDateDir = DateFormatUtils.format(
                new java.util.Date(), autoCreatedDateDirByParttern);
        String ctxDir = session.getServletContext().getRealPath(
                String.valueOf(File.separatorChar));
        if (!ctxDir.endsWith(String.valueOf(File.separatorChar))) {
            ctxDir = ctxDir + File.separatorChar;
        }
        File savePath = new File(ctxDir + uploadDir + autoCreatedDateDir);
        if (!savePath.exists()) {
            savePath.mkdirs();
        }
//		System.out.println(savePath.getName() + "======");
        String saveDirectory = ctxDir + uploadDir + autoCreatedDateDir;
        s[0] = saveDirectory;
        s[1] = File.separatorChar + uploadDir + autoCreatedDateDir;
        return s;
    }

    /**
     * 删除明细类目
     */
    public String delSectionsDetail() {
        Product prodTmp = null;
        Map jsonMap = new HashMap();
        try {
            List<Long> sectionsDetailIdList = new ArrayList<Long>();
            if (delSec_ids == null || delSec_ids.length == 0) {
                jsonMap.put("result", true);
                jsonMap.put("msg", "删除成功!");
                strWriteJson(JSON.toJSONString(jsonMap));
                return null;
            }
            for (String delId : delSec_ids) {
                sectionsDetailIdList.add(Long.valueOf(delId));
            }
            ResultMessage rmsg = sectionsDetailService.batchDeleteSectionsDetail(sectionsDetailIdList);
            jsonMap.put("result", rmsg.getIsSuccess());
            jsonMap.put("msg", rmsg.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            jsonMap.put("result", false);
            jsonMap.put("msg", "系统错误，删除栏目明细失败!");
            return null;
        }
        strWriteJson(JSON.toJSONString(jsonMap));
        return null;
    }

    /**
     * 对栏目明细的排序
     */
    public String saveSectionsDetailSortno() {
        //排序号
        String stno[] = delSec_id.split(",");
        //明细
        String sectionsDetai_id[] = sectionDetailId.split(",");
        sectionsDetailService.updateSectionDetailByStno(stno, sectionsDetai_id);
        return this.querySectionsDetailList();
    }


    public String findProductForSections() {
        try {
            sections = sectionsService.preSectionsById(Long.valueOf(pre_sectionId));
            if (page == null) page = new Page();
            if (product == null) product = new Product();
            //需要按SKU查询，要改
            productService.searchPage(page, product, "sections");
            //大类
            getBcategoryList(Long.valueOf(0));
            //中类
            if (product.getMCategoryId() != null && product.getMCategoryId().intValue() != 0) {
                getMcategoryList(product.getBCategoryId());
            }
            //小类
            if (product.getCategoryId() != null && product.getCategoryId().intValue() != 0) {
                getScategoryList(product.getMCategoryId());
            }
            setProductBrandMap();
            setProductStatusMap();
        } catch (Exception e) {
            e.printStackTrace();
            return ERROR;
        }
        return SUCCESS;
    }

    public String findProductSkuForSections() {
        try {
            sections = sectionsService.preSectionsById(Long.valueOf(pre_sectionId));
            if (page == null) {
                page = new Page();
            }
            if (product == null) {
                product = new Product();
            }

            //SKU查询
            productService.searchSkuPage(page, product, "sections", getLoginUserId());
            //大类
            getBcategoryList(Long.valueOf(0));
            //中类
            if (product.getMCategoryId() != null && product.getMCategoryId().intValue() != 0) {
                getMcategoryList(product.getBCategoryId());
            }
            //小类
            if (product.getCategoryId() != null && product.getCategoryId().intValue() != 0) {
                getScategoryList(product.getMCategoryId());
            }
            setProductBrandMap();
            setProductStatusMap();
        } catch (Exception e) {
            e.printStackTrace();
            return ERROR;
        }

        return SUCCESS;
    }

    //栏目code的校验
    public String checkSectionsRpeat() throws Exception {
        int result = sectionsService.checkSectionsRpeat(delSec_id);
        writeJson(result);
        return null;
    }

    //检查栏目名重复
    public String checkSectionsName() {
        PrintWriter out = null;
        Map jsonMap = new HashMap();
        try {
            ResultMessage result = sectionsService.checkSectionsName(super.decoder(getRequest().getParameter("name")));
            jsonMap.put("result", result.getIsSuccess());
            out = super.getPrintWriter();
            out.print(JSON.toJSONString(jsonMap));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.flush();
                out.close();
            }
        }

        return null;
    }

    //检查栏目标识重复
    public String checkIdentification() {
        PrintWriter out = null;
        Map jsonMap = new HashMap();
        try {
            ResultMessage result = sectionsService.checkIdentification(super.decoder(getRequest().getParameter("identification")));
            jsonMap.put("result", result.getIsSuccess());
            out = super.getPrintWriter();
            out.print(JSON.toJSONString(jsonMap));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.flush();
                out.close();
            }
        }

        return null;
    }

    //检查栏目名重复
    public String checkSectionsNameByModify() {
        PrintWriter out = null;
        Map jsonMap = new HashMap();
        try {
            ResultMessage result = sectionsService.checkSectionsNameByModify(super.decoder(getRequest().getParameter("name"))
                    , Long.valueOf(getRequest().getParameter("id")));

            jsonMap.put("result", result.getIsSuccess());
            out = super.getPrintWriter();
            out.print(JSON.toJSONString(jsonMap));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.flush();
                out.close();
            }
        }

        return null;
    }

    //检查栏目标识重复
    public String checkIdentificationByModify() {
        PrintWriter out = null;
        Map jsonMap = new HashMap();
        try {
            ResultMessage result = sectionsService.checkIdentificationByModify(super.decoder(getRequest().getParameter("identification"))
                    , Long.valueOf(getRequest().getParameter("id")));

            jsonMap.put("result", result.getIsSuccess());
            out = super.getPrintWriter();
            out.print(JSON.toJSONString(jsonMap));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.flush();
                out.close();
            }
        }

        return null;
    }

    @Override
    public Object getModel() {
        sections = new Sections();
        return sections;
    }

    public Sections getSections() {
        return sections;
    }

    public void setSections(Sections sections) {
        this.sections = sections;
    }

    @Override
    public Page getPage() {
        return page;
    }

    @Override
    public void setPage(Page page) {
        this.page = page;
    }

    public String getDelSec_id() {
        return delSec_id;
    }

    public void setDelSec_id(String delSec_id) {
        this.delSec_id = delSec_id;
    }

    public String getPre_sectionId() {
        return pre_sectionId;
    }

    public void setPre_sectionId(String pre_sectionId) {
        this.pre_sectionId = pre_sectionId;
    }

    public String getViewType() {
        return viewType;
    }

    public void setViewType(String viewType) {
        this.viewType = viewType;
    }

    public SectionsDetail getSectionsDetail() {
        return sectionsDetail;
    }

    public void setSectionsDetail(SectionsDetail sectionsDetail) {
        this.sectionsDetail = sectionsDetail;
    }

    public File getLogoFile() {
        return logoFile;
    }

    public void setLogoFile(File logoFile) {
        this.logoFile = logoFile;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public String getOld_image() {
        return old_image;
    }

    public void setOld_image(String old_image) {
        this.old_image = old_image;
    }

    public String getSectionDetailId() {
        return sectionDetailId;
    }

    public void setSectionDetailId(String sectionDetailId) {
        this.sectionDetailId = sectionDetailId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getSectionsChannle() {
        return sectionsChannle;
    }

    public void setSectionsChannle(String sectionsChannle) {
        this.sectionsChannle = sectionsChannle;
    }

    public String[] getProductIds() {
        return productIds;
    }

    public void setProductIds(String[] productIds) {
        this.productIds = productIds;
    }

    public String[] getSortno() {
        return sortno;
    }

    public void setSortno(String[] sortno) {
        this.sortno = sortno;
    }

    public String[] getDelSec_ids() {
        return delSec_ids;
    }

    public void setDelSec_ids(String[] delSec_ids) {
        this.delSec_ids = delSec_ids;
    }

    public String[] getSectionsDetailIds() {
        return sectionsDetailIds;
    }

    public void setSectionsDetailIds(String[] sectionsDetailIds) {
        this.sectionsDetailIds = sectionsDetailIds;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Sections getSectionsForSelectPara() {
        return sectionsForSelectPara;
    }

    public void setSectionsForSelectPara(Sections sectionsForSelectPara) {
        this.sectionsForSelectPara = sectionsForSelectPara;
    }

    public String getIdentifi() {
        return identifi;
    }

    public void setIdentifi(String identifi) {
        this.identifi = identifi;
    }

    public String[] getSkuIds() {
        return skuIds;
    }

    public void setSkuIds(String[] skuIds) {
        this.skuIds = skuIds;
    }

}
