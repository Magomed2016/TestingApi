$(
    
   
    function () {
        var answerButton = $("button");
        //var storage = Storages.localStorage;
        var textArea = $("textarea");
        var isCheckedType = false;
       

        //"Main функция" 
        $(document).ready(function () {
            setHeaderText("Вопрос №" + storage.get("questionNum"));
            location.hash = "testing/" + storage.get("questionNum");

           

            if (textArea.length === 0) {
                isCheckedType = true;
            }
            
        

            answerButton.on("click", function () {

                var checked = $("input:checked");
            
                if (isCheckedType) {
                    if (checked.length!==  0) {
                        var questionType = $('#id').attr("quetype");
                        
                        if(questionType==="1"){
                                var questionId = storage.get("questionNum");
                                 storage.set("object",storage.get("object")+'{ "questionId":"'+questionId+'",');

                                var checked = $("input:checked");
                                var i;
                                for(i=0;i<checked.length;i++){
                                    var answerId = checked.eq(i).val();
                                    storage.set("object", storage.get("object")+'"answerId":"'+answerId+'",');
                                  //  console.log("this "+ answerId );
                                }
                            storage.set("object",storage.get("object").substring(0,(storage.get("object").length-1))) ;   
                        storage.set("object",storage.get("object")+'};');
                           
  
                            
                        }
                        
                        if(questionType==="0"){
                            var questionId = storage.get("questionNum");
                           var answerId = $("input:checked").val();

                                 storage.set("object",storage.get("object")+'{ "questionId":"'+questionId+'","answerId":"'+answerId+'"};');
                        }
                            
                        /*var question={
                            questionId: storage.get("questionNum"),
                            answerId: checked.val()
                        }*/
                        
                        sendAnswer();

                        
                       
                    }
                } 
                
                else {
                    if (textArea.val() !== "") {
                        

                       /* var question={
                            questionId: storage.get("questionNum"),
                            answer: textArea.val()
                        }
                        storage.set('question'+storage.get("questionNum"),JSON.stringify(question))
                       */ 
                        
                         var questionId = storage.get("questionNum");
                        
                        var answerId = $('#id').attr("value");
                       
                        var answer = textArea.val();
                        
                        storage.set("object",storage.get("object")+'{ "questionId":"'+questionId+'",'+'"answer":"'+answer+'",'+'"answerId":"'+answerId+'"};');
                       
                        
                       //  alert(storage.get("question"));
                        
                        /* var question={
                            questionId: storage.get("questionNum"),
                            answerId: checked.val()
                        }*/
            
                        sendAnswer();
                    }
                }
            });
        });

        /*
         * Отправляет ответ на сервер.
         *   Ничего не возвращает
         */
        function sendAnswer() {
            var num = storage.get("questionNum");
            //Отправку на сервер вставить сюда
            unBindAll();
            storage.set("questionNum", ++num);
            $(document).trigger("hashchange");
        }

        function unBindAll() {
            answerButton.unbind();
        }

    }
);
