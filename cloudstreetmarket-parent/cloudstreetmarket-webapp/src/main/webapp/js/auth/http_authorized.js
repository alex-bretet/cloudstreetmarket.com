cloudStreetMarketApp.factory("httpAuth", function ($http) {
    return {
    	clearSession: function () {
    		var authBasicItem = sessionStorage.getItem('basicHeaderCSM');
    		var oAuthSpiItem = sessionStorage.getItem('oAuthSpiCSM');
    		if(authBasicItem || oAuthSpiItem){
    			sessionStorage.removeItem('basicHeaderCSM');
    			sessionStorage.removeItem('oAuthSpiCSM');
    			sessionStorage.removeItem('authenticatedCSM');
    			$http.defaults.headers.common.Authorization = undefined;
    			$http.defaults.headers.common.Spi = undefined;
    			$http.defaults.headers.common.OAuthProvider = undefined;
    		}
        },
    	refresh: function(){
    		var authBasicItem = sessionStorage.getItem('basicHeaderCSM');
    		var oAuthSpiItem = sessionStorage.getItem('oAuthSpiCSM');
    		if(authBasicItem){
    			$http.defaults.headers.common.Authorization = $.parseJSON(authBasicItem).Authorization;
    		}
    		if(oAuthSpiItem){
    			$http.defaults.headers.common.Spi = oAuthSpiItem;
    			$http.defaults.headers.common.OAuthProvider = "yahoo";
    		}
    	},
    	setHeaders: function(xmlHTTP){
    		this.refresh();
    		var authBasicItem = sessionStorage.getItem('basicHeaderCSM');
    		var oAuthSpiItem = sessionStorage.getItem('oAuthSpiCSM');
    		if(authBasicItem){
    			xmlHTTP.setRequestHeader("Authorization", $.parseJSON(authBasicItem).Authorization);
    		}
    		if(oAuthSpiItem){
    			xmlHTTP.setRequestHeader("Spi", oAuthSpiItem);
    			xmlHTTP.setRequestHeader("OAuthProvider", "yahoo");
    		}
    	},
    	setCredentials: function (login, password) {
    		var encodedData = window.btoa(login+":"+password);
    		var basicAuthToken = 'Basic '+encodedData;
        	var header = {Authorization: basicAuthToken};
        	sessionStorage.setItem('basicHeaderCSM', JSON.stringify(header));
        	$http.defaults.headers.common.Authorization = basicAuthToken;
        },
    	setCsrfToken: function (token) {
        	$http.defaults.headers.common.Authorization = basicAuthToken;
        },
    	setSession: function (attributeName, attributeValue) {
    		sessionStorage.setItem(attributeName, attributeValue);
        },
    	getSession: function (attributeName) {
    		return sessionStorage.getItem(attributeName);
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
        get: function (url, headers) {
        	this.refresh();
        	return $http.get(url, {
        	    headers: headers
        	});
        },
        isUserAuthenticated: function () {
    		var authBasicItem = sessionStorage.getItem('authenticatedCSM');
    		if(authBasicItem){
    			return true;
    		}
    		return false;
        }
    }
});