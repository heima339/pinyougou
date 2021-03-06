 //控制层 
app.controller('itemCatController' ,function($scope,$controller   ,itemCatService){	
	
	$controller('baseController',{$scope:$scope});//继承
	
    //读取列表数据绑定到表单中  
	$scope.findAll=function(){
		itemCatService.findAll().success(
			function(response){
				$scope.list=response;
			}			
		);
	}    
	
	//分页
	$scope.findPage=function(page,rows){			
		itemCatService.findPage(page,rows).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}
	
	//查询实体 
	$scope.findOne=function(id){				
		itemCatService.findOne(id).success(
			function(response){
				$scope.entity= response;					
			}
		);				
	}
	
	//保存 
	$scope.save=function(){				
		var serviceObject;//服务层对象  				
		if($scope.entity.id!=null){//如果有ID
			serviceObject=itemCatService.update( $scope.entity ); //修改  
		}else{
			$scope.entity.parentId=$scope.parentId;
			serviceObject=itemCatService.add( $scope.entity  );//增加 
		}				
		serviceObject.success(
			function(response){
				if(response.flag){
					//重新查询 
		        	//$scope.selectList();//重新加载
                    window.location.reload();
				}else{
					alert(response.message);
				}
			}		
		);				
	}
	
	 
	//批量删除 
	$scope.dele=function(){			
		//获取选中的复选框			
		itemCatService.dele( $scope.selectIds ).success(
			function(response){
				if(response.flag){
					//$scope.selectList();//刷新列表
                    window.location.reload();
					$scope.selectIds = [];
				}						
			}		
		);				
	}
	
	$scope.searchEntity={};//定义搜索对象 
	
	//搜索
	$scope.search=function(page,rows){			
		itemCatService.search(page,rows,$scope.searchEntity).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}

    //搜索
    $scope.searchEntity1={};//定义搜索对象
    $scope.search1=function(page,rows){
        itemCatService.search1(page,rows,$scope.searchEntity1).success(
            function(response){
                $scope.list=response.rows;
                $scope.paginationConf1.totalItems1=response.total;//更新总记录数
            }
        );
    }

    $scope.parentId = 0;
	// 根据父ID查询分类
	$scope.findByParentId =function(parentId){
        $scope.parentId = parentId;
		itemCatService.findByParentId(parentId).success(function(response){
			$scope.list=response;
		});
	}

    // 根据父ID查询分类  并且查询未审核的
    $scope.findByParentId1 =function(parentId){
        itemCatService.findByParentId1(parentId).success(function(response){
            $scope.list=response;
        });
    }
	
	// 定义一个变量记录当前是第几级分类
	$scope.grade = 1;
	
	$scope.setGrade = function(value){
		$scope.grade = value;
	}
	
	$scope.selectList = function(p_entity){
		
		if($scope.grade == 1){
			$scope.entity_1 = null;
			$scope.entity_2 = null;
		}
		if($scope.grade == 2){
			$scope.entity_1 = p_entity;
			$scope.entity_2 = null;
		}
		if($scope.grade == 3){
			$scope.entity_2 = p_entity;
		}
		
		$scope.findByParentId(p_entity.id);
	}

    $scope.selectList1 = function(p_entity){

        if($scope.grade == 1){
            $scope.entity_1 = null;
            $scope.entity_2 = null;
        }
        if($scope.grade == 2){
            $scope.entity_1 = p_entity;
            $scope.entity_2 = null;
        }
        if($scope.grade == 3){
            $scope.entity_2 = p_entity;
        }

        $scope.findByParentId1(p_entity.id);

    }


    // 显示状态
    $scope.status = ["未审核","审核通过","审核未通过","关闭"];

    // 审核的方法:
    $scope.updateStatus = function(status){
        itemCatService.updateStatus($scope.selectIds,status).success(function(response){
            if(response.flag){
                //$scope.selectList1();//刷新列表
                window.location.reload();
                $scope.selectIds = [];
            }else{
                alert(response.message);
            }
        });
    }
	
	
	
	
	
	
	
    
});	
