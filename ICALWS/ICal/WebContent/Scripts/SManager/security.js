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

iCal.controller('securityController',  ['$scope','$window', 'securityService', function($scope,$window, securityService)
{
	$scope.title = "Add Security Price";
	$scope.title1 = "Step 1 - Parse Security File";
	$scope.title2 = "Step 2 - Add Missing Securities";
	$scope.buttontitle1 = "Get Security File Template";
	$scope.buttontitle2 = "Get Add Security File Template";
	
	$scope.buttontitle3 = "Check Selected file for Security Availability";
	$scope.buttontitle4 = "Upload Selected file to add New Securities";
	$scope.showdiv1= true;
	
	$scope.uploadFile = function()
	{
		var file = $scope.myFile;
		
		console.log('file upload started' );
		console.log(file);
	       
		var uploadUrl = "/ICal2Rest/rest/security/parse";
		securityService.uploadFileToParse(file, uploadUrl);
		console.log('file is uploaded' );
    };
	    
	  //to add securities
    $scope.addMissingData = function()
    {
    	var file = $scope.myFile;
    	
    	console.log('file upload started' );
    	console.log(file);
	        
    	var uploadUrl1 = "/ICal2Rest/rest/security/addnew";
    	securityService.uploadFileToAddSecurities(file, uploadUrl1);
    	console.log('file is uploaded' );
     };
     
	 //to Add price securities
     $scope.upload = function()
     {
    	 var file = $scope.myFile;
    	 var mapUrl = "/ICal2Rest/rest/security/addprice";
    	 securityService.uploadFileToAddSecuritiesPrice(file, mapUrl);
     };
     $scope.getParseTemplate = function()
     {
    	 securityService.getTemplate("/ICal2Rest/rest/template/getSecurityTemplate");
     };
     $scope.getAddMissingTemplate = function()
     {
    	 securityService.getTemplate("/ICal2Rest/rest/template/getSecurityAddTemplate");
     };
     
     $scope.getTemplate = function()
     {
    	 securityService.getTemplate("/ICal2Rest/rest/template/getSecurityPriceTemplate");
     };
}]);

iCal.service('securityService', ['$http','icalFactory', function ($http,icalFactory)
{
	var baseURL = icalFactory.baseUrl;
	//parse securities
	this.uploadFileToParse = function(file, uploadUrl)
	{
		var Url = baseURL + uploadUrl;
		$http.post(Url ,file,{transformRequest: angular.identity,headers: {'Content-Type': undefined}
		})        
		.success(function(data)
		{
			console.log(data)
	                
			$http.get(baseURL + "/ICal2Rest/rest/security/get", {responseType: 'arraybuffer'})
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
    		
    		console.log("Securities added successfully")
    		alert("Securities added successfully");
    	})
    	.error(function(){
    		console.log("Ërror in adding securities")
    	});
    }
		
	//price securities
    this.uploadFileToAddSecuritiesPrice = function(file, uploadUrl)
    {
    	var Url = baseURL + uploadUrl;
    	$http.post(Url, file, {transformRequest: angular.identity,headers: {'Content-Type': undefined}})        
    	.success(function(data){
    		console.log("Securities price added successfully")
    		alert("Securities price added successfully");
    	})
    	.error(function(){
            console.log("Ërror in adding securities price")
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