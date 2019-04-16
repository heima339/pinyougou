//控制层
app.controller('orderCountController' ,function($scope,$controller,$location,$http,typeTemplateService ,itemCatService,uploadService ,orderCountService){

    $controller('baseController',{$scope:$scope});//继承


    //搜索
    $scope.search=function(page,rows){
        orderCountService.search(page,rows,$scope.searchEntity).success(
            function(response){
                $scope.list=response.rows;
                $scope.paginationConf.totalItems=response.total;//更新总记录数
            }
        );
    }

    $scope.searchEntity={};//定义搜索对象

    //$scope.dateStatus=["日订单","周订单","月订单"];


});
