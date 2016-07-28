// Define the `phonecatApp` module
var bootSmtp = angular.module('bootSmtp', []);

// Define the `PhoneListController` controller on the `phonecatApp` module
bootSmtp.controller('EmailController', function EmailController($scope, $http) {
	$http({
		method: 'GET',
		url: '/mail'
	}).then(function successCallback(response) {
		$scope.emails = response.data;
	}, function errorCallback(response) {
		// called asynchronously if an error occurs
		// or server returns response with an error status.
	});
});