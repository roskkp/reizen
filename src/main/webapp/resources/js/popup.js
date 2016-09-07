$(document).on("click",".snip1253",function(e) {  	
    $('.popup-wrap').fadeIn(250);
    $('.popup-box').removeClass('transform-out').addClass('transform-in');

    e.preventDefault();
 
  });

  $('.popup-close').click(function(e) {
    $('.popup-wrap').fadeOut(500);
    $('.popup-box').removeClass('transform-in').addClass('transform-out');

    e.preventDefault();
  });

  $('.js-tab').click(function() {
		var contentId = $(this).attr('data-content');
		$('.js-tab').removeClass('active');
		$('.tab--content').removeClass('active');
		$(this).addClass('active');
		$('#' + contentId).addClass('active');
});
  
  //tab
  function activateTab() {
	    if(activeTab) {
	      resetTab.call(activeTab);
	    }
	    this.parentNode.className = 'tab tab-active';
	    activeTab = this;
	    activePanel = document.getElementById(activeTab.getAttribute('href').substring(1));
	   	activePanel.className = 'tabpanel show';
	  	activePanel.setAttribute('aria-expanded', true);
	  }
	  
	  function resetTab() {
			activeTab.parentNode.className = 'tab';
	    if(activePanel) {
	    	activePanel.className = 'tabpanel hide';
	  		activePanel.setAttribute('aria-expanded', false);
	    }
	  }
	  
	  var doc = document,
	      tabs = doc.querySelectorAll('.tab a'),
	      panels = doc.querySelectorAll('.tabpanel'),
	      activeTab = tabs[0],
	      activePanel;
	 
	 	activateTab.call(activeTab);
	  
	  for(var i = tabs.length - 1; i >= 0; i--) {
	    tabs[i].addEventListener('click', activateTab, false);
	  }

	