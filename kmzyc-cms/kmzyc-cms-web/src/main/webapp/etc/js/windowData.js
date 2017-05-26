 /** 删除数据信息  **/
    function deleteSelected(id){
    	var obj = document.getElementsByName(id);
        var count = 0;
        var obj_cehcked = null;
        // 遍历所有用户，找出被选中的用户
        for (var i = 0; i < obj.length; i++) {
            if (obj[i].checked) {
                count++;
                obj_cehcked = obj[i];
            }
        }
         if (count == 0) {
                alert("请选择要删除的数据。");
                return false;
         }else if(confirm('是否确认删除?')==true){
                  document.windowDataForm.action="/cms/cmsWindowDataAction_deleteDatas.action";
                  document.windowDataForm.submit();
         }
    }
   
    
    /** 单条删除客户等级信息  **/
    function  deleteByKey(id){
         if(confirm("是否确认删除? ")==true){
           var windowId = $("#windowId").val();
           var pageId = $("#pageId").val();
           var adminType = $("#adminType").val();
           location.href="/cms/cmsWindowDataAction_deleteData.action?dataId="+id+"&windowId="+windowId+"&pageId="+pageId+"&adminType="+adminType;
         }
    }
    
	    	
    	
    

        //弹出活动层
        function popUpViewPromotion() {
        	var windowId=$("#windowId").val();
			var pageId = $("#pageId").val();
			var adminType = $("#adminType").val();
            dialog("选择活动信息","iframe:/cms/viewPromotionAction_queryViewromotion.action?windowId="+windowId+"&pageId="+pageId+"&adminType="+adminType ,"900px","530px","iframe");
        }

        //弹出窗口层
        function popUpcmsWindow() {
        	var windowId=$("#windowId").val();
        	var pageId = $("#pageId").val();
        	var adminType = $("#adminType").val();
            dialog("选择窗口信息","iframe:/cms/cmsWindowAction_queryWindow.action?windowId="+windowId+"&pageId="+pageId+"&adminType="+adminType ,"900px","530px","iframe");
        }
        
      //弹出品牌窗口层
        function popUpprodBrand() {
        	var windowId=$("#windowId").val();
        	var pageId = $("#pageId").val();
        	var adminType = $("#adminType").val();
            dialog("选择品牌信息","iframe:/cms/prodBrandAction_queryProdBrand.action?windowId="+windowId+"&pageId="+pageId+"&adminType="+adminType ,"900px","530px","iframe");
        }
        
      //弹出类目窗口层
        function popUpcategory() {
        	var windowId=$("#windowId").val();
        	var pageId = $("#pageId").val();
        	var adminType = $("#adminType").val();
            dialog("选择类目信息","iframe:/cms/categoryAction_findAllCategory.action?windowId="+windowId+"&pageId="+pageId+"&adminType="+adminType ,"900px","530px","iframe");
        }
        
      //弹出排行榜类目窗口层
        function popUprankingList() {
        	var windowId=$("#windowId").val();
        	var pageId = $("#pageId").val();
        	var adminType = $("#adminType").val();
            dialog("选择类目信息","iframe:/cms/categoryAction_rankingListCategory.action?windowId="+windowId+"&pageId="+pageId+"&adminType="+adminType ,"900px","530px","iframe");
        }
        
      //弹出产品窗口层
        function popUpViewProductInfo() {
        	var windowId=$("#windowId").val();
        	var pageId = $("#pageId").val();
        	var adminType = $("#adminType").val();
            dialog("选择产品信息","iframe:/cms/viewProductInfoAction_queryViewProductInfo.action?windowId="+windowId+"&pageId="+pageId+"&adminType="+adminType ,"900px","530px","iframe");
        }
        //弹出抽奖窗口层
        function popUpLottery() {
        	var windowId=$("#windowId").val();
        	var pageId = $("#pageId").val();
        	var adminType = $("#adminType").val();
            dialog("选择抽奖活动","iframe:/cms/cmsLottery_queryForPage.action?windowId="+windowId+"&pageId="+pageId+"&adminType="+adminType ,"900px","530px","iframe");
        }
        //弹出抽奖品窗口层
        function popUpLotteryPrize() {
        	var windowId=$("#windowId").val();
        	var pageId = $("#pageId").val();
        	var adminType = $("#adminType").val();
            dialog("选择奖品","iframe:/cms/cmsLotteryPrize_queryForPage.action?windowId="+windowId+"&pageId="+pageId+"&adminType="+adminType ,"900px","530px","iframe");
        }
        //弹出自定义数据层
        function popUpUserdefine()
        {
        	var windowId=$("#windowId").val();
        	var pageId = $("#pageId").val();
        	var adminType = $("#adminType").val();
        	 window.location.href="/cms/cmsWindowDataAction_addUserDefineDate.action?windowId="+windowId+"&pageId="+pageId+"&adminType="+adminType;
        }
        
      //弹出咨询类别窗口层
        function popInformationType() {
        	var windowId=$("#windowId").val();
        	var pageId = $("#pageId").val();
        	var adminType = $("#adminType").val();
            dialog("选择咨询类别信息","iframe:/cms/InformationType_queryPopInformationType.action?windowId="+windowId+"&pageId="+pageId+"&adminType="+adminType ,"900px","530px","iframe");
        }
        
        //弹出活动商品层
        function popUpViewPromotionProduct() {
        	var windowId=$("#windowId").val();
			var pageId = $("#pageId").val();
			var adminType = $("#adminType").val();
            dialog("选择活动商品信息","iframe:/cms/viewPromotionProductAction_queryViewPromotionProduct.action?windowId="+windowId+"&pageId="+pageId+"&adminType="+adminType ,"900px","530px","iframe");
        }
      