jQuery.extend(jQuery.validator.messages, {
  required: "必选字段",
  remote: "请修正该字段",
  email: "请输入正确格式的电子邮件",
  url: "请输入合法的网址",
  date: "请输入合法的日期",
  dateISO: "请输入合法的日期 (ISO).",
  number: "请输入合法的数字",
  digits: "只能输入整数",
  creditcard: "请输入合法的信用卡号",
  equalTo: "请再次输入相同的值",
  accept: "请输入拥有合法后缀名的字符串",
  maxlength: $.format("请输入一个长度最多是 {0} 的字符串"),
  minlength: $.format("请输入一个长度最少是 {0} 的字符串"),
  rangelength: $.format("请输入 一个长度介于 {0} 和 {1} 之间的字符串"),
  range: $.format("请输入一个介于 {0} 和 {1} 之间的值"),
  max: $.format("请输入一个最大为{0} 的值"),
  min: $.format("请输入一个最小为{0} 的值")
});
//手机号码验证
jQuery.validator.addMethod("cellphone",function(value, element){
	var tel =/^((\(\d{2,3}\))|(\d{3}\-))?1[3,8,5]{1}\d{9}$/; 
	return this.optional(element) || (tel.test(value)); 
},"请正确填写手机号码");

//联系方式校验
jQuery.validator.addMethod("telephone", function(value, element) {  
	var tel =/^\d{3}-\d{8}|\d{4}-\d{7,8}$/; 
	return this.optional(element) || (tel.test(value)); 
}, "请正确填写联系方式");
//邮政编码校验
jQuery.validator.addMethod("isZipCode", function(value, element) {  
	var tel =/^[0-9]{6}$/; 
	return this.optional(element) || (tel.test(value)); 
}, "请正确填写邮政编码");
//有效字符串验证
jQuery.validator.addMethod("commonChar", function(value, element) {  
	var tel =/^[A-Za-z0-9_]*$/; 
	return this.optional(element) || (tel.test(value)); 
}, "必须由数字、字母或下划线组成");

//特殊字符串验证
jQuery.validator.addMethod("specialChar", function(value, element) {  
	var tel=/^[A-Za-z0-9][A-Za-z0-9+*%\-\/\.():?>]*$/
	return this.optional(element) || (tel.test(value)); 
}, "必须由数字、字母或+-*/%.()组成并且以字母数字开头");

//整数
jQuery.validator.addMethod("keywordIdChar", function(value, element) {  
	var tel= /^[1-9]\d*$/;
	var reg =/^\s*$/;
	if(value==""){
 	 	return true;
 	}else{
 		if(reg.test(value)|| !tel.test(value)){
            return false;
    }else{
            return true;
      }
 		
 	}
	
	//return this.optional(element) || (tel.test(value))|| (reg.test(value)); 
}, "只能输入整数，且不能有空格" );


//姓名中、英文验证
jQuery.validator.addMethod("userName", function(value, element) {  
	//var tel=/^[a-zA-Z]{1,20}|[\u4e00-\u9fa5]{1,10}$/
	var tel= /^[a-zA-Z\u4e00-\u9fa5]+$/;
	
	return this.optional(element) || (tel.test(value)); 
}, "名称必须是中文、英文，不能使用其他字符组成");

//特殊字符验证
jQuery.validator.addMethod("unusualChar", function(value, element) {  
	//var tel=/^[a-zA-Z]{1,20}|[\u4e00-\u9fa5]{1,10}$/
	//var tel=/^[^/|"'<>&/^/?/*~/{/}/[/]/+/-/$()#!！@？“‘%（）……￥`·_-—=【】//;；’”，《》,.。、:：//]*$/ ;
	var tel=/^[^\|"'<>~&$%{}《》#｛｝￥（）【】|]*$/;
	return this.optional(element) || (tel.test(value)); 
}, "输入内容里不能包含特殊字符");

//图片格式限制
jQuery.validator.addMethod("checkFile", function(value, element) {
	 	if(value==""){
	 	 	return true;
	 	}
    if(!value.match(/.jpg|.gif|.png|.bmp/i)){
            return false;
    }else{
            return true;
    }
}, "图片类型必须是: .jpg, .gif, .bmp or .png");
//时间不能早于当前时间
jQuery.validator.addMethod("afterDate", function(value, element) {
	if(value==""){
		 return true;
	 }else{
		 var d2=new Date();
			var d3=d2.getFullYear()+"-"+(d2.getMonth()+1)+"-"+d2.getDate();
			var d4=new Date(d3.replace(/-/g, "/"));
			var d1=new Date(value.replace(/-/g, "/"));
			 return (Date.parse(d4) - Date.parse(d1)>=0);
		
	 }
	}, "时间不能晚于当前时间");
