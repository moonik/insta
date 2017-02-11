angular.module('testApp')
.controller('myGallery', function($scope, $http, $rootScope) {

    $scope.pictures = [];
      $http.get('api/pictures/my', $scope.pictures).then(function(data) {
                         $scope.pictures = data.data;});


                          $scope.delete = function(pic){
                                 $http.delete('api/pictures/delete/' + pic.id, $scope.pic).then(function(response){
                                var index = $scope.pictures.indexOf(pic);
                                $scope.pictures.splice(index, 1);
                                 })
                            };

                                $scope.showComments = function(id){
                                $http.get('api/pictures/' + id, $scope.comments).then(function(data) {
                                 $scope.comments = data.data;
                                 });
                                 };

    });