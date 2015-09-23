cloudStreetMarketApp.factory("communityFactory", function (httpAuth) {
    return {
        getUsersActivity: function (pn, ps) {
        	return httpAuth.get("/api/users/feed.json?page="+pn+"&size="+ps);
        }
    }
});

cloudStreetMarketApp.filter('orderObjectBy', function() {
	  return function(items, field, reverse) {
	    var filtered = [];
	    angular.forEach(items, function(item) {
	      filtered.push(item);
	    });
	    filtered.sort(function (a, b) {
	      return (a[field] > b[field] ? 1 : -1);
	    });
	    if(reverse) filtered.reverse();
	    return filtered;
	  };
	});

cloudStreetMarketApp.controller('homeCommunityActivityController', function ($scope, $timeout, httpAuth, modalService, communityFactory, genericAPIFactory, $filter){

	$scope.init = function () {
		$scope.loadMore();
		$('.feedEkList').slimScroll({
	        height: '365px',
	        railVisible: true,
	        disableFadeOut: true,
	        wheelStep: $scope.pageSize
	    }).bind('slimscroll', function(e, pos){
	    	if(pos === "bottom")
	    		$scope.loadMore();
	    });
	}
	
	$scope.loadMore = function () {
		communityFactory.getUsersActivity(pageNumber, $scope.pageSize)
			.success(
				function(usersData, status, headers, config) {
					if(usersData.content){
			        	if(usersData.content.length >0){
			        		pageNumber++;
			        	}
			        	$.each( usersData.content, function(index, el ) {
			        		usersData.content[index].urlProfileMiniPicture = $scope.renamePictureToMini(el.urlProfilePicture);
			        		$scope.communityActivities[usersData.content[index].id] = usersData.content[index];

			        		 //Check if other people have liked the action
			        		 if($scope.communityActivities[usersData.content[index].id].amountOfLikes > 0 
			        				 && $scope.communityActivities[usersData.content[index].id].authorOfLikes[httpAuth.getLoggedInUser()]){
			        			 $scope.communityActivities[usersData.content[index].id].userHasLiked = true;
			        		 }
			        	});
					}
					
					var timer = $timeout( function(){ 
							$scope.socket = new SockJS('/ws/channels/users/broadcast');
							$scope.stompClient = Stomp.over($scope.socket);
							$scope.socket.onclose = function() {
								$scope.stompClient.disconnect();
							};
							$scope.stompClient.connect({}, function(frame) {
								$scope.stompClient.subscribe('/topic/actions', function(message){
									 var newActivity = JSON.parse(message.body);
									 newActivity.urlProfileMiniPicture = $scope.renamePictureToMini(newActivity.urlProfilePicture);
									 if(newActivity.userAction.type == 'LIKE'){
							        	 if($scope.communityActivities[newActivity.targetActionId]){
							        		 $scope.communityActivities[newActivity.targetActionId].amountOfLikes = $scope.communityActivities[newActivity.targetActionId].amountOfLikes +1;
							        		 $scope.communityActivities[newActivity.targetActionId].authorOfLikes[newActivity.userName] = newActivity.id;
							        		 if(newActivity.userName == httpAuth.getLoggedInUser()){
							        			 $scope.communityActivities[newActivity.targetActionId].userHasLiked = true;
							        		 }
							        	 }
									 }
									 else if(newActivity.userAction.type == 'COMMENT'){
							        	 if($scope.communityActivities[newActivity.targetActionId]){
							        		 $scope.communityActivities[newActivity.targetActionId].amountOfComments = $scope.communityActivities[newActivity.targetActionId].amountOfComments +1;
							        	 }
									 }
									 else{
										 $scope.communityActivities[newActivity.id]=newActivity;
									 }
						        	 $scope.$apply();
						         });
						    });
					        $scope.$on(
					                "$destroy",
					                function( event ) {
					                	$timeout.cancel( timer );
					                	$scope.stompClient.disconnect();
					                }
					        );
					}, 5000);
				}).then(function(response){
					if(response.headers('Must-Register')){
						modalService.showModal({templateUrl:'/portal/html/partials/must_register_modal.html'}, {});
					}
					if(response.headers('Authenticated')){
						httpAuth.setSession('authenticatedCSM', response.headers('Authenticated'));
					}
				});
	};

	$scope.like = function (targetActionId){
		var likeAction = {
			  id: null,
			  type: 'LIKE',
			  date: null,
			  targetActionId: targetActionId,
			  userId: httpAuth.getLoggedInUser()
		};
		genericAPIFactory.post("/api/actions/likes", likeAction);
	}
	
	$scope.unLike = function (el){
		var likeActionId = el.authorOfLikes[httpAuth.getLoggedInUser()];
		genericAPIFactory.del("/api/actions/likes/"+likeActionId).success(
			function(usersData, status, headers, config) {
				if(status==204){
					 if($scope.communityActivities[el.id].amountOfLikes > 0){
						 $scope.communityActivities[el.id].amountOfLikes = $scope.communityActivities[el.id].amountOfLikes-1;
						 delete $scope.communityActivities[el.id].authorOfLikes[httpAuth.getLoggedInUser()];
						 $scope.communityActivities[el.id].userHasLiked = true;
					 }
				}
			});
	}
	
	$scope.renamePictureToMini = function (name) {
		if(!name){
			return "";
		}
		var ext = '.' + name.split('.').pop();
		return name = name.substring(0, name.length-ext.length) + "-mini"+ ext;
	}

   pageNumber = 0;
   $scope.communityActivities = {};
   $scope.pageSize=10;
   $scope.injectedActions = [];
   $scope.init();
});