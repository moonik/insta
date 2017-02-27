angular.module('testApp')
.controller('myConversationsCtrl', function($scope, $http, $rootScope, ModalService, $window) {

   $scope.conversations = [];
   $scope.newConversations = [];

   $http.get('api/messages/myDialogs/', $scope.conversations).then(function(data) {
                           $scope.conversations = data.data;});


                             var updateData = setInterval(function(){
                                   $http.post('api/messages/updateConversations/', $scope.conversations[$scope.conversations.length-1]).then(function(data) {
                                   $scope.newConversations = data.data;
                                   $scope.conversations = $scope.conversations.concat($scope.newConversations);
                                   });
                                }, 2000);


                               function myStopFunction() {
                               clearInterval(updateData);
                               };


});