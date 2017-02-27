angular.module('testApp')
.controller('myConversationsCtrl', function($scope, $http, $rootScope, ModalService, $window) {

   $scope.conversations = [];

   $http.get('api/messages/myDialogs/', $scope.conversations).then(function(data) {
                           $scope.conversations = data.data;});


});