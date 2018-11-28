// 验证是否为整数
function validInteger(value){
    return /^\d+(\.\d+)?$/i.test(value);
}

// 关闭easyui弹窗
function closeWindow(){
    window.parent.$('#win').window('close');
}

// 显示操作结果
function showMessage(obj,message) {
    obj.parent.$.messager.show({
        //title:'提示',
        msg:message,
        showType:'fade',  //null,slide,fade,show. Defaults to slide.
        timeout:3000,
        width:265,
        height:40,
        style:{
            right:'',
            top:document.body.scrollTop+document.documentElement.scrollTop,
            bottom:''
        }
    });
}

/**
 * 初始化combox
 * @param id:控件id
 * @param url:数据源地址
 */
function initSelect(id,dataUrl) {
    $("#"+id).combobox({
        url:dataUrl,
        valueField:"id",
        textField:"text",
        required:true,
        missingMessage:"请选择!"
    })
}

/**
 * 初始化combox
 * @param id:控件id
 * @param url:数据源地址
 * @param value:初始化默认值
 */
function initSelect(id,dataUrl,value) {
    $("#"+id).combobox({
        url:dataUrl,
        valueField:"id",
        textField:"text",
        required:true,
        missingMessage:"请选择!",
        onLoadSuccess:function(data){
            $(this).combobox("select", value);
        }
    })
}