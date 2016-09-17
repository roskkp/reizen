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
					var da = data[0].time 
					var time = da.split(' ')[0];
					$('.username').text(data[i].schedule.user.nickName);
					$('.description').text(time+"~"+timed);
					$('.bio').text(data[i].schedule.title);
					$('.fa-calendar-minus-o').text(" "+data[i].schedule.scrapCount);
					$('.heart').text(" "+data[i].schedule.recommandCount);
					if (data[i].schedule.user.thumbNail != null) {
						$('.avatar').attr("src","/resources/images/thumbnail/"+ data[i].schedule.user.thumbNail);
					}
					else{
						$('.avatar').attr("src","/resources/images/thumbnail/thumnail.jpg");
					}
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
function number() {
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
				data[i].schedule.scrapCount
				data[i].schedule.recommandCount
			
				}

			}
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
				
			
			/*	
				var allPanels = $('.postLength').hide();
				$('.accordion').on('click',function(){
					alert('ds');
					$('.postLength').show();
				});*/
				/*$('.accordion').accordion({

					collapsible : true
				});*/
				baseHeight = $('.front').position().top-83;

				mapDay = 1;
				initMap();
				
				$(document).on('click','.del',function(){
					$.ajax({
						url : reizenUrl + 'postscript/deletePicts.do',
						dataType : 'json',
						data : {
							pictureNo :$('.pictsNo').val()
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
				});
				
				for (var i = 0; i < $(".timeline").children(".front").length; i++) {
					console.log('카테고리 테스트'+$($('.front')[i]).attr('data-locate'));
					switch ($($('.timeline').children(".front")[i]).attr('data-locate')) {
					case 'A01':
						$($('.timeline').children(".front")[i]).addClass('fa fa-envira')
						break;
					case 'A02':
						$($('.timeline').children(".front")[i]).addClass('fa fa-bank')
						break;
					case 'A03':
						$($('.timeline').children(".front")[i]).addClass('fa fa-anchor')
						break;
					case 'A04':
						$($('.timeline').children(".front")[i]).addClass('fa fa-shopping-bag')
						break;
					case 'A05':
						$($('.timeline').children(".front")[i]).addClass('fa fa-cutlery')
						break;
					case 'B02':
						$($('.timeline').children(".front")[i]).addClass('fa fa-hotel')
						break;
					case 'A03':
						$($('.timeline').children(".front")[i]).addClass('fa fa-camera-retro')
						break;
					default:
						$($('.timeline').children(".front")[i]).addClass('fa fa-camera-retro')
						break;
					}
					
					
				}
				
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
							$($('.tran')[i]).text('이동수단 : 비행기')
							break;
						
					}
				}
				
				
				var size = $('.scriptImage').length;
				for (var i = size-1; i >= 0; i--) {
					if($($('.scriptImage')[i]).attr('src') == "/resources/images/viewSchedule/"){
						console.log('여기들어왔나여?');
						$($('.scriptImage')[i]).remove();
					}
				}
				
				$('.accordion').hide();

				$(".sho").on('click', function() {
					$(this).parents('article').next().toggle(500);
				});
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
							$($('.tran')[i]).text('이동수단 : 비행기')
							break;
						
					}
					}
					
				for (var i = 0; i < $(".timeline").children(".front").length; i++) {
					switch ($($('.timeline').children(".front")[i]).attr('data-locate')) {
					case 'A01':
						$($('.timeline').children(".front")[i]).addClass('fa fa-envira')
						break;
					case 'A02':
						$($('.timeline').children(".front")[i]).addClass('fa fa-bank')
						break;
					case 'A03':
						$($('.timeline').children(".front")[i]).addClass('fa fa-anchor')
						break;
					case 'A04':
						$($('.timeline').children(".front")[i]).addClass('fa fa-shopping-bag')
						break;
					case 'A05':
						$($('.timeline').children(".front")[i]).addClass('fa fa-cutlery')
						break;
					case 'B02':
						$($('.timeline').children(".front")[i]).addClass('fa fa-hotel')
						break;
					case 'A03':
						$($('.timeline').children(".front")[i]).addClass('fa fa-camera-retro')
						break;
					default:
						$($('.timeline').children(".front")[i]).addClass('fa fa-camera-retro')
						break;
					}
					
					
				}
					
				var size = $('.scriptImage').length;
				for (var i = size-1; i >= 0; i--) {
					if($($('.scriptImage')[i]).attr('src') == "/resources/images/viewSchedule/"){
						$($('.scriptImage')[i]).remove();
					}
				}

									
					$('.accordion').hide();

					$(".sho").on('click', function() {
						$(this).parents('article').next().toggle(500);
					});
				
				$(document).on('click', '.del', function(e) {
					alert('dsada');// 삭제버튼 이벤트 
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
							alert('dsad');
							$.ajax({
								url : reizenUrl + 'postscript/deletePicts.do',
								dataType : 'json',
								data : {
									pictureNo :$this.attr('data-pictsNo'),
									routeNo:$('.accordion').attr('data-routeNo')
									
								},
								method : 'post',
								success : function(result) {
									if (result.status != 'success') {
										alert('후기 삭제 에러');
									}
									  swal({
										    title: "후기",
										    text: "삭제되었습니다.",
										    timer: 3000,
										    confirmButtonText: "Ok!", 
										  }, function(){
										    window.location.reload();
										  });
										  setTimeout(function() {
										    window.location.reload();
										  }, 3000);
							
								}
							})
							 
					});
				});


		/*		var allPanels = $('.postBox').fadeOut();
				$('.accordion').accordion({

					collapsible : true
				});*/
				baseHeight = $('.front').position().top-83;

				mapDay = 1;
				initMap();
				
			
		}
	});
}


/***************좋아영**********************/
function usersrAjax() {

	$.ajax({
		url : reizenUrl + 'postscript/checkRecm.do?scheduleNo='+scheduleNo,
		method : 'get',
		dataType : 'json',
		success : function(result) {
			if (result.status != 'success') {
				console.log('error');
				return;
			}
			console.log(result.scrap);
			if(result.scrap == 'checked'){
				$('.fa-calendar-minus-o').css("color","pink").attr('data-active','true');
			}
			if(result.recm == 'checked'){
				$('.heart').css("color","pink").attr('data-active','true');
			}
		}
	}) // ajax

	$('.scheduleButton').on('click',function(){

		var url = null;
		var toggle = null;
		if($('.fa-calendar-minus-o').attr('data-active') != 'true'){ // 기 추천 기록이 없다면
			toggle = false;
			url =  'postscript/sscrs.do?scheduleNo='+scheduleNo;
		}else{ // 기 추천 기록이 있다면
			toggle = true;
			url = 'postscript/deleteScrap.do?scheduleNo='+scheduleNo;
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
								
								  swal({
									    title: "스크랩",
									    text: "취소되셨습니다.",
									    timer: 3000,
									    confirmButtonText: "Ok!",
									  })
									   $('.fa-calendar-minus-o').text(' '+" "+data[i].schedule.scrapCount);
									$('.fa-calendar-minus-o').css("color","#ffffff").removeAttr('data-active');
						
								}

							}
						}

					});
				
				}else{
					
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
								
								  swal({
									    title: "후기",
									    text: "스크랩되셨습니다.",
									    timer: 3000,
									    confirmButtonText: "Ok!", 
									  })
									 $('.fa-calendar-minus-o').text(' '+" "+data[i].schedule.scrapCount);
									 $('.fa-calendar-minus-o').css("color","pink").attr('data-active','true');
								}

							}
						}

					});
					
				
			
					
				}
			}
		})//ajax
	});//scrap on click
	
	$('.heart').on('click',function(){
		var url = null;
		var toggle = null;
		var $this = $(this);
		if($(this).attr('data-active') != 'true'){ // 기 추천 기록이 없다면
			toggle = false;
			url =  'postscript/srecm.do?scheduleNo='+scheduleNo;
			
		}else{ // 기 추천 기록이 있다면
			toggle = true;
			url = 'postscript/deleteRecm.do?scheduleNo='+scheduleNo;
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
								$this.text(' '+data[i].schedule.recommandCount);
								$this.css("color","#ffffff").removeAttr('data-active');
								}

							}
						}

					});
				
				}else{
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
								$this.text(' '+data[i].schedule.recommandCount)
								$this.css("color","pink").attr('data-active','true');
								}

							}
						}

					});
					
					
				}
			}
		})// ajax
	}); // like on click
	
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
