$(function() {
  $('.acc_ctrl').on('click', function(e) {
    e.preventDefault();
    if ($(this).hasClass('active')) {
    	$('.post-page').css('padding-top','0px');
      $(this).removeClass('active');
      $(this).next()
      .stop()
      .slideUp(400);
    } else {
      $(this).addClass('active');
      $('.post-page').css('padding-top','170px');
      $(this).next()
      .stop()
      .slideDown(400);
    }
  });
});