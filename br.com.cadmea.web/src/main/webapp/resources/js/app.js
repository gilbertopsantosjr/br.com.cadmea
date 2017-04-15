/**
 * @author Gilberto Santos
 */

var app = angular.module('cadmea', ['ngRoute', 'ngResource', 'ngAnimate']);

app.config(function($routeProvider) {
	
	$routeProvider.when("/admin/user/:id", {
		controller : "userController", 
		templateUrl :  "/private/user/form.html"
	}).
	
	when("/admin/user/list", {
		controller : "userController", 
		templateUrl :  "/private/user/list.html"
	}).
	
	when("/admin/user/", {
		controller : "userController", 
		templateUrl :  "/private/user/form.html"
	}).
	
	otherwise({
		redirectTo : "/login.jsp"
	});
	
});

app.controller('userController', function( $scope, $http, $location, $routeParams) {
	$scope.verify = function() {
		console.log($scope.user);
		$http.post("/api/public/user/create/", $scope.user)
		.success(
			function(response){
				
			}		
		).error(
			function(data, status, headers, config){
				console.log(data);
				erro("Error: " + data.message);
			}
		)
	}
});

app.controller('loginController', function( $scope, $http, $location, $routeParams) {
	
	$scope.verify = function() {
		$http.post("/api/public/user/authentication/", $scope.login)
		.success(
			function(response){
				$scope.login = {}
				window.location = response.url;
			}		
		).error(
			function(data, status, headers, config){
				erro("Error: " + data.message);
			}
		)
  	};
  
});

// mostra msg de erro
function erro(msg) {
	Materialize.toast(msg, 4000)
}