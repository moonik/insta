angular.module('testApp').controller('RegistrationCtrl', function ($scope, $rootScope, $http, $window, close) {
     $scope.userForm = {};
     $rootScope.user = {};

     $scope.signUp = function (username) {
        $http.post('api/users', $scope.userForm).then(function (response) {
            console.log('signed up');
            $http.post('api/login', $scope.userForm).then(function (response) {
                localStorage.setItem('jwt', response.headers()['x-auth-token']);
                console.log('signed in');
                $http.post('api/users/setOnlineUser/' + username).then(function(response){
                })
                $rootScope.isSignedUp = true;
                closeModal();
            })
        },function(response){
            $rootScope.isSignedUp = false;
        });
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