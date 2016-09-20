var searchData;
var areaCode;
var localCode;
var keyword;
var locationPage = 1;
var schedulePage = 1;
var ar = ['one','two','three'];
var i = 0;
var locationSource;
var locationTemplate;
var scheduleSource;
var scheduleTemplate;
var cateSource;
var cateTemplate;
var size = 6;
var cateL;
var cateS;
var nop;
var month;
var term;

$(function(){
	
	locationSource = $('#locationListTemplate').html();
	locationTemplate = Handlebars.compile(locationSource);
	scheduleSource = $('#scheduleListTemplate').html();
	scheduleTemplate = Handlebars.compile(scheduleSource);
	cateSource = $('#subclassListing').html();
	cateTemplate = Handlebars.compile(cateSource);
	
	$('#fullpage').fullpage({ // 풀페이지.js
		css3 : true,
		afterRender: function(){
			//playing the video
			$('video').get(0).play();
		},
		onLeave: function(index, nextIndex, direction){
			if ($('#ui-id-1').css('display') == 'none' && $('#ui-id-2').css('display') == 'none') {
				
			} else {
				return false;
			}
		},
		afterLoad: function(anchorLink, index){
			if(index == 1){
			}
			if(index == 2){
				$('#scrollable').scrollTop(1);
			}
		}
	});
	
	$.fn.isolatedScroll = function() { // div 안에서만 스크롤 되게 하는 귀중한 소스입니다.
	    this.bind('mousewheel DOMMouseScroll', function (e) {
	        var delta = e.wheelDelta || (e.originalEvent && e.originalEvent.wheelDelta) || -e.detail,
	            bottomOverflow = this.scrollTop + $(this).outerHeight() - this.scrollHeight >= 0,
	            topOverflow = this.scrollTop <= 0;

	        if ((delta < 0 && bottomOverflow) || (delta > 0 && topOverflow)) {
	            e.preventDefault();
	        }
	    });
	    return this;
	};
	$('.subclass-list').isolatedScroll();
	$('.ui-menu-item-wrapper').isolatedScroll();

	$('.cateL').on('click',function(){ // 대분류 선택시 소분류 리스팅
		var $this = $(this);
		if(addFilterValue($this.text(),$this.attr('data-cateL'))){ // filter max and duplication check
			searchCategoryM($this.attr('data-cateL'));
			$('.subclass-list').animate({ height : '80px'});
		}
		searchLocation();
	});
	
	
	
	$(document).on('click','.cateS',function(){ // 소분류 선택시 필터링
		addFilterValue($(this).text(),$(this).attr('data-subno'));
		searchLocation();
	})
	$('.planToggle').on('click',function(){ // 일정 상세검색 토글기능 구현 ....
		var $this = $(this);
		if($this.attr('data-toggle')=='on'){
			$('.planToggle').fadeIn().attr('data-toggle','off');
			$('.planFilter').css('display','none');
			return;
		}
		$('.planToggle').not($this).css('display','none');
		$this.attr('data-toggle','on');

		switch ($this.attr('data-wrap')) {
		case 'people':
			$('.planFilter[data-type="peopleCount"]').fadeIn();
			break;
		case 'season':
			$('.planFilter[data-type="season"]').fadeIn();
			break;
		case 'days':
			$('.planFilter[data-type="days"]').fadeIn();
			break;			
		default:
			break;
		}
	})
	$('.planFilter').on('click',function(){ // 일정 상세조건 클릭시 이벤트 리스너
		$this = $(this);
		addFilterPlan($this.text(),$this.attr('data-type'),$this.attr('data-value'));
		switch ($this.attr('data-type')) {
		case 'peopleCount':
			nop = $this.attr('data-value');
			break;
		case 'season':
			month = $this.attr('data-value');
			break;
		case 'days':
			term = $this.attr('data-value');
			break;			
		default:
			break;
		}
		schedulePage = 1;
		searchSchedule();
	})
	$(document).on('click','.filter-value',function(){ // 장소 필터링 삭제 이벤트 리스터
		$(this).remove();
		if($('.filter-value').length == 0){
			$('#filter-tab').fadeOut();
		}
		searchLocation();
	})
	$(document).on('click','.filter-plan',function(){ // 일정 필터링 삭제 이벤트 리스너 
		$this = $(this);
		$this.remove();
		if($('.filter-value').length == 0){
			$('#filter-tab').fadeOut();
		}
		switch ($this.attr('data-type').trim()) { // 전역변수로 되어있어서 초기화해줘야함 
		case 'peopleCount':
			nop = 0;
			break;
		case 'season':
			month = 0;
			break;
		case 'days':
			term = 0;
			break;			
		default:
			break;
		}
	})
	$('.fa-arrow-down').on('click',function(){
		var value = $('#'+$(this).attr('target')).text();
		if (value == 0) {
			return;
		}
		$('#'+$(this).attr('target')).text($('#'+$(this).attr('target')).text()-1);
		schedulePage=1;
		nop = $('#nop').text();
		month = $('#month').text();
		term = $('#term').text();
		$('#locationBox').hide();
		searchSchedule();
	})
	
	$('.fa-arrow-up').on('click',function(){
		$('#'+$(this).attr('target')).text(parseInt($('#'+$(this).attr('target')).text())+1)
		schedulePage=1;
		nop = $('#nop').text();
		month = $('#month').text();
		term = $('#term').text();
		$('#locationBox').hide();
		searchSchedule();
	})

	$('#locationRefresh').on('click',function(){
		if ($(this).text() == '처음으로') {
			$(this).text('더 보기')
		}
		locationPage += 1;
		searchLocation();
	});

	$('#scheduleRefresh').on('click',function(){
		if ($(this).text() == '처음으로') {
			$(this).text('더 보기')
		}
		schedulePage += 1;
		searchSchedule();
	})

	$('#scrollable').scroll(function() {
		if ($(this).scrollTop() != 0) {
			$('.section:last-child').removeClass('active');
		} else {
			$('.section:last-child').addClass('active');
		}
	});

	$(document).on('click','.nick-name',function(e){ // 닉네임 클릭하면 해당 회원 dashboard로 가게
		location.href='scheduler/dashboard.html?no='+$(this).attr("data-dashNo");
		e.preventDefault();
	});

	$('#searchBar').on('keydown',function(event){
		if( event.keyCode == 13 ){
			search();
		}
	})
	
	$(document).on('click','#ui-id-2 li div',function(){
		search();
	})

	searchLocation();
	searchSchedule();

});
function addFilterValue(str, cateNo){
	var $filterValue=$('.filter-value');
	var $filterPlan=$('.filter-plan');
	if($filterValue.length + $filterPlan.length == 6){
		swal("Failed!", "The exceeded the limits", "warning"); 
		return false; 
	}
	for(var i = 0 ; i <=$filterValue.length;i++){ // 중복검사 
		if($($filterValue[i]).text().trim()==str.trim()){
			return true;
		}
	}
	$('.filter-list').append('<div class="filter-value" data-cate="'+cateNo+'">'+str+'&nbsp;<i class="fa fa-times remove-filter" aria-hidden="true"></i></div>').parent().fadeIn();
	return true;
}
function addFilterPlan(str, type, no){ // 중복코드 있습니다 ㅜㅜ
	var $filterValue=$('.filter-value');
	var $filterPlan=$('.filter-list').children('.filter-plan');
	
	if($filterValue.length + $filterPlan.length == 6){ // 길이 검사
		swal("Failed!", "The exceeded the limits", "warning"); 
		return false; 
	}

	for(var i = 0 ; i <=$filterPlan.length;i++){ // 중복 검사
		if($.trim($($filterPlan[i]).attr('data-type')) == type.trim()){ // 중복타입인 경우 삭제하고 새로 생성
			$($filterPlan[i]).remove();
		}
	}

	$('.filter-list').append('<div class="filter-plan" data-type=" '+type+ ' " data-no=" '+no+'">'+str+'&nbsp;<i class="fa fa-times remove-filter" aria-hidden="true"></i></div>').parent().fadeIn();
	return true;
}

function categoryJson(array,length){
	array = new Array();
	var index = 0;
	for (var i = 0; i < $('.filter-value').length; i++) {
		if ($($('.filter-value')[i]).attr('data-cate') != 'undefined' && $($('.filter-value')[i]).attr('data-cate').length == length) {
			array[index] = $($('.filter-value')[i]).attr('data-cate');
			index++;
		}
	}
	return array;
}

function search(){
	keyword = $('#searchBar').val();
	$('#searchBar').val('');
	$('#ui-id-2').css('display','none');
	$.fn.fullpage.moveSectionDown();
	locationPage = 1;
	schedulePage = 1;
	searchLocation();
	searchSchedule();
}