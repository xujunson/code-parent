
function date2Str(date){
	 if(isNull(date)){
	   	 return "";
	 }
	 var date=new Date(date);
	 var y = date.getFullYear();
	 if(y > 1985 && y < 1992) {
		 //夏令时的处理,确保不会少一个小时
		 date = new Date(date.getTime() + 3600*1000);
	 }
	 y = date.getFullYear();
	 var m = date.getMonth() + 1;
	 if(m<10) m = "0" + m;
	 var d = date.getDate();
	 if(d<10) d = "0" + d;
	 var redate = y + "-" + m + "-" + d;
	 return redate;
}


function datetime2Str(dt){
	 if(dt==null){
	   	 return "";
	 }
	 var datetime=new Date(dt);
	 var y = datetime.getFullYear();
	 var m = datetime.getMonth() + 1;  
	 if(m < 10) m = "0" + m;
	 var d = datetime.getDate();  
	 if(d < 10) d = "0" + d;
	 var h = datetime.getHours();
	 if(h < 10) h = "0" + h;
	 var min = datetime.getMinutes();
	 if(min < 10) min = "0" + min;
	 var s=datetime.getSeconds();
	 if(s<10)s="0"+s;
	 var redatetime = y + "-" + m + "-" + d + " " + h + ":" + min+":"+s; 
	 return redatetime;
};


/**
 * 获取URL指定参数值。
 * @param {String} name
 */
$.getQueryString = function (name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]); return null;
}

/**
 * 获取查询参数
 */
String.prototype.getQueryString = function(name) {
	var reg = new RegExp("(^|&|\\?)" + name + "=([^&]*)(&|$)");
	var r = this.match(reg);
	if (r) {
		return decodeURIComponent(r[2]);
	}
	return null;
};
/**
 * js没有replaceAll方法,设置replaceAll方法替换字符串
 */
String.prototype.replaceAll = function(s1,s2){
	　　return this.replace(new RegExp(s1,"gm"),s2);
}
/**
 * 是否为空或空字符串
 */
function isNull(obj) {
    if(typeof(obj) == "number") return false;
    if(typeof(obj) == "undefined" || obj == null || !obj || ( typeof(obj) == "string" && obj.trim() == "" )) {
        return true;
    } else {
        return false;
    }
}
/**
 * 使用循环的方式判断一个元素是否存在于一个数组中
 * @param {Object} arr 数组
 * @param {Object} value 元素值
 */
function isInArray(arr,value){
    for(var i = 0; i < arr.length; i++){
        if(value === arr[i]){
            return true;
        }
    }
    return false;
}
var file_upload = {};
/**
 * 上传附件
 * @returns
 */
function attachUpload(attachId,attachId_disp){
	file_upload["attachId"]=attachId;
	file_upload["attachId_disp"]=attachId_disp;
	var href=basePath+'/streamUpload/toUpload?method=appendFileToList'
	var dialog=$('<div>').dialog({
		 title : '上传',
            width : '70%',
            height : '70%',
            modal:true,
            onClose : function() {
                $(this).dialog('destroy');
            },
            href : href,
            buttons : [ {
                text : '确定',
                handler : function() {
                    dialog.dialog('close');
                }
           }]
	});
}

function appendFileToList(fileName,fileid,fileType){
	console.log(fileid)
	  var pre = $("#"+file_upload["attachId"]).val();
	  var now="";
	  if(pre){
	  	now = pre+","+fileid;
	  }else{
	  	now = fileid;
	  }
	  var pdf="";
	  if(fileType=='pdf'){
		  pdf="<a style='margin-left:20px;' href='/jsp/pdfjs/web/viewer.html?file=/pdfView/view?name%3d"+fileid+"' target='_blank'>在线预览</a>";
	  }
	  $("#"+file_upload["attachId"]).val(now);
	  $("#"+file_upload["attachId_disp"]).append("<p>"+fileName
			  +pdf
			  +"<a style='margin-left:20px;' href='javascript:void(0)' onClick='downloadAttach(\""+fileid+"\")'>下载</a>"
			  +"<a href='#' style='margin-left:20px;'  onClick='delAttach(\""+fileid+"\",this,\""+file_upload["attachId"]+"\");'>删除</a><p>"
			  );
}

function delAttach(fileid,ele,attachId){		
	//只有提交后才会删除
	$(ele).parent().remove();
	var pre = $("#"+attachId).val();
	if(pre){
		pre = pre.replace(fileid+",","");
		//最后只有一个元素
		{
			pre = pre.replace(fileid,"");
			if(pre.charAt(pre.length-1)==","){
				pre= pre.substring(0,pre.length-1);
			}
		}					
		$("#"+attachId).val(pre);
	}
}

/**
 * 字符串（以逗号隔开）替换某一个字符串
 * @param el
 * @param id
 */
function strdelId(el,id){
    var pre = $("#"+el).val();
    if(pre){
        pre = pre.replace(id+",","");
        //最后只有一个元素
        {
            pre = pre.replace(id,"");
            if(pre.charAt(pre.length-1)==","){
                pre= pre.substring(0,pre.length-1);
            }
        }
        $("#"+el).val(pre);
    }
}
/**
 * 下载附件
 * @param id
 * @returns
 */
function downloadAttach(id){
	window.location.href=basePath+"/upload/downloadAttach/"+id;
}

/**
 * [isArray description] 判断是否数组
 * @param  {[type]}  o [description]
 * @return {Boolean}   [description]
 */
function isArray(o) {
    return Object.prototype.toString.call(o)=='[object Array]';
}

/**
 * 执行带参数的回调函数
 *
 * @param callback
 * @param paramsArray
 */
function execCallback(callback,paramsArray) {
    if(isArray(paramsArray)) {
        if(paramsArray.length == 0) {
            callback();
        } else if(paramsArray.length == 1) {
            callback(paramsArray[0]);
        } else if(paramsArray.length == 2) {
            callback(paramsArray[0],paramsArray[1]);
        } else if(paramsArray.length == 3) {
            callback(paramsArray[0],paramsArray[1],paramsArray[2]);
        } else if(paramsArray.length == 4) {
            callback(paramsArray[0],paramsArray[1],paramsArray[2],paramsArray[3]);
        } else if(paramsArray.length == 5) {
            callback(paramsArray[0],paramsArray[1],paramsArray[2],paramsArray[3],paramsArray[4]);
        } else if(paramsArray.length == 6) {
            callback(paramsArray[0],paramsArray[1],paramsArray[2],paramsArray[3],paramsArray[4],paramsArray[5]);
        }
    } else {
        callback();
    }
}

/**
 * 选人
 * @param multiSelect
 * @param userId
 * @param userId_disp
 */
function selectUser(multiSelect,userId,userId_disp){
    var userids= $('#'+userId).val();
    var url=basePath+"/user/userSelect?userids="+userids+"&flag="+multiSelect;
    var title='选人';
    var width='50%';
    var dialog= $('<div>').dialog({
        title:title,
        href:url,
        width : width,
        height : 500,
        modal:true,
        onClose : function() {
            $(this).dialog('destroy');
        },
        buttons:[{
            text:'确定',
            handler:function () {
                var id=getMultiuserids();
                 var name=getMultiusernames();
                $('#'+userId).val(id);
                $('#'+userId_disp).val(name);
                dialog.dialog('close');
            }
        },{
            text:'取消',
            handler:function () {
                dialog.dialog('close');
            }
        }]
    });
}

/**
 * 选择部门
 * @param flag=true|false 多选/单选
 * @param orgId  部门Id
 * @param orgId_disp 部门名称Id
 */
function selectOrg(flag,orgId,orgId_disp){
    var deptids=$('#'+orgId).val();
    var url=basePath+"/organization/organizationSelect?deptids="+deptids+"&flag="+flag;
    var title='选部门';
    var dialog= $('<div>').dialog({
        title:title,
        href:url,
        width:'50%',
        height : '70%',
        modal:true,
        onClose : function() {
            $(this).dialog('destroy');
        },
        buttons:[{
            text:'确定',
            handler:function () {
                var ids=getMultiOrgIds();
                var names=getMultiOrgNames();
                $('#'+orgId).val(ids);
                $('#'+orgId_disp).val(names);
                dialog.dialog('close');
            }
        },{
            text:'取消',
            handler:function () {
                dialog.dialog('close');
            }
        }]
    });
}
/**
 * 选择职位
 * @param flag=true|false 多选/单选
 * @param orgId  部门Id
 * @param orgId_disp 职位名称Id
 */
function selectPosition(flag,positionId,positionId_disp){
    var positionIds=$('#'+positionId).val();
    var url=basePath+"/position/positionSelect?positionIds="+positionIds+"&flag="+flag;
    var title='选职位';
    var dialog= $('<div>').dialog({
        title:title,
        href:url,
        width:'50%',
        height : '70%',
        modal:true,
        onClose : function() {
            $(this).dialog('destroy');
        },
        buttons:[{
            text:'确定',
            handler:function () {
                var ids=getMultiPositionIds();
                var names=getMultiPositionNames();
                $('#'+positionId).val(ids);
                $('#'+positionId_disp).val(names);
                dialog.dialog('close');
            }
        },{
            text:'取消',
            handler:function () {
                dialog.dialog('close');
            }
        }]
    });
}

/**
 * 选择资源
 * @param flag
 * @param resourceId
 * @param resourceId_disp
 */
function selectResource(flag,resourceId,resourceId_disp){
    var resIds=$('#'+resourceId).val();
    var url=basePath+"/resource/resourceSelect?resIds="+resIds+"&flag="+flag;
    var title='选资源菜单';
    var dialog= $('<div>').dialog({
        title:title,
        href:url,
        width:'50%',
        height : '70%',
        modal:true,
        onClose : function() {
            $(this).dialog('destroy');
        },
        buttons:[{
            text:'确定',
            handler:function () {
                var ids=getMultiResourceIds();
                var names=getMultiResourceNames();
                $('#'+resourceId).val(ids);
                $('#'+resourceId_disp).val(names);
                dialog.dialog('close');
            }
        },{
            text:'取消',
            handler:function () {
                dialog.dialog('close');
            }
        }]
    });
}

/**
 * 查看历史批注
 * @param processInstanceId
 */
function queryHistoryCommentFun(processInstanceId){
    var dialog=$('<div>').dialog({
        title : '查看历史批注',
        width : '80%',
        height : '80%',
        onClose : function() {
            $(this).dialog('destroy');
        },
        href : basePath+'/process/getCommentsByProcessInstanceId?processInstanceId='+processInstanceId,
        buttons : [ {
            text : '关闭',
            handler : function() {
                dialog.dialog('close');
            }
        } ]
    });
}

var dialog;
/**
 * 设置任务执行人
 * @param processInstanceId
 */
function setTaskAssignee(processInstanceId){
     dialog=$('<div>').dialog({
        title : '设置任务执行人',
        width : '50%',
        height : '50%',
        onClose : function() {
            $(this).dialog('destroy');
        },
        modal:true,
        href : basePath+'/process/toSetTaskAssignee?processInstanceId='+processInstanceId,
        buttons : [ {
            text : '保存',
            handler : function() {
                $('#setTaskAssignee').submit();
            }
        } ]
    });
}
/**
 * 选择流程类型
 * @param flag
 * @param pid
 * @param pidDIV
 * @returns
 */
function selectProcessType(flag,pid,pidDIV){
	var processTypeIds=$('#'+pid).val();
	dialog=$('<div>').dialog({
        title : '选择流程类型',
        width : '80%',
        height : '80%',
        onClose : function() {
            $(this).dialog('destroy');
        },
        modal:true,
        href : basePath+'/processType/toSelect?processTypeIds='+processTypeIds+'&flag='+flag,
        buttons : [ {
            text : '确定',
            handler : function() {
                if(flag){
                	var ids=[];
                	var names=[];
                	var rows=processTypetreegridSelect.treegrid('getSelections');
                	if(rows.length>0){
                		for(var i=0;i<rows.length;i++){
                			ids.push(rows[i].key);
                			names.push(rows[i].name);
                		}
                		$('#'+pid).val(ids);
                		$('#'+pidDIV).val(names);
                	}
                }else{
                	var row=processTypetreegridSelect.treegrid('getSelected');
                	if(!isNull(row)){
                		$('#'+pid).val(row.key);
                		$('#'+pidDIV).val(row.name);
                	}
                }
                dialog.dialog('close');
            }
        } ]
    });
}

/**
 * 选择角色
 * @param flag
 * @param rid
 * @param rid_disp
 */
function selectRole(flag,rid,rid_disp){
	 var title="选择角色";
	 var roleids=$('#'+rid).val();
	 var url=basePath+"/role/roleSelect?flag="+flag+"&roleids="+roleids;
	 var dialog= $('<div>').dialog({
	        title:title,
	        href:url,
	        width:'70%',
	        height : '70%',
	        modal:true,
	        onClose : function() {
	            $(this).dialog('destroy');
	        },
	        buttons:[{
	            text:'确定',
	            handler:function () {
	                var ids=[];
	                var names=[];
	                if(flag){
	                	var rows=roleSelectDataGrid.datagrid('getSelections');
	                	if(rows.length>0){
	                		for(var i=0;i<rows.length;i++){
	                			ids.push(rows[i].id);
	                			names.push(rows[i].name);
	                		}
	                		 $('#'+rid).val(ids.join(","));
	     	                 $('#'+rid_disp).val(names.join(","));
	                	}
	                }else{
	                	var row=roleSelectDataGrid.datagrid('getSelected');
	                	if(!isNull(row)){
	                		$('#'+rid).val(row.id);
	                		$('#'+rid_disp).val(row.name);
	                	}
	                }
	                dialog.dialog('close');
	            }
	        },{
	            text:'取消',
	            handler:function () {
	                dialog.dialog('close');
	            }
	        }]
	 });
}
/**
 * 选择文章类型
 * @param flag
 * @param rid
 * @param rid_disp
 */
function selectArticleType(flag,rid,rid_disp){
	 var title="选择文章类型";
	 var ids=$('#'+rid).val();
	 var url=basePath+"/articleType/toSelect?flag="+flag+"&ids="+ids;
	 var dialog= $('<div>').dialog({
	        title:title,
	        href:url,
	        width:'70%',
	        height : '70%',
	        modal:true,
	        onClose : function() {
	            $(this).dialog('destroy');
	        },
	        buttons:[{
	            text:'确定',
	            handler:function () {
	                var ids=[];
	                var names=[];
	                if(flag){
	                	var rows=articleTypeTreeGridSelect.treegrid('getSelections');
	                	if(rows.length>0){
	                		for(var i=0;i<rows.length;i++){
	                			ids.push(rows[i].id);
	                			names.push(rows[i].name);
	                		}
	                		 $('#'+rid).val(ids.join(","));
	     	                 $('#'+rid_disp).val(names.join(","));
	                	}
	                }else{
	                	var row=articleTypeTreeGridSelect.treegrid('getSelected');
	                	if(!isNull(row)){
	                		$('#'+rid).val(row.id);
	                		$('#'+rid_disp).val(row.name);
	                	}
	                }
	                dialog.dialog('close');
	            }
	        },{
	            text:'取消',
	            handler:function () {
	                dialog.dialog('close');
	            }
	        }]
	 });
}



/**
 * 打印
 */
var LODOP;
function printHTML(id){
	LODOP=getLodop(); 
	LODOP.SET_PRINT_PAGESIZE(3, 0, 0,"A4");//设置A4纸打印
	var strHtml=document.getElementById(id).innerHTML;
	var printHTML='<!DOCTYPE html><html lang="en"><head><meta charset="UTF-8"><title></title><style>.table-print{width: 700px;  }</style></head><body>'
            +strHtml
            +'</body></html>';
	LODOP.ADD_PRINT_HTM(20,55,"100%","100%",printHTML);
	LODOP.PREVIEW();
}

/**
 * 格式化字符串
 * @param str
 * @param length
 * @param color
 * @returns
 */
function str2span(str,len){
	if(len == null || len <= 0 || len >= str.length) {
		return str;
	} else {
		str = str.substr(0, len) + "...";
		return str;
	}
}

/**
 * 格式化字符串，主要用于表格显示
 * @param str
 * @param len
 * @returns {*}
 */
function strTospan2(str,len){
    var oldStr=str;
    if(len == null || len <= 0 || len >= str.length) {
        return str;
    } else {
        str = str.substr(0, len) + "...";
        return '<span title="'+oldStr+'">'+str+'</span>';
    }
}

function exp2pdf(fileName,contentId,formId){
    var content=$('#'+contentId).html();
    $('#'+formId+' [name=fileName]').val(fileName);
    $('#'+formId+' [name=content]').val(content)
    $('#'+formId).attr("action",basePath+"/exp2pdf");
    $('#'+formId).submit();
    $('#'+formId+' [name=fileName]').val('');
    $('#'+formId+' [name=content]').val('');
}

function clearDisp(id,id_disp){
    $('#'+id).val('');
    $('#'+id_disp).val('');
}

/**
 * 给form表单赋值
 * @param el
 * @param data
 * @returns
 */
function setFromValues(el, data)
{
    for (var p in data)
    {
        el.find(":input[name='" + p + "']").val(data[p]);
    } 
}
/**
 * 设置form属性为disabled
 * @param {[type]} el   [description]
 * @param {[type]} data [description]
 * @param {[type]} flag [description]
 */
function setFromAttrDisabled(el, data,flag)
{
    for (var p in data)
    {
        el.find(":input[name='" + p + "']").attr('disabled',flag);
    } 
}

/*$.fn.formSerialize = function (formdate) {
    var element = $(this);
    if (!!formdate) {
        for (var key in formdate) {
            var $id = element.find('#' + key);
            var value = $.trim(formdate[key]).replace(/ /g, '');
            var type = $id.attr('type');
            if ($id.hasClass("select2-hidden-accessible")) {
                type = "select";
            }
            switch (type) {
                case "checkbox":
                    if (value == "true") {
                        $id.attr("checked", 'checked');
                    } else {
                        $id.removeAttr("checked");
                    }
                    break;
                case "select":
                    $id.val(value).trigger("change");
                    break;
                default:
                    $id.val(value);
                    break;
            }
        };
        return false;
    }
    var postdata = {};
    element.find('input,select,textarea').each(function (r) {
        var $this = $(this);
        var id = $this.attr('id');
        var type = $this.attr('type');
        switch (type) {
            case "checkbox":
                postdata[id] = $this.is(":checked");
                break;
            default:
                var value = $this.val() == "" ? " " : $this.val();
                if (!$.request("keyValue")) {
                    value = value.replace(/ /g, '');
                }
                postdata[id] = value;
                break;
        }
    });
   
    return postdata;
};*/

/**
 * 限制输入字符
 * total 限制总字数
 * inputId 输入框的id
 * spanId 显示字符的ID
 */
function limitWords(total,inputId,spanId){
    $("#"+inputId).on("input propertychange", function () {
        var $this = $(this),
            _val = $this.val(),
            count = "";
        if (_val.length > total) {
            $this.val(_val.substring(0, total));
        }
        count = $this.val().length;
        $("#"+spanId).text(count);
    });
}

/**
 * 统计输入字符
 */
function countWords(inputId,spanId){
    $("#"+inputId).on("input propertychange", function () {
        var $this = $(this),
            _val = $this.val(),
            count = "";
        count = $this.val().length;
        $("#"+spanId).text(count);
    });
}

/**
 * 数字转金额
 * @param num
 * @returns {string}
 */
function upperMoney(num) {
    var pref = "";
    num = Number(num);
    if(num < 0) {
        pref = "负";
        num = Math.abs(num);
    }

    var strOutput = "";
    var strUnit = '仟佰拾亿仟佰拾万仟佰拾元角分';
    num += "00";
    var intPos = num.indexOf('.');
    if (intPos >= 0) {
        num = num.substring(0, intPos) + num.substr(intPos + 1, 2);
    }
    strUnit = strUnit.substr(strUnit.length - num.length);
    for (var i=0; i < num.length; i++) {
        strOutput += '零壹贰叁肆伍陆柒捌玖'.substr(num.substr(i,1),1) + strUnit.substr(i,1);
    }
    return pref + strOutput.replace(/零角零分$/, '整').replace(/零[仟佰拾]/g, '零').replace(/零{2,}/g, '零').replace(/零([亿|万])/g, '$1').replace(/零+元/, '元').replace(/亿零{0,3}万/, '亿').replace(/^元/, "零元");
}