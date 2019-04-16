//服务层
app.service('orderService',function($http){
	    	
	//读取列表数据绑定到表单中
	this.findAll=function(){
		return $http.get('../order/findAll.do');
	}
    this.search = function(page,rows,searchEntity){
        return $http.post("../order/search.do?pageNo="+page+"&pageSize="+rows,searchEntity);
    }



    this.updateStatus = function(ids){
        return $http.get('../order/updateStatus.do?ids='+ids);
    }
});
