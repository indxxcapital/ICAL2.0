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
//	    	alert("submitForm");
	    	console.log('inside submitForm');
	    	var indexData = $scope.user;
	        console.log(indexData);
	        $http({ 
	        	method  : 'POST',
	        	url     : 'http://192.168.1.72:8080/ICal2Rest/rest/index/addIndex',
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
		console.log('inside upload');
        var file = $scope.myFile;
        var addUrl = "http://192.168.1.72:8080/ICal2Rest/rest/index/add";
        indexService.uploadFileToAddIndex(file, addUrl);
    };
	    
    $scope.getTemplate = function()
    {
    	indexService.getTemplate();
    };
}]);

iCal.controller('mapSecurityIndexController',  ['$scope','$window', 'indexService','securityService', 
function($scope,$window, indexService,securityService)
{
	$scope.title = "Map Securities with Index";
	$scope.upload = function()
	{
		var file = $scope.myFile;
		var mapUrl = "http://192.168.1.72:8080/ICal2Rest/rest/index/map";
		indexService.uploadFileToMapSecurities(file, mapUrl);
	};
	$scope.getTemplate = function()
	{
		securityService.getTemplate("http://192.168.1.72:8080/ICal2Rest/rest/template/getMapSecuritiesTemplate");
	};
}]);


iCal.service('indexService', ['$http', function ($http)
{
	//add indices
    this.uploadFileToAddIndex = function(file, uploadUrl)
    {
    	console.log('inside uploadFileToAddIndex');
    	$http.post(uploadUrl, file, {transformRequest: angular.identity,headers: {'Content-Type': undefined}})        
    	.success(function(data){
    		console.log("indices added successfully")
    		alert("indices added successfully");
    	})
    	.error(function(){
            console.log("Ërror in adding indices")
    	});
    }
    
  //map securities to index
  this.uploadFileToMapSecurities = function(file, uploadUrl)
  {
	  
	  $http.post(uploadUrl, file, {transformRequest: angular.identity,headers: {'Content-Type': undefined}})        
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
		$http.get("http://192.168.1.72:8080/ICal2Rest/rest/template/getIndexTemplate", {responseType: 'arraybuffer'})
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
	    return $http({ 
	    	method  : 'GET',
	    	url     : 'http://192.168.1.72:8080/ICal2Rest/rest/currency/getCurrencies',
	    	
	    }).then(function (response) {
	    	console.log(response.data);
            return response.data;
        });
    }
	
	this.getAllClient = function ()
	{
	    return $http({ 
	    	method  : 'GET',
	    	url     : 'http://192.168.1.72:8080/ICal2Rest/rest/client/getAllClients',
	    	
	    }).then(function (response) {
	    	console.log(response.data);
            return response.data;
        });
    }
		
}]);