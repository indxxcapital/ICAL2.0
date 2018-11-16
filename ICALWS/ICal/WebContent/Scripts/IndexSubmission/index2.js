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
iCal.controller('addIndexController2',  ['$scope','$window', 'indexService','$http','icalFactory',
	function($scope,$window, indexService,$http,icalFactory)
{
	console.log('inside addIndexController2');
	$scope.SingleIndex = {};
	$scope.CData=[];
	$scope.ClientData=[];
	$scope.SingleIndex.weightType = 'MWI';
	$scope.title = "Add Multiple Indices";
	$scope.index = {};
	$scope.index.setupType = '';
	var baseURL = icalFactory.baseUrl;
	$scope.showdiv3= false;
	$scope.showdiv= true;
	
	$scope.loadCurrencies = function () 
    {
		$scope.CData = icalFactory.currencyList;
//		indexService.getAllCurrencies().then(function (response) 
//    	{
//			console.log(response);
//            $scope.CData = response;
//            console.log( $scope.CData);
//        });
    }
	$scope.loadClient = function () 
    {
		$scope.ClientData = icalFactory.clientList;
//		indexService.getAllClient().then(function (response) 
//    	{
//			console.log(response);
//            $scope.ClientData = response;
//            console.log( $scope.CData);
//        });
    }
  	 
    $scope.loadCurrencies();
    $scope.loadClient();
    
    $scope.next = function() 
    {
    	if($scope.index.setupType =='SI')
		{
    		$scope.showdiv= false;
    		$scope.showdiv2= false;
    		$scope.showdiv3= true;
		}
    	if($scope.index.setupType =='MI')
		{
    		if( $scope.myFile == undefined)
    		{
    			alert("Please select a file to upload Multiple Indices.");
    			return;
    		}
            var extn =  $scope.myFile.name.split(".").pop();
            if(extn != 'csv')
        	{
            	alert("File should be in CSV Format only.");
    			return;
        	}
            $scope.showdiv= false;
    		$scope.showdiv1= false;        	
     		$scope.showdiv3= true;
		}  
    }
    $scope.cancel = function() 
    {
    	$scope.index.setupType ==''
    	$scope.showdiv= true;
		$scope.showdiv2= false;
		$scope.showdiv3= false;
		$scope.showdiv1= false;        	
    }
    $scope.back = function() 
    {
    	$scope.showdiv= true;
    	if($scope.index.setupType =='SI')
		{
    		$scope.showdiv2= true;
    		$scope.showdiv3= false;
		}
    	if($scope.index.setupType =='MI')
		{
    		$scope.showdiv1= true;        	
     		$scope.showdiv3= false;
		}     	
    }
    
    $scope.SubmitSingleIndexData = function() 
	{
    	//File Validation
    	var sFile = $scope.SecurityMapFile;
		if( $scope.SecurityMapFile == undefined)
		{
			alert("Please select a file to upload.");
			return;
		}
		var extn =  $scope.SecurityMapFile.name.split(".").pop();
        if(extn != 'csv')
    	{
        	alert("File should be in CSV Format only.");
			return;
    	}
        
        // Add Single Index Details
		var baseUrl = baseURL + '/ICal2Rest/rest/index/addIndex';
    	console.log('inside SubmitSingleIndexData');
    	var indexData = $scope.SingleIndex;
        console.log(indexData);
        $http({ 
        	method  : 'POST',
        	url     : baseUrl,
        	data: indexData,
        	headers: {"Content-Type": "application/json"}
        })
        .success(function(data) 
		{
        	if(data == 'SUCCESS')
    		{
        		console.log('Single Index added successfully');
        		var Url = baseURL + '/ICal2Rest/rest/index/map';
            	console.log('inside uploadFileToAddIndex');
            	$http.post(Url, sFile, {transformRequest: angular.identity,headers: {'Content-Type': undefined}})        
            	.success(function(data1)
            	{
            		if(data1 == 'SUCCESS')
                	{
                		alert("Index has been Submitted Successfully");
                		$scope.index.setupType = '';
                		$scope.SingleIndex = {};
                		$scope.showdiv= true;
                		$scope.showdiv1= false;
                		$scope.showdiv2= false;
        	     		$scope.showdiv3= false;
                	}
                	else
            		{
                		console.log('Error in Mapping Securities with Index.' + data1);
                		alert('Error in uploading Security Map input file.Please check the file.' + data1);	        		
            		}
            	})
            	.error(function(data1){
            		console.log('Error in Mapping Securities with Index.' + data1);
            		alert('Error in uploading Security Map input file.Please check the file.' + data1);
            	});
    		}
    		else
    		{
    			console.log('Error in adding Single Index.' + data);
        		alert('Error in adding Single Index.Please check the input data.' + data);
        		$scope.showdiv= true;
        		$scope.showdiv1= false;
        		$scope.showdiv2= true;
         		$scope.showdiv3= false;
    		}
		}).
		error(function(data)
		{
			console.log('Error in adding Single Index.' + data);
    		alert('Error in adding Single Index.Please check the input data.' + data);
    		$scope.showdiv= true;
    		$scope.showdiv1= false;
    		$scope.showdiv2= true;
     		$scope.showdiv3= false;
    	});
    };
    
    $scope.SubmitMultipleIndexData = function() 
    {
    	//File Validation
    	var indexFile = $scope.myFile;
    	var sFile = $scope.SecurityMapFile;
    	
		if( $scope.SecurityMapFile == undefined)
		{
			alert("Please select a file to upload.");
			return;
		}
		var extn =  $scope.SecurityMapFile.name.split(".").pop();
        if(extn != 'csv')
    	{
        	alert("File should be in CSV Format only.");
			return;
    	}
        
        // Add Multiple Index Details
    	console.log('inside SubmitMultipleIndexData');
        var baseUrl = baseURL + '/ICal2Rest/rest/index/add2';
    	console.log('inside uploadFileToAddIndex');
    	$http.post(baseUrl, indexFile, {transformRequest: angular.identity,headers: {'Content-Type': undefined}}) 
        .success(function(data) 
		{
        	if(data == 'SUCCESS')
    		{
        		console.log('Multile Indices added successfully');
        		var Url = baseURL + '/ICal2Rest/rest/index/map';
            	console.log('inside uploadFileToAddIndex');
            	$http.post(Url, sFile, {transformRequest: angular.identity,headers: {'Content-Type': undefined}})        
            	.success(function(data1)
            	{
            		if(data1 == 'SUCCESS')
                	{
                		alert("Multiple Indices has been Submitted Successfully");
                		$scope.index.setupType = '';
                		$scope.showdiv= true;
                		$scope.showdiv1= false;
                		$scope.showdiv2= false;
        	     		$scope.showdiv3= false;
                	}
                	else
            		{
                		console.log('Error in Mapping Securities with Index.' + data1);
                		alert('Error in uploading Security Map input file.Please check the file.' + data1); 	        		
            		}
            	})
            	.error(function(data1){
            		console.log('Error in Mapping Securities with Index.' + data1);
            		alert('Error in uploading Security Map input file.Please check the file.' + data1); 	  
            	});
    		}
    		else
    		{
    			console.log('Error in adding Miltiple Index.' + data);
        		alert('Error in uploading Multiple Index input file.Please check the file.' + data);
        		$scope.showdiv= true;
        		$scope.showdiv1= true;
        		$scope.showdiv2= false;
         		$scope.showdiv3= false;
    		}
		}).
		error(function(data)
		{
			console.log('Error in adding Miltiple Index.' + data);
    		alert('Error in uploading Multiple Index input file.Please check the file.' + data);
    		$scope.showdiv= true;
    		$scope.showdiv1= true;
    		$scope.showdiv2= false;
     		$scope.showdiv3= false;
    	});
    }
    
    $scope.SubmitIndexData = function() 
    {
    	$scope.showdiv= false;
    	
    	if($scope.index.setupType =='SI')
		{
    		 $scope.SubmitSingleIndexData();
		}
    	if($scope.index.setupType =='MI')
		{
    		 $scope.SubmitMultipleIndexData();
    	}  
    }
    
    $scope.updateIndexSetup = function () 
    {
    	if($scope.index.setupType =='')
		{
    		$scope.showdiv1= false;
    		$scope.showdiv2= false;
    		$scope.showdiv3= false;
		}
    	if($scope.index.setupType =='SI')
		{
    		$scope.showdiv1= false;
    		$scope.showdiv2= true;
    		$scope.showdiv3= false;
		}
    	if($scope.index.setupType =='MI')
		{
    		$scope.showdiv1= true;
    		$scope.showdiv2= false;
    		$scope.showdiv3= false;
		}    	
    }
        
    $scope.getTemplate = function()
    {
    	var baseUrl = '/ICal2Rest/rest/template/getIndexTemplate';
    	indexService.getTemplate(baseUrl);
    };
    
    $scope.getSecurityMapTemplate = function()
	{
		var baseUrl = '/ICal2Rest/rest/template/getMapSecuritiesTemplate';
		indexService.getTemplate(baseUrl);
	};
}]);

