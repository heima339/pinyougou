app.service("itemCatService",function($http){
	this.findItemCat = function(categoryId){
		return $http.get("itemCat/findItemCat.do?");
		// ../ / 不写都行
	}
});