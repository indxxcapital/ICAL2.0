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
iCal.controller('addIndexController2',  ['$scope','$window', 'indexService','$http', function($scope,$window, indexService,$http)
{
	console.log('inside addIndexController2');
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
	        	url     : 'http://192.168.1.72:8080/ICal2Rest/rest/index/addIndex2',
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
        var addUrl = "http://192.168.1.72:8080/ICal2Rest/rest/index/add2";
        indexService.uploadFileToAddIndex(file, addUrl);
    };
	    
    $scope.getTemplate = function()
    {
    	indexService.getTemplate();
    };
}]);
