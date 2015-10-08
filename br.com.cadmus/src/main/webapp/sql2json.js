/**
 * gsantos 
 */
(function(){
	
	var app = angular.module('sql2jsonApp', ['ngResource']);
	
	var MainController = function ($scope, $http, $interval, $log)
	{
		$scope.json = "";
		$scope.foreignkey = { value : true };
		
		$scope.getJson = function (){
			
			var obj = { query : $scope.query, foreignkey : $scope.foreignkey.value };
			
			$http.post('sql2json.php', obj )
				.success(
					function(callback){
						$scope.json = callback;	
					})
				.error(
					function(msg){
						$scope.json = "couldnt find out the server";
						console.log(msg);
				});
		};
	}
	
	app.controller("MainController", ["$scope", "$http", "$interval", "$log", MainController]);
	
}());

