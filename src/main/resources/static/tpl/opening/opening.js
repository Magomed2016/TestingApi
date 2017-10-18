$(
    function () {
        var applyButton = $("button");
        var storage = Storages.localStorage;
        var firstNameInput = $("#firstName");
        var lastNameInput = $("#lastName");
        var emailInput = $("#email");
        //HTML5 regexp
        var reg = /^[a-zA-Z0-9.!#$%&'*+\/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$/;

        //"Main функция" 
        $(document).ready(function () {
            setHeaderText("Ввод данных");
            location.hash = "opening";

            applyButton.unbind();

            applyButton.on("click", function () {
                firstNameInput.trigger("keyup");
                lastNameInput.trigger("keyup");
                emailInput.trigger("keyup");
                if (firstNameInput.hasClass("is-valid") &&
                    lastNameInput.hasClass("is-valid") &&
                    emailInput.hasClass("is-valid")) {
                    storage.set("state", 1);
                    storage.set("firstName", firstNameInput.val());
                    storage.set("lastName", lastNameInput.val());
                    storage.set("email", emailInput.val());
                    unBindAll();
                    
                    var user = {
                        testPersonName: storage.get("firstName"),
                        testPersonSurname: storage.get("lastName"),
                        testPersonMail: storage.get("email")
                        
                    }
                    
                  var json = JSON.stringify(user);
//                    
//                    $.post("/api/user",user,function(XPathResult){
//                                alert(XPathResult);
//                         },'json');
                    
                    
                    
       //отправка на сервер
                    var request = new XMLHttpRequest();
                    request.open("POST", "/api/user");
                    request.setRequestHeader('Content-type', 'application/json; charset=utf-8');
                    
                    request.send(json);
                    request.onreadystatechange = function () {
                            if (request.readyState == 4 && request.status == 200)
                               // alert(request.responseText);
                                storage.set("personId",request.responseText);
                    }


                    
                    //location.href = "#testing";
                    $(document).trigger("hashchange");
                }
            });


            firstNameInput.keyup(function () {
                if ($(this).val() !== "") {
                    inputSetValid($(this));
                } else {
                    inputSetInvalid($(this));
                }
            });

            lastNameInput.keyup(function () {
                if ($(this).val() !== "") {
                    inputSetValid($(this));
                } else {
                    inputSetInvalid($(this));
                }
            });

            emailInput.keyup(function () {
                if (verifyEmail($(this).val())) {
                    inputSetValid($(this));
                } else {
                    inputSetInvalid($(this));
                }
            });
        });


        /*
         * Добавляет стиль is-valid и убирает is-invalid у jQuery объекта
         *  (object) или ([object]) input - jQuery объект или массив jQuery объектов
         *  Ничего не возвращает
         */
        function inputSetValid(input) {
            input.removeClass("is-invalid");
            input.addClass("is-valid");
        }

        /*
         * Добавляет стиль is-invalid и убирает is-valid у jQuery объекта
         *  (object) input - jQuery объект
         *  Ничего не возвращает
         */
        function inputSetInvalid(input) {
            input.removeClass("is-valid");
            input.addClass("is-invalid");
        }

        /*
         * Проверяет правильность ввода email 
         */
        function verifyEmail(email) {
            if (reg.test(email)) {
                return true;
            } else {
                return false;
            }
        }

        function unBindAll() {
            applyButton.unbind();
            emailInput.unbind();
            firstNameInput.unbind();
            lastNameInput.unbind();
        }
    }
);
