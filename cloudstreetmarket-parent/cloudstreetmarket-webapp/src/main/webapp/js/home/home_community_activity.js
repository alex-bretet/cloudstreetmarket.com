cloudStreetMarketApp.factory("communityFactory", function (httpAuth) {
    return {
        getUsersActivity: function (pn) {
        	return httpAuth.get("/api/users/activity.json?page="+pn+"&size=10");
        }
    }
});

cloudStreetMarketApp.controller('homeCommunityActivityController', function ($scope, communityFactory){
	
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
		communityFactory.getUsersActivity(pageNumber).success(function(usersData, status, headers, config) {
			if(usersData.content){
	        	if(usersData.content.length >0){
	        		pageNumber++;
	        	}
	        	$.each( usersData.content, function(index, el ) {
	        		$scope.communityActivities.push(usersData.content[index]);
	        	});
			}
		});
	};
   
   pageNumber = 0;
   $scope.communityActivities = [];
   $scope.init();

});