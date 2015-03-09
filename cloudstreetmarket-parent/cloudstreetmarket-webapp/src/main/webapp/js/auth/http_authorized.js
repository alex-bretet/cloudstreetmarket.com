cloudStreetMarketApp.factory("httpAuth", function ($http) {
    return {
    	clearSession: function () {
    		var sessionItem = sessionStorage.getItem('basicHeaderCSM');
    		if(sessionItem){
    			sessionStorage.removeItem('basicHeaderCSM');
    			$http.defaults.headers.common.Authorization = undefined;
    		}
        },
    	refresh: function(){
    		var sessionItem = sessionStorage.getItem('basicHeaderCSM');
    		if(sessionItem){
    			$http.defaults.headers.common.Authorization = $.parseJSON(sessionItem).Authorization;
    		}
    	},
    	set: function (login, password) {
    		var encodedData = window.btoa(login+":"+password);
    		var basicAuthToken = 'Basic '+encodedData;
        	var header = {Authorization: basicAuthToken};
        	sessionStorage.setItem('basicHeaderCSM', JSON.stringify(header));
        	$http.defaults.headers.common.Authorization = basicAuthToken;
        },
        post: function (url, body) {
        	this.refresh();
        	return $http.post(url, body);
        },
        post: function (url, body, headers, data) {
        	this.refresh();
        	return $http.post(url, body, headers, data);
        },
        get: function (url) {
        	this.refresh();
        	return $http.get(url);
        },
        isUserAuthenticated: function () {
    		var sessionItem = sessionStorage.getItem('basicHeaderCSM');
    		if(sessionItem){
    			return true;
    		}
    		return false;
        }
    }
});