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
iCal.controller('addIndexController',  ['$scope','$window', 'indexService','$http','icalFactory',
	function($scope,$window, indexService,$http,icalFactory)
{
	console.log('inside addIndexController');
	$scope.user = {};
	$scope.CData=[];
	$scope.ClientData=[];
	$scope.user.weightType = 'PWI';
	$scope.title = "Add Multiple Indices";
	$scope.index = {};
	$scope.index.setupType = '';
	var baseURL = icalFactory.baseUrl
	
	$scope.loadCurrencies = function () 
    {
		indexService.getAllCurrencies().then(function (response) 
    	{
			console.log(response);
            $scope.CData = response;
            console.log( $scope.CData);
        });
    }
	$scope.loadClient = function () 
    {
		indexService.getAllClient().then(function (response) 
    	{
			console.log(response);
            $scope.ClientData = response;
            console.log( $scope.CData);
        });
    }
  	 
    $scope.loadCurrencies();
    $scope.loadClient();
    
	$scope.submitForm = function() 
	{
		var baseUrl = baseURL + '/ICal2Rest/rest/index/addIndex';
	    	console.log('inside submitForm');
	    	var indexData = $scope.user;
	        console.log(indexData);
	        $http({ 
	        	method  : 'POST',
	        	url     : baseUrl,
	        	data: indexData,
	        	headers: {"Content-Type": "application/json"}
	        })
	        .success(function(data) 
			{
	        	console.log('data saved');
	        	$scope.content = data;
	        	alert("Index added successfully");
			});
    };
    
    $scope.updateIndexSetup = function () 
    {
    	if($scope.index.setupType =='')
		{
    		$scope.showdiv1= false;
    		$scope.showdiv2= false;
		}
    	if($scope.index.setupType =='SI')
		{
    		$scope.showdiv1= false;
    		$scope.showdiv2= true;
		}
    	if($scope.index.setupType =='MI')
		{
    		$scope.showdiv1= true;
    		$scope.showdiv2= false;
		}    	
    }
    
	$scope.upload = function()
    {
		var baseUrl = '/ICal2Rest/rest/index/add';
		console.log('inside upload');
        var file = $scope.myFile;
        var addUrl = baseUrl;
        indexService.uploadFileToAddIndex(file, addUrl);
    };
	    
    $scope.getTemplate = function()
    {
    	indexService.getTemplate();
    };
}]);

iCal.controller('mapSecurityIndexController',  ['$scope','$window', 'indexService','securityService', 'icalFactory',
function($scope,$window, indexService,securityService,icalFactory)
{
	var baseURL = icalFactory.baseUrl
	$scope.title = "Map Securities with Index";
	$scope.upload = function()
	{
		var baseUrl = '/ICal2Rest/rest/index/map';
		var file = $scope.myFile;
		var mapUrl = baseUrl;
		indexService.uploadFileToMapSecurities(file, mapUrl);
	};
	$scope.getTemplate = function()
	{
		var baseUrl = '/ICal2Rest/rest/template/getMapSecuritiesTemplate';
		securityService.getTemplate(baseUrl);
	};
}]);


iCal.service('indexService', ['$http','icalFactory', function ($http,icalFactory)
{
	var baseURL = icalFactory.baseUrl
	//add indices
    this.uploadFileToAddIndex = function(file, uploadUrl)
    {
		var Url = baseURL + uploadUrl;
    	console.log('inside uploadFileToAddIndex');
    	$http.post(Url, file, {transformRequest: angular.identity,headers: {'Content-Type': undefined}})        
    	.success(function(data)
    	{
    		console.log("indices added successfully")
    		console.log(data)
    		alert("indices added successfully");
    	})
    	.error(function(data){
            console.log("Ërror in adding indices")
            console.log(data)
            alert(data);
    	});
    }
    
  //map securities to index
  this.uploadFileToMapSecurities = function(file, uploadUrl)
  {
	  var Url = baseURL + uploadUrl;
	  $http.post(Url, file, {transformRequest: angular.identity,headers: {'Content-Type': undefined}})        
    		.success(function(data){
    		console.log("Securities mapped successfully")
    		alert("Securities mapped successfully");
    	})
    	.error(function(){
            console.log("Ërror in mapping securities")
    	});
    }
  
  //Get Template
	this.getTemplate = function()
	{
		var baseUrl = baseURL + '/ICal2Rest/rest/template/getIndexTemplate';	
		$http.get(baseUrl, {responseType: 'arraybuffer'})
		.then(function (response) 
		{
			var header = response.headers('Content-Disposition')
			var fileName = header.split("=")[1].replace(/\"/gi,'');
			console.log(fileName);
	    
			var blob = new Blob([response.data],{type : 'application/vnd.openxmlformats-officedocument.presentationml.presentation;charset=UTF-8'});
			var objectUrl = (window.URL || window.webkitURL).createObjectURL(blob);
			var link = angular.element('<a/>');
			link.attr({	href : objectUrl,download : fileName})[0].click();
		})
	}
	
	this.getAllCurrencies = function ()
	{
		var baseUrl = baseURL + '/ICal2Rest/rest/currency/getCurrencies';
	    return $http({ 
	    	method  : 'GET',
	    	url     : baseUrl,
	    	
	    }).then(function (response) {
	    	console.log(response.data);
            return response.data;
        });
    }
	
	this.getAllClient = function ()
	{
		var baseUrl = baseURL + '/ICal2Rest/rest/client/getAllClients';
	    return $http({ 
	    	method  : 'GET',
	    	url     : baseUrl,
	    	
	    }).then(function (response) {
	    	console.log(response.data);
            return response.data;
        });
    }
		
}]);