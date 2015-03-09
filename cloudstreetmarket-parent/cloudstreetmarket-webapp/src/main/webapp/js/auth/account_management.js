cloudStreetMarketApp.factory("accountManagementFactory", function ($http) {
    return {
        login: function (body) {
        	return $http.post('/api/users/login', body);
        },
        createAccount: function (body) {
        	return $http.post('/api/users', body);
        },
        saveImage: function (formData) {
        	return $http.post('/api/users/file', formData, {
                headers: {'Content-Type': undefined },
                transformRequest: angular.identity
            });
        }
    }
});

cloudStreetMarketApp.controller('createNewAccountController', function ($scope, accountManagementFactory, httpAuth){
      $scope.form = {
      		username: "",
    		email: "",
    		password: "",
    		currency: "",
    		profileImg: "img/anon.png"
      };

	  $scope.create = function () {
		  accountManagementFactory.createAccount(JSON.stringify($scope.form)).success(
				  function(data, status, headers, config) {
					httpAuth.set($scope.form.username, $scope.form.password);
					angular.element($('.modal')[0]).scope().modalOptions.close();	
		  });
	  }
	  
	  $scope.progressVisible = false;
	  $scope.progressType = "warning";
	  $scope.progress = 0;
	  
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
    	username: "",
  		password: ""
      };

	  $scope.login = function () {
		  accountManagementFactory.login(JSON.stringify($scope.form)).success(
				  function(data, status, headers, config) {
					httpAuth.set($scope.form.username, $scope.form.password);
					angular.element($('.modal')[0]).scope().modalOptions.close();	
		  });
	  }
});
