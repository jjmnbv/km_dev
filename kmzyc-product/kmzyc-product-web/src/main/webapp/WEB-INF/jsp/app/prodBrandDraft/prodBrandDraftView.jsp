<%@page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>查看品牌</title>
    <link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<!--    <link href="/etc/css/addproduct.css" type="text/css" rel="stylesheet">-->
    <link rel="stylesheet" href="/kindeditor/plugins/code/prettify.css"/>
    <link rel="stylesheet" href="/kindeditor/themes/default/default.css"/>
    <Script language="JavaScript" src="/etc/js/Form.js" type="text/javascript"></Script>
    <link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="/etc/js/jquery-1.8.3.js"></script>
    <script language='JavaScript' src='/etc/js/artDialog4.1.7/artDialog.js?skin=default' type='text/javascript'></script>
    <script language='JavaScript' src='/etc/js/artDialog4.1.7/plugins/iframeTools.source.js' type='text/javascript'></script>
</head>
<body>
<!-- 导航栏 -->
<s:set name="parent_name" value="'供应商管理'" scope="request"/>
<s:set name="name" value="'供应商品牌审核列表'" scope="request"/>
<s:set name="son_name" value="'审核'" scope="request"/>
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>

<s:form action="/basedata/saveProdBrand.action" method="POST"  namespace='/basedata'
        onsubmit="return Validator.Validate(this,2)">
    <!-- 数据编辑区域 -->
    <table width="95%" class="edit_table" align="center" cellpadding="3" cellspacing="0"
           border="1" bordercolor="#C7D3E2" style="border-collapse: collapse;font-size:12px;">
        <s:if test="rtnMessage != null">
            <tr>
                <td colspan="2" align="center">
                    <font color="red"><s:property value='rtnMessage'/></font>
                </td>
            </tr>
        </s:if>
        <tr>
            <th colspan="2" align="left" class="edit_title">基本信息</th>
        </tr>
        <tr>
            <th align="right" class="eidt_rowTitle">品牌名称：</th>
            <td>
                <s:property value='prodBrandDraft.brandName'/>
            </td>
        </tr>
        <tr>
            <th align="right" class="eidt_rowTitle">国籍：</th>
            <td>
                <s:property value='prodBrandDraft.nation'/>
            </td>
        </tr>
        <tr>
            <th width="20%" align="right" class="eidt_rowTitle">英文名称：</th>
            <td width="80%">
                <s:property value='prodBrandDraft.engName'/>
            </td>
        </tr>

        <tr>
            <th width="20%" align="right" class="eidt_rowTitle">中文拼音：</th>
            <td width="80%">
                <s:property value='prodBrandDraft.chnSpell'/>
            </td>
        </tr>
        <tr>
            <th align="right" class="eidt_rowTitle">Logo图片：</th>
            <td>
                <s:if test="prodBrandDraft.logoPath == null">
                    暂无图片
                </s:if>
                <s:else>
                    <a href="<s:property value="path"/><s:property value='prodBrandDraft.logoPath'/>" target="_blank">
                        <img width="142" height="50"
                             src="<s:property value="path"/><s:property value='prodBrandDraft.logoPath'/>"/>
                    </a>
                </s:else>
            </td>
        </tr>
    </table>
</s:form>
    <!-- 底部 按钮条 -->
    <table width="98%" align="center" class="edit_bottom" height="30" border="0"
           cellpadding="0" cellspacing="0" style="font-size:12px;">
        <tr>
            <td align="center">
                <s:if test="prodBrandDraft.status == 1">
                    <input type="button" value="审核通过" class="btn-custom btnStyle " onClick="passProdBrandDraft(this)">
                    <input type="button" value="审核拒绝" class="btn-custom btnStyle " onClick="refuseProdBrandDraft(this)">
                </s:if>
                <input type="button" class="backBtn" onClick="gotoList()"/>
            </td>
            <td width="20%" align="center"></td>
        </tr>
    </table>
    <br><br>

    <div id="reason" style="display: none;">
        <textarea id="reasonArea" rows="10" cols="100" style="resize: none;" maxlength="100"></textarea>
    </div>

<script language="JavaScript">
    function gotoList() {
        location.href = "/app/showProdBrandDraftList.action";
    }

    function passProdBrandDraft(obj) {
        $(obj).attr("disabled","disabled");
        $.ajax({
            async:false,
            url:'/app/passProdBrandDraft.action',
            type:"post",
            dataType:'json',
            data:{"prodBrandDraft.brandName":"${prodBrandDraft.brandName}",
                    "prodBrandDraft.brandId":"${prodBrandDraft.brandId}"},
            error:function(){
                $(obj).removeAttr('disabled');
                alert('请求失败，请稍后重试或与管理员联系！')
            },
            success:function(data){
                alert(data.message);
                if (data.isSuccess) {
                    gotoList();
                } else {
                    $(obj).removeAttr('disabled');
                }
            }
        });
    }

    /**
     * 审核拒绝
     * @param value 品牌id
     */
    function refuseProdBrandDraft(obj){
        if(confirm('确定此品牌审核未通过吗？')){
            $(obj).attr("disabled","disabled");
            var dia = art.dialog({
                title:'请填写品牌审核未通过的原因',
                content: $("#reason").html(),
                drag:true,
                lock:true,
                ok: function () {
                    var reason = $.trim($("#reasonArea").val());
                    if (reason == "" || reason == undefined) {
                        alert('请填写品牌审核未通过的原因！')
                        return false;
                    }

                    this.close();
                    $.ajax({
                        type: "POST",
                        dataType:'json',
                        url:'/app/refuseProdBrandDraft.action',
                        data: {"prodBrandDraft.brandId": "${prodBrandDraft.brandId}",
                            "prodBrandDraft.reasons": reason},
                        error:function(){
                            $(obj).removeAttr('disabled');
                            alert('请求失败，请稍后重试或与管理员联系！')
                        },
                        success:function(data){
                            alert(data.message);
                            if (data.isSuccess) {
                                gotoList();
                            } else {
                                $(obj).removeAttr('disabled');
                            }
                        }
                    });
                },
                cancelVal: '关闭',
                cancel: true
            });
        }
    }
</script>
</body>
</html>