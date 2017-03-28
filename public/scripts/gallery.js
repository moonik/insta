angular.module('testApp')
.controller('myGallery', function($scope, $http, $rootScope, ModalService, $window) {

    $scope.pictures = [];
    $scope.myFollowers = [];
    $scope.myFollowings = [];
    $scope.comments = [];

    clearInterval($rootScope.updateData);
       // gets my pictures
    $http.get('api/pictures/my', $scope.pictures).then(function(data) {
        $scope.pictures = data.data;
    });

      // gets my followers
    $http.get('api/users/myFollowers', $scope.myFollowers).then(function(data) {
        $scope.myFollowers = data.data;
    });

      // gets users that i'm following
    $http.get('api/users/iFollow', $scope.myFollowings).then(function(data) {
        $scope.myFollowings = data.data;
    });

    $scope.delete = function(pic){
        $http.delete('api/pictures/delete/' + pic.id, $scope.pic).then(function(response){
            var index = $scope.pictures.indexOf(pic);
            $scope.pictures.splice(index, 1);
        })
    };

    $scope.showComments = function(picture){
        ModalService.showModal({
            templateUrl: 'showCommentsModal.html',
            controller: 'showCommentsCtrl',
            inputs:{
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

    $rootScope.showFollowers = function(){
        ModalService.showModal({
            templateUrl: 'showFollowersModal.html',
            controller: 'showFollowersCtrl',
        })
        .then(function(modal) {
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