$(document).ready(function(e) {
    var editor;
	KindEditor.ready(function(K) {
		editor = K.create('textarea[name="activityInfo.activityIntroduce"]', {
			cssPath : '/kindeditor/plugins/code/prettify.css',
			uploadJson : '/kindeditor/jsp/upload_json_activity.jsp',
			fileManagerJson : '/kindeditor/jsp/file_manager_json.jsp',
			allowFileManager : true,
			afterBlur:function(){this.sync();}
			
		});
	});
});