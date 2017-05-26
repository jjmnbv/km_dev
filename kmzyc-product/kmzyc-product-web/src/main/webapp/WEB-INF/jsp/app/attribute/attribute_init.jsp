<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>产品属性</title>
<link href="/etc/css/style_sys.css" type="text/css" rel="stylesheet">
<Script language="JavaScript" src="/etc/js/Form.js" type="text/javascript"></Script>
 
<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
<script src="/etc/js/jquery-latest.pack.js"></script>
<script src="/etc/js/dialog.js"></script>
<Script src="/etc/js/97dater/WdatePicker.js"></Script>
<script type="text/javascript" src="../../../../etc/js/jquery-1.8.3.js"></script></head>
<body>
  <table width="95%" class="tableInput1" align="center" cellpadding="3" cellspacing="0" border="1" bordercolor="#C7D3E2" style="border-collapse: collapse;font-size:12px;">
       <tr> <th colspan="2" align="left" class="modeltitle">产品属性</th></tr>
        <!-- 动态属性 -->
        <tbody id="createAtt">
        </tbody>
        <!-- 自定义属性 -->
        <tbody >
        	<tr> 
		      <th colspan="2" align="left" class="modeltitle">
		          <INPUT id="addAtt" class="btngreen" TYPE="button" value="添加属性">
		          
		          <INPUT id="attValueCheck" class="btngreen" TYPE="button" value="数据校验">
		      </th>
	        </tr>
        </tbody>
        <tbody id= "custom">
        </tbody>
    </table>
    <script type="text/javascript">

        //ajax获取json数据
        	$.ajax( {
        		url : "../product/attribute_view.action?"+"&_dc="+new Date().getTime(),
        		type : "get",
        		dataType : "json",
        		cache: false,
        		success : generateHtml
        	});

    	    //添加自定义属性
    	    $("#addAtt").click(function () {
    	        var _custom = $("#custom");
    	        var _customhtml = "";
    	        _customhtml += "<tr>";
    	        _customhtml += "<td align='left' width='30%'><span>属性名:</span> <input name='CustomName' type='text'/>";
    	        _customhtml += "</td>";
    	        _customhtml += "<td align='left' width='70%'><span>属性值:</span> <input name='CustomValue' type='text'/>";
    	        _customhtml += "<a href='#' onclick='delAttCustom(this);'>&nbsp;删除&nbsp;</a></td>";
    	        _customhtml += "</tr>";
    	        _custom.append(_customhtml);
    	    });
        
        function delAttCustom(obj){
        	$(obj).parent().parent().remove();
        }
        
        //生成html表单数据
        function generateHtml(json){
        	
        var _data = json;
        var _append = $("#createAtt");
        var _html = "";
        for (var i = 0; i < _data.length; i++) {
            var _displayName = _data[i].name;
            var _fieldName = _data[i].id;

            var _valueLength = 64;//输入框最大输入长度
            var _inputType = _data[i].inputType;
            var _isRequire = _data[i].isReq;
          
            _html += "<tr><td align='right'>" + (_isRequire == "Y" ? "<span style='color:red'>(*)</span>" : "") + _displayName + "：</td><td align='left'>";
            switch (_data[i].inputType) {
                //处理单选框
                case "radio":
                    if(_data[i].defaultValue.length < 0){
                    	break;
                    }
                    else{
                    	//选中的值
                    	_dv = _data[i].checkValue[0].value
                        for (var j = 0; j < _data[i].defaultValue.length; j++) {
                           //属性值
                        	_defayultValue = _data[i].defaultValue[j].value;
                        	
                            _html += "<input type='radio' " + (_dv == _defayultValue ? "checked" : "") + " value='" + _defayultValue + "' name='" + _fieldName + "' id='" + _fieldName + "_" + j + "' isRequired='" + _isRequire + "' maxLength='" + _valueLength + "' label='" + _displayName + "' /><label for='" + _fieldName + "_" + j + "'>" + _defayultValue + "</label>"
                        }
                    }
                    break;
                //处理复选框
                case "list":
                    if(_data[i].defaultValue.length < 0){
                    	break;
                    }
                    else{
                        for (var j = 0; j < _data[i].defaultValue.length; j++) {
                        	//是否有选中值
                            var _checked = false;
                            _defayultValue = _data[i].defaultValue[j].value;
                            for (var k = 0; k < _data[i].checkValue.length; k++) {
                                if (_defayultValue == _data[i].checkValue[k].value) {
                                    _checked = true;
                                }
                            }
                            _html += "<input type='checkbox' " + (_checked ? "checked" : "") + " value='" + _defayultValue + "' name='" + _fieldName + "' id='" + _fieldName + "_" + j +  "' isRequired='" + _isRequire + "' maxLength='" + _inputType + "' label='" + _displayName + "'/><label for='" + _fieldName + "_" + j + "'>" + _defayultValue + "</label>"
                        }
                    }

                    break;
                case "input":
                	//文本默认只有一个值
                	 _defayultValue = _data[i].checkValue[0].value
                    _html += "<input type='text' id='" + _fieldName + "' name='" + _fieldName + "' value='" + _defayultValue +  "' isRequired='" + _isRequire + "' maxLength='" + _valueLength + "' label='" + _displayName + "'/>"
                    break;
                    //处理下拉框
                case "select":
                    if(_data[i].defaultValue.length < 0){
                    	break;
                    }
                    _html += "<select name='" + _fieldName + "' id='" + _fieldName +  "' isRequired='" + _isRequire + "' maxLength='" + _valueLength + "' label='" + _displayName + "'>"
                    
                    for (var j = 0; j < _data[i].defaultValue.length; j++) {
                    	//是否有选中值
                        var _checked = false;
                        _defayultValue = _data[i].defaultValue[j].value;
                        for (var k = 0; k < _data[i].checkValue.length; k++) {
                            if (_defayultValue == _data[i].checkValue[k].value) {
                                _checked = true;
                            }
                        }
                        _html += "<option " + (_checked ? "selected" : "") + " value='" + _defayultValue + "'>" + _defayultValue + "</option>";
                    }
                    _html += "</select>"
                    break;
                default:
                    break;
            }
            _html += "</td></tr>";
        }
	
        _append.html(_html);
        
        }
        
        //数据校验
        $("#attValueCheck").click(function () {
            var nameArr = [];
            var objArr = $("#createAtt [isRequired]");
            var i = 0;
            objArr.each(function (i) {
                var _o = { name: $(this).attr("name"), maxlength: parseInt($(this).attr("maxlength"), 10), label: $(this).attr("label"), type: this.type, tagName: this.tagName, dataType: $(this).attr("dataType"), isRequire: $(this).attr("isRequired") };
                if (!indexOfNameArr(nameArr, _o)) {
                    nameArr.push(_o);
                }
            })
            for (i = 0; i < nameArr.length; i++) {
                var obj = nameArr[i];
                var _e = document.getElementsByName(obj.name)[0];
                var v = _e.value; ;
                //单选框,或复选框
                if (obj.type == 'radio' || obj.type == 'checkbox') {
                    if (obj.isRequire == "Y") {
                        v = getGroupValue(obj.name);
                        if (v == "") {
                            alert("请选择 [" + obj.label + "] ！");
                            setGroupfocus(obj.name);
                            return false;
                        }
                    }
                }
                else if (obj.isRequire == "Y")//其它类型
                {
                    if (v == "") {
                        alert("请输入 [" + obj.label + "] !");
                        _e.focus();
                        return false;
                    }
                }
            }
            return true;
        })

        //获取单选/复选组的值
        function getGroupValue(groupName) {
            var g = document.getElementsByName(groupName);
            var result = "";
            for (var k = 0; k < g.length; k++) {
                if (g[k].checked) {
                    result = g[k].value;
                    break;
                }
            }
            return result;
        }
        //设置焦点
        function setGroupfocus(groupName) {
            document.getElementsByName(groupName)[0].focus();
        }
        //是否包含
        function indexOfNameArr(arr, o) {
            var result = false;
            for (var i = 0; i < arr.length; i++) {
                if (arr[i].name == o.name) {
                    result = true;
                    break;
                }
            }
            return result;
        }
    </script>
</BODY>
</HTML>


