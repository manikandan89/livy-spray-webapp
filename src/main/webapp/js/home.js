
var app = angular.module('homeApp', [])
app.controller('WelcomeController', function($scope, $http) {

    $scope.example = {
        operation: "kdensity",
    };
    console.log("value::", $scope.example.operation)


    $scope.submitForm = function() {
        console.log("submit called")
        console.log("new-value::", $scope.example.operation)
        var url = "http://localhost:8085/" + $scope.example.operation
        console.log("URL::::", url)
        window.location = url
    }
});