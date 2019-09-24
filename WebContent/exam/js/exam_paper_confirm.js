function $$(aa){
    return document.getElementById(aa);
}


function examBox(){
    var that = this; 
    
    layer.open({
        title:  ['您的得分：', 'font-size:18px;'],
        content: lookUpScore(),
        area: ['400px', '400px'],
        shade: 0,
        maxmin: true,
         ,offset: [ //为了演示，随机坐标  
         value,
         value
        ]   
        btn: ['继续弹出', '全部关闭'],
        yes: function(){
            examBox(); 
        },
        btn2: function(){
            layer.closeAll();
        },
        zIndex: 2,
        success: function(layero){
            layer.setTop(layero); 
        }
    }); 
}

function lookUpScore(){
    var title1='单选题：';
    var title2='多选题：';
    var title3='判断题：';
    var see1='你的答案：';
    var see2='参考答案：AAADD';
    var see3='参考答案：AB/CD';
    var see4='参考答案：√ / ×';
    var result1='';
    var result2='';
    var result3='';

    var arr1=document.getElementsByName("aa");
    for(var i=0;i<arr1.length;i++){
        result1+=arr1[i].value;
    }

    var arr2=document.getElementsByName("bb");
    for(var j=0;j<arr2.length;j++){
        result2+=arr2[j].value;
    }

    

    return title1+see1+result1+"&nbsp;&nbsp;&nbsp;"+see2+
    "<br>"+title2+see1+result2+"&nbsp;&nbsp;&nbsp;"+see3+
    "<br>"+title3+see1+result3+"&nbsp;&nbsp;&nbsp;"+see4;
}
