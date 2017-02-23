angular.module('testApp').controller('showFollowingsCtrl', function ($scope, $rootScope, $http, $window, close) {

$scope.showMyFollowings = [];

   $http.get('api/users/iFollow', $scope.showMyFollowings).then(function(data) {
                                                          $scope.showMyFollowings = data.data;
                                                          });




  $scope.close = function () {
        closeModal(undefined);
    };


    function closeModal(data) {
        close(data, 500);
    }

});