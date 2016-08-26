
var app = angular.module('correlationApp', [])
app.controller('CorrelationController', function($scope, $http) {

    $scope.submitForm = function() {
        $scope.loading = true;
        console.log("submit called correlation")
        console.log("value1::", $scope.column1)
        console.log("value2::", $scope.column2)
        console.log("operation::", $scope.correlation.operation)
        var url = "http://localhost:8085/correlation"
        var input = new Object();
        input.column1 = $scope.column1
        input.column2 = $scope.column2
        input.method = $scope.correlation.operation
        $http.post(url, JSON.stringify(input)).success(function (response) {
            $scope.loading = false;
            console.log("response::", response)
            var resultsUrl = "http://localhost:8085/correlation/result"
            window.location = resultsUrl
        })
    }
});
