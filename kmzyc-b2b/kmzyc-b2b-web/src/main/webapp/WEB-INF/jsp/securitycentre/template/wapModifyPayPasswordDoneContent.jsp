<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

 
<!--内容区域-->
<div class="container">
    <ul class="steps">
        <li>
            <span class="step"><i class="icon-uniE650"></i></span>
            <span class="title">身份验证</span>
        </li>
        <li>
            <span class="step"><i class="icon-uniE64F"></i></span>
            <span class="title">设置支付密码</span>
        </li>
        <li class="active">
            <span class="step">OK</span>
            <span class="title">完成</span>
        </li>
    </ul>
    <div class="ui-tipbox ui-tipbox-success">
        <div class="ui-tipbox-icon"><i class="icon-uniE635"></i></div>
        <div class="ui-tipbox-content">
            <h3 class="ui-tipbox-title">支付密码设置成功</h3>
            <p class="ui-tipbox-explain">请牢记您设置的密码</p>
            <p class="ui-tipbox-explain"><a class="btn btn-default" href="/member/goWapMyHome.action" role="button">个人中心 >></a></p>
        </div>
    </div>
</div>
<!--内容区域 end-->
 