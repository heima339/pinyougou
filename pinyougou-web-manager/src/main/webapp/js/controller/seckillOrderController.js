// 定义控制器:
app.controller("seckillOrderController",function($scope,$controller,$http,seckillOrderService){
    // AngularJS中的继承:伪继承
    $controller('baseController',{$scope:$scope});

    // 查询所有的品牌列表的方法:
    $scope.findAll = function(){
        // 向后台发送请求:
        seckillOrderService.findAll().success(function(response){
            $scope.list = response;
        });
    }

    // 分页查询
    $scope.findPage = function(page,rows){
        // 向后台发送请求获取数据:
        seckillOrderService.findPage(page,rows).success(function(response){
            $scope.paginationConf.totalItems = response.total;
            $scope.list = response.rows;
        });
    }

    // 保存品牌的方法:
    $scope.save = function(){
        // 区分是保存还是修改
        var object;
        if($scope.entity.id != null){
            // 更新
            object = seckillOrderService.update($scope.entity);
        }else{
            // 保存
            object = seckillOrderService.add($scope.entity);
        }
        object.success(function(response){
            // {flag:true,message:xxx}
            // 判断保存是否成功:
            if(response.flag){
                // 保存成功
                alert(response.message);
                $scope.reloadList();
            }else{
                // 保存失败
                alert(response.message);
            }
        });
    }

    // 查询一个:
    $scope.findById = function(id){
        seckillOrderService.findOne(id).success(function(response){
            // {id:xx,name:yy,firstChar:zz}
            $scope.entity = response;
        });
    }

    // 删除品牌:
    $scope.dele = function(){
        seckillOrderService.dele($scope.selectIds).success(function(response){
            // 判断保存是否成功:
            if(response.flag==true){
                // 保存成功
                // alert(response.message);
                $scope.reloadList();
                $scope.selectIds = [];
            }else{
                // 保存失败
                alert(response.message);
            }
        });
    }

    $scope.searchEntity={};

    // 假设定义一个查询的实体：searchEntity
    $scope.search = function(page,rows){
        // 向后台发送请求获取数据:
        seckillOrderService.search(page,rows,$scope.searchEntity).success(function(response){
            $scope.paginationConf.totalItems = response.total;
            $scope.list =response.rows;

        });
    }
    $scope.status = ["未付款","已付款","未发货","已发货","交易成功","交易关闭","待评价"];



});