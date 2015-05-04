cloudStreetMarketApp.controller('menuController',  function ($scope, modalService, httpAuth) {
	
	$scope.menuModal = function (feature) {
        modalService.showModal({templateUrl:'/portal/html/partials/'+feature+'_modal.html'}, {});
    }
	
	$scope.userAuthenticated = function () {
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
    }

	$scope.init();
});