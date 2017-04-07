angular.module('testApp')
.controller('sendMessageCtrl', function($scope, $http, $rootScope, ModalService, $routeParams, $window, close, $interval) {

    $scope.messages = [];
    $scope.newMessages = [];

    $scope.username = $routeParams['username'];
    // gets all messages with user

    $http.get('api/messages/getMessages/' + $scope.username, $scope.messages).then(function(data) {
        $scope.messages = data.data;
    });

   // function that checks if there are new messages with user
    $rootScope.updateData = setInterval(function(){
        if($scope.messages.length != 0){
            $http.post('api/messages/updateMessages/' + $scope.username, $scope.messages[$scope.messages.length-1])
            .then(function(data) {
                $scope.newMessages = data.data;
                $scope.messages = $scope.messages.concat($scope.newMessages);
            });
        }else
            $http.get('api/messages/getMessages/' + $scope.username, $scope.messages).then(function(data) {
                $scope.messages = data.data;
            });
    }, 1000);

    function myStopFunction() {
        clearInterval($rootScope.updateData);
    };

    $scope.sendMessage = function() {
        $http.post('api/messages/sendTo/'+ $scope.username, {
            text_message: $scope.message.text_message,
            receiver: $scope.username
        }).then(function(response){
        });
        $scope.message = {};
    };

    $scope.closeAndGo = function(username){
        closeModal(username);
    };

    $scope.close = function () {
        closeModal(undefined);
    };

    function closeModal(data) {
        myStopFunction();
        close(data, 500);
        $('body').removeClass('modal-open');
        $('.modal-backdrop').remove();
    }
});