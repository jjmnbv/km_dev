FCKConfig.FontNames		= '宋体;黑体;楷体_GB2312;Arial;Comic Sans MS;Courier New;Tahoma;Times New Roman;Verdana' ;
//FCKConfig.FontSizes		= 'smaller/较小;larger/较大;xx-small/极细字;x-small/细字;small/小字体;medium/中字体;large/大字体;x-large/加大字;xx-large/特大字' ;

FCKConfig.AutoDetectLanguage = false ;
FCKConfig.DefaultLanguage = "zh-cn" ;
FCKConfig.StartupFocus = false ;
FCKConfig.SkinPath = FCKConfig.BasePath + 'skins/office2003/' ;
FCKConfig.LinkUpload = true ;
FCKConfig.LinkBrowser = true ;

FCKConfig.ImageBrowser = true ;

FCKConfig.Plugins.Add('simplecommands');
//FCKConfig.Plugins.Add('Media','en,zh-cn');

//FCKConfig.Plugins.Add('attachment','zh-cn');

FCKConfig.ToolbarSets["mydefault"] = [
	['Source','FitWindow','Preview','Save'],
	['Templates','Print'],
	['Cut','Copy','Paste','PasteText','PasteWord'],
	['Undo','Redo','-','Find','Replace','-','SelectAll','RemoveFormat'],
	['Form','Checkbox','Radio','TextField','Textarea','Select','Button','ImageButton','HiddenField'],
	['Bold','Italic','Underline','StrikeThrough','-','Subscript','Superscript'],
	['OrderedList','UnorderedList','-','Outdent','Indent','Blockquote','CreateDiv'],
	['JustifyLeft','JustifyCenter','JustifyRight','JustifyFull'],
	['Link','Unlink','Anchor'],
	['Image','Flash','Media','Table','Rule','SpecialChar'],
	['StyleSimple','FontFormatSimple','FontNameSimple','FontSizeSimple'],
	['TextColor','BGColor'],
	['PageBreak']
];

FCKConfig.ToolbarSets["template"] = [
	['Source','DocProps','-','Preview','-','Templates'],
	['Cut','Copy','Paste','PasteText','PasteWord','-'],
	['Undo','Redo','-','Find','Replace','-','SelectAll','RemoveFormat'],
	['Bold','Italic','Underline','StrikeThrough','-','Subscript','Superscript'],
	['OrderedList','UnorderedList','-','Outdent','Indent','Blockquote','CreateDiv'],
	['JustifyLeft','JustifyCenter','JustifyRight','JustifyFull'],
	['Link','Unlink','Anchor'],
	['Image','Flash','Table','Rule','Smiley','SpecialChar','PageBreak'],
	['Style','FontFormat','FontName','FontSize'],
	['TextColor','BGColor'],
	['FitWindow','ShowBlocks','-','About']
];

FCKConfig.ToolbarSets["ios"] = [
	['FitWindow','Preview','Bold','Italic','-','StyleSimple','FontFormatSimple','FontNameSimple','FontSizeSimple','-','JustifyLeft','JustifyCenter','JustifyRight','JustifyFull','-','TextColor','BGColor','-','Smiley','Table','Rule','-','OrderedList','UnorderedList','-','Image','Flash']
];