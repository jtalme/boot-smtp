var bootSmtp = angular.module('bootSmtp', ['ngRoute']);

bootSmtp.config(function($routeProvider) {
	$routeProvider

	// route for the home page
	.when('/', {
		templateUrl: 'pages/mails.html',
		controller: 'emailsController'
	})

	// route for the about page
	.when('/mail/:id', {
		templateUrl: 'pages/mail.html',
		controller: 'emailController'
	})
});

bootSmtp.controller('emailsController', function EmailController($scope, $http) {
	$http({
		method: 'GET',
		url: '/mail'
	}).then(function successCallback(response) {
		$scope.emails = response.data;
	}, function errorCallback(response) {

	});
});

bootSmtp.controller('emailController', function EmailController($scope, $http, $routeParams) {
	$http({
		method: 'GET',
		url: '/mail/' + $routeParams.id
	}).then(function successCallback(response) {
		$scope.email = response.data;
	}, function errorCallback(response) {

	});
});