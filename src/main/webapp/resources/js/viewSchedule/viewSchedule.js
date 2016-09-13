var dayOffset = [];
var listData;
var mapDay;
var mapNameSource;
var mapNameTemplate;
var baseHeight;
var file;
var scheduleNo;
var routeNo;
var no
var filesList = new Array();
$(function() {
	scheduleNo = location.href.substr(location.href.lastIndexOf('=') + 1);
	var user = sessionStorage.getItem('userNo');
	if( user ){
			/*no = $(location).attr('search').substring(12);*/
		$.ajax({
			url : reizenUrl+'postscript/checkPostscript.do?scheduleNo='+scheduleNo,
			method : 'GET',
			dataType: 'json',
			data:{
				scheduleNo:scheduleNo
			},
			success : function(result){
				if(result.status=='success'){
					if(result.pass=='false'){
						getUser();
						userScheduleAjax();
						
					}else if(result.pass=='right'){
						getUser();
						scheduleAjax();
						
					}
				}else{
					alert('check proceeding status fail');
				}
			}, error  : function(){
				alert('ajax error');
			}
		});
	}else {
		getUser();
		userScheduleAjax();
	}
	

	$('#photoFile').fileupload({
		autoUpload : false
	}).on('fileuploadadd', function(e, data) {
		filesList.pop(); 
		console.log('여기들어왓나');
		console.log(data.files[0]);
		filesList.push(data.files[0]);
		console.log(filesList);
	}).on('fileuploaddone', function (e, data) {
		alert('성공');
		location.reload();
	});

	$(document).on('click','.addPost',function() {
		$('#routeNo').attr('value',$(this).parents('.accordion').attr('data-routeNo').trim());
	});
	
	$('#ajaxform').submit(function(event){
		alert(filesList.length);
		if(filesList.length>0){
			event.preventDefault();
			$('#photoFile').fileupload('send', {
				files : filesList
			});
		}else{
			event.preventDefault();
			$('#photoFile').fileupload('send', {
				files : ""
			});
		}
	});

	/******updatePost*********/
	$('#updatefile').fileupload({
		autoUpload : false
	}).on('fileuploadadd', function(e, data) {
		filesList.pop(); 
		console.log('여기들어왓나');
		console.log(data.files[0]);
		filesList.push(data.files[0]);
		console.log(filesList);
	}).on('fileuploaddone', function (e, data) {
		alert('성공');
		location.reload();
	});
	$(document).on('click','.update',function() {
		$('#updateRouteNo').attr('value',$(this).parents('.accordion').attr('data-routeNo').trim());
		getPictures();
	});
	
	/****************updatePost*********************/
	$('#update-Post').submit(function(event){
		alert(filesList.length);
		
		event.preventDefault();
		if(filesList.length>0){
			event.preventDefault();
			$('#updatefile').fileupload('send', {
				files : filesList
			});
			
		}else{
	     	event.preventDefault();
			$('#updatefile').fileupload('send', {
				files :""
			});
		}
	});

	/*routeNo = $('#routeNo').attr('value',$(this).parents('.accordion').attr('data-routeNo').trim());*/
	$('#scheduleNo').attr('value',scheduleNo);
	
	mapNameSource = $('#mapData').html();
	mapNameTemplate = Handlebars.compile(mapNameSource);

	$(document).on('click', '.scheduleButton', function(e){
		alert('일정보기');
		userScheduleAjax();
		location.href= 'http://reizen.com:8080/scheduler/scheduler.html?scheduleNo='+scheduleNo
		e.preventDefault();
	});

	$('.integration-checklist__copy-button').click(function() {
		$('.aaa').empty();
	});

    var lastScroll = 0;
	$('.scroll').on('scroll',function(){
		var next = mapDay;
		var prev = mapDay-1;
        var st = $(this).scrollTop();
        if (st > lastScroll){
        	if (next != dayOffset.length) {
        		var offset = $($('.fill')[next]).offset();
        		if(offset  == null){
        			return;
        		}
        		if (Math.ceil(offset.top) <= Math.ceil(window.innerHeight*0.3)+51) {
        			mapDay++;
        			initMap();
        		}
			}
        }
        else {
        	if (prev != 0) {
        		var offset = $($('.fill')[prev]).offset();
        		if (Math.ceil(offset.top) >= Math.ceil(window.innerHeight*0.3)+71) {
        			mapDay = prev;
        			initMap();
        		}
			}
        }
        lastScroll = st;
	});
	
	// map 하단 텍스트 클릭시 좌측 스클롤 이동
	$(document).on('click', '.mapName', function(){
		$('.scroll').animate({scrollTop : $('.scroll').scrollTop()+$('.fa-camera-retro[data-no='+$(this).attr('data-no')+']').position().top-baseHeight}, 400);
	});
	
	$(document).on('click', '.gmnoprint > img', function(){
		$(this).next().children('area').click();
	});
	
	$(document).on('click','area',function(){
		for (var i = 0; i < $('area').length; i++) {
			if (this == $('area')[i]) {
				$($('.mapName')[i]).click();
			}
		}
	});
/*	getUser();
	scheduleAjax();*/
});



/*
 * function post(data){ var source = $('#scriptTemplate-post').html(); var
 * template = Handlebars.compile(source); $.ajax({ url : reizenUrl +
 * "postscript/postscript.do", method : 'post', dataType : 'json', data : {
 * scheduleNo : 36 }, success : function(result) { if (result.status !=
 * 'success') {
 * 
 * alert("댓츠 노노"); } else if(data == result) {
 * 
 * 
 * 
 *  } else{ $('#accordion').append(template(result)); } }, error : function() {
 * console.log('error'); } }); }
 */


/*********************postSelect*********************************//*
function postSelect(scheduleNo){
	
	$.ajax({
		url:reizenUrl+'postscript/addPost.do',
		dataType:'json',
		data:{
			scheduleNo:scheduleNo
		},
		method:'post',
		success : function(result){
			if( result.status != 'success'){
				alert('추가에러');
			}
			console.log('ㅊㅋ');
		}
		
	})
}

*/
/**********************selectPictures******************************/



/*function selectPicture(){
	
	$.ajax({
		url:reizenUrl+'postscript/selectPicture.do',
		dataType:'json',
		data:{
			pictureNo:$('#pictureNo').attr('value')
		},
		method:'post',
		success : function(result){
			if( result.status != 'success'){
				alert('추가에러');
			}
			console.log('ㅊㅋ');
		}
		
	})
}

*/





/**************	일정보기 Handlebars helper	**************/
var cnt = 1;

Handlebars.registerHelper('day', function(day, block){
	if ( cnt == day ){
		cnt++;
		return block.fn(this);
	}
	return;
});



Handlebars.registerHelper('splitTime', function(time){
	var times = time.split(' ');
	var out = times[0]
	return out;
});
Handlebars.registerHelper('stime', function(time){
	var times = time.split(' ');
	var out = times[1];
	return out;
});


/**************	맵	**************/

function initMap() {
	var spots = [];
	var dayLength;
	var spotIndex = 0;
	$('.map-sub').empty();
	for(var i=0; i< listData.length ; i++){
		if (mapDay == listData[i].route.day) {
			var location = listData[i].route.location;
			var map2 = parseFloat(location.mapX);
			var map1 = parseFloat(location.mapY);
			spots[spotIndex]={
					lat: map1, 
					lng: map2
			}
			location.index = i;
			$('.map-sub').append(mapNameTemplate(location));
			spotIndex++;
		}
	}
	
	$('.mapName:last-child').children('i').remove();

	var bound = new google.maps.LatLngBounds();//중심점계산하기
	for (i = 0; i < spots.length; i++) {
		bound.extend( new google.maps.LatLng(spots[i]));
	}
	var centerp = bound.getCenter();
	var map = new google.maps.Map(document.getElementById('map'), {
		zoom: 13,
		center: centerp
	});
	map.fitBounds(bound);//여러개의 마커를 찍을 경우 마커를찍음
	var spotPath = new google.maps.Polyline({//경로를 표시하는 클래스 일련의 직선들
		path: spots,//선을 만들 여러개의 위/경도 좌표
		geodesic: true,
		strokeColor: '#FF0000',//빨강
		strokeOpacity: 1.0,//선의투명도
		strokeWeight: 2//선의 굵기
	});
	var labels = '123456789';
	for(var i=0; i<spots.length; i++){
		var marker=[];
		marker[i] = new google.maps.Marker({
			position: spots[i],
			map: map,
			label: labels[i]
		});
	}
	spotPath.setMap(map);//마커를 맵에추가함
}
