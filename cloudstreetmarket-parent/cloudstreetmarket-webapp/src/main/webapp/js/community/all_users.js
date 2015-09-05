cloudStreetMarketApp.factory("usersFactory", function (httpAuth) {
    return {
        get: function (ps, pn, cn, sf, sd) {
        	return httpAuth.get("/api/users.json?cn="+cn+"&size="+ps+"&page="+pn+"&sort="+sf+","+sd);
        }
    }
});

cloudStreetMarketApp.controller('allUsersController', function PaginationCtrl($scope, usersFactory){
	  $scope.loadPage = function () {
		  usersFactory.get($scope.pageSize, $scope.currentPage, $scope.userFilter, $scope.sortedField, $scope.sortDirection)
			.success(function(data, status, headers, config) {
				updatePaginationUsers ($scope, data);
	        });
	  }

	  /*
	   * Pagination
	   */
	  $scope.setPage = function (pageNo) {
	    $scope.currentPage = pageNo-1;
	    $scope.loadPage();
	  };
	  initPaginationUsers ($scope);
	  
	  /*
	   * Request spec.
	   */
	  $scope.setContains = function () {
		  $scope.containsSubmitted = $scope.userFilter;
		  $scope.loadPage();
	  };

	  $scope.userFilter ="";

	  /*
	   * Sorting
	   */
	  $scope.setSort = function(field) {
		  updateSortParamUsers ($scope, field)
	  }
	  
	  $scope.loadPage();
});

/**
 * Static functions
 */
function updatePaginationUsers ($scope, data){
	$scope.users = data.content;
    $scope.currentPage = data.number;
    $scope.paginationCurrentPage = data.number+1;
    $scope.paginationTotalItems =  data.totalElements;//number total of objects
}

function updateSortParamUsers ($scope, field){
	  if( $scope.sortedField == field){
		  $scope.sortDirection = ($scope.sortDirection === "asc")? "desc" : "asc";
	  }
	  else{
		  $scope.sortDirection = "asc";
	  }
	  $scope.sortedField = field;
	  $scope.loadPage()
}

function initPaginationUsers ($scope){
	$scope.sortedField = "balance";
	$scope.sortDirection = "desc";
	
	$scope.maxSize = 12; //number of visible buttons for pages
	$scope.currentPage = 0;
	$scope.paginationTotalItems = 5;
	$scope.paginationCurrentPage = 1;
	$scope.pageSize = 15;
	$scope.users = [];
}