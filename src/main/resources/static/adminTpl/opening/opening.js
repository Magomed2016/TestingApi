$(
    function () {
        var applyButton = $("button");
        var loginInput = $("#login");
        var passwordInput = $("#password");

        //"Main функция" 
        $(document).ready(function () {
            setHeaderText("Ввод данных");
            location.hash = "opening";

            applyButton.unbind();

            applyButton.on("click", function () {
                loginInput.trigger("keyup");
                passwordInput.trigger("keyup");
                if (loginInput.hasClass("is-valid") &&
                    passwordInput.hasClass("is-valid")) {
                    storage.set("login", loginInput.val());
                    storage.set("password", passwordInput.val());
                    unBindAll();
                    //location.href = "#testing";
                    $(document).trigger("hashchange");
                }
            });


            loginInput.keyup(function () {
                if ($(this).val() !== "") {
                    inputSetValid($(this));
                } else {
                    inputSetInvalid($(this));
                }
            });

            passwordInput.keyup(function () {
                if ($(this).val() !== "") {
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
            loginInput.unbind();
            passwordInput.unbind();
        }
    }
);
