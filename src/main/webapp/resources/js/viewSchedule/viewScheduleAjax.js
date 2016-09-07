function getUser(){
	$.ajax({
		url : reizenUrl + "postscript/userPost.do?scheduleNo="+scheduleNo,
		method : 'post',
		dataType : 'json',
		data : {
			scheduleNo : scheduleNo
		},
		success : function(result) {
			if (result.status == 'success') {
				var data = result.data;
			
				for (var i = 0; i < data.length; i++) {
					var ti = data[data.length -1];
					var t = ti.time;
					var timed = t.split(' ')[0];
					console.log("timed:::::"+timed);
					var da = data[0].time 
					var time = da.split(' ')[0];
					console.log(time);
					$('.username').text(data[i].schedule.user.nickName);
					$('.description').text(time+"~"+timed);
					$('.bio').text(data[i].schedule.title);
					$('.heart').text(" "+data[i].schedule.scrapCount);
					$('.fa-calendar-minus-o').text(" "+data[i].schedule.recommandCount);
					console.log(data[i].schedule.user.thumbNail);
					if (data[i].schedule.user.thumbNail != null) {
						$('.avatar').attr("src","/resources/images/thumbnail/"+ data[i].schedule.user.thumbNail);
					}
					else{
						$('.avatar').attr("src","/resources/images/thumbnail/thumnail.jpg");
					}
					console.log('마지막 data'+data[i].time);
				}

			}
		}

	});
}
/****************getPictures********************/
function getPictures(){
	alert('들어왔나여?')
	$.ajax({
		url : reizenUrl + "postscript/selectPict.do",
		method : 'post',
		dataType : 'json',
		data : {
			routeNo :$('#updateRouteNo').val()
		},
		success : function(result) {
			if (result.status == 'success') {
				alert('들어왔나여?')
				
				var data = result.data;
			for (var i = 0; i < data.length; i++) {
					console.log(data[i].transportation);
				/*$('#updatefile').css({'background' : 'url(\''
					+'/resources/images/viewSchedule/' +data[i].picturePath});*/
				$('#updatePrice').val(data[i].price);
				$(".select").val(data[i].transportation);
				/*$('.select-options').attr(data[i].transportation);*/
				$('.cont').val(data[i].content);
				
				}
			}
		}
	})
}



/**************스크랩******************/
function userScheduleAjax(){
	$.ajax({
		url : reizenUrl + 'postscript/sscrs.do?',
		data:{
			scheduleNo:scheduleNo
		},
		method : 'post',
		dataType : 'json',
		success : function(result) {
				if (result.status != 'success') {
					console.log('일정 불러오기 실패');
					return;
				}
				
				console.log('성공');		
		}
	
	});

}

/***************user nono******************/
function userScheduleAjax(){
	var source = $('#schedule').html();
	var template = Handlebars.compile(source);
	$.ajax({
		url : reizenUrl + 'postscript/postscript.do?scheduleNo=' + scheduleNo,
		method : 'get',
		dataType : 'json',
		success : function(result) {
			var list = result.list;
			listData = list;
				if (result.status != 'success') {
					console.log('일정 불러오기 실패');
					return;
				}

				$('ol.timeline').append(template(result));
				
				var allPanels = $('.postBox').fadeOut();
				$('.accordion').accordion({

					collapsible : true
				});
				baseHeight = $('.fa-camera-retro').position().top-83;

				mapDay = 1;
				initMap();
				
				$(document).on('click','.deletePicts',function(){
					$.ajax({
						url : reizenUrl + 'postscript/deletePicts.do',
						dataType : 'json',
						data : {
							pictureNo :$('.pictsNo').val(),
							
						},
						method : 'post',
						success : function(result) {
							if (result.status != 'success') {
								alert('후기 삭제 에러');
							}
							alert('성공');
							location.reload();
						}
					})
				})
				
				for (var i = 0; i < $('.tran').length; i++) {
						console.log($($('.tran')[i]).attr('data-trans'));
						switch ($($('.tran')[i]).attr('data-trans')) {
						case '1':
							$($('.tran')[i]).text('이동수단 : 자동차')
							break;
						case '2':
							$($('.tran')[i]).text('이동수단 : 기차')
							break;
						case '3':
							$($('.tran')[i]).text('이동수단 : 버스')
							break;
						case '4':
							$($('.tran')[i]).text('이동수단 : 차')
							break;
						
					}
					}
				
				
				for(var i=0; i<list.length; i++){
					console.log('picture'+list[i].picturePath);
					if(list[i].picturePath == null){
						console.log('picture'+list[i].picturePath);
						$('.scriptImage').remove();
					}
				
				}
		}
	
	
	
	
	});

}

/** ************ 일정보기 ************* */
function scheduleAjax() {
	var source = $('#scheduleList').html();
	var template = Handlebars.compile(source);
	$.ajax({
		url : reizenUrl + 'postscript/postscript.do?scheduleNo=' + scheduleNo,
		method : 'get',
		dataType : 'json',
		success : function(result) {
			var list = result.list;
			listData = list;
				if (result.status != 'success') {
					console.log('일정 불러오기 실패');
					return;
				}
				for(var i=0; i<list.length; i++){
					if(list[i].content != null || list[i].picturePath != null){
						console.log('null check')
						list[i].check = 'true';
					}
				
				
				}
				$('ol.timeline').append(template(result));
				
					/*for(var i=0; i<list.length; i++){*/
					for (var i = 0; i < $('.tran').length; i++) {
						console.log($($('.tran')[i]).attr('data-trans'));
						switch ($($('.tran')[i]).attr('data-trans')) {
						case '1':
							$($('.tran')[i]).text('이동수단 : 자동차')
							break;
						case '2':
							$($('.tran')[i]).text('이동수단 : 기차')
							break;
						case '3':
							$($('.tran')[i]).text('이동수단 : 버스')
							break;
						case '4':
							$($('.tran')[i]).text('이동수단 : 차')
							break;
						
					}
					}
					
				
				

				$(document).on('click', '.deletePicts', function(e) { // 삭제버튼 이벤트 
					var $this= $(this);
					swal({   
						title: "Are you sure?",   
						text: "You will not be able to recover",   
						type: "warning",   
						showCancelButton: true,   
						confirmButtonColor: "#DD6B55",   
						confirmButtonText: "Yes, delete it!",   
						closeOnConfirm: false }, 
						function(){
							$.ajax({
								url : reizenUrl + 'postscript/deletePicts.do',
								dataType : 'json',
								data : {
									pictureNo :$('.pictsNo').val(),
									
								},
								method : 'post',
								success : function(result) {
									if (result.status != 'success') {
										alert('후기 삭제 에러');
									}
									swal("Deleted!", "Your imaginary file has been deleted.", "success");
									location.reload();
									
								}
							})
							 
					});
				});


				var allPanels = $('.postBox').fadeOut();
				$('.accordion').accordion({

					collapsible : true
				});
				baseHeight = $('.fa-camera-retro').position().top-83;

				mapDay = 1;
				initMap();
				
			
		}
	});
}

/***********************user****************************//*
function getUser(){
	$.ajax({
		url : reizenUrl + "postscript/userPost.do?scheduleNo="+scheduleNo,
		method : 'post',
		dataType : 'json',
		data : {
			scheduleNo : scheduleNo
		},
		success : function(result) {
			if (result.status == 'success') {
				var data = result.data;
			}
		}
	})
}
*/

/**********************post*******************************//*
function addPostAjax() {
	$.ajax({
		url : reizenUrl + 'postscript/addPost.do',
		dataType : 'json',
		data : {
			scheduleNo : scheduleNo,
			picturePath : $('#photoFile').val(),
			content : $('.cont').val(),
			routeNo : $('.btn-default').attr('routeNo'),
			transportation : $(".select option:selected").val(),
			price : $('.price').val()

		},
		method : 'post',
		success : function(result) {
			if (result.status != 'success') {
				alert('후기 추가 에러');
			}
			alert('성공');
			location.reload();
		}
	})
}*/
