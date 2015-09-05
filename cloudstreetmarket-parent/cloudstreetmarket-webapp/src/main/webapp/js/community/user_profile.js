cloudStreetMarketApp.controller('userProfileController', function ($scope, $http, httpAuth, genericAPIFactory, $routeParams){
    $scope.user = {
      		id: "",
    		email: "",
    		headline: "",
    		password: "",
    		language: "EN",
    		currency: "",
    		profileImg: "img/anon.png",
    };

	$scope.currencyExchange =1;
	$scope.loggedInUser = httpAuth.getLoggedInUser();
	$scope.user.bigProfileImg= "img/anon.png";

	genericAPIFactory.get("/api/users/"+$routeParams.username+".json")
	.success(function(data, status, headers, config) {
		$scope.user = data;
		if($scope.loggedInUser && $scope.loggedInUser.currency){
				if($scope.user.currency != $scope.loggedInUser.currency){
					genericAPIFactory.get("/api/currencyX/"+ $scope.loggedInUser.currency+$scope.user.currency+"=X.json")
						.success(function(data, status, headers, config) {
							$scope.currencyExchange = data.ask;
					});
				}
		}
	});
});
