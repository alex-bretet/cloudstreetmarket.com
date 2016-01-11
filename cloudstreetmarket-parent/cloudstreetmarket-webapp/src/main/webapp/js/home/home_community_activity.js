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

cloudStreetMarketApp.controller('homeCommunityActivityController', function ($scope, $rootScope, httpAuth, modalService, communityFactory, genericAPIFactory, $filter){

	var $this = this,
	socket = new SockJS('/api/users/feed/add'),
	stompClient = Stomp.over(socket);
    pageNumber = 0;
    $scope.communityActivities = {};
    $scope.pageSize=10;

	$scope.init = function () {
		$scope.loadMore();
		
		socket.onclose = function() {
			$scope.stompClient.disconnect();
		};
		socket.onclose = function() {
			$scope.stompClient.disconnect();
		};
		stompClient.connect({}, function(frame) {
			stompClient.subscribe('/topic/actions', function(message){
				 var newActivity = $this.prepareActivity(JSON.parse(message.body));
				 $this.addAsyncActivityToFeed(newActivity);
	        	 $scope.$apply();
	         });
	    });
		
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
			.then(function(response) {
				var usersData = response.data,
				status = response.status,
				headers  = response.headers,
				config = response.config;

				$this.handleHeaders(headers);

				if(usersData.content){
		        	if(usersData.content.length > 0){
		        		pageNumber++;
		        	}
		        	$this.addActivitiesToFeed(usersData.content);
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

    $this.addActivitiesToFeed = function(content){
    	$.each( content, function(index, el ) {
    		el.urlProfileMiniPicture = $scope.renamePictureToMini(el.urlProfilePicture);
    		$scope.communityActivities[el.id] = el;
    		 //Check if the user has liked the activity
    		 if($scope.communityActivities[el.id].amountOfLikes > 0 
    				 && $scope.communityActivities[el.id].authorOfLikes[httpAuth.getLoggedInUser()]){
    			 $scope.communityActivities[el.id].userHasLiked = true;
    		 }
    	});
    }
    
    $this.prepareActivity = function(newActivity){
		newActivity.urlProfileMiniPicture = $scope.renamePictureToMini(newActivity.urlProfilePicture);
		return newActivity;
    }
    
    $this.addAsyncActivityToFeed = function(newActivity){
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
    }
    
    $this.handleHeaders = function(headers){
    	if(headers('Must-Register')){
    		modalService.showModal({templateUrl:'/portal/html/partials/must_register_modal.html'}, {});
    	}
    	if(headers('Authenticated')){
    		httpAuth.setSession('authenticatedCSM', headers('Authenticated'));
    	}
    }

   $scope.init();
});