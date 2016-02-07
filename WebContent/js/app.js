var app = angular.module('sBook', ['ngRoute', 'sBookControllers', 'ngCookies']);

app.config(function($routeProvider) {
	$routeProvider.
	when('/', {
		templateUrl: 'partials/home.html',
		controller: 'HomeCtrl',
	}).
	when('/admin', {
		templateUrl: 'partials/admin.html',
		controller: 'AdminCtrl'
	}).
	when('/product/:name', {
		templateUrl: 'partials/product.html',
		controller: 'ProductCtrl'
	}).
	when('/tag/:name', {
		templateUrl: 'partials/tag.html',
		controller: 'TagCtrl'
	}).
	when('/new_product', {
		templateUrl: 'partials/new_product.html',
		controller: 'NewProductCtrl'
	}).
	when('/user', {
		templateUrl: 'partials/user.html',
		controller: 'UserCtrl'
	}).
	when('/user/:session', {
		templateUrl: 'partials/user_data.html',
		controller: 'UserDataCtrl'
	}).
	when('/new_user', {
		templateUrl: 'partials/new_user.html',
		controller: 'NewUserCtrl'
	}).
	when('/browse/:name', {
		templateUrl: 'partials/browse.html',
		controller: 'BrowseCtrl'
	}).
	otherwise({
		redirectTo: '/'
	});

});