var testProject = angular.module('testApp', ['ngRoute', 'angularModalService', 'angular.filter'])

testProject.config(function($routeProvider, $httpProvider){

    $routeProvider
    .when('/pictureUpload',{
        templateUrl: 'pictureUpload.html',
        controller: 'AddPicCtrl'
    })
    .when('/intro',{
        templateUrl: 'intro.html'
    })
    .when('/gallery',{
        templateUrl: 'gallery.html',
        controller: 'myGallery'
    })
    .when('/home',{
        templateUrl: 'home.html',
        controller: 'AddPicCtrl'
    })
    .when('/userProfile/:username',{
        templateUrl: 'userProfile.html',
        controller: 'UserProfileCtrl'
    })
    .when('/chatWith/:username',{
        templateUrl: 'myMessagesWithUser.html',
        controller: 'myMessagesCtrl'
    })
    .when('/conversations',{
        templateUrl: 'myConversations.html',
        controller: 'myConversationsCtrl'
    })
    .when('/news',{
        templateUrl: 'news.html',
        controller: 'NewsCtrl'
    })
    .when('/search',{
        templateUrl: 'search.html',
        controller: 'MainCtrl'
    })
    .otherwise({
        redirectTo: '/intro'
    });
    $httpProvider.interceptors.push('httpRequestInterceptor');
});

testProject.run(function ($rootScope, $location, ModalService, $http, $window) {
    $rootScope.me = {};
    $rootScope.isSignedIn = false;

    getUser();

    $rootScope.registration = function(){
        ModalService.showModal({
            templateUrl: 'registration.html',
            controller: 'RegistrationCtrl',
        }).then(function(modal) {
            modal.element.modal();
            modal.close.then(function (result) {
                getUser();
                //$window.location.href = "#/home"
            });
        });
    };

    $rootScope.login = function(){
        ModalService.showModal({
            templateUrl: 'enter.html',
            controller: 'LoginCtrl',
        }).then(function(modal) {
            modal.element.modal();
            modal.close.then(function (result) {
                getUser();
                //$window.location.href = "#/home"
            });
        });
    };

    $rootScope.logOut = function () {
        clearInterval($rootScope.updateData);
        $http.delete('api/users/goOffline/' + $rootScope.me.username).then(function(response){})
        $rootScope.isSignedIn = false;
        localStorage.removeItem('jwt');
        console.log('signed out');
    };

    function getUser() {
        if (localStorage.getItem('jwt') === null) {
            return;
        }
        $http.get('api/users/me').then(function (response) {
            $rootScope.me = response.data;
            $rootScope.isSignedIn = true;
        }, function (response) {
            localStorage.removeItem('jwt');
            $rootScope.isSignedIn = false;
        })
    }
});

testProject.factory('httpRequestInterceptor', function () {
    return {
        request: function (config) {
            var token = localStorage.getItem('jwt');
            if(token === null) {
                token = '';
            }
            config.headers['x-auth-token'] = token;
            return config;
        }
    };
});