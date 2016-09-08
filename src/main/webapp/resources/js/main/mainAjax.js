$( "#areaBar" ).autocomplete({
	source : function( request, response ) {
		$.ajax({
			type: 'post',
			url: reizenUrl+"location/searchareacode.do",
			dataType: "json",
			data: { value : request.term },
			success: function(result) {
				if(result.status != 'success'){
					console.log('error');
					return;
				} 
				console.log('success');
				var data = result.data;
				searchData = data;
				//서버에서 json 데이터 response 후 목록에 뿌려주기 위함
				response( 
						$.map(data, function(item) {
							if ((item.split("/")).length == 2) {
								return {
									label: item.split("/")[0]+" ",
									value: item.split("/")[0]+" "
								}	
							}
							return {
								label: item.split("/")[0]+" "+item.split("/")[1]+" ",
								value: item.split("/")[0]+" "+item.split("/")[1]+" "
							}
						})
				);

			}
		});
	},
	//조회를 위한 최소글자수
	minLength: 1,
	select: function( event, ui ) {
		areaCode = '';
		localCode = '';
		if ( searchData[0] != null ) {
			for (var i = 0; i < searchData.length; i++) {
				var datas = searchData[i].split("/");
				if (datas[0]+" "+datas[1]+" " == ui.item.value && datas.length == '4') {
					areaCode = searchData[i].split("/")[2];
					localCode = searchData[i].split("/")[3];
				} else if (datas[0]+" " == ui.item.value && datas.length == '2') {
					areaCode = searchData[i].split("/")[1];
				}
			}
		} else {
			areaCode = searchData.split("/")[2];
			localCode = searchData.split("/")[3];
		}
		console.log("selected: "+ui.item.value);
		console.log("areaCode: "+areaCode);
		console.log("localCode: "+localCode);
	}
});

$( "#searchBar" ).autocomplete({
	source : function( request, response ) {
		$.ajax({
			type: 'post',
			url: reizenUrl+"location/searchKeywordAuto.do",
			dataType: "json",
			data: {
				'keyword': $('#searchBar').val(),
				'areaCode': areaCode,
				'localCode': localCode,
				'cateS': JSON.stringify(cateS).replace('[','').replace(']','')
			},
			success: function(result) {
				if(result.status != 'success'){
					console.log('error');
					return;
				} 
				console.log('success');
				var data = result.data;
				searchData = data;
				//서버에서 json 데이터 response 후 목록에 뿌려주기 위함
				response( 
						$.map(data, function(item) {
							return {
								label: item,
								value: item
							}
						})
				);

			}
		});
	},
	//조회를 위한 최소글자수
	minLength: 1,
});

function searchLocation(){
	for (var i = 0; i < $('.filter-value').length; i++) {
		console.log($($('.filter-value')[i]).length)	
	}
	
	cateS = categoryJson(cateS,9);
	cateL = categoryJson(cateL,3);
	$.ajax({
		url: reizenUrl+"location/searchkeyword.do",
		method: 'post',
		dataType: 'json',
		data: {
			'keyword': keyword,
			'areaCode': areaCode,
			'localCode': localCode,
			'page': locationPage,
			'size': size,
			'cateS': JSON.stringify(cateS).replace('[','').replace(']',''),
			'cateL': JSON.stringify(cateL).replace('[','').replace(']','')
		},
		success: function(result){
			if (result.status == 'success') {
				$('.locationList > div').empty();
				if (result.data == 'noData' || result.data.length == 0) {
					swal({
						title: "",
						text: "장소 데이터가 없습니다.",
						type: "warning",
						confirmButtonText: "확인"
					});
					shcedulePage = 0;
					$('#scheduleRefresh').text('처음으로');
				}
				i=0; // 탭 마다 처음 위치에 포스트를 넣기 위해 
				result.data.forEach(function(value,index){ 
					$('.locationList').find("."+ar[i]).append(locationTemplate(value));// 핸들바스 이용 포스트 생성
					i++;
					if(i>2){ // 3분할 화면이기에 
						i=0;
					}
				});	

				$('.post').off().hover(function(event) { // 포스트 카드 마우스 오버시 반응
					$(this).children().first().animate({top:'15px',opacity:'1'});
					$(this).children().last().addClass('dash-hover');
				}, function(event) {
					$(this).children().first().animate({top:'0',opacity:'0'});
					$(this).children().last().removeClass('dash-hover');
				})
			}
		},
		error : function() {
			console.log('error');
		}
	})
}


function searchSchedule(){
	$.ajax({
		url: reizenUrl+"scheduler/searchkeyword.do",
		method: 'post',
		dataType: 'json',
		data: {
			'keyword': keyword,
			'areaCode': areaCode,
			'localCode': localCode,
			'page': schedulePage,
			'size': size,
			'nop': nop,
			'month': month,
			'term': term
		},
		success: function(result){
			if (result.status == 'success') {
				$('.scheduleList > div').empty();
				if (result.data == 'noData' || result.data.length == 0) {
					swal({
						title: "",
						text: "일정 데이터가 없습니다.",
						type: "warning",
						confirmButtonText: "확인"
					});
					schedulePage = 0;
					$('#scheduleRefresh').text('처음으로');
				}

				i=0; // 탭 마다 처음 위치에 포스트를 넣기 위해 
				result.data.forEach(function(value,index){
					$('.scheduleList').find("."+ar[i]).append(scheduleTemplate(value));// 핸들바스 이용 포스트 생성
					i++;
					if(i>2){ // 3분할 화면이기에 
						i=0;
					}
				});
				
				var imgs = result.img; 

				if (imgs.length < 5) {
					schedulePage = 0;
					$('#scheduleRefresh').text('처음으로');
				}

				imgs.forEach(function(value,index){
					$($('.sceduleImg')[index]).attr('src',value);
				});

				$('.post').off().hover(function(event) { // 포스트 카드 마우스 오버시 반응
					$(this).children().first().animate({top:'15px',opacity:'1'});
				}, function(event) {
					$(this).children().first().animate({top:'0',opacity:'0'});
				})
			}
		},
		error : function() {
			console.log('error');
		}
	})
}


function searchCategoryM(cate01){
	$.ajax({
		url: "http://reizen.com:8888/category/middle.do?cate01="+cate01,
		dataType: 'json',
		success: function(result){
			$('.subclass-list').empty();
			$('.subclass-list').append(cateTemplate(result));
		}
	})
}
