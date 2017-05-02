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

    // function checks if there are new comments
    $rootScope.updateData = setInterval(function(){
        if($scope.comments.length != 0){
            $http.post('api/pictures/updateComments/' + $scope.pictureId.id, $scope.comments[$scope.comments.length-1])
            .then(function(data) {
                $scope.newComments = data.data;
                $scope.comments = $scope.comments.concat($scope.newComments);
            });
        }else
            $http.get('api/pictures/' + $scope.pictureId.id, $scope.comments).then(function(data){
            $scope.comments = data.data;
            });
    }, 1000);

    function myStopFunction() {
        clearInterval($rootScope.updateData);
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

    $scope.delete = function(comment){
        $http.delete('api/pictures/deleteComment/' + comment.id, $scope.comment).then(function(response){
            var index = $scope.comments.indexOf(comment);
            $scope.comments.splice(index, 1);
        },
        function(response){
            $scope.message = response;
            alert($scope.message.data.message);
        })
    };;

    $scope.closeAndGo = function(username){
       closeModal(username);
    };

    $scope.close = function () {
        closeModal(undefined);
    };


    function closeModal(data) {
        myStopFunction();
        close(data, 500);
        $('body').removeClass('modal-open');
        $('.modal-backdrop').remove();
    }

});