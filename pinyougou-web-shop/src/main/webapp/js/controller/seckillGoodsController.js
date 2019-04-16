//控制层
app.controller('seckillGoodsController' ,function($scope,$controller,$location,typeTemplateService ,itemCatService,uploadService ,seckillGoodsService){

    $controller('baseController',{$scope:$scope});//继承

    //搜索
    $scope.search=function(page,rows){
        seckillGoodsService.search(page,rows,$scope.searchEntity).success(
            function(response){
                $scope.list=response.rows;
                $scope.paginationConf.totalItems=response.total;//更新总记录数
            }
        );
    }


    //保存
    $scope.save=function(){
        // 再添加之前，获得富文本编辑器中的内容。
        $scope.entity.goodsDesc.introduction=editor.html();




        var serviceObject;//服务层对象
        if($scope.entity.goods.id!=null){//如果有ID
            serviceObject=seckillGoodsService.update( $scope.entity ); //修改
        }else{
            serviceObject=seckillGoodsService.add( $scope.entity  );//增加
        }
        serviceObject.success(
            function(response){
                if(response.flag){
                    //重新查询
                    alert(response.message);
                    location.href="goods.html";
                }else{
                    alert(response.message);
                }
            }
        );
    }

    $scope.searchEntity={};//定义搜索对象

    $scope.status = ["审核未通过","审核通过"];
});