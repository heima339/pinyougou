// 定义服务层:
app.service("salesService",function($http){
    /**
	 * 查询7日销售数据
     */
	this.getSevenSales = function () {
		return $http.get("../sales/getSevenSales.do");
    }
	this.findAll = function(){
		return $http.get("../sales/findAll.do");
	}

	this.findPage = function(page,rows){
		return $http.get("../sales/findPage.do?pageNum="+page+"&pageSize="+rows);
	}



	this.findOne=function(id){
		return $http.get("../sales/findOne.do?id="+id);
	}


	this.search = function(page,rows,searchEntity){
		return $http.post("../sales/search.do?pageNo="+page+"&pageSize="+rows,searchEntity);
	}

	this.selectOptionList = function(){
		return $http.get("../sales/selectOptionList.do");
	}

});