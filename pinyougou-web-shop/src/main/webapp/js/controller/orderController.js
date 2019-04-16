 //控制层 
app.controller('orderController',function($scope, $controller, orderService){
	
	$controller('baseController',{$scope:$scope});//继承

    //读取列表数据绑定到表单中  
	$scope.findAll=function(){
        orderService.findAll().success(
			function(response){
				$scope.list=response;
			}			
		);
	}


     /*   <option value="">全部</option>
        <option value="1">未付款</option>
        <option value="2">已付款</option>
        <option value="3">未发货</option>
        <option value="4">已发货</option>
        <option value="5">交易成功</option>
        <option value="6">交易关闭</option>
        <option value="7">待评价</option>*/



    $scope.searchEntity={};//定义搜索对象

    //搜索
    $scope.search=function(page,rows){
        orderService.search(page,rows,$scope.searchEntity).success(
            function(response){
                $scope.list=response.rows;
                $scope.paginationConf.totalItems=response.total;//更新总记录数
            }
        );
    }

    // 显示状态
    $scope.status = [null,"未付款","已付款","未发货","已发货","交易成功","交易关闭","待评价"];

  /*  // 审核的方法:
    $scope.updateStatus = function(status){
        goodsService.updateStatus($scope.selectIds,status).success(function(response){
            if(response.flag){
                $scope.reloadList();//刷新列表
                $scope.selectIds = [];
            }else{
                alert(response.message);
            }
        });
    }*/
    // 审核的方法:
    $scope.updateStatus = function(){
        orderService.updateStatus($scope.selectIds).success(function(response){
            if(response.flag){
                $scope.reloadList();//刷新列表
                $scope.selectIds = [];
            }else{
                alert(response.message);
            }
        });
    }
});	

