iCal.directive('indexManager', ['$parse', function ($parse) {
	return {
		restrict: 'A',
		link: function(scope, element, attrs) 
		{
			var model = $parse(attrs.indexManager);
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

iCal.service('IManagerService', ['$http','icalFactory', function ($http,icalFactory)
{
//	var hostValue = $location.host();
//	var portValue = $location.port();
//	var baseValue = hostValue + ':' + portValue;
	console.log('Host: :::;' + icalFactory.baseUrl);
	var baseURL = icalFactory.baseUrl
	//Get All New Indices List
	this.getAllNewIndex = function (indexDdata)
    {
		//http://192.168.1.72:8080
//    	var baseUrl = 'http://192.168.1.72:8080/ICal2Rest/rest/index/getnewindex';
    	var baseUrl = baseURL + '/ICal2Rest/rest/index/getnewindex';
        return $http({
            method	: 'POST',
            data	: indexDdata,
            headers	: {"Content-Type": "application/json"},
            url		: baseUrl 
        }).then(function (response) {
            return response.data;
        });
    }
	//Get All Upcoming Indices List
	this.getAllUpcomingIndex = function (indexDdata)
    {
//    	var baseUrl = 'http://192.168.1.72:8080/ICal2Rest/rest/index/getupcomongindex';
		var baseUrl = baseURL + '/ICal2Rest/rest/index/getupcomongindex';
        return $http({
            method: 'POST',
            data	: indexDdata,
            headers	: {"Content-Type": "application/json"},
            url: baseUrl 
        }).then(function (response) {
            return response.data;
        });
    }
	//Get All Run Indices List
	this.getAllRunIndex = function (indexDdata)
    {
//    	var baseUrl = 'http://192.168.1.72:8080/ICal2Rest/rest/index/getrunindex';
    	var baseUrl = baseURL + '/ICal2Rest/rest/index/getrunindex';
        return $http({
            method: 'POST',
            data	: indexDdata,
            headers	: {"Content-Type": "application/json"},
            url: baseUrl 
        }).then(function (response) {
            return response.data;
        });
    }
	
	//Get All Live Indices List
	this.getAllLiveIndex = function (indexDdata)
    {
//    	var baseUrl = 'http://192.168.1.72:8080/ICal2Rest/rest/index/getliveindex';
    	var baseUrl = baseURL + '/ICal2Rest/rest/index/getliveindex';
        return $http({
            method: 'POST',
            data	: indexDdata,
            headers	: {"Content-Type": "application/json"},
            url: baseUrl 
        }).then(function (response) {
            return response.data;
        });
    }
	    
	this.getAllSecurities = function ()
	{
		var baseUrl = baseURL + '/ICal2Rest/rest/security/getSecurities';
	    return $http({ 
	    	method  : 'GET',
//	    	url     : 'http://192.168.1.72:8080/ICal2Rest/rest/security/getSecurities',
	    	url     : baseUrl,
	    	
	    }).then(function (response) {
            return response.data;
        });
    }
	
	
	this.deleteIndex = function (indexDataList)
	{
		var baseUrl = baseURL + '/ICal2Rest/rest/index/deleteindex';
	    var indexData = {"indexData":indexDataList};
        console.log(indexData);
        return $http({ 
         		method  : 'POST',//
//         		url     : 'http://192.168.1.72:8080/ICal2Rest/rest/index/deleteindex',
         		url     : baseUrl,
         		data	: indexData,
         	    headers	: {"Content-Type": "application/json"}
         })
        .then(function (response)
         {
              return "Selected Indices has been deleted Successfully";
         });
    }
	
	this.updateIndexStatus = function (indexDataList,indexStatus)
	{
		var baseUrl = baseURL + '/ICal2Rest/rest/index/updateIndicesStatus';
	    var indexData = {"indexTicker":indexDataList,"indexStatus":indexStatus};
        console.log(indexData);
        return $http({ 
         		method  : 'POST',
//         		url     : 'http://192.168.1.72:8080/ICal2Rest/rest/index/updateIndicesStatus',
         		url     : baseUrl,
         		data	: indexData,
         	    headers	: {"Content-Type": "application/json"}
         })
        .then(function (response)
         {
            return "Selected Indices has been apprved Successfully";
         });
    }
	 	
	this.getIndexPreClosingFile = function (indexData)
	{
		var baseUrl = baseURL + '/ICal2Rest/rest/index/getpreclosing';
        console.log(indexData);
        $http({ 
         		method  : 'POST',
//         		url     : 'http://192.168.1.72:8080/ICal2Rest/rest/index/getpreclosing',
         		url     : baseUrl,
         		data	: indexData,
         	    headers	: {"Content-Type": "application/json","responseType": 'arraybuffer'}
         })
        .then(function (response)
         {
        	var header = response.headers('Content-Disposition')
			var fileName = header.split("=")[1].replace(/\"/gi,'');
			console.log(fileName);
        
			var blob = new Blob([response.data],{type : 'application/vnd.openxmlformats-officedocument.presentationml.presentation;charset=UTF-8'});
			var objectUrl = (window.URL || window.webkitURL).createObjectURL(blob);
			var link = angular.element('<a/>');
			link.attr({	href : objectUrl,download : fileName})[0].click();
         });
    }
	
	this.getAllIndexSecurities = function (indexTicker)
	{
		var baseUrl = baseURL + '/ICal2Rest/rest/security/getSecuritiesForIndex';
	    var indexData = {"indexTicker":indexTicker};
        console.log(indexData);
        return $http({ 
         		method  : 'POST',//
//         		url     : 'http://192.168.1.72:8080/ICal2Rest/rest/security/getSecuritiesForIndex',
         		url     : baseUrl,
         		data	: indexData,
         	    headers	: {"Content-Type": "application/json"}
         })
        .then(function (response)
         {
              return response.data;;
         });
    }
	
	this.deleteSecurityFromIndex = function (indexData)
	{
		var baseUrl = baseURL + '/ICal2Rest/rest/index/deleteSecurityFromIndex';
        console.log(indexData);
        return $http({ 
         		method  : 'POST',//
//         		url     : 'http://192.168.1.72:8080/ICal2Rest/rest/index/deleteSecurityFromIndex',
         		url     : baseUrl,
         		data	: indexData,
         	    headers	: {"Content-Type": "application/json"}
         })
        .then(function (response)
         {
              return "Security has been deleted Successfully";
         });
    }
	
	this.updateIndexValues = function (indexDataList)
	{
		var baseUrl = baseURL + '/ICal2Rest/rest/index/addIndexValues';
        console.log(indexDataList);
        return $http({ 
         		method  : 'POST',//
//         		url     : 'http://192.168.1.72:8080/ICal2Rest/rest/index/addIndexValues',
         		url     : baseUrl,
         		data	: indexDataList,
         	    headers	: {"Content-Type": "application/json"}
         })
        .then(function (response)
         {
              return "Index Values has been added Successfully";
         });
    }
	
	this.runIndices = function (indexDataList,runDate)
	{
		var baseUrl = baseURL + '/ICal2Rest/rest/index/runIndices';
		var indexData = {"indexData":indexDataList,"indexRunDate":runDate};
        console.log(indexDataList);
        return $http({ 
         		method  : 'POST',
//         		url     : 'http://192.168.1.72:8080/ICal2Rest/rest/index/runIndices',
         		url     : baseUrl,
         		data	: indexData,
         	    headers	: {"Content-Type": "application/json"}
         })
        .then(function (response)
         {
              return "Selected indices has been run Successfully";
         });
    }
}]);