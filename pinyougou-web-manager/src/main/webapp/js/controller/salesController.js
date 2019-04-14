// 定义控制器:
app.controller("salesController", function ($scope, $controller, $http, salesService) {
    // AngularJS中的继承:伪继承
    $controller('baseController', {$scope: $scope});

    /**
     * 查询7日销售数据
     * key string     value 数组
     日期            sevenDays[1,2,3,4,5,6,7]
     数据            sevenDataSales[100,343,4323,4352,234,67,564]
     */
    $scope.getSevenSales = function () {
        salesService.getSevenSales().success(function (response) {
            $scope.sevenDays = response.sevenDays;
            $scope.sevenDataSales = response.sevenDataSales;
        })
    }

});