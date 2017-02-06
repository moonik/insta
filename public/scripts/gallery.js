angular.module('testApp')
.controller('myGallery', function($scope, $http, $rootScope) {

    $scope.pictures = [];
      $http.get('api/pictures/my', $scope.pictures).then(function(data) {
                         $scope.pictures = data.data;});

    });