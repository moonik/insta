var testProject = angular.module('testApp', ['ngRoute'])

testProject.config(function($routeProvider, $httpProvider){

$routeProvider.when('/registration', {
    templateUrl: 'registration.html',
    controller: 'regCtrl'

}).when('/pictureUpload',{

    templateUrl: 'pictureUpload.html',
    controller: 'AddPicCtrl'
    })

    .when('/gallery',{

        templateUrl: 'Gallery.html',
        controller: 'AddPicCtrl'
        })

          .when('/home',{

                templateUrl: 'home.html',
                controller: 'AddPicCtrl'
                })


                .when('/login',{

                                templateUrl: 'enter.html',
                                controller: 'MainCtrl'
                                });
                                $httpProvider.interceptors.push('httpRequestInterceptor');
})


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