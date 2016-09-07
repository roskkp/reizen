//spot 초기화면 실행시...
function serchInfo(path, cid) {
	var sparqlPath = 'http://data.visitkorea.or.kr/sparql?format=json&query='+encodeURIComponent(path);
	$.ajax({
		url : reizenUrl + 'location/searchDetail.do',
		method : 'post',
		data : {
			'path' : sparqlPath,
			'contentId' : cid
		},
		dataType : 'json',
		success : function(result) {
			console.log(result)
			var data = result.data.results.bindings;
			var realData = [];
			var dataMap = new resMap();
			mapX = result.subData.mapX;
			mapY = result.subData.mapY;
			aroundCount();

			$.each(data[0], function(key, value) {
				if (key != 'resource' && key != 'name' && key != '상세설명' && key != 'img') {
					realData.push({
						cate : key,
						data : value.value
					});
				} else if (key == 'name') {
					$('.spot-name').append(value.value);
				} else if (key == '상세설명') {
					$('.overviewBox').html('<br/>'+value.value);
				}
			});
			dataMap.put("data", realData);
			$('.table').append(infoTemplate(dataMap.map));

			for (var i = 1; i <= data.length; i++) {
				$.each(data[i-1],function(key, value) {
					if (key == 'img' && i == 1) {
						$('.info-img1').attr("src",value.value);
					} else if (key == 'img'	&& i > 1) {
						$('.carousel-inner').append('<div class="item"><img class="info-img'+i+'" src="'+value.value+'" alt="'+i+'"><!-- 이미지'+i+' --><div class="carousel-caption"></div></div>');
					}
				})
			}

			if ($('.info-img1').attr("src") == '') {
				$('.info-img1').attr("src",result.subData.firstImage);
				if (result.subData.firstImage2 != '' ) {
					$('.carousel-inner').append('<div class="item"><img class="info-img2" src="'+result.subData.firstImage2+'" alt="2"><!-- 이미지2 --><div class="carousel-caption"></div></div>');
				}
			}

			$('.post').off().hover(function(event) { // 포스트 카드 마우스 오버시 반응
				$(this).children().first().animate({top:'15px',opacity:'1'});
				$(this).children().last().addClass('dash-hover');
			}, function(event) {
				$(this).children().first().animate({top:'0',opacity:'0'});
				$(this).children().last().removeClass('dash-hover');
			})

		}
	})
}

//spot 초기화면 실행시 주변 데이터 갯수 검색
function aroundCount(){
	$.ajax({
		url: nodeUrl+':9999/around/count.do?mapX='+mapX+'&mapY='+mapY,
		method: 'get',
		dataType: 'json',
		success: function(result){
			var tabSource = $('#recommandTab').text();
			var tabTemplate = Handlebars.compile(tabSource);
			var paneSource = $('#recommandPane').text();
			var paneTemplate = Handlebars.compile(paneSource);
			
			for (var i = 0; i < result.length; i++) {
				switch (result[i].tid) {
				case 12:
					result[i].name = '볼거리';
					break;
				case 14:
					result[i].name = '문화';
					break;
				case 15:
					result[i].name = '축제';
					break;
				case 28:
					result[i].name = '레포츠';
					break;
				case 32:
					result[i].name = '숙박';
					break;
				case 38:
					result[i].name = '쇼핑';
					break;
				case 39:
					result[i].name = '음식';
					break;
				}
				if (result[i].count != 0) {
					$('.nav-tabs').prepend(tabTemplate(result[i]));
					$('.tab-content').append(paneTemplate(result[i]));
				}
			}
		}
	})
}

function searchAround(tid){
	$.ajax({
		url: reizenUrl+'location/around.do?tid='+tid+'&mapX='+mapX+'&mapY='+mapY,
		method: 'get',
		dataType: 'json',
		success: function(result){
			if (result.status != 'success') {
				console.log('error');
				return;
			};
			
			i=0; // 탭 마다 처음 위치에 포스트를 넣기 위해 
			result.data.forEach(function(value,index){
				$('#recommand'+tid).find("."+ar[i]).append(recommandTemplate(value));// 핸들바스 이용 포스트 생성
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
	})
}

function scheduleData(contentId) {
	$.ajax({
		url : reizenUrl + 'scheduler/searchSchedule.do?cId='+contentId,
		method : 'get',
		dataType : 'json',
		success : function(result) {
			if (result.status != 'success') {
				console.log('error');
				return;
			};
			// 탭바 갯수 표시
			if (result.data.length == 0) { // 표시할 데이터가 제로라면 아예 삭제해버리고 다음꺼 보여줌
				$("a[href='#postcard']").parent().remove();
				$('#postcard').remove();
				$('.nav-tabs').children().first().addClass('active');
				$('.tab-content').children().first().addClass('active');
				return;
			} else {
				$("a[href='#postcard'] > span").text(result.data.length);
			}

			i=0; // 탭 마다 처음 위치에 포스트를 넣기 위해 
			result.data.forEach(function(value,index){
				value.img = result.img[index];
				$('#postcard').find("."+ar[i]).append(postcardTemplate(value));// 핸들바스 이용 포스트 생성
				i++;
				if(i>2){ // 3분할 화면이기에 
					i=0;
				}
			});
		}
	})
}

//좋아요, 스크랩 체크후 이벤트 걸어줌
function checkStatus(){
	$.ajax({
		url : reizenUrl + 'location/statusCheck.do?nick='+sessionStorage.getItem("nick")+'&cid='+contentId,
		method : 'get',
		dataType : 'json',
		success : function(result) {
			if (result.status != 'success') {
				console.log('error');
				return;
			}
			if(result.scrap == 'checked'){
				$('#scrap').css("color","pink").attr('data-active','true');
			}
			if(result.recm == 'checked'){
				$('#like').css("color","pink").attr('data-active','true');
			}
		}
	}) // ajax

	$('#scrap').on('click',function(){
		var url = null;
		var toggle = null;
		var $this = $(this);
		if($(this).attr('data-active') != 'true'){ // 기 추천 기록이 없다면
			toggle = false;
			url =  'location/addScrap.do?nick='+sessionStorage.getItem("nick")+'&cid='+contentId;
		}else{ // 기 추천 기록이 있다면
			toggle = true;
			url = 'location/delScrap.do?cid='+contentId;
		}
		$.ajax({
			url : reizenUrl + url,
			method : 'get',
			dataType : 'json',
			success : function(result) {
				if (result.status != 'success') {
					console.log('error');
					return;
				}
				if(toggle){
					$this.css("color","#ffffff").removeAttr('data-active');
				}else{
					$this.css("color","pink").attr('data-active','true');
				}
			}
		})//ajax
	});//scrap on click
	
	$('#like').on('click',function(){
		var url = null;
		var toggle = null;
		var $this = $(this);
		if($(this).attr('data-active') != 'true'){ // 기 추천 기록이 없다면
			toggle = false;
			url =  'location/addRecm.do?nick='+sessionStorage.getItem("nick")+'&cid='+contentId;
		}else{ // 기 추천 기록이 있다면
			toggle = true;
			url = 'location/delRecm.do?cid='+contentId;
		}
		$.ajax({
			url : reizenUrl + url,
			method : 'get',
			dataType : 'json',
			success : function(result) {
				if (result.status != 'success') {
					console.log('error');
					return;
				}
				if(toggle){
					$this.css("color","#ffffff").removeAttr('data-active');
				}else{
					$this.css("color","pink").attr('data-active','true');
				}
			}
		})// ajax
	}); // like on click
}// checkStatus()

function memoCheck(){
	$('#comment-input').val('');
	$('.comment-wrap').remove();

	$.ajax({
		url : reizenUrl + 'location/getMemo.do?cid='+contentId,
		method : 'get',
		dataType : 'json',
		success : function(result) {
			if (result.status != 'success') {
				console.log('error');
				return;
			}
			if (result.data.length == 0) { // 탭바 갯수 표시
				$("a[href='#memo'] > span").text("0");
				$('.comments').append('<span id="emptyMemo"> 아직 작성된 메모가 없네요 ! </span>');
			} else {
				$("a[href='#memo'] > span").text(result.data.length);
				$('#emptyMemo').remove();
				$('.comments').append(memoTemplate(result));
				$('.comment-wrap').each(function(){ // 메모 수정 권한 체크
					console.log($(this).find('.nick').text());
					if($(this).find('.nick').text()  == sessionStorage.getItem('nick')){
						$(this).find('.comment-actions').css('visibility','visible');
					}
				});
			}

		}
	})
}

function memoSubmit(){
	$.ajax({
		url : reizenUrl + 'location/writeMemo.do',
		method : 'post',
		dataType : 'json',
		data : {
			'content' : $('#comment-input').val(),
			'cid' : contentId,
			'dsno' : sessionStorage.getItem('dashNo')
		},
		success : function(result) {
			if (result.status != 'success') {
				console.log('error');
				return;
			}
			memoCheck();
			updateMemoAlarm();
		}
	})
}

function deleteMemo(mno){
	swal({   
		title: "Are you sure?",   
		text: "You will not be able to recover this imaginary file!",   
		type: "warning",   
		showCancelButton: true,   
		confirmButtonColor: "#DD6B55",   
		confirmButtonText: "Yes, delete it!",   
		closeOnConfirm: false }, 
		function(){
			$.ajax({
				url : reizenUrl + 'location/removeMemo.do?mno='+mno,
				method : 'get',
				dataType : 'json',
				success : function(result) {
					if (result.status != 'success') {
						swal("Failed!", "Failed to delete. Please contact your administrator", "error"); 
						console.log('error');
						return;
					}
					swal("Deleted!", "Your imaginary file has been deleted.", "success"); 
					memoCheck();
				}
			})
		});
}

function updateMemo(mno,content){
	$.ajax({
		url : reizenUrl + 'location/updateMemo.do',
		method : 'post',
		dataType : 'json',
		data : {
			'content' : content,
			'mno' : mno
		},
		success : function(result) {
			if (result.status != 'success') {
				console.log('error');
				return;
			}
			memoCheck();
		}
	})
}

function updateMemoAlarm(){
	$.ajax({
		url : reizenUrl + 'location/updateMemoAlarm.do?cid='+contentId,
		method : 'get',
		dataType : 'json',
		success : function(result) {
			if (result.status != 'success') {
				console.log('error');
				return;
			}
			console.log(result);
		}
	})
}