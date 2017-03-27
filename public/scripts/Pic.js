angular.module('testApp')
.controller('AddPicCtrl', function($scope, $http, $rootScope, ModalService, $window) {
  $scope.pic = {};
  $scope.file = {};
  $scope.pictures = [];
  $scope.comment = {};
  $scope.comments = [];
  $scope.isUploadComplete = false;

    clearInterval($rootScope.updateData);
    // gets all picture but not yours
  $http.get('api/pictures/home', $scope.pictures).then(function(data) {
    $scope.pictures = data.data;});

   $scope.saveToMyAlbom = function(id)
   {
    $http.post('api/pictures/savePicture/' + id, $scope.picture).then(function(data){
        });
   };

   $scope.upload = function () {
    $scope.isUploadComplete = true;
    var fd = new FormData();
    fd.append('file', $scope.file);
    fd.append('name', $scope.pic.name);
    fd.append('owner',  $rootScope.getUser)
    $http.post('api/pictures/upload', fd, {
        transformRequest: angular.identity,
        headers: {'Content-Type': undefined}
        }).then(function(response){
            $scope.pic = {};
            $scope.isUploadComplete = false;
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




});