angular.module('testApp').controller('UserProfileCtrl', function ($scope, $rootScope, $http, $window, $routeParams, ModalService) {


   $scope.pic = {};
   $scope.file = {};
   $scope.pictures = [];
   $scope.comment = {};
   $scope.comments = [];
   $scope.message = {};

    clearInterval($rootScope.updateData);

   $scope.username = $routeParams['username'];

              // gets users picture
             $http.get('api/pictures/profile/' + $scope.username, $scope.pictures).then(function(data) {
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


    $scope.showComments = function(picture){
                                 ModalService.showModal({
                                                  templateUrl: 'showCommentsModal.html',
                                                  controller: 'showCommentsCtrl',
                                                       inputs: {
                                                         picture: jQuery.extend({}, picture)
                                                       }
                                              }).then(function(modal) {
                                                  modal.element.modal();
                                                     modal.close.then(function (result) {
                                                     if (angular.isDefined(result)) {
                                                   $window.location.href = "#/userProfile/"+result;
                                                  }
                                                });
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



//     $scope.sendMessage = function() {
//            $http.post('api/messages/sendTo/'+ $scope.username, {
//                text_message: $scope.message.text_message,
//                receiver: $scope.username
//            }).then(function(response){
//                         });
//                          $scope.message = {};
//                          };


                            $scope.sendMessage = function(username){
                            ModalService.showModal({
                            templateUrl: 'sendMessageModal.html',
                            controller: 'sendMessageCtrl',
                            inputs: {
                            user: username
                              }
                            }).then(function(modal) {
                            modal.element.modal();
                            modal.close.then(function (result) {
                            if (angular.isDefined(result)) {
                            $window.location.href = "#/userProfile/"+result;
                            }
                              });
                             });
                                };



});







