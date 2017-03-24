angular.module('testApp').controller('sendMessageCtrl', function ($scope, $rootScope, $http, $window, close, user) {

$scope.username = user;
$scope.message;

$scope.sendMessage = function() {
    $http.post('api/messages/sendTo/'+ $scope.username, {
    text_message: $scope.message.text_message,
    receiver: $scope.username
    }).then(function(response){
    });
    $scope.message = {};
    };

  $scope.close = function () {
        closeModal(undefined);
    };


    function closeModal(data) {
        close(data, 500);
    }

});