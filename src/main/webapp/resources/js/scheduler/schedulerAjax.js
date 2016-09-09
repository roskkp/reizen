function addScheduleAjax(eventDate){
	title = $('#title').val();
	if( title=='' || title==null ){
		title = '나만의 여행';
	}

	$.ajax({
		url : reizenUrl+'scheduler/addSchedule.do',
		method : 'POST',
		dataType : 'json',
		data : {
			'title' : title
		},
		success : function(result){
			if( ! result.status == 'success' ){
				sweetAlert('스케줄 생성 에러');
			}else{
				scheduleNo = result.scheduleNo;
				$('#schedule-title').text(title);
				$('#startDate').text(date);
			}//else
		}//success
	});
}

function searchAjax(){

	var keyword = $('#searchInput').val();
	var areaCode;

	if( ! $radio1.is(':checked') ){
		areaCode = $('#citySelect option:selected').val();
	}	

	$.ajax({
		url: reizenUrl+"location/searchkeyword.do",
		method: 'post',
		dataType: 'json',
		data: {
			'keyword': keyword,
			'areaCode': areaCode,
			'category' :  category,
			'date' : eventDate,
			'page': page,
			'size': 15
		},
		success: function(result){
			if (result.status != 'success') {
				console.log('search error');
				return;
			}else{
				$('#tip').remove();
				var data = result.data;
				if( data[0] != null ){
					var source = $('#searchResult').text();
					var template = Handlebars.compile(source);
					var resultset = template(result);

					var draggable = $('#draggable'); 
					infinityScroll = true;
					draggable.append(resultset);
					doDrag();

				}else {
					return;
				}
			}
		}
	});	// ajax
	baseMap();
}	// searchAjax

function deleteDayAjax(scheduleNo, day){
	$.getJSON(reizenUrl+'scheduler/deleteDay.do?scheduleNo='+scheduleNo+'&day='+day, 
			function(result){
		if( result.status != 'success' ){ console.log('day 삭제 실패'); }
		console.log('day'+day+' 삭제');
	});
}

function updateDayList(condition, scheduleNo, day){
	$.getJSON(reizenUrl+'scheduler/updateDay.do?scheduleNo='+scheduleNo+'&day='+day+'&condition='+condition,
			function(result){
		if( result.status != 'success' ){ console.log('day update 실패'); }
		console.log('day update 성공');
	});
}

function indexAjax(data){
	$.ajax({
		url: reizenUrl+'scheduler/arrayUpdate.do',
		method:'post',
		contentType:"application/json; charset=utf-8",
		data: JSON.stringify({data:data, currentDate:date+' '+data.time, scheduleNo:scheduleNo, day:day}),
		dataType:'json',
		success: function(result){
			if (result.status != 'success') {
				console.log('일정 변경 에러');
			}
			console.log('일정 변경 성공');
			listAjax(scheduleNo, day);
		}
	});
}

function addAjax(contentId, date, day, time, scheduleNo){
	$.getJSON('http://reizen.com:8890/scheduler/checkTime.do?scheduleNo='+scheduleNo+'&day='+day+'&time='+time, function(result){
		if(result.status=='exist'){ // 기 존재하는 시간이라면
			swal("Failed!", "해당 시간에는 다른 일정이 있네요!", "error"); 
			listAjax(scheduleNo, day);
		} else { 
			$.ajax({
				url: reizenUrl+'scheduler/addRoute.do?location.contentId='+contentId+'&travelSequence='+9999+'&time='+date+' '+time+'&scheduleNo='+scheduleNo+'&day='+day+'&currentDate='+date,
				method: 'get',
				dataType: 'json',
				success : function(result){
					if( result.status != 'success'){
						console.log('error addAjax');
					}
					listAjax(scheduleNo, day);
				}
			});		
		}

	});
}
function updateAjax(routeNo,date,day,time,scheduleNo){
	$.ajax({
		url: reizenUrl+'scheduler/updateRoute.do?routeNo='+routeNo+'&travelSequence='+9999+'&time='+date+' '+time+'&scheduleNo='+scheduleNo+'&day='+day+'&currentDate='+date,
		method: 'get',
		dataType: 'json',
		success : function(result){
			if( result.status != 'success'){
				swal("변경 실패!", "일정 변경중 에러가 발생 하였습니다.", "error");
				listAjax(scheduleNo, day);
				return;
			}
			swal({
				title: "변경 성공!", 
				text: "해당 일정을 변경 하였습니다.",
				type: "success"},
				function(isConfirm){
					if (isConfirm) {
						listAjax(scheduleNo, $('#daysInfo').attr('data-day'));
					}
				}); 
		}
	});		
}
function removeRouteAjax(routeNo){
	$.ajax({
		url : reizenUrl+'scheduler/removeRoute.do?routeNo='+routeNo,
		dataType : 'json',
		success : function(result){
			if(result.status=='success'){
				swal("삭제 성공!", "해당 일정을 삭제 하였습니다.", "success"); 
			}else{
				swal("삭제 실패!", "일정 삭제중 에러가 발생 하였습니다.", "error"); 
			}
		}
	});
}
function getTotal(scheduleNo){
	$.getJSON(reizenUrl+'scheduler/scheduleDayCount.do?scheduleNo='+scheduleNo, 
			function(result){
		if(result.status!='success'){
			return;
		}else{
			total = result.total;
		}
	});
}

function listAjax(scheduleNo, day){
	$.ajax({
		url : reizenUrl+'scheduler/scheduleList.do?scheduleNo='+scheduleNo+'&day='+day,
		method : 'GET',
		dataType : 'json',
		success : function(result){
			if( result.status != 'success' ){
				console.log('리스트 로딩 실패');
			}
			console.log('리스트 로딩');

			var listSource = $('#scheduleList').text();
			var listTemplate = Handlebars.compile(listSource);
			$('#sortable').empty();
			$('#sortable').append(listTemplate(result));
			$('#schedule-title').text(title);
			$('#startDate').text(date);

			routeData = new Array();
			for (var i = 0; i < result.list.length; i++) {
				var routeInfo = new Object();
				routeInfo.cid = result.list[i].location.contentId;
				routeInfo.mapX = result.list[i].location.mapX;
				routeInfo.mapY = result.list[i].location.mapY;
				routeInfo.index = i;
				routeInfo.sdno = scheduleNo;
				routeInfo.day = day;
				if (result.list[i].location.locateName.length > 10) {
					routeInfo.name = result.list[i].location.locateName.substr(0,10);
				} else {
					routeInfo.name = result.list[i].location.locateName;
				}

				routeData.push(routeInfo);
			}
			var $i=$('i');
			for (var i = 0; i < $i.size(); i++) {
				if ($($i[i]).attr('data-type') != null) {
					switch ($($i[i]).attr('data-type')) {
					case '12': // 관광
						$($i[i]).addClass('fa-camera');
						break; 
					case '14': // 문화
						$($i[i]).addClass('fa-university');
						break;
					case '15': // 축제
						$($i[i]).addClass('fa-star');
						break;
					case '28': // 레포츠
						$($i[i]).addClass('fa-motorcycle');
						break;
					case '32': // 숙박
						$($i[i]).addClass('fa-hotel');
						break;
					case '38': // 쇼핑
						$($i[i]).addClass('fa-shopping-bag');
						break;
					case '39': // 음식
						$($i[i]).addClass('fa-cutlery');
						break;
					}
				}
			}; // for
			baseMap();
		}// success
	});
	$.getJSON('http://reizen.com:8889/scheduler/checkDay.do?scheduleNo='+scheduleNo, function(result){
		if(result.length<=0){ // 스케줄이 없다면 
			emptySchedule = true;
		}else{
			emptySchedule = false;
		}
	});
}

function deleteScheduleAjax(scheduleNo){
	console.log(reizenUrl);
	$.ajax({
		url : reizenUrl+'dashboard/removeplan.do?scdNo='+scheduleNo,
		method : 'GET',
		dataType : 'json',
		success : function(result){
			if( result.status != 'success' ){
				console.log('삭제 실패');
			}
			console.log('삭제 성공');
		}
	});
}

function bestRoute(){
	$.ajax({
		url : reizenUrl + 'scheduler/bestRoute.do',
		method : 'POST',
		data : {
			data : JSON.stringify(eval(routeData)),
			startNo : $('.btnStart').attr('data-start'),
			endNo : $('.btnEnd').attr('data-end')
		},
		dataType : 'json',
		success : function(result){
			routeSeq = result.data;
			spotPath.setMap(null);
			for (var i = 0; i < marker.length; i++) {
				marker[i].setMap(map);
			}
			var departure;
			var arrival;
			var labels = '123456789';
			for (var i = 0; i < routeSeq.length; i++) {
				marker=[];
				marker[i] = new google.maps.Marker({
					position: {lat: parseFloat(routeSeq[i].map.mapY), lng: parseFloat(routeSeq[i].map.mapX)},
					map: routeMap,
					label: labels[i]
				});
				if (i != routeSeq.length-1) {
					drawLine(new google.maps.LatLng(routeSeq[i].map.mapY, routeSeq[i].map.mapX),new google.maps.LatLng(routeSeq[i+1].map.mapY, routeSeq[i+1].map.mapX),i);	
				}

			}

		}
	})
}

function bestRouteUpdate(){
	for (var i = 0; i < routeSeq.length; i++) {
		routeSeq[i].map.index = i;
	}
	$.ajax({
		url : reizenUrl + 'scheduler/bestRouteUpdate.do',
		method : 'POST',
		data : {
			data : JSON.stringify(routeSeq)
		},
		dataType : 'json',
		success : function(result){
			if (result.status != "success") {
				return;
			}

			listAjax(scheduleNo, day);

		}
	})
}

function dataTheorem(){
	var routeData2 = new Array();
	var routeInfo = new Object();
	routeInfo.cid = routeData[$('.btnStart').attr('data-start')].cid;
	routeInfo.mapX = routeData[$('.btnStart').attr('data-start')].mapX;
	routeInfo.mapY = routeData[$('.btnStart').attr('data-start')].mapY;
	routeInfo.index = 1;
	routeInfo.sdno = scheduleNo;
	routeInfo.day = day;
	if (routeData[$('.btnStart').attr('data-start')].name > 10) {
		routeInfo.name = routeData[$('.btnStart').attr('data-start')].name.substr(0,10);
	} else {
		routeInfo.name = routeData[$('.btnStart').attr('data-start')].name;
	}
	routeData2.push(routeInfo);

	for (var i = 0; i < routeData.length; i++) {
		if (i != $('.btnStart').attr('data-start') && i != $('.btnEnd').attr('data-end')) {
			routeInfo = new Object();
			routeInfo.cid = routeData[i].cid;
			routeInfo.mapX = routeData[i].mapX;
			routeInfo.mapY = routeData[i].mapY;
			routeInfo.index = 1;
			routeInfo.sdno = scheduleNo;
			routeInfo.day = day;
			if (routeData[i].name > 10) {
				routeInfo.name = routeData[i].name.substr(0,10);
			} else {
				routeInfo.name = routeData[i].name;
			}
			routeData2.push(routeInfo);
		}
	}

	routeInfo = new Object();
	routeInfo.cid = routeData[$('.btnEnd').attr('data-end')].cid;
	routeInfo.mapX = routeData[$('.btnEnd').attr('data-end')].mapX;
	routeInfo.mapY = routeData[$('.btnEnd').attr('data-end')].mapY;
	routeInfo.index = 1;
	routeInfo.sdno = scheduleNo;
	routeInfo.day = day;
	if (routeData[$('.btnEnd').attr('data-end')].name > 10) {
		routeInfo.name = routeData[$('.btnEnd').attr('data-end')].name.substr(0,10);
	} else {
		routeInfo.name = routeData[$('.btnEnd').attr('data-end')].name;
	}
	routeData2.push(routeInfo);

	routeData = routeData2;

}

function aroundSearch(mapX,mapY){
	$('#draggable').empty();
	console.log(mapX+'ddd'+mapY);
	$.ajax({
		url : reizenUrl+'location/aroundList.do?mapX='+mapX+'&mapY='+mapY+'&tid='+typeId+'&size=100&page=1',
		method: 'GET',
		dataType: 'json',
		success: function(result){
			if (result.status != 'success') {
				console.log('search error');
				return;
			}else{
				$('#tip').remove();
				var data = result.data;
				var maps = []; 
				if(data.length>0){
					var source = $('#searchResult').text();
					var template = Handlebars.compile(source);
					var resultset = template(result);

					var draggable = $('#draggable'); 
					draggable.append(resultset);
					doDrag();

					for(var i=0; i<result.data.length; i++){
						var lat = parseFloat(result.data[i].mapY);
						var lng = parseFloat(result.data[i].mapX);
						maps.push({lat:lat, lng:lng});
					}
					pointMap(mapX, mapY, maps);
					infinityScroll = false;
				}else{
					
					swal("데이터가 없어요 :(", "", "error"); 
					return ;
				}
			}
		}
	});
}