angular.module('testApp').controller('MainCtrl', function ($scope, $rootScope, $http, $window) {
    $scope.userForm = {};
    $rootScope.user = {};
    $scope.searchUser = {};
    $rootScope.searchText = '';
    $scope.following = {};
    $rootScope.showBttn = false; // if false don't show follow button
    $rootScope.getPictures = [];
    //var search = $routeParams['search'];

    clearInterval($rootScope.updateData);

    $rootScope.search = function(search)
    {
        if (angular.isDefined(search) && search !== "") {
            $http.get('api/users/search/' + search).then(function (response) {
                $scope.searchUser = response.data;
                $rootScope.showBttn = true;
            },function(response){
                $rootScope.showBttn = false;
                alert("User not found");
            });
        };
    };

    $scope.followUser = function(username){
        $http.post('api/users/follow/' + username).then(function(response){
            console.log('followed');
            $rootScope.showBttn = false;
        });
    };
});