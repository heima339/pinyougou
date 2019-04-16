// 定义服务层:
app.service("orderCountService",function($http){

    this.search = function(page,rows,searchEntity){
        return $http.post("../countOrder/search.do?pageNo="+page+"&pageSize="+rows,searchEntity);
    }


});