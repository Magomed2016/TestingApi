var Router = {
    path: [],
    values: {},
    update: function (){
        var str = window.location.hash;
        str = str.substr(1, str.length);
        var path = "";
        var hasValues = true;
        var hasPath = true;
        var hash = [];
        
        if (str.indexOf("?") !== -1) {
            hash = str.split('?');
            if (hash[0].length === 0) {
                hasPath = false;
            }
        } else {
            hasValues = false;
            hash[0] = str;
        }
        
        if (hasPath) {
            path = hash[0].split("/").filter(function (str) {
                if (str.length !== 0) return true;
                else return false;
            });
            Router.path = path;
        }
        
        if (hasValues) {
            var valuesArr = hash[1].split("&");
            for (var i = 0; i < valuesArr.length; i++) {
                var entry = valuesArr[i].split("=");
                if (entry[0] !== "" && entry[1] !== "") {
                    Router.values[entry[0]] = entry[1];
                }
            }
        }
        //$("body").append(JSON.stringify(Router));
    }
};

$(function () {
    $(document).ready(function () {
        Router.update();
        $(document).on("hashchange", Router.update);
    });
    
    
});
