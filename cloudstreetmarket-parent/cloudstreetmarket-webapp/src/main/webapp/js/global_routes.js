/** * Configure the Routes */ 
cloudStreetMarketApp.config(function($locationProvider, $routeProvider) {
  $locationProvider.html5Mode(true);
  $routeProvider
    .when('/portal/index', {
      templateUrl: '/portal/html/home.html', 
      controller: 'homeMainController'
    })
	.when('/portal/indices-:name', {
	    templateUrl: '/portal/html/indices-by-market.html', 
	    controller: 'indicesByMarketTableController' 
	})
    .when('/portal/stock-search', {
      templateUrl: '/portal/html/stock-search.html', 
      controller:  'stockSearchMainController'
    })
    .when('/portal/stock-search-by-market', {
      templateUrl: '/portal/html/stock-search-by-market.html', 
      controller:  'stockSearchByMarketMainController'
    })
    .when('/portal/stocks-risers-fallers', {
      templateUrl: '/portal/html/stocks-risers-fallers.html', 
      controller:  'stocksRisersFallersMainController'
    })
    .otherwise({ redirectTo: '/' });
});

cloudStreetMarketApp.controller("homeMainController", function($scope) {});
cloudStreetMarketApp.controller("indicesByMarketsMainController", function($scope) {})
cloudStreetMarketApp.controller("stockSearchMainController", function($scope) {})
cloudStreetMarketApp.controller("stockSearchByMarketMainController", function($scope) {})
cloudStreetMarketApp.controller("stocksRisersFallersMainController", function($scope) {})