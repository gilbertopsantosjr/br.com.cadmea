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
	
});

app.controller('loginController', function( $scope, $http, $location, $routeParams) {
	
	$scope.verify = function() {
		$http.post("/api/public/user/authentication/", $scope.login)
		.success(
			function(response){
				$scope.login = {}
				window.location = "/admin";
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