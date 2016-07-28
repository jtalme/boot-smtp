var bootSmtp = angular.module('bootSmtp', []);

bootSmtp.controller('EmailController', function EmailController($scope, $http) {
	$http({
		method: 'GET',
		url: '/mail'
	}).then(function successCallback(response) {
		$scope.emails = response.data;
	}, function errorCallback(response) {
		
	});
});