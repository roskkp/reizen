var bodySource;
var bodyTemplate;
var infoSource;
var infoTemplate;
var recommandSource;
var recommandTemplate;
var memoSource;
var memoTemplate;
var ar = ['one','two','three'];
var mapX;
var mapY;

var contentId = null;
$(function() {

	init();	
	
	$('.carousel').carousel();
	$('#add-location').attr('data-no',contentId);
	$(document).on('click','.nick-name',function(e){ // 닉네임 클릭하면 해당 회원 dashboard로 가게
		location.href='dashboard.html?no='+$(this).attr("data-dashNo");
		e.preventDefault();
	});
	$('#writeBtn').on('click',function(e){ // 메모 등록 이벤트
		memoSubmit();
	})
	
	$(document).on('click','.tabs',function(){
		if ($(this).attr('load') != 'on') {
			searchAround($(this).attr('data-tid'));
			$(this).attr('load','on');
		}
		
	})
	
	$(document).on('click','.memo-edit',function(e){ // 메모 수정 이벤트 
		var $this = $(this);
		var $textView = $this.parents('.comment-block').children().first();
		
		if($this.attr("data-togle") == "on" ){ // 수정 취소
			$this.attr("data-togle","off");
			$textView.empty().text(sessionStorage.getItem('tempMemo'));
			sessionStorage.removeItem('tempMemo');
			$('.memo-edit').parent().css("visibility","visible");
			$this.text('edit').next().text('remove');
		}else{ // 수정 진행 
			$this.attr("data-togle","on");
			var originText = $textView.text();
			$textView.empty().append('<input class="comment-edit" type="text" value=" '+originText+' "/>');
			sessionStorage.setItem('tempMemo',originText); // 세션에 기 내용 저장
			$('.memo-edit').not($this).parent().css("visibility","hidden"); // 이벤트 발생 지점 빼고 나머지 hidden
			$this.text('undo').next().text('update');
			$('.comment-edit').focus();
		}
	})
	$(document).on('click','.memo-action',function(e){ // 메모 삭제 or 수정 이벤트 (결코 귀찮아서 이렇게 한게아님)
		var $this = $(this);
		var mno = $this.closest('.comment-wrap').attr('data-mno');
		if($this.text()=='remove'){
			deleteMemo(mno);
		}else if($this.text() == 'update'){
			var content = $('.comment-edit').val();
			updateMemo(mno,content);
		}else{
			console.log('memo action error');
		}
		
	})
})

function init() {
	bodySource = $('#spotBody').text();
	bodyTemplate = Handlebars.compile(bodySource);
	infoSource = $('#infoBox').text();
	infoTemplate = Handlebars.compile(infoSource);
	recommandSource = $('#recommandBox').text();
	recommandTemplate = Handlebars.compile(recommandSource);
	postcardSource = $('#postcardBox').text();
	postcardTemplate = Handlebars.compile(postcardSource);
	memoSource = $('#memoTemplate').text();
	memoTemplate = Handlebars.compile(memoSource);
	var url = location.href;
	var variable = (url.substr(url.lastIndexOf('?') + 1)).replace('#','');
	contentId = (variable.split('&')[0]).substr(variable.split('&')[0].lastIndexOf('=') + 1);
	var typeId = (variable.split('&')[1]).substr(variable.split('&')[1].lastIndexOf('=') + 1);

	var path = $('#baseSparql').text() + contentId;
	switch (typeId) {
	case "12":
		path += $('#tourist').text();
		break;
	case "14":
		path += $('#culture').text();
		break;
	case "15":
		path += $('#festival').text();
		path = path.replace('Place','Event');
		break;
	case "28":
		path += $('#leports').text();
		break;
	case "32":
		path += $('#lodge').text();
		break;
	case "38":
		path += $('#shopping').text();
		break;
	case "39":
		path += $('#food').text();
		break;
	}

	serchInfo(path, contentId);
	
	scheduleData(contentId);
	
	checkStatus();
	
	memoCheck();
	var maphover = true;
	$('.map-hover').on('click',function(){
		if (maphover) {
			maphover = false;
			$('#carousel-example-generic').css('display','none');
			$('#map').css('display','block');
			initMap();
		} else {
			$('#carousel-example-generic').css('display','block');
			$('#map').empty();
			$('#map').css('display','none');
			maphover = true;
		}
	})

}

function initMap() {
	
	var spot = {lat: parseFloat(mapY), lng: parseFloat(mapX)};

	var map = new google.maps.Map(document.getElementById('map'), {
		zoom: 18,
		center: spot 
	});
	
	var label = $('.spot-name').text().trim();
	console.log(label)
	var marker = new google.maps.Marker({
		position: spot,
		map: map,
		icon:'/resources/images/marker/point.png'
	});
	
	var content = label;
	var infowindow = new google.maps.InfoWindow({ content: content});
	infowindow.open(map,marker);
    google.maps.event.addDomListener(window, 'load', initialize);
    
    $('.carousel-inner').position();
    
}

var start = 1;
$('#btnBlog').off('click').on('click', function(){
	var keyword = $('.spot-name').text();
	$.ajax({
		url : 'http://reizen.com:8899/naverPost.do?keyword='+keyword+'&start='+start,
		type : 'GET',
		success : function(result){
			console.log(result.rss.channel[0].item);
			var blogSource = $('#blogList').text();
			var blogTemplate = Handlebars.compile(blogSource, {noEscape:true});
			$('ul.blogList').append(blogTemplate(result.rss.channel[0].item));
		}
	});
});

$('.blogList').on('click', 'li.blogLists', function(){
	var url = $(this).data('url');
    window.open(url,'_blank');
});