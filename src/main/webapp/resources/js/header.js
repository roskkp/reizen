
var regEmail = /([\w-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$/;
var filesList = new Array();

function maskImgs() {
	$.each($('.img-wrapper img'), function(index, img) {
		var src = $(img).attr('src');
		var parent = $(img).parent();
		parent
		.css('background',
				'url(' + src + ') no-repeat center center').css(
						'background-size', 'cover');
		$(img).remove();
	});
}

var navHeight = 207;

$(function(){ 
	
	sessionCheck();

	$('#thumbnail').fileupload({
		autoUpload : false
	}).on('fileuploadadd', function(e, data) {
		filesList.pop(); 
		$.each(data.files, function(index, file) {
			filesList.push(data.files[index]);
			$('#nail').attr("placeholder",data.files[index].name);
			console.log("Added: " + data.files[index].name);
		});
	}).on('fileuploaddone', function (e, data) {
		if( data.textStatus == 'success' ){
			$('#loginForm').css('display', 'block');
			$('#registerForm').css('display', 'none');
			swal("회원 가입을 축하합니다.","", "success");
		}else {
			swal("회원 가입 실패.", "회원 정보를 확인해주세요 :( ", "error");
		}
	});

	$("#sign-form").submit(function(event) {
		if (!regEmail.test($('#email').val())) {
			alert('이메일 주소가 유효하지 않습니다');
			$('#email').focus();
			return false;
		} else if ($("#email").val() == "") {
			alert("이메일 주소를 꼭 입력하세요!");
			$("#email").focus();
			return false;
		} else if ($("#nickName").val() == "") {
			alert("닉네임을 꼭 입력하세요!");
			$("#nickName").focus();
			return false;
		} else if ($("#password").val() == "") {
			alert("비밀번호확인 을 꼭 입력하세요!");
			$("#password").focus();
			return false;
		} else if ($("#checkPassword").val() == "") {
			alert("비밀번호확인 을 꼭 입력하세요!");
			$("#checkPassword").focus();
			return false;
		} else { // 폼에 문제가 없다면 
			if ( filesList.length > 0 ) {
				event.preventDefault();
				$('#thumbnail').fileupload('send', {
					files : filesList
				});
			} else {
				event.preventDefault();
				$('#thumbnail').fileupload('send', {
					files : {"name" : "empty.png"}
				});
				console.log("plain default form submit");
			}
		}
	});

	$("#login").on("click", function() {
		loginAjax();
	}); // login btn

	$('#email').on("focusout", function() {
		var $email = $('#email');
		if ($email.val() != '') {
			if (!regEmail.test($email.val())) {
				$('#email-span').text("이메일 주소가 유효 하지 않습니다.");
				return false;
			} else {
				checkDuplicateAjax($email.val());
			}
		}
	});

	$('#password ,#checkPassword').on("focusout", function() {
		var $target = $('#pw-span');
		var $pw = $('#password');
		var $cpw = $('#checkPassword');
		if ($pw.val() != '' && $cpw.val() != '') {
			if ($pw.val().length < 6 || $cpw.val().length < 6) {
				$target.text("패스워드가 너무 짧습니다.");
				return;
			} else {
				$target.text('');
			}
			if ($pw.val() != $cpw.val()) {
				$target.text("패스워드 불일치");
			} else {
				$target.text("패스워드 일치");
			}
		}
	});

	var preview = {
			init : function() {
				preview.setPreviewImg();
				preview.listenInput();
			},
			setPreviewImg : function(fileInput) {
				var path = $(fileInput).val();
				var uploadText = $(fileInput).siblings('.file-upload-text');
				if (!path) {
					$(uploadText).val('');
				} else {
					path = path.replace(/^C:\\fakepath\\/, "");
					$(uploadText).val(path);

					preview.showPreview(fileInput, path, uploadText);
				}
			},
			showPreview : function(fileInput, path, uploadText) {
				var file = $(fileInput)[0].files;

				if (file && file[0]) {
					var reader = new FileReader();
					reader.onload = function(e) {
						var previewImg = $('.preview');
						var img = $(previewImg).find('img');
						if (img.length == 0) {
							$(previewImg).html('<img src=" ' + e.target.result + ' " alt=" "/>');
						} else {
							img.attr('src', e.target.result);
						}
						maskImgs();
					}
					reader.readAsDataURL(file[0]);
				}
			},
			listenInput : function() {
				$('.file-upload-native').on('change', function() {
					preview.setPreviewImg(this);
				});
			}
	};
	preview.init();

	// Toggle Function
	$('.toggle').click(function() {
		// Switches the Icon
		$(this).children('i').toggleClass('fa-pencil');
		for (var int = 0; int < $('#sign-form  input').length; int++) {
			$($('#sign-form  input')[int]).val('');
		}
		$('.img-wrapper').css('background','');
		$('.check').text('');
		$('#nail').attr('placeholder','img upload');
		// Switches the forms
		$('.form').animate({
			height : "toggle",
			'padding-top' : 'toggle',
			'padding-bottom' : 'toggle',
			opacity : "toggle"
		}, "slow");
	});

	// 정보수정
	$('#btnEdit').on('click', function(){
		$('#updateForm div.img-wrapper').css({
			'background' : 'url(\''
				+'/resources/images/thumbnail/' + sessionStorage.getItem('thumbnail')
				+'\') no-repeat center center',
				'background-size' : 'cover' });
		updateFormAjax();
	});

	$('#updateThumb').fileupload({
		autoUpload : false
	}).bind('fileuploadadd', function(e, data) {
		filesList.pop(); 
		$.each(data.files, function(index, file) {
			filesList.push(data.files[index]);
			$('#updateNail').attr("placeholder",data.files[index].name);
			console.log("Added: " + data.files[index].name);
		});
	}).on('fileuploaddone', function (e, data) {
		if( data.textStatus == 'success' ){
			$('.min-thumbnail').css('background-image', 'url(' + "/resources/images/thumbnail/" + sessionStorage.getItem('thumbnail')+ ')').fadeIn();
			$('#id').text(sessionStorage.getItem('nick'));
			$("#lean_overlay").fadeOut(200);
			$('#updateForm').css({
				"display" : "none"
			});
			swal("회원 정보 수정 완료.","", "success");
		}else {
			swal("회원 정보 수정 실패.", "회원 정보를 확인해주세요 :( ", "error");
		}
	});

	$('#update-form').submit(function(event){
		if ($("#updateNickName").val() == "") {
			swal("닉네임을 꼭 입력하세요!","","warning");
			$("#updateNickName").focus();
			return false;
		} else if ($("#updateForm > form > input[name=password]").val() == "") {
			swal("비밀번호를 꼭 입력하세요!","","warning");
			$("#updateForm > form > input[name=password]").focus();
			return false;
		} else if ($("#checkUpdatePwd").val() == "") {
			swal("비밀번호를 확인을 꼭 입력하세요!","","warning");
			$("#checkUpdatePwd").focus();
			return false;
		} else { // 폼에 문제가 없다면 
			
			if (filesList.length > 0) {
				console.log("multi file submit");
				event.preventDefault();
				$('#updateThumb').fileupload('send', {
					files : filesList
				}).success(function(result, textStatus, jqXHR) {
					if (textStatus == "success") {
						sessionStorage.setItem('nick', result.user.nickName);
						sessionStorage.setItem('thumbnail', result.user.thumbNail);
						swal("회원 정보를 수정하였습니다.", "", "success");
					} else {
						console.log("회원 정보 수정 오류!")
					}
				}).error(function(jqXHR, textStatus, errorThrown) {
					console.log('error');
				}).complete(function(result, textStatus, jqXHR) {
					console.log('complete: ' + JSON.stringify(result));
					$('.min-thumbnail').css('background-image', 'url(' + "/resources/images/thumbnail/" + sessionStorage.getItem('thumbnail')+ ')').fadeIn();
					$('#id').text(sessionStorage.getItem('nick'));
					$("#lean_overlay").fadeOut(200);
					$('#updateForm').css({
						"display" : "none"
					});
				});
			} else {
				event.preventDefault();
		        $('#updateThumb').fileupload('send', {
		          files : {"name" : "empty.png"}
		        });
				console.log("plain default form submit");
			}
		}
	});

	/*********** 탈퇴 ***********/
	
	$(document).on('click', '#btnOut', function(){
		swal({
			title: "탈퇴하시겠습니까?",
			text: "후회하실꺼에요!",
			type: "warning",
			showCancelButton: true,
			confirmButtonClass: "btn-danger",
			confirmButtonText: "확인",
			cancelButtonText: "취소",
			closeOnConfirm: false,
			closeOnCancel: false
		},
		function(isConfirm) {
			if (isConfirm) {
				swal("탈퇴 완료!", "다음에 또 봐요 :)", "success");
				leaveAjax();
			} else {
				swal("취소되었습니다.", "열심히 하겠습니다 :)", "error");
			}
		});
	});
	

	/*********** 네이버 로그인 ***********/

	var naver = new naver_id_login("5VKp7qdAUXuURPz9imbk", "http://reizen.com:8080/");
	var state = naver.getUniqState();
	naver.setDomain("reizen.com");
	naver.setState(state);
	naver.init_naver_id_login();

	$('#btnNaver').on('click', function(){
		$(location).attr('href', naver_id_login_url);
		return false;
	});

	function naverCheck(){
		naver.get_naver_userprofile();
	}
	
	// 네이버 로그인 체크
	if($(location).attr('href').indexOf('access_token')!=-1){
		naverCheck();
	}
	
	
});  // on load


/*********** 구글 로그인 ***********/

function initG(){
	gapi.load('auth2', function(){
		auth2 = gapi.auth2.init({
			client_id:'579387072803-k4uml5ab53u8t9krh541cuvv8mlgas39.apps.googleusercontent.com'
		})
	});
}

$('#btnGoogle').off('click').on('click', function(){
	auth2.attachClickHandler(this, {}, 
			function(googleUser){
				var user = googleUser.getBasicProfile();
				var email = user.getEmail();
				var name = user.getName();
				googleLoginAjax(email, name);
			}, function(error){
				console.log('google login error'+error);
			}
	);
});


/*********** 진행중인 일정 보기 ***********/
$('#btnProceeding').off('click').on('click', function(){
	if(!$(this).hasClass('empty')){
		$('#proceedingList').slideToggle('fast', function(){
			if($(this).is(':hidden')){
				$('nav.headerNav').css('height', navHeight);
			}else{
				$('nav.headerNav').css('height', $('#proceedingList').height()+navHeight);
			}
		});
	}
});


/*********** 세션 체크 ***********/

function sessionCheck(){
	console.log('세션체크');
	if(sessionStorage.length>0){
		$('#modaltrigger').hide();
		$('nav.headerNav').css('height', '0px');
		$('.login').fadeIn();
		$('.min-thumbnail').css('background-image', 'url(' + "/resources/images/thumbnail/" + sessionStorage.getItem('thumbnail')+ ')').fadeIn();
		$('#id').text(sessionStorage.getItem('nick'));
		$('.like-count').text(sessionStorage.getItem('totalRecommand'));
		$('.write-count').text(sessionStorage.getItem('totalScrap'));
		var activeList = JSON.parse(sessionStorage.getItem("activeScheduleNo"));
		
		if(activeList.length>0){
			$('#btnProceeding').removeClass('empty').html('진행중인 일정 <span class="badge">'+activeList.length+'</span>');
			$('#proceedingList').empty();
			for(var index in activeList){
				var tag = '<a href='+reizenUrl+'scheduler/proceeding.html?scheduleNo='+activeList[index].scheduleNo+'>'+activeList[index].title+'</a>';
				$('#proceedingList').append(tag);
			}
		}else{
			$('#btnProceeding').addClass('empty').text('진행중인 일정이 없습니다');
		}

		$('#btnDashBoard').attr('href','/scheduler/dashboard.html?no='+sessionStorage.getItem('dashNo'));
		$('#btnLogout').off('click').on('click', function() {
			if(auth2 != null){
				if( auth2.currentUser.get().isSignedIn() ) {
					auth2.signOut();
				}
			}
			$.getJSON(reizenUrl+'/user/logout.do', function(result){
				if(result.status=='success'){
					console.log('로그아웃');
				}
			});
			$('.min-thumbnail').css('background-image','');
			sessionStorage.clear();
			sessionCheck();
			$(location).attr('href', 'http://reizen.com:8080')
		});
		$('.login').on('click', function() {
			$('nav.headerNav').css('height', navHeight);
		});
		$('nav.headerNav').on('mouseleave', function() {
			$('nav.headerNav').css('height', '0px');
			$('#proceedingList').hide();
		});

		$('#btnEdit').leanModal();
	}else{
		$('nav.headerNav').css('height', '0px');
		$('.login').hide();
		$('#modaltrigger').fadeIn();
		$('#modaltrigger').on('click', function() {
			$(this).css('transform', 'rotate(180deg)');

			$('#loginForm').css('display','block');
			$('#registerForm').css('display','none');
		});

		$('#lean_overlay').on('click', function() {
			$('#modaltrigger').css('transform', 'rotate(0deg)');
		});
		$('#modaltrigger').leanModal({
			top : 110,
			overlay : 0.5,
			closeButton : ".hidemodal, #login"
		});
	}
}