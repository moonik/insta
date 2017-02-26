var app = angular.module('testApp')
app.controller('showCommentsCtrl', function ($scope, $rootScope, $http, $window, close, picture, $interval) {

   $scope.comments = [];
   $scope.pictureId = picture;
   $scope.comment = {};
   $scope.picture = picture;
   $scope.data = [];
   $scope.newComment = {};

    $http.get('api/pictures/' + $scope.pictureId.id, $scope.comments).then(function(data){
        $scope.comments = data.data;
    });

//  var myVar = setInterval(function(){
//        $http.get('api/pictures/' + $scope.pictureId.id, $scope.comments).then(function(data) {
//                                        $scope.comments = data.data;
//                                        });
//     }, 1500);

   var myVar = setInterval(function(){
        $http.post('api/pictures/updateComments/' + $scope.pictureId.id, $scope.comments[$scope.comments.length-1]).then(function(data) {
        $scope.newComment = data.data;
        $scope.comments.push($scope.newComment);
        console.log($scope.newComment);
        });
     }, 1500);


    function myStopFunction() {
    clearInterval(myVar);
    };


     $http.get('api/pictures/getOne/' + $scope.pictureId.id).then(function(data) {
                                    $scope.picture = data.data;
                                    });

     $scope.addComment = function() {
            $http.post('api/pictures/comment', {
                content: $scope.comment.content,
                picture_id: $scope.pictureId.id
            }).then(function(response){
//              $http.get('api/pictures/' + $scope.pictureId.id, $scope.comments).then(function(data) {
//                                             $scope.comments = data.data;
//                                             });
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