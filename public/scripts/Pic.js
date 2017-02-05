angular.module('testApp')
.controller('AddPicCtrl', function($scope, $http, $rootScope) {
  $scope.pic = {};
  $scope.file = {};
  $scope.pictures = [];
  $scope.comment = {};

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

//    $scope.addComment = function() {
//        $http.post('api/account/saveComment', $scope.comment).then(function(response){
//                         alert("Comment sent :)");
//                     });
//                      $scope.comment = {};
//                      };

    $scope.delete = function(pic){
        $http.delete('api/pictures/delete/' + pic.id, $scope.pic).then(function(response){
       var index = $scope.pictures.indexOf(pic);
       $scope.pictures.splice(index, 1);
        })
   };



});