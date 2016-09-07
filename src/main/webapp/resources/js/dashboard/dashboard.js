
var scheduleListSource = null;
var scheduleListTemplate = null;
var locationListSource = null;
var locationListTemplate = null;
var scrapListSource = null;
var scrapListTemplate =null;
var dashNo = 0;
var ar = ['one','two','three'];
var i = 0;
$(function() {
	dashNo = location.href.substr(location.href.lastIndexOf('=') + 1);
	scheduleListSource = $('#scheduleListTemplate').text();
	scheduleListTemplate = Handlebars.compile(scheduleListSource);
	scrapListSource = $('#scrapListTemplate').text();
	scrapListTemplate = Handlebars.compile(scrapListSource);
	locationListSource = $('#locationListTemplate').text();
	locationListTemplate = Handlebars.compile(locationListSource);
	refresh();
	$(document).on('click','.nick-name',function(e){ // 닉네임 클릭하면 해당 회원 dashboard로 가게
		location.href='dashboard.html?no='+$(this).attr("data-dashNo");
		e.preventDefault();
	});

})
function refresh() {
	getDashAjax(dashNo);
	$('.post').remove();
	listAjax("planlist.do?boardNo=" + dashNo,$('#myschedule') ,$('a[href="#myschedule"] >span'),scheduleListTemplate);
	listAjax("scplanlist.do?boardNo=" + dashNo,$('#scrap-schedule'), $('a[href="#scrap-schedule"] >span'), scrapListTemplate);
	listAjax("sclocationlist.do?boardNo=" + dashNo,$('#scrap-location'), $('a[href="#scrap-location"] >span'), locationListTemplate);
	
}