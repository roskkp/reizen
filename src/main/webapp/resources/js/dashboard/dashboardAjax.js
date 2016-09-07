function getDashAjax(dashNo) {
	$.ajax({
		dataType : 'json',
		url : reizenUrl+'dashboard/getDash.do?boardNo='+dashNo,
		method : 'get',
		success : function(result) {
			$('.user-nickname').text(result.user.nickName);
			$('.user-thumbnail').css('background-image', 'url(' + "/resources/images/thumbnail/" + result.user.thumbNail+ ')');
		}
	})
}
function listAjax(path,$target, $tab, template) {
	$.ajax({
		dataType : 'json',
		url : reizenUrl+'dashboard/' + path,
		method : 'get',
		success : function(result) {
			$target.attr('data-type',result.dataType); // 각각의 탭의 내용을 구분하기 위해 
			i=0; // 탭 마다 처음 위치에 포스트를 넣기 위해 
			result.list.forEach(function(value,index){ 
				$target.find("."+ar[i]).append(template(value));// 핸들바스 이용 포스트 생성
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
			
			if (result.list == null) { // 탭바 갯수 표시
				$tab.text('0');
			} else {
				$tab.text(result.list.length);
			}
			
			if(dashNo != sessionStorage.getItem('dashNo')){ // 삭제 권한 체크
				$('.remove').parent().remove();
			}else{
				$(document).on('click', '.remove', function(e) { // 삭제버튼 이벤트 
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
							switch ($this.parents('.tab-pane').attr('data-type')) {
							case 'plan':
								deleteAjax('removeplan.do?scdNo=' + $this.parents('.post').attr('data-no'));
								break;
							case 'scrPlan':
								deleteAjax('removescp.do?scdNo=' + $this.parents('.post').attr('data-no'));
								break;
							case 'scrLocation':
								deleteAjax('removescl.do?contentId=' + $this.parents('.post').attr('data-no'));
								break;
							default:
								swal("Failed!", "Failed to delete. Please contact your administrator", "error"); 
								break;
							}
							swal("삭제되었습니다.", "", "success"); 
					});
					e.preventDefault();
				});
			}
			
		}
	})
}
function deleteAjax(path) {
	$.ajax({
		dataType : 'json',
		url : reizenUrl+'dashboard/' + path,
		method : 'get',
		success : function(result) {
			refresh();
		},
		error : function() {
			alert('error');
		}
	})
}

