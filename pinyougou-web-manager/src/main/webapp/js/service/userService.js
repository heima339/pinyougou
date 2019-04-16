app.service("userService",function($http){
    this.countUser = function(){
        return $http.get("../user/countUser.do");
    }

});