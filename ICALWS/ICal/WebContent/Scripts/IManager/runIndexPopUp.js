iCal.controller('runIndexPopUpController', ['$scope','$window','IManagerService','$http','$rootScope',
	function($scope,$window,IManagerService,$http,$rootScope)
{
	$scope.title = "Select Date";
	$scope.selectedRows = $window.selectedRows;
	$scope.index = {};
	  //runIndex
    $scope.runIndex = function() 
    {
      if (window.confirm("Are you sure you want to Run Selected indices?")) $scope.result = "Yes";  
      else $scope.result = "No";  
      if ($scope.result == "Yes")
      { 
  		IManagerService.runIndices($scope.selectedRows,$scope.index.IndexRunDate).then(function (response) 
		{
//  			alert(response);
  			window.opener.location.reload(true);
  			$window.close();
		});
      }   
  };     
}]);


