iCal.controller('CAUploadController', ['$scope','$window','CAUploadService','CommonService',
	function($scope,$window,CAUploadService,CommonService)
{
	console.log('Inside TodaysCAController');
	
    $scope.getCATemplate = function() 
    {
    	var baseUrl = "/ICal2Rest/rest/template/getCATemplate";
    	CommonService.getTemplate(baseUrl);
    };
    
    $scope.uploadCAFile = function()
	{
		var file = $scope.myCAFile;
		if( $scope.myCAFile == undefined)
		{
			alert("Please select a file to upload.");
			return;
		}
		var extn =  $scope.myCAFile.name.split(".").pop();
        if(extn != 'csv')
    	{
        	alert("File should be in CSV Format only.");
			return;
    	}
		console.log('CA file upload started' );
		console.log(file);
	       
		var uploadUrl = "/ICal2Rest/rest/CorporateActions/addNewCA";
		CAUploadService.uploadFileToAddNewCA(file, uploadUrl);
		console.log('CA file upload End' );
    };
    
}]);

iCal.service('CAUploadService', ['$http','icalFactory', function ($http,icalFactory)
{
	var baseURL = icalFactory.baseUrl;
			
	this.uploadFileToAddNewCA = function(file, uploadUrl)
	{
		var Url = baseURL + uploadUrl;
		$http.post(Url, file, {transformRequest: angular.identity,headers: {'Content-Type': undefined}})        
		.success(function(data)
		{
			if(data == 'SUCCESS')
			{
				console.log("Corporate Actions added successfully")
				alert("Corporate Actions added successfully");
			}
			else
			{
				console.log(data);
				alert(data);
			}
		})
		.error(function()
		{
			console.log("Ërror in adding Corporate Actions")
		});
	};
	
}]);
