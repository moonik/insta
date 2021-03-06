angular.module('testApp').controller('showFollowersCtrl', function ($scope, $rootScope, $http, $window, close) {

    $scope.showMyFollowers = [];

    $http.get('api/users/myFollowers', $scope.showMyFollowers).then(function(data) {
        $scope.showMyFollowers = data.data;
    });

    $scope.closeAndGo = function(username){
        closeModal(username);
    };

    $scope.close = function () {
        closeModal(undefined);
    };

    function closeModal(data) {
        close(data, 500);
        $('body').removeClass('modal-open');
        $('.modal-backdrop').remove();
    }

});