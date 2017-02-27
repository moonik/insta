angular.module('testApp')
.controller('myMessagesCtrl', function($scope, $http, $rootScope, ModalService, $routeParams, $window) {

   $scope.messages = [];
   $scope.newMessages = [];

   $scope.username = $routeParams['username'];

   $http.get('api/messages/getMessages/' + $scope.username, $scope.messages).then(function(data) {
                           $scope.messages = data.data;});


                             var updateData = setInterval(function(){
                                   $http.post('api/messages/updateMessages/' + $scope.username, $scope.messages[$scope.messages.length-1]).then(function(data) {
                                   $scope.newMessages = data.data;
                                   $scope.messages = $scope.messages.concat($scope.newMessages);
                                   });
                                }, 1000);


                               function myStopFunction() {
                               clearInterval(updateData);
                               };


                               $scope.sendMessage = function() {
                                           $http.post('api/messages/sendTo/'+ $scope.username, {
                                               text_message: $scope.message.text_message,
                                               receiver: $scope.username
                                           }).then(function(response){
                                                        });
                                                         $scope.message = {};
                                                         };


});