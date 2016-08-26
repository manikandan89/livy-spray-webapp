
var app = angular.module('correlationResultApp', [])
app.controller('CorrelationResultController', function($scope, $http) {

    console.log("get_results - correlation")

    var url = "http://localhost:8085/correlation/result"
    var input = new Object();
    $http.post(url, JSON.stringify(input)).success(function (response) {
        console.log("result in kdensity::", response)
        $scope.value = response.result

    })

});
