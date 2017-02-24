angular.module('testApp')
.controller('NewsCtrl', function($scope, $http, $rootScope) {
  $scope.pic = {};
  $scope.file = {};
  $scope.pictures = [];
  $scope.comment = {};
  $scope.comments = [];

  $http.get('api/pictures/myNews', $scope.pictures).then(function(data) {
                     $scope.pictures = data.data;});


    $scope.addComment = function(id) {
        $http.post('api/pictures/comment', {
            content: $scope.comment[id].content,
            picture_id: id
        }).then(function(response){
                         alert("Comment sent :)");
                     });
                      $scope.comment = {};
                      };


    $scope.showComments = function(id){
    $http.get('api/pictures/' + id, $scope.comments).then(function(data) {
     $scope.comments = data.data;
     });
     };

     $scope.likePhoto = function(pics){
        $http.post('api/pictures/like/' + pics.id).then(function(data){
                              pics.pictureLikes.push(data.data);
                          },
                          function(response){
                          pics.pictureLikes.pop(response.data)
                          });


     };
});