var acesApp = angular.module('acesApp', [ 
	'ngRoute'
,	'ngSanitize'
, 	'ui.bootstrap' 
,	'vcRecaptcha'
]);
// this is to make sure the javascript link in anchor tags are treated as safe urls.
acesApp.config([ '$compileProvider', function($compileProvider) {
	$compileProvider.aHrefSanitizationWhitelist(/^\s*(javascript|http|https|file):/);
} ]);