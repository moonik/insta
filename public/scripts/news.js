angular.module('testApp')
.controller('NewsCtrl', function($scope, $http, $rootScope, ModalService, $window) {
  $scope.pic = {};
  $scope.file = {};
  $scope.pictures = [];
  $scope.comment = {};
  $scope.comments = [];

    clearInterval($rootScope.updateData);

// gets pictures of people that you are following
  $http.get('api/pictures/myNews', $scope.pictures).then(function(data) {
    $scope.pictures = data.data;
    });


    $scope.addComment = function(id) {
        $http.post('api/pictures/comment', {
            content: $scope.comment[id].content,
            picture_id: id
            }).then(function(response){
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
            },function(response){
                pics.pictureLikes.pop(response.data)
                });
        };
});