angular.module("acesApp")
.config(function (
	$routeProvider
,	$httpProvider
,	$locationProvider
,	acesConstants
) {
	$locationProvider.html5Mode({
		  enabled: true,
		  requireBase: false
		});	
	$routeProvider.when("/"+acesConstants.PATH_LOGIN, {
		templateUrl: acesConstants.HTML_LOGIN
	});	
	$routeProvider.when("/"+acesConstants.PATH_REGISTER, {
		templateUrl: acesConstants.HTML_REGISTER
	});	
	$routeProvider.when("/"+acesConstants.PATH_PRODUCT_LIST, {
		templateUrl: acesConstants.HTML_PRODUCT_LIST
	});	
	$routeProvider.when("/"+acesConstants.PATH_PRODUCT, {
		templateUrl: acesConstants.HTML_PRODUCT
	});	
	$routeProvider.when("/"+acesConstants.PATH_ACCOUNT_SETTINGS, {
		templateUrl: acesConstants.HTML_ACCOUNT_SETTINGS
	});
	$routeProvider.when("/"+acesConstants.PATH_ABOUT_US, {
		templateUrl: acesConstants.HTML_ABOUT_US
	});
	$routeProvider.when("/"+acesConstants.PATH_TEAM, {
		templateUrl: acesConstants.HTML_TEAM
	});
	$routeProvider.when("/"+acesConstants.PATH_CONTACT, {
		templateUrl: acesConstants.HTML_CONTACT
	});
	$routeProvider.when("/"+acesConstants.PATH_PRIVACY_POLICY, {
		templateUrl: acesConstants.HTML_PRIVACY_POLICY
	});
	$routeProvider.when("/"+acesConstants.PATH_TERMS, {
		templateUrl: acesConstants.HTML_TERMS
	});
	$routeProvider.when("/"+acesConstants.PATH_RESET_PASSWORD, {
		templateUrl: acesConstants.HTML_RESET_PASSWORD
	});
	$routeProvider.when("/"+acesConstants.PATH_DASHBOARD, {
		templateUrl: acesConstants.HTML_DASHBOARD
	});
	$routeProvider.when("/"+acesConstants.PATH_CREATE_EXAM, {
		templateUrl: acesConstants.HTML_CREATE_EXAM
	});
	$routeProvider.when("/"+acesConstants.PATH_CREATE_QUIZ, {
		templateUrl: acesConstants.HTML_CREATE_QUIZ
	});
	$routeProvider.when("/"+acesConstants.PATH_QUESTION, {
		templateUrl: acesConstants.HTML_QUESTION
	});
	$routeProvider.when("/"+acesConstants.PATH_EXAM_SUMMARY, {
		templateUrl: acesConstants.HTML_EXAM_SUMMARY
	});
	$routeProvider.when("/"+acesConstants.PATH_EXAM_CONFIRM, {
		templateUrl: acesConstants.HTML_EXAM_CONFIRM
	});
	$routeProvider.when("/"+acesConstants.PATH_ACTIVATE_ACCOUNT, {
		templateUrl: acesConstants.HTML_ACTIVATE_ACCOUNT
	});
	$routeProvider.otherwise({
		templateUrl: acesConstants.HTML_ABOUT_US
	});
	$httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
	$httpProvider.interceptors.push('securityInterceptor');
});

//Factories Start
var factories = {};
factories.securityInterceptor = function($q, $location,acesConstants) {
	return {
		request : function(config) {
			return config;
		},
		response : function(response) {
			return response || $q.when(response);
		},
		responseError: function (rejection) {
			if(rejection.status === 401) {				
				$location.path(acesConstants.PATH_LOGIN);
	        }
			if(rejection.status === 403) {				
				$location.$$search = {};
				$location.path("/");
	        }
	        return $q.reject(rejection);
	   }
	};
};
//Factories End

//Filters Start
var filters = {};
filters.unsafeHTML = function ($sce) {
	return function(val){
		return $sce.trustAsHtml(val);
	};
};
//Filters End
angular.module("acesApp").factory(factories);
angular.module("acesApp").filter(filters);