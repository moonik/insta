angular.module('testApp')
.controller('myGallery', function($scope, $http, $rootScope, ModalService, $window) {

    $scope.pictures = [];
    $scope.myFollowers = [];
    $scope.myFollowings = [];
      $http.get('api/pictures/my', $scope.pictures).then(function(data) {
                         $scope.pictures = data.data;});

        $http.get('api/users/myFollowers', $scope.myFollowers).then(function(data) {
                             $scope.myFollowers = data.data;
                             });

                             $http.get('api/users/iFollow', $scope.myFollowings).then(function(data) {
                                                          $scope.myFollowings = data.data;
                                                          });

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

                                 $rootScope.showFollowers = function(){
                                       ModalService.showModal({
                                                 templateUrl: 'showFollowersModal.html',
                                                 controller: 'showFollowersCtrl',
                                             }).then(function(modal) {
                                                 modal.element.modal();
                                                    modal.close.then(function (result) {
                                                    if (angular.isDefined(result)) {
                                                  $window.location.href = "#/userProfile/"+result;
                                                 }
                                               });
                                             });

                                 };

                                 $rootScope.showFollowings = function(){
                                                                        ModalService.showModal({
                                                                                  templateUrl: 'showFollowingsModal.html',
                                                                                  controller: 'showFollowingsCtrl',
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