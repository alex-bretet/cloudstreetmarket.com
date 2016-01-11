cloudStreetMarketApp.factory("accountManagementFactory", function ($http, httpAuth) {
    return {
        login: function (body) {
        	return httpAuth.post('/api/sessions/login', body);
        },
        createAccount: function (body, spi) {
        	if(spi){
            	httpAuth.setSession('Spi', spi);
            	httpAuth.setSession('OAuthProvider', 'yahoo');
        	}
        	return httpAuth.post('/api/users', body);
        },
        updateAccount: function (body) {
        	return httpAuth.put('/api/users', body);
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

cloudStreetMarketApp.controller('accountController', function ($scope, $translate, $location, errorHandler, accountManagementFactory, httpAuth, genericAPIFactory){
      $scope.form = {
      		id: "",
    		email: "",
    		headline: "",
    		password: "",
    		language: "EN",
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
					httpAuth.setSession('authenticatedCSM', response.headers('Authenticated'));
				}
				window.location="../portal/index";
		  });
	  };
	  
	  $scope.update = function () {
		  $scope.formSubmitted = true;

		  if(!$scope.updateAccount.$valid) {
		       return;
		  }

		  httpAuth.put('/api/users', JSON.stringify($scope.form)).success(
			  function(data, status, headers, config) {
				httpAuth.setCredentials($scope.form.id, $scope.form.password);
				$scope.updateSuccess = true;
		  }).error(function(data, status, headers, config) {
			  $scope.updateFail = true;
			  $scope.updateSuccess = false;
			  $scope.serverErrorMessage = errorHandler.renderOnForms(data);
		  });
	  };

	  //Only called in creation..
	  $scope.updateCredit = function(){
		  if($scope.form.currency=="USD"){
			  $scope.credit = 20000;
		  }
		  else{
			  accountManagementFactory.getCurrencyX("USD"+$scope.form.currency+"=X").success(
				  function(data, status) {
					  $scope.credit = data.ask*20000;
				  });
		  }
	  }

	  $scope.restFormFromUser = function() {
		  genericAPIFactory.get("/api/users/"+httpAuth.getLoggedInUser()+".json")
			.success(function(data, status, headers, config) {
				if(!data.profileImg){
					data.profileImg = "img/anon.png";
				}
				if(data.bigProfileImg){
					delete data.bigProfileImg;
				}
				data.password ='';
				$scope.form = data;
		  });
	  }
	  
	  $scope.progressVisible = false;
	  $scope.progressType = "warning";
	  $scope.progress = 0;
	  $scope.credit = 20000;
	  $scope.formSubmitted = false;
	  $scope.serverErrorMessage ="";
	  $scope.updateSuccess = false;
	  $scope.updateFail = false;
	  
	  if(httpAuth.isUserAuthenticated() && !$scope.formSubmitted){
		  $scope.restFormFromUser();
	  }

	  $scope.setLanguage = function(language) {
		  $translate.use(language);
		  $scope.form.language = language;
	  }
	  
	  $scope.setFiles = function(element) {
		  $scope.progressType = "warning";
		  $scope.progress = 0;
	      $scope.progressVisible = true;
	      $scope.form.profileImg = "img/anon.png";
	      
	      var fd = new FormData();
	      fd.append("file",  element.files[0]);

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
					httpAuth.setSession('authenticatedCSM', response.headers('Authenticated'));
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
