var app = angular.module('testApp')
app.controller('showCommentsCtrl', function ($scope, $rootScope, $http, $window, close, picture, $interval) {

   $scope.comments = [];
   $scope.pictureId = picture;
   $scope.comment = {};
   $scope.picture = picture;
   $scope.data = [];
   $scope.newComments = [];

    $http.get('api/pictures/' + $scope.pictureId.id, $scope.comments).then(function(data){
        $scope.comments = data.data;
    });

//  var myVar = setInterval(function(){
//        $http.get('api/pictures/' + $scope.pictureId.id, $scope.comments).then(function(data) {
//                                        $scope.comments = data.data;
//                                        });
//     }, 1500);

    // function checks if there are new comments
   var updateData = setInterval(function(){
              if($scope.comments.length != 0){
                          $http.post('api/pictures/updateComments/' + $scope.pictureId.id, $scope.comments[$scope.comments.length-1]).then(function(data) {
                           $scope.newComments = data.data;
                           $scope.comments = $scope.comments.concat($scope.newComments);
                           });
                   }else
                      $http.get('api/pictures/' + $scope.pictureId.id, $scope.comments).then(function(data){
                           $scope.comments = data.data;
                       });
                   }, 1000);


    function myStopFunction() {
    clearInterval(updateData);
    };


     $http.get('api/pictures/getOne/' + $scope.pictureId.id).then(function(data) {
                                    $scope.picture = data.data;
                                    });

     $scope.addComment = function() {
            $http.post('api/pictures/comment', {
                content: $scope.comment.content,
                picture_id: $scope.pictureId.id
            }).then(function(response){
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
           myStopFunction();
           close(data, 500);
       }

});