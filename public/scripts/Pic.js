angular.module('testApp')
.controller('AddPicCtrl', function($scope, $http, $rootScope) {
  $scope.pic = {};
  $scope.file = {};
  $scope.pictures = [];
  $scope.comment = {};
  $scope.comments = [];
  $scope.like = 0;

  $http.get('api/pictures/getAll', $scope.pictures).then(function(data) {
                     $scope.pictures = data.data;});


   $scope.upload = function () {
     var fd = new FormData();
     fd.append('file', $scope.file);
     fd.append('name', $scope.pic.name);
     fd.append('owner',  $rootScope.getUser)
     $http.post('api/pictures/upload', fd, {
     transformRequest: angular.identity,
     headers: {'Content-Type': undefined}
     }).then(function(response){
//       var voices = speechSynthesis.getVoices();
//         var utterance = new SpeechSynthesisUtterance("Фотография была успешно загружена");
//         utterance.voice = voices[1];
//         speechSynthesis.speak(utterance);
        $scope.pic = {};

     });
   };

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

     $scope.likePhoto = function(id){
        $http.post('api/pictures/like/', {
                 likeCounter: $scope.like,
                 picture_id: id
             }).then(function(response){
                              alert("Comment sent :)");
                          });


     }


});