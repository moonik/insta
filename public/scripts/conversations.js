angular.module('testApp')
.controller('myConversationsCtrl', function($scope, $http, $rootScope, ModalService, $window) {

   $scope.conversations = [];
   $scope.newConversations = [];

    clearInterval($rootScope.updateData);

   $http.get('api/messages/myDialogs/', $scope.conversations).then(function(data) {
                           $scope.conversations = data.data;});


                             $rootScope.updateData = setInterval(function(){
                                   $http.post('api/messages/updateConversations/', $scope.conversations[$scope.conversations.length-1]).then(function(data) {
                                   $scope.newConversations = data.data;
                                   $scope.conversations = $scope.conversations.concat($scope.newConversations);
                                   });
                                }, 2000);


                               $scope.myStopFunction = function(userName) {
                               clearInterval($rootScope.updateData);
                               $window.location.href = "#/userProfile/" + userName;
                               };

                               $scope.goTotheConversation = function(userName){
                                clearInterval($rootScope.updateData);
                                $window.location.href = "#/chatWith/" + userName;
                                };




});