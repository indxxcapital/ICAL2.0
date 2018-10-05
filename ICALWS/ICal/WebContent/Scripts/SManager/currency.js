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

iCal.controller('currencyController',  ['$scope','$window', 'currencyService',
	function($scope,$window, currencyService)
{
	$scope.title1 = "Step 1 - Parse Currency File";
	$scope.title2 = "Step 2 - Add Missing Currencies";
	$scope.buttontitle1 = "Get Check Currency File Template";
	$scope.buttontitle2 = "Get Add Currency File Template";
	$scope.buttontitle3 = "Check Selected file for Currency Availability";
	$scope.buttontitle4 = "Upload Selected file to add New Currencies";
	$scope.showdiv1= false;
	
	$scope.uploadFile = function()
	{
		var file = $scope.myFile;
		
		console.log('file upload started' );
		console.log(file);
	       
//		var uploadUrl = "http://192.168.1.72:8080/ICal2Rest/rest/currency/parseCurrency";
		var uploadUrl = "/ICal2Rest/rest/currency/parseCurrency";
		currencyService.uploadFileToParse(file, uploadUrl);
		console.log('file is uploaded' );
    };
	    
    $scope.addMissingData = function()
    {
    	var file = $scope.myFile;
    	
    	console.log('file upload started' );
    	console.log(file);
	        
//    	var uploadUrl1 = "http://192.168.1.72:8080/ICal2Rest/rest/currency/addNewCurrency";
    	var uploadUrl1 = "/ICal2Rest/rest/currency/addNewCurrency";
    	currencyService.uploadFileToAddSecurities(file, uploadUrl1);
    	console.log('file is uploaded' );
     };
     
   
     $scope.getParseTemplate = function()
     {
//    	 currencyService.getTemplate("http://192.168.1.72:8080/ICal2Rest/rest/template/getCurrencyCheckTemplate");
    	 currencyService.getTemplate("/ICal2Rest/rest/template/getCurrencyCheckTemplate");
     };
     $scope.getAddMissingTemplate = function()
     {
//    	 currencyService.getTemplate("http://192.168.1.72:8080/ICal2Rest/rest/template/getCurrencyAddTemplate");
    	 currencyService.getTemplate("/ICal2Rest/rest/template/getCurrencyAddTemplate");
     };
     
}]);

iCal.service('currencyService', ['$http','icalFactory', function ($http,icalFactory)
{
	var baseURL = icalFactory.baseUrl;
	this.uploadFileToParse = function(file, uploadUrl)
	{
		var Url = baseURL + uploadUrl;
		$http.post(Url ,file,{transformRequest: angular.identity,headers: {'Content-Type': undefined}
		})        
		.success(function(data)
		{
			console.log(data)
	                
//			$http.get("http://192.168.1.72:8080/ICal2Rest/rest/currency/get", {responseType: 'arraybuffer'})
			$http.get(baseURL + "/ICal2Rest/rest/currency/get", {responseType: 'arraybuffer'})
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
		})
		.error(function(){
			console.log("status code for error is ")
		});
	}
		
	this.uploadFileToAddSecurities = function(file, uploadUrl)
    {
		var Url = baseURL + uploadUrl;
    	$http.post(Url, file, {transformRequest: angular.identity,headers: {'Content-Type': undefined}})        
    	.success(function(data){
    		
    		console.log("Currencies added successfully")
    		alert("Currencies added successfully");
    	})
    	.error(function(){
    		console.log("Error in adding currencies")
    		alert("Error in adding currencies");
    	});
    }
	    
	//Get Template
	this.getTemplate = function(url)
	{
		var Url = baseURL + url;
		$http.get(Url, {responseType: 'arraybuffer'})
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
}]);