cloudStreetMarketApp.factory("communityFactory", function () {
	var data=[];
    return {
        fetchData: function () {
        	return data;
        },
        pull: function () {
        	
        	$.each( userActivities, function(index, el ) {
        		if(el.userAction =='BUY'){
        			userActivities[index].iconDirection='ico-up-arrow actionBuy';
        		}
        		else{
        			userActivities[index].iconDirection='ico-down-arrow actionSell';
        		}
        		userActivities[index].defaultProfileImage='';
        		if(!el.urlProfilePicture){
        			userActivities[index].defaultProfileImage='ico-user';
        		}
        		userActivities[index].price='$'+el.price;
        	});
        	data = userActivities;
        }
    }
});

cloudStreetMarketApp.controller('homeCommunityActivityController', function ($scope, communityFactory){
	   communityFactory.pull();
	   $scope.communityActivities = communityFactory.fetchData();
});
