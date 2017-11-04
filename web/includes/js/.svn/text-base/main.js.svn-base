// JavaScript Document
//Load up jQuery
//code for sc2gg.com by zeeight@gmail.com
$(document).ready(function() {
	preloader();
	menu_rollovers();
	//header_rotate();
	//check if exists before launch!
	prep_comments();
	watch_comment_length();
	//COMMENTS SUBMISSIONS
	jQuery(".comments_button").click(function() {
		//grab the all-important topic_id (or tid, if you prefer)
		var topic_id = jQuery(this).attr("id");
		//grab just the number
		topic_id = topic_id.substring(7);
		//check the length
		var comment_post = jQuery("#new_comment_form_"+topic_id+" > textarea").val();
		if(comment_post.length > 200) {
			alert("You cannot post a comment more than 200 characters in length!");
			return false;
		} else if (comment_post.length < 2) {
			alert("Your comment is too short!");
			return false;
		} else {
			//determine the FORM ID and the CONTAINING DIV
			var form_id = "new_comment_form_"+topic_id;
			var target_div = "replyform_"+topic_id;
			jQuery("#"+form_id).ajaxSubmit(function(){
				jQuery("#"+target_div).slideUp("fast",function(){
					jQuery("#"+target_div).empty();
					jQuery("#"+target_div).append("<p class=\"thanks\">Comment submitted! Thanks for your comment!</p>");
					jQuery("#"+target_div).slideDown();
				});
			});
		}
		return false;
	});
});
/* PREPARE MENU ROLLOVERS */
function menu_rollovers() {
	jQuery("#menu a img").hover(function(){
		var target_src = jQuery(this).parent().attr("class");
		jQuery(this).attr("src","http://www.sc2gg.com/images/buttons/"+target_src+".gif");
	}, function() {
		var original_src = jQuery(this).parent().attr("id");
		jQuery(this).attr("src","http://www.sc2gg.com/images/buttons/"+original_src+".gif");
	});
}
/* PREPARE COMMENTS FOR SHOWING/HIDING */
function prep_comments() {
	jQuery("a.commentsbar").click(function() {
		var topic = jQuery(this).parent().attr("id");
		var topic_container = topic + "_container";
		//change to "show" or "hide"
		var link_text = jQuery(this).children("<small>").text();
		if (link_text == "(+ show)") {
			jQuery(this).children("<small>").text("(- hide)");
		} else if (link_text == "(- hide)") {
			jQuery(this).children("<small>").text("(+ show)");
		}
		show_or_hide_comments(topic_container);
		return false;
	});
}
function show_or_hide_comments(target) {
	if(jQuery("#"+target).length > 0) {
		var status = jQuery("#"+target).css("display");
		if (status == "none") {
			jQuery("#"+target).show();
		} else if (status == "block") {
			jQuery("#"+target).hide();
		}
	}
}
function watch_comment_length() {
	jQuery("textarea").keyup(function() {
		var curr_chars = jQuery(this).val().length	;
		var counter_id = jQuery(this).attr("class").substr(12);
		counter_id = "#remaing_chars_"+counter_id;
		var char_remaining = 200 - curr_chars;
		jQuery(counter_id).text(char_remaining);																					 
	});
}
/* ROTATE THE HEADERS -- CURRENTLY NOT IN USE!! */
function header_rotate() {
	var headers = new Array();
	headers[0] = "url(http://www.sc2gg.com/images/layout/mainLogo_blue.jpg)";
	headers[1] = "url(http://www.sc2gg.com/images/layout/mainLogo_green.jpg)";
	headers[2] = "url(http://www.sc2gg.com/images/layout/mainLogo_crimson.jpg)";
	var headers_length = headers.length;
	var whichImage = Math.round(Math.random()*(headers_length-1));
	jQuery("#header").css("backgroundImage",headers[whichImage]);
}
/* PRELOAD IMAGES (ONLY USED IN MAIN MENU RIGHT NOW! */
function preloader() {
	/* MENU BUTTONS */
	pic1= new Image(100,37); 
	pic1.src="http://www.sc2gg.com/images/buttons/home_2.gif";
	pic2= new Image(85,37); 
	pic2.src="http://www.sc2gg.com/images/buttons/forums_2.gif";
	pic3= new Image(115,37); 
	pic3.src="http://www.sc2gg.com/images/buttons/chat_2.gif";
	pic4= new Image(80,37); 
	pic4.src="http://www.sc2gg.com/images/buttons/vods_2.gif";
	pic5= new Image(80,37); 
	pic5.src="http://www.sc2gg.com/images/buttons/msl_2.gif";
}