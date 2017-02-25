angular.module('testApp').controller('showCommentsCtrl', function ($scope, $rootScope, $http, $window, close, picture) {

   $scope.comments = [];
   $scope.pictureId = picture;
   $scope.comment = {};
   $scope.picture = picture;
   $http.get('api/pictures/' + $scope.pictureId.id, $scope.comments).then(function(data) {
                                 $scope.comments = data.data;
                                 });
     $http.get('api/pictures/getOne/' + $scope.pictureId.id).then(function(data) {
                                    $scope.picture = data.data;
                                    });

     $scope.addComment = function(id) {
            $http.post('api/pictures/comment', {
                content: $scope.comment.content,
                picture_id: $scope.pictureId.id
            }).then(function(response){
              $http.get('api/pictures/' + $scope.pictureId.id, $scope.comments).then(function(data) {
                                             $scope.comments = data.data;
                                             });
                         });
                          $scope.comment = {};
                          };

    $scope.closeAndGo = function(username){
       closeModal(username);
       };

     $scope.close = function () {
           closeModal(undefined);
       };


       function closeModal(data) {
           close(data, 500);
       }

});