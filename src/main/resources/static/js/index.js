
/*===========侧边菜单栏==============*/

$(window).scroll(function(){
	if($(window).scrollTop()>=200){
		//获取回到顶部按钮
		$('#sideBottom').css({display:'block'});
	}else{
		$('#sideBottom').css({display:'none'});
	}
});
//回到顶部
$('#sideBottom').click(function(){
	$('body,html').animate({scrollTop:0},1000);
	return false;
});


/*========轮播区菜单栏============*/
menu();
function menu(){
	$('#more-banner-list li').each(function(i){
		//轮播区菜单边框颜色数组
		var color='#318EC3';
		$(this).hover(function(){
			$(this).css({backgroundColor:'#363636',borderLeft:'2px solid '+color+''});
		},function(){
			$(this).css({backgroundColor:'transparent',borderLeft:'none'});
		});
	});
	//二级菜单显示
	$('#more-banner-list li').each(function(i){
		$(this).hover(function(e){
			e.stopPropagation();
			$('[data-ban="ban-menu"]').eq(i).show();
		},function(e){
			e.stopPropagation();
			$('[data-ban="ban-menu"]').eq(i).hide();
		});
	});	
}

/*========工具函数===========*/
/**
 * 阻止事件的冒泡
 * @param  {[type]} e [description]
 * @return {[type]}   [description]
 */
function myfn(e){
	//如果提供了事件对象，则这是一个非IE浏览器 
	if ( e && e.stopPropagation ) 
    	//因此它支持W3C的stopPropagation()	方法 
   	 e.stopPropagation(); 
	else 
   	 	//否则，我们需要使用IE的方式来取消事件冒泡 
   	 	window.event.cancelBubble = true; 
}

/**
 * 获取样式的值
 * @param  {对象} obj  [元素]
 * @param  {属性} attr [样式]
 * @return {属性值}      [样式的值]
 */
function getStyle (obj,attr) { 
	//IE兼容
	if(obj.currentStyle){ 
		return obj.currentStyle[attr];
	}else{ 
		return getComputedStyle(obj,false)[attr];
	}

}

/**
 * 跨浏览器添加事件方法
 * @param {[type]} element [元素]
 * @param {[type]} type    [添加的事件类型]
 * @param {[type]} handler [事件处理函数]
 */
function addEvent(element, type, handler) {
	//为每一个事件处理函数分派一个唯一的ID
	if (!handler.$$guid) 
		handler.$$guid = addEvent.guid++;
	//为元素的事件类型创建一个哈希表
	if (!element.events) 
		element.events = {};
	//为每一个"元素/事件"对创建一个事件处理程序的哈希表
	var handlers = element.events[type];
	if (!handlers) {
		handlers = element.events[type] = {};
		//存储存在的事件处理函数(如果有)
		if (element["on" + type]) {
			handlers[0] = element["on" + type];
		}
	}
	//将事件处理函数存入哈希表
	handlers[handler.$$guid] = handler;
	//指派一个全局的事件处理函数来做所有的工作
	element["on" + type] = handleEvent;
};
//用来创建唯一的ID的计数器
addEvent.guid = 1;
function removeEvent(element, type, handler) {
	//从哈希表中删除事件处理函数
	if (element.events && element.events[type]) {
		delete element.events[type][handler.$$guid];
	}
};
function handleEvent(event) {
	var returnValue = true;
	//抓获事件对象(IE使用全局事件对象)
	event = event || fixEvent(window.event);
	//取得事件处理函数的哈希表的引用
	var handlers = this.events[event.type];
	//执行每一个处理函数
	for (var i in handlers) {
		this.$$handleEvent = handlers[i];
		if (this.$$handleEvent(event) === false) {
			returnValue = false;
		}
	}
	return returnValue;
};
//为IE的事件对象添加一些“缺失的”函数
function fixEvent(event) {
//添加标准的W3C方法
	event.preventDefault = fixEvent.preventDefault;
	event.stopPropagation = fixEvent.stopPropagation;
	return event;
};
fixEvent.preventDefault = function() {
	this.returnValue = false;
};
fixEvent.stopPropagation = function() {
	this.cancelBubble = true;
};


/*======获取浏览器可视窗口========*/
function visibleWin(){
	var winWidth,winHeight;
	// 获取窗口宽度
	if (window.innerWidth){
		winWidth = window.innerWidth;
	}else if((document.documentElement)&&(document.documentElement.clientWidth)){
		winWidth = document.documentElement.clientWidth;
	}else if ((document.body) && (document.body.clientWidth)){
		winWidth = document.body.clientWidth;
	}
	// 获取窗口高度
	if (window.innerHeight){
		winHeight = window.innerHeight;
	}else if((document.documentElement)&&(document.documentElement.clientHeight)){
		winHeight = document.documentElement.clientHeight;
	}else if ((document.body) && (document.body.clientHeight)){
		winHeight = document.body.clientHeight;
	}
	return {innerWidth:winWidth,innerHeight:winHeight}
}
/*=======获取IE浏览器类型及版本号=========*/
function browser(){
	if(navigator.userAgent.indexOf("MSIE")>0){   
      if(navigator.userAgent.indexOf("MSIE 6.0")>0){   
        return("ie6");    
      }   
      if(navigator.userAgent.indexOf("MSIE 7.0")>0){  
        return("ie7");   
      }   
      if(navigator.userAgent.indexOf("MSIE 8.0")>0 && !window.innerWidth){//这里是重点，你懂的
        return("ie8");  
      }   
      if(navigator.userAgent.indexOf("MSIE 9.0")>0){  
        return("ie9");  
      }   
    } 
}
/*========工具函数===========*/
