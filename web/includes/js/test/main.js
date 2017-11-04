// JavaScript Document
//Load up jQuery
//code for sc2gg.com by zeeight@gmail.com
$(document).ready(function() {
	left_col_hover("#eeeeee","#5f7fa1");
	rotate_news();
});

function left_col_hover(original,target) {
	$("div#left_col ul li a").hover(function() {
		$(this).animate({ backgroundColor: target, color: "#ffffff"},100);																			 
	},function(){
		$(this).animate({ backgroundColor: original, color: "#444" },200);
	});
}

function rotate_news() {
	$('#news_rotator').cycle({ 
    fx:    'scrollUp', 
		timeout:       5000,  // milliseconds between slide transitions (0 to disable auto advance) 
    continuous:    0,     // true to start next transition immediately after current one completes 
    speed:         1000,  // speed of the transition (any valid fx speed value) 
    speedIn:       null,  // speed of the 'in' transition 
    speedOut:      null,  // speed of the 'out' transition 
    next:          null,  // id of element to use as click trigger for next slide 
    prev:          null,  // id of element to use as click trigger for previous slide 
    prevNextClick: null,  // callback fn for prev/next clicks:  function(isNext, zeroBasedSlideIndex, slideElement) 
    pager:         null,  // id of element to use as pager container 
    pagerClick:    null,  // callback fn for pager clicks:  function(zeroBasedSlideIndex, slideElement) 
    pagerEvent:   'click', // event which drives the pager navigation 
    pagerAnchorBuilder: null, // callback fn for building anchor links 
    before:        news_highlight,  // transition callback (scope set to element to be shown) 
    after:         null,  // transition callback (scope set to element that was shown) 
    end:           null,  // callback invoked when the slideshow terminates (use with autostop or nowrap options) 
    easing:        null,  // easing method for both in and out transitions 
    easeIn:        null,  // easing for "in" transition 
    easeOut:       null,  // easing for "out" transition 
    shuffle:       null,  // coords for shuffle animation, ex: { top:15, left: 200 } 
    animIn:        null,  // properties that define how the slide animates in 
    animOut:       null,  // properties that define how the slide animates out 
    cssBefore:     null,  // properties that define the initial state of the slide before transitioning in 
    cssAfter:      null,  // properties that defined the state of the slide after transitioning out 
    fxFn:          null,  // function used to control the transition 
    height:       '187', // container height 
    startingSlide: 0,     // zero-based index of the first slide to be displayed 
    sync:          1,     // true if in/out transitions should occur simultaneously 
    random:        0,     // true for random, false for sequence (not applicable to shuffle fx) 
    fit:           0,     // force slides to fit container 
    pause:         1,   // true to enable "pause on hover"
		delay:         1000     // additional delay (in ms) for first transition (hint: can be negative) 
 });
}
function news_highlight() {
	$("div.news_headlines li a").each(function(){
		$(this).removeClass("active");
		$(this).animate({ backgroundColor: "#eeeeee", color: "#444"},100);																			 
	});
	var target_li = $(this).attr("id");
	$("div.news_headlines a."+target_li).addClass("active");
	$("div.news_headlines a."+target_li).animate({ backgroundColor: "#5f7fa1", color: "#ffffff"},100);	
}