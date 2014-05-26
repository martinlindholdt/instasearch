$(document).ready(function(){
	$("#next").hide();
	$("#search").click(is.search);
	$("#next").click(is.nextpage);
	$("#save").click(is.saveImages);

});

var baseurl = "/instasearch/";
var nexturl = ""; 

var saves = []; 

var is = {

	search:function(){

		var tag = $("#tag").val();
		$("#list").empty();
		saves = [];

		is.tagcount(tag);
		$.getJSON(baseurl + "search/" + tag, function(o, status){
			//console.log(status);
			//console.log(o.pagination.next_url);
			nexturl = o.pagination.next_url;
			$(o.data).each(is.drawImage);
		});
		$("#next").show();
		return false;
	}, 
	drawImage:function(i, idata){
		//console.log(idata);
		// console.log(i);

		//var caption = idata.caption.text; 
		//var fullName = idata.user.full_name; 
		var username = idata.user.username; 
		var imageUri = idata.images.standard_resolution.url; 
		saves.push(imageUri); 

		$("#list").append('<li><img src='+imageUri+'><p><a href="' + idata.link + '" target="_blank">'+username+'</a></p></li>');

	}, 
	nextpage:function(){
		$.getJSON(baseurl + "search/" + tag +"/next?uri=" + nexturl, function(o, status){
			nexturl = o.pagination.next_url;
			$(o.data).each(is.drawImage);
		});
		return false;
	}, 
	tagcount:function(tag){
		$.getJSON(baseurl+"search/" + tag + "/count", function(d, status){
			//console.log(d);
			$("#count").empty().append(d.count);

		});

	}, 
	saveImages:function(){
		$.post(baseurl+"save/", {'saves' : saves}, function(d,status){
			console.log(saves, status, d);
		}); 
	}
}