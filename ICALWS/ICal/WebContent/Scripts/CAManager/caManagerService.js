iCal.directive('caServiceManager', ['$parse', function ($parse) {
	return {
		restrict: 'A',
		link: function(scope, element, attrs) 
		{
			var model = $parse(attrs.caServiceManager);
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

iCal.service('CAManagerService', ['$http','icalFactory', function ($http,icalFactory)
{
	console.log('Host: :::;' + icalFactory.baseUrl);
	var baseURL = icalFactory.baseUrl
	//Get All Corporate Actions 
	this.getAllCA = function (caData)
    {
//    	var baseUrl = baseURL + '/ICal2Rest/rest/CorporateActions/getTodaysCA';//getCA
    	var baseUrl = baseURL + '/ICal2Rest/rest/CorporateActions/getCA';
        return $http({
            method	: 'POST',
            data	: caData,
            headers	: {"Content-Type": "application/json"},
            url		: baseUrl 
        }).then(function (response) {
        	console.log(response.data);
            return response.data;
        });
    }	
	
	this.updateCorporateActions = function (caList)
	{
		var baseUrl = baseURL + '/ICal2Rest/rest/CorporateActions/updateCA';
	    var CAData = {"CorporateActions":caList};
        console.log(caList);
        return $http({ 
         		method  : 'POST',
         		url     : baseUrl,
         		data	: CAData,
         	    headers	: {"Content-Type": "application/json"}
         })
        .success(function (data)
         {
        	if(data == 'SUCCESS')
    		{
        		console.log(data);
        		alert("Corporate Actions has been updated Successfully");
    		}
        	else
    		{
        		console.log(data);
        		alert(data);        		
    		}
         }).
		 error(function(data)
		{
			console.log('Error in updating Corporate Actions.' + data);
    		alert('Error in updating Corporate Actions..' + data);
    	});
    }	
	
	this.CACMDownload = function (ca)
	{
		var baseUrl = baseURL + '/ICal2Rest/rest/CACM/CACMDownload';
	    var CAData = ca;
        console.log(ca);
        $http({ 
         		method  : 'POST',
         		url     : baseUrl,
         		data	: CAData,
         		headers	: {"Content-Type": "application/json"}//,"responseType": 'arraybuffer'}
         })
         .success(function (data)
         {
        	if(data == 'SUCCESS')
    		{
        		console.log(data);
        		var Url = baseURL + "/ICal2Rest/rest/CACM/getCACM";
        		$http.get(Url,{responseType: 'arraybuffer'})
//        		$http({ 
//         		method  : 'POST',
//         		url     : Url,
//         		data	: CAData,
//         		headers	: {"Content-Type": "application/json","responseType": 'arraybuffer'}
//        		 })
        		.success(function (response) 
        		{
//        			var header = response.headers('Content-Disposition')
        			var fileName ='CACM-' + ca.effectiveDateFrom +'.xls';// header.split("=")[1].replace(/\"/gi,'');
        			console.log(fileName);
        	    
        			var blob = new Blob([response],{type : 'application/vnd.openxmlformats-officedocument.presentationml.presentation;charset=UTF-8'});
        			var objectUrl = (window.URL || window.webkitURL).createObjectURL(blob);
        			var link = angular.element('<a/>');
        			link.attr({	href : objectUrl,download : fileName})[0].click();
        		});
    		}
        	else
    		{
        		console.log(data);
//        		alert(data);        		
    		}
         }).
		 error(function(data)
		{
			console.log('Error in updating Corporate Actions.' + data);
//    		alert('Error in updating Corporate Actions..' + data);
    	});   
    }	
}]);