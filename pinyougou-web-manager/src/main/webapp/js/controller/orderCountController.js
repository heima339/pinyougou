// 定义控制器:
app.controller("orderCountController",function($scope,$controller,$http,orderCountService){
    // AngularJS中的继承:伪继承
    $controller('baseController',{$scope:$scope});


    $scope.searchEntity={};
    // 显示状态
    //$scope.status = ["未审核","审核通过","审核未通过","关闭"];

    // 假设定义一个查询的实体：searchEntity
    $scope.search = function(page,rows){
        // 向后台发送请求获取数据:
        orderCountService.search(page,rows,$scope.searchEntity).success(function(response){
            $scope.paginationConf.totalItems = response.total;
            $scope.list = response.rows;
        });
    }
});
