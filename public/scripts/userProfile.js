angular.module('testApp').controller('UserProfileCtrl', function ($scope, $rootScope, $http, $window, $routeParams, ModalService){
    $scope.pic = {};
    $scope.file = {};
    $scope.pictures = [];
    $scope.comment = {};
    $scope.comments = [];
    $scope.message = {};
    $scope.followers = [];
    $scope.followings = [];
    $rootScope.followButton = true;

    clearInterval($rootScope.updateData);

    $scope.username = $routeParams['username'];

    // gets users picture
    $http.get('api/pictures/profile/' + $scope.username, $scope.pictures).then(function(data) {
        $scope.pictures = data.data;
    });

    $http.get('api/users/profileFollowings/' + $scope.username, $scope.followings).then(function(data){
        $scope.followings = data.data;
    });

    $http.get('api/users/profileFollowers/' + $scope.username, $scope.followers).then(function(data){
        $scope.followers = data.data;
    })

    $http.get('api/users/check/' + $scope.username).then(function(response){
        $rootScope.followButton = true;
    },
    function(response){
        $rootScope.followButton = false;
    });

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
        },function(response){
            pics.pictureLikes.pop(response.data)
        });
    };

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

    $scope.followUser = function(username){
         $http.post('api/users/follow/' + username).then(function(response){
            $rootScope.followButton = !$rootScope.followButton;
        });

    };

});







