cloudStreetMarketApp.factory("communityFactory", function (httpAuth) {
    return {
        getUsersActivity: function (pn) {
        	return httpAuth.get("/api/users/feed.json?page="+pn+"&size=10");
        }
    }
});

cloudStreetMarketApp.controller('homeCommunityActivityController', function ($scope, httpAuth, modalService, communityFactory){
	
	$scope.init = function () {
		$scope.loadMore();
		
		$('.feedEkList').slimScroll({
	        height: '365px',
	        railVisible: true,
	        disableFadeOut: true,
	        wheelStep: 10
	    }).bind('slimscroll', function(e, pos){
	    	if(pos === "bottom")
	    		$scope.loadMore();
	    });
	}
	
	$scope.loadMore = function () {
		communityFactory.getUsersActivity(pageNumber)
			.success(
				function(usersData, status, headers, config) {
					if(usersData.content){
			        	if(usersData.content.length >0){
			        		pageNumber++;
			        	}
			        	$.each( usersData.content, function(index, el ) {
			        		usersData.content[index].urlProfileMiniPicture = $scope.renamePictureToMini(el.urlProfilePicture);
			        		$scope.communityActivities.push(usersData.content[index]);
			        	});
					}
				}).then(function(response){
					if(response.headers('Must-Register')){
						modalService.showModal({templateUrl:'/portal/html/partials/must_register_modal.html'}, {});
					}
					if(response.headers('Authenticated')){
						httpAuth.setSession('authenticatedCSM', "true");
					}
				});
	};
	
	$scope.renamePictureToMini = function (name) {
		if(!name){
			return "";
		}
		var ext = '.' + name.split('.').pop();
		return name = name.substring(0, name.length-ext.length) + "-mini"+ ext;
	}

   pageNumber = 0;
   $scope.communityActivities = [];
   $scope.init();
});