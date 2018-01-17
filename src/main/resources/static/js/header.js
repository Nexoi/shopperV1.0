/*如果不是 index.html 页面，其余页面需要引用 header 的必须包含此脚本 */
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