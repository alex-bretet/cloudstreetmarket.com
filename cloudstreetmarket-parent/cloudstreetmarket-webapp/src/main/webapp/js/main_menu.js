cloudStreetMarketApp.controller('menuController',  function ($scope, $translate, $location, modalService, httpAuth, genericAPIFactory) {
	
	$scope.menuModal = function (feature) {
        modalService.showModal({templateUrl:'/portal/html/partials/'+feature+'_modal.html'}, {});
    }
	
	$scope.userAuthenticated = function () {
		$scope.loggedInUser = httpAuth.getLoggedInUser();
        return httpAuth.isUserAuthenticated();
    }
	$scope.logout = function () {
        httpAuth.clearSession();
        window.document.location="../portal/index";
    }
	$scope.init = function () {
		if($('#spi').text()){
			httpAuth.setSession('oAuthSpiCSM', $('#spi').text());
		}
		if(httpAuth.getLoggedInUser()){
			genericAPIFactory.get("/api/users/"+httpAuth.getLoggedInUser()+".json")
			.success(function(data, status, headers, config) {
			    $translate.use(data.language);
			    $location.search('lang', data.language);
			})
		};
    }
	
	$scope.loggedInUser = null;
	$scope.init();
});

cloudStreetMarketApp.factory("genericAPIFactory", function (httpAuth) {
    return {
        get: function (url) {
        	return httpAuth.get(url);
        },
        post: function (url, body) {
        	return httpAuth.post(url, body);
        }
    }
});

cloudStreetMarketApp.factory('UrlLanguageStorage', ['$location', function($location) {
    return {
        put: function (name, value) {},
        get: function (name) {
            return $location.search()['lang']
        }
    };
}]);

cloudStreetMarketApp.factory("errorHandler", ['$translate', function ($translate) {
    return {
        render: function (data) {
        	if(data.message && data.message.length > 0){
        		return data.message;
        	}
        	else if(!data.message && data.i18nKey && data.i18nKey.length > 0){
        		return $translate(data.i18nKey);
        	}
        	return $translate("error.api.generic.internal");
        },
        renderOnForms: function (data) {
        	if(data.error && data.error.length > 0){
        		return data.error;
        	}
        	else if(data.message && data.message.length > 0){
            	return data.message;
        	}
        	else if(!data.message && data.i18nKey && data.i18nKey.length > 0){
        		return $translate(data.i18nKey);
        	}

        	return $translate("error.api.generic.internal");
        }
    }
}]);