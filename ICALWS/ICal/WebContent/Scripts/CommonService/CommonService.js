iCal.directive('commonServiceManager', ['$parse', function ($parse) {
	return {
		restrict: 'A',
		link: function(scope, element, attrs) 
		{
			var model = $parse(attrs.commonServiceManager);
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

iCal.service('CommonService', ['$http','icalFactory', function ($http,icalFactory)
{
	console.log('Inside : CommonService');
	
	var baseURL = icalFactory.baseUrl
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