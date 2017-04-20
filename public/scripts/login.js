angular.module('testApp').controller('LoginCtrl', function ($scope, $rootScope, $http, $window, close) {
     $scope.userForm = {};

    $scope.signIn = function (username) {
        $http.post('api/login', $scope.userForm).then(function (response) {
            localStorage.setItem('jwt', response.headers()['x-auth-token']);
            $http.post('api/users/setOnlineUser/' + username).then(function(response){
            })
            $scope.userForm = {};
            closeModal();
        })
    };

     $scope.close = function () {
        closeModal(undefined);
     };

     function closeModal(data) {
        $('body').removeClass('modal-open');
        $('.modal-backdrop').remove();
        close(data, 500);
     }

});