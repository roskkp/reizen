function loginAjax(){
	$.ajax({
		url : reizenUrl+"user/login.do", 
		dataType : "json",
		data : {
			email : $('#mail').val(),
			password : $('#psw').val()
		},
		method : "POST",
		success : function(result) {
			if (result.status == "success") {
				sessionStorage.setItem('userNo', result.user.userNo);
				sessionStorage.setItem('nick', result.user.nickName);
				sessionStorage.setItem('thumbnail', result.user.thumbNail);
				sessionStorage.setItem('dashNo', result.user.dashNo);
				sessionStorage.setItem('activeScheduleNo', JSON.stringify(result.activeScheduleNo));
				sessionStorage.setItem('totalRecommand', result.totalRecommand);
				sessionStorage.setItem('totalScrap', result.totalScrap);
				
				sessionCheck();
			} else {
				swal("로그인 실패", "회원 정보를 확인해주세요 :( ", "error");
			}
		},
		error : function(request,status,error) {
			swal("로그인 실패", "회원 정보를 확인해주세요 :( ", "error");
		}
	}); // ajax
}


function googleLoginAjax(email, name){
	$.ajax({
		url : reizenUrl+'user/googleLogin.do',
		data : {
			'email' : email,
			'nickName' : name
		},
		type : 'POST',
		dataType : 'json',
		success : function(result){
			if( result.status == 'success' ){
				sessionStorage.setItem('userNo', result.user.userNo);
				sessionStorage.setItem('nick', result.user.nickName);
				sessionStorage.setItem('thumbnail', result.user.thumbNail);
				sessionStorage.setItem('dashNo', result.user.dashNo);
				sessionStorage.setItem('activeScheduleNo', JSON.stringify(result.activeScheduleNo));
				sessionStorage.setItem('totalRecommand', result.totalRecommand);
				sessionStorage.setItem('totalScrap', result.totalScrap);
				$("#lean_overlay").fadeOut(200);
				$('#loginmodal').css({
					"display" : "none"
				});
				$('#modaltrigger').css('transform', 'rotate(0deg)').css('transition', '1s ease');   
				sessionCheck();
			}else{
				swal("로그인 실패", "회원 정보를 확인해주세요 :( ", "error");
			}
		}
	});
}

function checkDuplicateAjax(email){
	$.ajax({
		type : 'POST',
		url : reizenUrl+"user/checkMail.do",
		data : {
			email : email
		},
		success : function(result) {
			var $emailCheck = $('#email-span');
			if (result.status == 'success') {
				$emailCheck.text("사용 할 수 있습니다 :) ");
			} else {
				$emailCheck.text("이메일 중복 입니다");
			}
		} //success
	}); // ajax
}

function updateFormAjax(){
	$.getJSON(reizenUrl+'user/updateUserForm.do', function(result){
		if( result.status != 'success' ){
			console.log('로그인 해주세요!');
			return;
		}else {
			$('#updateEmail').val(result.user.email);
			$('#updateNickName').val(result.user.nickName);
		}
	});
}

function leaveAjax(){
	$.getJSON(reizenUrl+'user/leaveUser.do', function(result){
		if( result.status == 'success' ){
			$('.min-thumbnail').css('background-image','');
			sessionStorage.clear();
			sessionCheck();
		}else {
			console.log('회원 탈퇴 에러');
		}
	});
}