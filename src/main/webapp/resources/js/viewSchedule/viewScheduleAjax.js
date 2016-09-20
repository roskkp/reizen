function getUser(){
	$.ajax({
		url : reizenUrl + "postscript/userPost.do?scheduleNo="+scheduleNo,
		method : 'post',
		dataType : 'json',
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
					$('.fa-calendar-minus-o').next().text(" "+data[i].schedule.scrapCount);
					$('.heart').next().text(" "+data[i].schedule.recommandCount);
					var dashNo =data[i].schedule.user.dashNo
					$(document).on('click','.username',function(e){ // 닉네임 클릭하면 해당 회원 dashboard로 가게
						location.href='dashboard.html?no='+dashNo
						e.preventDefault();
					});	


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
	$.ajax({
		url : reizenUrl + "postscript/selectPict.do",
		method : 'post',
		dataType : 'json',
		data : {
			routeNo :$('#updateRouteNo').val()
		},
		success : function(result) {
			if (result.status == 'success') {
				var data = result.data;
			for (var i = 0; i < data.length; i++) {
				$('#updatePrice').val(data[i].price);
				$(".select").val(data[i].transportation);
				$('.cont').val(data[i].content);
				}
			}
		}
	})
}


/**************스크랩******************/
function number() {
	$.ajax({
		url : reizenUrl + "postscript/userPost.do",
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
				console.log(result)
				$('ol.timeline').append(template(result));
				
				baseHeight = $('.front').position().top-83;

				mapDay = 1;
				initMap();
				
				for (var i = 0; i < $(".timeline").children(".front").length; i++) {
					console.log($($('.timeline').children(".front")[i]).attr('data-locate'))
					switch ($($('.timeline').children(".front")[i]).attr('data-locate')) {
					case '12':
						$($('.timeline').children(".front")[i]).addClass('fa fa-camera')
						break;
					case '14': // 문화
						$($('.timeline').children(".front")[i]).addClass('fa fa-camera');
						break;
					case '15': // 축제
						$($('.timeline').children(".front")[i]).addClass('fa fa-star');
						break;
					case '28': // 레포츠
						$($('.timeline').children(".front")[i]).addClass('fa fa-motorcycle');
						break;
					case '32': // 숙박
						$($('.timeline').children(".front")[i]).addClass('fa fa-hotel');
						break;
					case '38': // 쇼핑
						$($('.timeline').children(".front")[i]).addClass('fa fa-shopping-bag');
						break;
					case '39': // 음식
						$($('.timeline').children(".front")[i]).addClass('fa fa-cutlery');
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
						$($('.scriptImage')[i]).remove();
					}
				}
				$('.accordion').hide();
				$(".show").on('click', function() {
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
						list[i].check = 'true';
					}
				}
				$('ol.timeline').append(template(result));
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
					case '12':
						$($('.timeline').children(".front")[i]).addClass('fa fa-camera')
						break;
					case '14': // 문화
						$($('.timeline').children(".front")[i]).addClass('fa fa-university');
						break;
					case '15': // 축제
						$($('.timeline').children(".front")[i]).addClass('fa fa-star');
						break;
					case '28': // 레포츠
						$($('.timeline').children(".front")[i]).addClass('fa fa-motorcycle');
						break;
					case '32': // 숙박
						$($('.timeline').children(".front")[i]).addClass('fa fa-hotel');
						break;
					case '38': // 쇼핑
						$($('.timeline').children(".front")[i]).addClass('fa fa-shopping-bag');
						break;
					case '39': // 음식
						$($('.timeline').children(".front")[i]).addClass('fa fa-cutlery');
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

				$(".show").on('click', function() {
					$(this).parents('article').next().toggle(500);
				});
				
				$(document).on('click', '.del', function(e) {
					var $this= $(this);
		               swal({   
		                   title: "삭제 하시겠습니까?",   
		                   text: "삭제 버튼을 누르시면 삭제됩니다.",   
		                   type: "warning",   
		                   showCancelButton: true,   
		                   confirmButtonColor: "#DD6B55",  
		                   confirmButtonText: "삭제",    
		                   cancelButtonText: "취소",   
		                   closeOnConfirm: false }, 
						function(){
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
										swal('후기 삭제 에러');
									}
									  swal({
										    title: "후기",
										    text: "삭제되었습니다.",
										    timer: 3000,
										    confirmButtonText: "Ok!", 
										  }, function(){
										   location.href="viewSchedule.html?shceduleNo="+scheduleNo
										  });
										  setTimeout(function() {
											  location.href="viewSchedule.html?shceduleNo="+scheduleNo
										  }, 3000);
							
								}
							})
							 
					});
				});
				baseHeight = $('.front').position().top-83;
				mapDay = 1;
				initMap()
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
			if(result.scrap == 'checked'){
				$('.fa-calendar-minus-o').css("color","pink").attr('data-active','true');
			}
			if(result.recm == 'checked'){
				$('.fa-heart').css("color","pink").attr('data-active','true');
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
						url : reizenUrl + "postscript/userPost.do",
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
									   $('.fa-calendar-minus-o').next().text(' '+" "+data[i].schedule.scrapCount);
									$('.fa-calendar-minus-o').css("color","#ffffff").removeAttr('data-active');
						
								}

							}
						}

					});
				
				}else{
					
					$.ajax({
						url : reizenUrl + "postscript/userPost.do",
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
									 $('.fa-calendar-minus-o').next().text(' '+" "+data[i].schedule.scrapCount);
									 $('.fa-calendar-minus-o').css("color","pink").attr('data-active','true');
								}

							}
						}

					});
				}
			}
		})//ajax
	});//scrap on click
	
	$('.fa-heart').on('click',function(){
	
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
						url : reizenUrl + "postscript/userPost.do",
						method : 'post',
						dataType : 'json',
						data : {
							scheduleNo : scheduleNo
						},
						success : function(result) {
							if (result.status == 'success') {
								var data = result.data
								for (var i = 0; i < data.length; i++) {
								$this.next().text(' '+data[i].schedule.recommandCount);
								$this.css("color","#ffffff").removeAttr('data-active');
								}
							}
						}
					});
				}else{
					$.ajax({
						url : reizenUrl + "postscript/userPost.do",
						method : 'post',
						dataType : 'json',
						data : {
							scheduleNo : scheduleNo
						},
						success : function(result) {
							if (result.status == 'success') {
								var data = result.data;
								for (var i = 0; i < data.length; i++) {
								$this.next().text(' '+data[i].schedule.recommandCount)
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

var DateFormats = {
	       short: "DD MMMM - YYYY",
	       long: "dddd DD.MM.YYYY HH:mm"
	};