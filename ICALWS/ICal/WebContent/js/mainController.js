
iCal.controller('mainController', ['$scope','$window','icalFactory','$http', 
	function($scope,$window,icalFactory,$http)
{
	
	var values = icalFactory.configValues();
	console.log("configValues::" + icalFactory.configData);

}]);