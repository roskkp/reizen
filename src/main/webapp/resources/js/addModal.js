
$(function(){
	
	/*modal css 는 common.css 에 있습니다*/
	/************	POST -> Scheduler page 이동      ************/
	var cid; 

	
	$(document).on('click', '.btnPlus', function(event){ // addBtn 클릭 이벤트 리스너
		event.preventDefault();
		cid = $(this).parents('.post').data('no');
		if(cid == null){
			cid=$(this).data('no');
		}
		if(cid==null){
			swal("Failed!", "장소 정보를 받아오지 못했습니다. 다시 시도해주세요.", "error"); 
			return;
		}
		$('.adm-form-group,.dayList').hide();
		$('#updateHour').empty();
		for(var i=0; i<24; i++){ // 00시 ~ 23시30분 까지 지원 *db가 24시를 거부합니다.
			if(i<10){
				$('#updateHour').append('<option value='+'0'+i+'>'+'0'+i+'</option>');
				continue;
			}
			$('#updateHour').append('<option value='+i+'>'+i+'</option>');
		}
		$('select.scheduleSelectList option').not('option.default').remove();
		$('select.dayList option').not('option.default').remove();

		if(sessionStorage.length>0){ // 로그인 되어있다면
			var userNo = sessionStorage.getItem("userNo");
			$.ajax({
				url : 'http://reizen.com:8889/scheduler/checkSchedule.do', 
				method : 'POST',
				data : {'userNo' : userNo}, 
				dataType : 'json',
				success : function(result){
					if(result.length>0){ // 스케줄이 있다면
						var scheduleSource = $('#scheduleList').html();
						var scheduleTemplate = Handlebars.compile(scheduleSource);
						$('select.scheduleSelectList').append(scheduleTemplate(result));
						$('#scheduleModal').appendTo("body").modal('show');
					}else { // 스케줄이 없다면
						swal({   
							title: "일정이 없네요 !",   
							text: "새로운 일정을 만드시겠어요?",   
							type: "info",  
							showCancelButton: true,   
							confirmButtonColor: "#DD6B55",   
							confirmButtonText: "Yes",   
							closeOnConfirm: false }, 
							function(){ // 메인에서의 접근과 여타 페이지에서의 접근 경로가 달라서 절대경로 써야합니다
								window.location.href='http://reizen.com:8890/scheduler/scheduler.html?cid='+cid;
							}); // swal
					}
				}
			});
		}else{
			swal("Failed!", "로그인 해주세요.", "warning"); 
		}
	});

	$(document).on('change', 'select.scheduleSelectList', function(){ // 일정이 변경되면 발생하는 이벤트 
		$('select.dayList option').not('option.default').remove(); // day select 지움
		if($(this).data('no')==null){ // 일정을 선택해주세요 선택시
			$('.dayList').fadeOut();
			$('.adm-form-group').fadeOut();
		}
		var scheduleNo = $('select.scheduleSelectList option:selected').data('no');
		$.getJSON('http://reizen.com:8889/scheduler/checkDay.do?scheduleNo='+scheduleNo, function(result){
			if(result.length>0){ // day가 있다면 ....? day가 없는 일정이 있을 수 있나 ?
				$('.dayList').fadeIn();
				var daySource = $('#dayList').html();
				var dayTemplate = Handlebars.compile(daySource);
				$('select.dayList').append(dayTemplate(result));
			}else { //day가 없다면 .... 인데 ... day가 없는 일정이 있을 수 없으니까 .. 에러...
				swal("Failed!", "일정 조회 실패. 관리자에게 문의하세요. ", "error"); 
			}
		});
	});
	$(document).on('change','.adm-formMargin', function(){ // day가 변경되면 발생하는 이벤트
		if($(this).data('day')==null){ // 날짜를 선택해주세요 선택시  
			$('.adm-form-group').fadeOut();
		}
		$('.adm-form-group').fadeIn();
	});
	$('#btnTimeSubmit').on('click', function(event){
		event.preventDefault();
		var scheduleNo = $('select.scheduleSelectList option:selected').data('no');
		var time = $('#updateHour option:selected').val()+':'+$('#updateMin option:selected').val();
		var date = $('.dayList option:selected').val();
		var day = $('.dayList option:selected').data('day');
		if(time==null || date ==null || day==null){ // 입력값 검증 
			swal("Failed!", "모든 정보를 기입해 주세요", "error"); 
			return;
		}
		$.getJSON('http://reizen.com:8890/scheduler/checkTime.do?scheduleNo='+scheduleNo+'&day='+day+'&time='+time, function(result){
			if(result.status=='exist'){
				$('.control-label').remove();
				$('div.form-group').append('<label class="control-label" for="inputError1">중복된 시간입니다.</label>');
				$('div.form-group').addClass('has-error');
			} else {
				$.getJSON(reizenUrl+'scheduler/addRoute.do?location.contentId='+cid+'&travelSequence='+9999+'&time='+date+' '+time+'&scheduleNo='+scheduleNo+'&day='+day+'&currentDate='+date, function(result){
					if(result.status=='success'){
						$('#scheduleModal').modal('hide');
						swal({   
							title: "일정 추가 성공",   
							text: "플래너로 이동하시겠습니까? ",   
							type: "success",  
							showCancelButton: true,   
							confirmButtonColor: "#DD6B55",   
							confirmButtonText: "Yes",   
							closeOnConfirm: false }, 
							function(){ // 메인에서의 접근과 여타 페이지에서의 접근 경로가 달라서 절대경로 써야합니다
								window.location.href='http://reizen.com:8080/scheduler/scheduler.html?scheduleNo='+scheduleNo;
							}); // swal
						}else{
							swal("Failed!", "일정 추가 실패. 관리자에게 문의하세요. ", "error"); 
						} //else
				}); //getJson
			} //else
		});

	});
/************ ************/
});