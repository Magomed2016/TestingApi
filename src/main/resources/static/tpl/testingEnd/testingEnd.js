$(
    function () {
        var exitButton = $("button");
        //"Main функция" 
        $(document).ready(function () {
            setHeaderText("Конец теста");
            location.hash = "testingEnd";
            
                              storage.set("object",storage.get("object").substring(0,(storage.get("object").length-1))) ;   

            storage.set("object", storage.get("object")+']');
            
                       
            
            alert(storage.get("object"));
            var final={
                personId: storage.get("personId"),
                topicId: storage.get("topicId"),
                resolves: storage.get("object")
                
            }
                     
            
              var json = JSON.stringify(final);
               var request = new XMLHttpRequest();
                    request.open("POST", "/api/testing/end");
                    request.setRequestHeader('Content-type', 'application/json; charset=utf-8');
                    
                    request.send(json);
                    request.onreadystatechange = function () {
                            if (request.readyState == 4 && request.status == 200)
                                alert("Отправлено");
                                //storage.set("personId",request.responseText);
                    }
                    
                    ////очищаяем storage
                        

                          storage.set("object",'[');



            
             
           
            exitButton.on("click", function () {
                                    
                  setDefaultSessionParams();


                $(document).trigger("hashchange");
                exitButton.unbind();
            });
        });
    }
);
