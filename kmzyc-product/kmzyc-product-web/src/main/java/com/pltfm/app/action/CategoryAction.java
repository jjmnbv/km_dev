package com.pltfm.app.action;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.imageio.ImageIO;

import com.pltfm.app.vobject.CategoryPv;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.context.annotation.Scope;

import com.alibaba.fastjson.JSON;
import com.kmzyc.zkconfig.ConfigurationUtil;
import com.opensymphony.xwork2.Action;
import com.pltfm.app.enums.CategoryType;
import com.pltfm.app.maps.BusiCategoryConditionMap;
import com.pltfm.app.maps.CategoryTypeMap;
import com.pltfm.app.maps.ProductBrandMap;
import com.pltfm.app.service.CategoryService;
import com.pltfm.app.util.FileUploadUtils;
import com.pltfm.app.vobject.Category;
import com.pltfm.app.vobject.Message;
import com.kmzyc.commons.exception.ServiceException;

import net.sf.json.JSONArray;
import org.springframework.stereotype.Controller;


@Scope(value = "prototype")
@Controller("categoryAction")
public class CategoryAction extends BaseAction {

	private Category category;

	@Resource
	private CategoryService categoryService;

	private String categoryString;

	private String type;

	private String para;

	private File categoryFile;

	private String categoryFileWidth = ConfigurationUtil.getString("categoryFileWidth");

	private String categoryFileHeight = ConfigurationUtil.getString("categoryFileHeight");

	private String categoryFileSize = ConfigurationUtil.getString("categoryFileSize");

	private String imagePath = ConfigurationUtil.getString("categoryFilePreviewPath");

	private String defaultRebate;

	// 运营类目的Sql
	private String execSql;
	// sql关系文字说明
	private String sqlString;

	private String phyCategorys;

	private String copyCategoryIds;

	private String categorysDate;

	public String getCategorysDate() {
		return categorysDate;
	}

	public void setCategorysDate(String categorysDate) {
		this.categorysDate = categorysDate;
	}

	/**
	 * 返回结果
	 */
	private Message message = new Message();

	/**
	 * 产品类目列表
	 * 
	 * @return String 返回值
	 */
	public String queryCategoryList() {
		String resultInfo = "success";
        if (category == null) {
            category = new Category();
        }
        if ("phy".equals(type)) {
            category.setIsPhy(1);
        } else if ("busi".equals(type)) {
            category.setIsPhy(2);
        }

		try {
			List<Category> list = categoryService.queryCategoryList(category);
			categoryString = JSON.toJSONString(list);
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		return resultInfo;
	}

	/**
	 * 类目佣金设置
	 * 
	 * @return String 返回值
	 */
	public String queryCategoryRebateList() {
		String resultInfo = "success";
        if (category == null) {
            category = new Category();
        }
        if ("phy".equals(type)) {
            category.setIsPhy(1);
        } else if ("busi".equals(type)) {
            category.setIsPhy(2);
        }

		try {
			List<Category> list = categoryService.queryCategoryRebateList(category);
			categoryString = JSON.toJSONString(list);
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		return resultInfo;
	}

	public void saveCategoryRebateList() {
		try {
			categoryService.delRebateCategory();// 删除
			JSONArray jsonArr = JSONArray.fromObject(categorysDate);
			List<Category> listCategories = JSONArray.toList(jsonArr, Category.class);
			categoryService.addRebateCategory(listCategories);// 新增
            strWriteJson("success");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 产品类目信息
	 * 
	 * @return 返回值
	 */
	public String showCategory() {
        getRequest().setAttribute("categoryTypeMap", CategoryTypeMap.getMap());
		try {
			if (category == null) {
                return SUCCESS;
			}

            if (category.getCategoryId() != null) {
                category = categoryService.showCategory(category.getCategoryId());
                Long parentId = category.getParentId();
                if (parentId != null && parentId != 0) {
                    category.setParentName(categoryService.showCategory(parentId).getCategoryName());
                }
            } else if (category.getParentName() != null) {
                category.setParentName(java.net.URLDecoder.decode(category.getParentName(), "UTF-8"));
            }
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 限制图片上传的分辨率大小
	 *
	 * @return
	 */
	private boolean checkFile() {
		BufferedImage bi = null;
		if (categoryFile != null) {
			try {
				bi = ImageIO.read(categoryFile);
			} catch (Exception e) {
				logger.error("获取目录图片信息出错" + e.getMessage(), e);
			}
			if (bi == null) {
				message.setContent("请选择100*100像素的图片。");
				return true;
			}
			if (bi.getWidth() != Integer.parseInt(categoryFileWidth)
					|| bi.getHeight() != Integer.parseInt(categoryFileHeight)) {
				message.setContent("请选择100*100像素的图片。");
				return true;
			}
			if (categoryFile.length() > Long.parseLong(categoryFileSize)) {
				message.setContent("请选择小于50KB的图片。");
				return true;
			}
		}
		return false;
	}

	/**
	 * 保存类目
	 * 
	 * @return String @exception
	 */
	public String saveCategory() throws Exception {
		String msg = "fail";
		Map<Object, Object> json = new HashMap<Object, Object>();
		try {
			if (category == null) {
				return ERROR;
			}

            if (category.getIsPhy().equals(CategoryType.PHYSICS.getKey())) {
                type = "phy";
            } else if (category.getIsPhy().equals(CategoryType.BUISNESS.getKey())) {
                type = "busi";
                if (checkFile()) {
                    json.put("status", msg);
                    json.put("msg", message.getContent());
                    writeJson(json);
                    return null;
                }

                if (categoryFile != null && StringUtils.isNotEmpty(category.getFileName())) {
                    String fileName = category.getFileName();
                    // 文件后缀名
                    String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
                    // 文件名
                    fileName = DateFormatUtils.format(new java.util.Date(), "yyyyMMddHHmmssSSS") + "_"
                            + new Random().nextInt(1000) + "." + fileExt;
                    // 绝对路径，上传时使用
                    String savePath = FileUploadUtils.createSavePath("category");
                    // 相对路径，预览时使用
                    String filePath = savePath.substring(ConfigurationUtil.getString("pictureUploadPath").length());
                    category.setFilePath(filePath + fileName);// 保存到数据库
                    File destFile = new File(savePath, fileName);
                    FileUtils.copyFile(categoryFile, destFile);
                    // 删除临时文件
                    categoryFile.delete();
                }
            }

            category.setModifUser(getLoginUserId());
            if (category.getCategoryId() != null) {
                if (category.getIsPhy().equals(CategoryType.BUISNESS.getKey())) {
                    if (categoryFile != null && StringUtils.isNotEmpty(category.getFileName())) {
                        categoryService.delCategoryFile(category.getCategoryId());
                    }
                }
                categoryService.updateCategory(category);
            } else {
                categoryService.addCategory(category);
            }
            message.setContent("操作成功！");
            msg = "success";
		} catch (Exception e) {
			e.printStackTrace();
			message.setContent("操作失败！");
			msg = "fail";
		}
		category = new Category();
		message.setModule("保存类目");
		message.setCode(0);
		json.put("status", msg);
		json.put("msg", message.getContent());
		writeJson(json);
		return null;
	}

	/**
	 * 删除类目
	 * 
	 * @return String @exception
	 */
	public String deleteCategory() {
		try {
			if (category != null && category.getCategoryId() != null) {
				if (category.getIsPhy().equals(CategoryType.PHYSICS.getKey())) {
					type = "phy";
				} else if (category.getIsPhy().equals(CategoryType.BUISNESS.getKey())) {
					type = "busi";
				}

				// 新的删除
				String notDeleteCategoryName = categoryService.delCategory(category.getCategoryId());
				if (StringUtils.isNotEmpty(notDeleteCategoryName)) {
					message.setContent(notDeleteCategoryName + "与产品有关联，不能被删除！");
				} else {
					message.setContent("操作成功！");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			message.setContent("操作失败，系统错误，请联系管理员！");
		}
		category = new Category();
		return this.queryCategoryList();
	}

	public String checkRepeatName() {
		String message = "no";
		try {
            if (categoryService.queryRepeatName(category)) {
				message = "yes";
			}
            strWriteJson(message);
		} catch (Exception e) {
			logger.error("checkRepeatName失败，", e);
            strWriteJson("error");
		}

		return null;
	}

	/**
	 * 运营类目配置搜索语法
	 * 
	 * @return
	 */
	public String gotoEditCondition() {
		try {
			category = categoryService.showCategory(category.getCategoryId());

			Category c = new Category();
			c.setIsPhy(1);
			List<Category> list = categoryService.queryCategoryList(c);
			super.getRequest().setAttribute("categoryMap", JSON.toJSONString(list));
		} catch (Exception e) {
			e.printStackTrace();
		}
		super.getRequest().setAttribute("conditionMap", BusiCategoryConditionMap.getMap());
		super.getRequest().setAttribute("brandMap", ProductBrandMap.getMap());

		return Action.SUCCESS;
	}

	public String editCondition() {
		category.setExecSql(execSql);
		category.setSqlString(sqlString);
		String msg = "";
		try {
			categoryService.updateCategory(category);
			msg = "操作成功！";
		} catch (Exception e) {
			msg = "系统发生错误，操作失败！";
			e.printStackTrace();
		}
		super.getRequest().setAttribute("rtnMsg", msg);
		return gotoEditCondition();
	}

	public String gotoCopyCategorys() {
		try {
			phyCategorys = JSON.toJSONString(categoryService.findSomePhyCategories());
		} catch (Exception e) {
            logger.error("gotoCopyCategorys失败，", e);
		}
		return Action.SUCCESS;
	}

	public String saveCopyCategorys() {
		try {
            String[] str = copyCategoryIds.split(",");
            Long[] copyCategoryId = new Long[str.length];
            for (int i = 0; i < str.length; i++) {
                copyCategoryId[i] = Long.valueOf(str[i]);
            }

			int result = categoryService.copyCategories(copyCategoryId);
			if (result == 1) {
                strWriteJson("success");
			}
		} catch (Exception e) {
            logger.error("查询PV默认比例失败，", e);
		}
		return null;
	}

    //查询PV默认比例
    public String queryPVCategoryList() {
        try {
            List<CategoryPv> list = categoryService.queryPVCategoryList();
            categoryString = JSON.toJSONString(list);
        } catch (Exception e) {
            logger.error("查询PV默认比例失败，", e);
            return ERROR;
        }
        return SUCCESS;
    }

    //保存PV比例配置
    public void savePVCategory() {
        try {
            JSONArray jsonArr = JSONArray.fromObject(categorysDate);
            List<Category> categoryList = JSONArray.toList(jsonArr, Category.class);
            categoryList.stream().forEach(category -> {
                category.setCreateTime(new Date());
                category.setModifUser(getLoginUserId());//充当createUser
            });
            categoryService.modifyPvCategory(categoryList);// 新增
            strWriteJson("success");
        } catch (Exception e) {
            logger.error("保存PV比例配置失败，", e);
        }
    }

	public String gotoAddFirstLevelCategory() {
		return Action.SUCCESS;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getCategoryString() {
		return categoryString;
	}

	public void setCategoryString(String categoryString) {
		this.categoryString = categoryString;
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPara() {
		return para;
	}

	public void setPara(String para) {
		this.para = para;
	}

	public String getExecSql() {
		return execSql;
	}

	public void setExecSql(String execSql) {
		this.execSql = execSql;
	}

	public String getSqlString() {
		return sqlString;
	}

	public void setSqlString(String sqlString) {
		this.sqlString = sqlString;
	}

	public String getPhyCategorys() {
		return phyCategorys;
	}

	public void setPhyCategorys(String phyCategorys) {
		this.phyCategorys = phyCategorys;
	}

	public String getCopyCategoryIds() {
		return copyCategoryIds;
	}

	public void setCopyCategoryIds(String copyCategoryIds) {
		this.copyCategoryIds = copyCategoryIds;
	}

	public File getCategoryFile() {
		return categoryFile;
	}

	public void setCategoryFile(File categoryFile) {
		this.categoryFile = categoryFile;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
}
