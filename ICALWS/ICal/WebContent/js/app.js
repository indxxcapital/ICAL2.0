var iCal = angular.module("myApp", ['ui.router','ngGrid']);
iCal.config(function($stateProvider, $urlRouterProvider) {
    $stateProvider
    //module1
        .state('ISubmission', {
            url: '/ISubmission',
            templateUrl: 'templates/ISubmission.html'
        })
        .state('ISubmission.setupindex', {
            url: '/setupindex',
            templateUrl: 'templates/IndexSubmission/IndexSetup.html',
            controller: 'addIndexController'
        })
        .state('ISubmission.addsecuritytoindex', {
            url: '/addsecuritytoindex',
            templateUrl: 'templates/CommonPages/fileUpload.html',
            controller: 'mapSecurityIndexController'
        })
        //module 2
        .state('MCapISubmission', {
            url: '/MCapISubmission',
            templateUrl: 'templates/ISubmission2.html'
        })
        .state('MCapISubmission.setupindex', {
            url: '/setupindex',
            templateUrl: 'templates/IndexSubmission/IndexSetup.html',
            controller: 'addIndexController2'
        })
        .state('MCapISubmission.addsecuritytoindex', {
            url: '/addsecuritytoindex',
            templateUrl: 'templates/CommonPages/fileUpload.html',
            controller: 'mapSecurityIndexController'
        })       
        //module 3
        .state('IManager', {
            url: '/IManager',
            templateUrl: 'templates/IManager.html'
        })
        .state('IManager.new', {
            url: '/new',
            templateUrl: 'templates/CommonPages/gridControl.html',
            controller: 'indexManagerController'
        })
        .state('IManager.upcoming', {
            url: '/upcoming',
            templateUrl: 'templates/CommonPages/gridControl.html',
            controller: 'upcomingIndexController'
        })
        .state('IManager.run', {
            url: '/run',
            templateUrl: 'templates/CommonPages/gridControl.html',
            controller: 'runIndexController'
        })
        .state('IManager.live', {
            url: '/live',
            templateUrl: 'templates/CommonPages/gridControl.html',
            controller: 'liveIndexController'
        })
        //module 4
        .state('SManager', {
            url: '/SManager',
            templateUrl: 'templates/SManager.html'
        })
        .state('SManager.security', {
            url: '/security',
            templateUrl: 'templates/SManager/securityGrid.html',
            controller: 'securityManagerController'
        })
        .state('SManager.parsesecurity', {
            url: '/parsesecurity',
            templateUrl: 'templates/SManager/prepareinput.html',
            controller: 'securityController'
        })
        .state('SManager.addsecurityprice', {
        	url: '/addsecurityprice',
            templateUrl: 'templates/CommonPages/fileUpload.html',
            controller: 'securityController'
        })
        .state('SManager.parseCurrency', {
        	url: '/parseCurrency',
            templateUrl: 'templates/SManager/prepareinput.html',
            controller: 'currencyController'
        })
        //module 5
        .state('CAManager', {
            url: '/CAManager',
            templateUrl: 'templates/CAManager.html'
        })
        .state('CAManager.All', {
            url: '/All',
            templateUrl: 'templates/CAManager/AllCAGrid.html',
            controller: 'CAController'
        })
        .state('CAManager.Todays', {
        	url: '/Todays',
            templateUrl: 'templates/CAManager/TodaysCAGrid.html',
            controller: 'TodaysCAController'
        })
       ;
//    $urlRouterProvider.otherwise('/settings/profile');
    $urlRouterProvider.otherwise('');
});

iCal.directive('fileModel', ['$parse', function ($parse) {
	return {
		restrict: 'A',
		link: function(scope, element, attrs) 
		{
			var model = $parse(attrs.fileModel);
			var modelSetter = model.assign;
			
			element.bind('change', function()
			{
				scope.$apply(function()
				{
					modelSetter(scope, element[0].files[0]);
				});
			});
		}
	};
}]); 
//iCal.controller('IndexModuleController',  ['$scope','$window', 'indexService','$http', function($scope,$window, indexService,$http)
//{
//	console.log('inside IndexModuleController');
//	$scope.title = "Proprietary Index Submission";	
//}]);
//iCal.controller('IndexModuleController2',  ['$scope','$window', 'indexService','$http', function($scope,$window, indexService,$http)
//{
//	console.log('inside IndexModuleController2');
//	$scope.title = "Market Cap Index Submission";	
//}]);

iCal.factory('icalFactory', function($location,$http) 
{
	  var factory  = {};
//	  var urlBase = '/api/v1';
	  
	  var hostValue = $location.host();
	  var portValue = $location.port();	  
	  var baseValue = hostValue + ':' + portValue;
	  var baseUrl = 'http://' + baseValue;
//	  var configData;
	  factory.baseUrl = 'http://' + baseValue;
	  factory.currencyList = [];
	  factory.permissionList = [];
//
	  factory.configValues = function() 
	  {
//	      return $http.get(baseUrl + '/readConfig');
	      return $http({
	    	  	method	: 'GET',
	            headers	: {"Content-Type": "application/json"},
	            url		: baseUrl + '/ICal2Rest/rest/Config/readConfigData'
	        }).then(function (response) {
//	            console.log(response.data);
	            factory.configData = response.data;
//	            console.log(factory.configData);
	        	return response.data;
	        });
	  };
//
//	  service.getUser = function(id){
//	      return $http.get(urlBase + '/user/' + id);
//	  };

	  return factory ;

});
//
//iCal.controller('mainController', ['$scope','$window','icalFactory','$http', 
//	function($scope,$window,icalFactory,$http)
//{
//	
//	var values = icalFactory.configValues();
//	console.log("configValues::" + values);
////	//Get All New Indices List
////	this.getAllNewIndex = function (indexDdata)
////    {
////    	var baseUrl = baseURL + '/ICal2Rest/rest/index/getnewindex';
////        return $http({
////            method	: 'POST',
////            data	: indexDdata,
////            headers	: {"Content-Type": "application/json"},
////            url		: baseUrl 
////        }).then(function (response) {
////            return response.data;
////        });
////    }
//	
//
//}]);




