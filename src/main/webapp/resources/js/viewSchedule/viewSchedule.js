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
		$.ajax({
			url : reizenUrl+'postscript/checkPostscript.do?scheduleNo='+scheduleNo,
			method : 'GET',
			dataType: 'json',
			success : function(result){
				if(result.status=='success'){
					if(result.pass=='false'){
						getUser();
						userScheduleAjax();
						usersrAjax();
						addSchedule();
					}else if(result.pass=='right'){
						getUser();
						scheduleAjax();
						usersrAjax();
						 MySchedule();
					}
				}else{
					swal('check proceeding status fail');
				}
			}, error  : function(){
				swal('ajax error');
			}
		});
	}else {
		getUser();
		userScheduleAjax();
	}
	

	
	$('#photoFile')
	.fileupload({
	    replaceFileInput:false,autoUpload : false})
	.on('fileuploadadd', function(e, data) {
		filesList.pop(); 
		filesList.push(data.files[0]);
		
	})
	.on('fileuploaddone', function (e, data) {
		  swal({
			    title: "후기",
			    text: "작성되었습니다.",
			    timer: 3000,
			    confirmButtonText: "Ok!", 
			  }, function(){
				  location.href="viewSchedule.html?shceduleNo="+scheduleNo
			  });
			  setTimeout(function() {
				  location.href="viewSchedule.html?shceduleNo="+scheduleNo
			  }, 3000);
			
	})
	$(document).on('click','.addPost',function() {
		$('#routeNo').attr('value',$(this).attr('data-routeNo').trim());
	});
	
	$('#ajaxform').submit(function(event){
		if(filesList.length>0){
			event.preventDefault();
			$('#photoFile').fileupload('send', {
				files : filesList
			});
		}else{
			event.preventDefault();
			$('#photoFile').fileupload('send', {

				files :""

			});
		}
	});

	/******updatePost*********/
	$('#updatefile').fileupload({
		 replaceFileInput:false,autoUpload : false
	}).on('fileuploadadd', function(e, data) {
		filesList.pop(); 
		filesList.push(data.files[0]);
	}).on('fileuploaddone', function (e, data) {
		  swal({
			    title: "후기",
			    text: "수정되었습니다.",
			    timer: 3000,
			    confirmButtonText: "Ok!", 
			  }, function(){
				  location.href="viewSchedule.html?shceduleNo="+scheduleNo
			  });
			  setTimeout(function() {
				  location.href="viewSchedule.html?shceduleNo="+scheduleNo
			  }, 3000);
			
	});
	$(document).on('click','.update',function() {
		$('#updateRouteNo').attr('value',$(this).parents('.accordion').attr('data-routeNo').trim());
		getPictures();
	});
	
	/****************updatePost*********************/
	$('#update-Post').submit(function(event){
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

	$('#scheduleNo').attr('value',scheduleNo);
	
	mapNameSource = $('#mapData').html();
	mapNameTemplate = Handlebars.compile(mapNameSource);
	
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
        			initMap();/*toggle*/
        		}
			}
        }
        lastScroll = st;
	});
	
	// map 하단 텍스트 클릭시 좌측 스클롤 이동

	var maphover = true;
	$(document).on('click','.mapName',function(){
		
		$('.scroll').animate({scrollTop : $('.scroll').scrollTop()+$('.front[data-no='+$(this).attr('data-no')+']').position().top-baseHeight}, 400);
		
	})

	
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
});

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
	var out = times[1].substring(0,5);
	return out;
});
Handlebars.registerHelper("inc", function(value, options){
		    return parseInt(value) + 1;
});



/**********************addSchedule***************************/
function addSchedule(){
	$(document).on('click','#addSchedule',function() {
		swal({   
			title: "스케줄을 추가하시겠습니까?",   
			text: "버튼을 누르시면 추가가 완료됩나다.",     
			showCancelButton: true,   
			confirmButtonColor: "#59b3f1",   
			confirmButtonText: "추가",   
			closeOnConfirm: false }, 
			function(){
				location.href="/scheduler/scheduler.html?copyScheduleNo="+scheduleNo
		});
		
	});
}


function MySchedule() {
	$(document).on('click','#addSchedule',function() {
		swal({   
			title: "스케줄을 추가하시겠습니까?",   
			text: "버튼을 누르시면 추가가 완료됩나다.",     
			showCancelButton: true,   
			confirmButtonColor: "#59b3f1",   
			confirmButtonText: "추가",   
			closeOnConfirm: false }, 
			function(){
				location.href="/scheduler/scheduler.html?scheduleNo="+scheduleNo
		});
		
	});
}
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
