
var app = angular.module('kdensityApp', [])
app.controller('KernelDensityController', function($scope, $http) {
    
    $scope.submitForm = function() {
        $scope.loading = true;
        console.log("submit called in kernel density")
        console.log("new-name::", $scope.name)
        console.log("new-points::", $scope.points)
        var url = "http://localhost:8085/kdensity"
        var input = new Object();
        input.column = $scope.name
        input.points = $scope.points
        $http.post(url, JSON.stringify(input)).success(function (response) {
            $scope.loading = false;
            console.log("response::", response)
            console.log("response::", response.message)
            var resultsUrl = "http://localhost:8085/kdensity/result"
            window.location = resultsUrl
        })
    }
});