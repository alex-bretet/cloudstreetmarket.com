cloudStreetMarketApp.factory("accountManagementFactory", function ($http, httpAuth) {
    return {
        login: function (body) {
        	return httpAuth.post('/api/oauth2/login', body);
        },
        createAccount: function (body, spi) {
        	if(spi){
            	httpAuth.setSession('Spi', spi);
            	httpAuth.setSession('OAuthProvider', 'yahoo');
        	}
        	return httpAuth.post('/api/users', body);
        },
        saveImage: function (formData) {
        	return $http.post('/api/images/users', formData, {
                headers: {'Content-Type': undefined },
                transformRequest: angular.identity
            });
        },
        getCurrencyX: function (pn) {
        	return httpAuth.get("/api/currencyX/"+ pn +".json");
        }
    }
});

cloudStreetMarketApp.controller('createNewAccountController', function ($scope, accountManagementFactory, httpAuth){
      $scope.form = {
      		id: "",
    		email: "",
    		password: "",
    		currency: "",
    		profileImg: "img/anon.png"
      };

	  $scope.create = function (forOAuth) {
		  var oAuthSpiItem = null;
		  if(forOAuth){
	  		  oAuthSpiItem = httpAuth.getSession('oAuthSpiCSM');
		  }
		  
		  accountManagementFactory.createAccount(JSON.stringify($scope.form), oAuthSpiItem).success(
			  function(data, status, headers, config) {
				httpAuth.setCredentials($scope.form.id, $scope.form.password);
				angular.element($('.modal')[0]).scope().modalOptions.close();	
		  }).then(function(response){
				if(response.headers('Authenticated')){
					httpAuth.setSession('authenticatedCSM', "true");
				}
				window.location="../portal/index";
		  });
	  };
	  
	  $scope.updateCredit = function(){
		  if($scope.form.currency=="USD"){
			  $scope.credit = 20000;
		  }
		  else{
			  accountManagementFactory.getCurrencyX("USD"+$scope.form.currency+"=X").success(
				  function(data, status) {
					  $scope.credit = data.dailyLatestValue*20000;
				  });
		  }
	  }

	  $scope.progressVisible = false;
	  $scope.progressType = "warning";
	  $scope.progress = 0;
	  $scope.credit = 20000;
	  
	  $scope.setFiles = function(element) {
		  $scope.progressType = "warning";
		  $scope.progress = 0;
	      $scope.progressVisible = true;
	      $scope.form.profileImg = "img/anon.png";
	      
	      var fd = new FormData();
	      fd.append("file",  element.files[0])

	      accountManagementFactory.saveImage(fd)
	    	.success(
				function(data, status, headers, config) {
		    	  $scope.progressType = "success";
		    	  $scope.form.profileImg = '..'+headers('Location');
		    	  $scope.progress = 100;
		  })
		  .error(
				function(data, status, headers, config) {
		    	  $scope.progressType = "danger";
		    	  $scope.progress = 100;
	      });
	   };
});

cloudStreetMarketApp.controller('LoginByUsernameAndPasswordController', function ($scope, accountManagementFactory, httpAuth){
      $scope.form = {
    	id: "",
  		password: "",
      };

	  $scope.login = function () {
		  accountManagementFactory.login(JSON.stringify($scope.form))
		  	.success(
				  function(data, status, headers, config) {
					httpAuth.setCredentials($scope.form.id, $scope.form.password);
					angular.element($('.modal')[0]).scope().modalOptions.close();
			}).then(function(response){
				if(response.headers('Authenticated')){
					httpAuth.setSession('authenticatedCSM', "true");
				}
				window.location="../portal/index";
			});
	  }
});

cloudStreetMarketApp.controller('OAuth2Controller', function ($scope, accountManagementFactory, httpAuth){
	  $scope.socialLogin = function () {
		 	$('#oAuthForm').submit();
	  }
});
